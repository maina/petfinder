/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.honeacademy.petfinder.repository;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;


import com.honeacademy.petfinder.AppExecutors;
import com.honeacademy.petfinder.api.ApiResponse;
import com.honeacademy.petfinder.api.PetFinderService;
import com.honeacademy.petfinder.dao.ContactDao;
import com.honeacademy.petfinder.dao.ImageDao;
import com.honeacademy.petfinder.dao.PetDao;
import com.honeacademy.petfinder.db.PetsDb;
import com.honeacademy.petfinder.model.Contact;
import com.honeacademy.petfinder.model.Image;
import com.honeacademy.petfinder.model.Pet;
import com.honeacademy.petfinder.model.PetDTO;
import com.honeacademy.petfinder.model.webservice.PetContactType;
import com.honeacademy.petfinder.model.webservice.PetPhotoType;
import com.honeacademy.petfinder.model.webservice.Petfinder;
import com.honeacademy.petfinder.model.webservice.PetfinderPetRecord;
import com.honeacademy.petfinder.util.RateLimiter;
import com.honeacademy.petfinder.util.Resource;
import com.honeacademy.petfinder.util.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Petsitory that handles Pet objects.
 */
@Singleton
public class PetRepository {
    private final PetDao petDao;
    @Inject
    public PetsDb petsDb;
    private final ImageDao imageDao;
    private final ContactDao contactDao;
    private final PetFinderService petFinderService;
    private final AppExecutors appExecutors;
    private RateLimiter<String> petListRateLimit = new RateLimiter<>(10, TimeUnit.MINUTES);

    @Inject
    PetRepository(AppExecutors appExecutors, PetDao petDao, ImageDao imageDao, ContactDao contactDao, PetFinderService petFinderService) {
        this.petDao = petDao;
        this.imageDao = imageDao;
        this.contactDao = contactDao;
        this.petFinderService = petFinderService;
        this.appExecutors = appExecutors;
    }

    public LiveData<PetDTO> loadPet(Long id) {
        LiveData<PetDTO> pet = petDao.getPetById(id);
        return pet;
    }
    public LiveData<List<Image>> loadPetImages(Long id) {
        LiveData<List<Image>> images = imageDao.getImagesByPetId(id);
        return images;
    }

    public LiveData<Resource<List<PetDTO>>> loadPets(String animal, String location) {
        return new NetworkBoundResource<List<PetDTO>, Petfinder>(appExecutors) {


            @Override
            protected boolean shouldFetch(@Nullable List<PetDTO> data) {
                return data == null || data.isEmpty();
            }

            @NonNull
            @Override
            protected LiveData<List<PetDTO>> loadFromDb() {
                LiveData<List<PetDTO>> pets = petDao.loadPetsByType(animal.toLowerCase());
                return pets;
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<Petfinder>> createCall() {
                LiveData<ApiResponse<Petfinder>> response = petFinderService.findPets(animal, location);

                response.getValue();
                return response;
            }

            @Override
            protected void onFetchFailed() {
                petListRateLimit.reset(animal.concat(location));
            }

            @Override
            protected void saveCallResult(@NonNull Petfinder item) {
                saveApiResponse(item);

            }

            @Override
            protected Petfinder processResponse(ApiResponse<Petfinder> response) {
                Petfinder body = response.body;
                return body;
            }


        }.asLiveData();
    }

    /**
     * process the api response and save pet,images to the db
     *
     * @param petfinder
     */
    public void saveApiResponse(Petfinder petfinder) {
        try {
            List<PetfinderPetRecord> pets = petfinder.getPets();
            petsDb.beginTransaction();
            for (PetfinderPetRecord record : pets) {
                Pet pet = new Pet();
                pet.setLocation(record.getContact().getAddress1());
                pet.setName(record.getName());
                pet.setType(record.getAnimal());
                pet.setAge(record.getAge());
                pet.setBreed(Utils.listToString(record.getBreeds(), ","));
                pet.setMix(record.getMix());
                pet.setPetId(record.getShelterPetId());
                pet.setSex(record.getSex().equalsIgnoreCase("f") ? "Female" : "Male");
                pet.setShelterId(record.getShelterId());
                pet.setSize(record.getSize().equalsIgnoreCase("m") ||record.getSize().equalsIgnoreCase("pnt") ? "Medium" : record.getSize().equalsIgnoreCase("s") ? "Small" : "Large");
                pet.setStatus(record.getStatus());
                pet.setDescription(record.getDescription() == null ? "" : record.getDescription().trim());
                long id = petDao.insert(pet);

                List<PetPhotoType> photos = record.getMedia().getPhotos() != null ? record.getMedia().getPhotos().getPhotos() : null;
                if (photos != null) {
                    List<Image> images = new ArrayList<>();
                    //save the images in a list to batch insert
                    for (PetPhotoType photo : photos) {
                        Image image = new Image();
                        image.setSize(photo.getSize());
                        image.setPhotoId(photo.getId());
                        image.setUrl(photo.getUrl());
                        image.setPetId(id);
                        images.add(image);
                    }
                    imageDao.insertImages(images);
                }
                PetContactType petContactType = record.getContact();
                Contact contact = new Contact();
                contact.setAddress1(petContactType.getAddress1() == null ? "" : petContactType.getAddress1());
                contact.setAddress2(petContactType.getAddress2() == null ? "" : petContactType.getAddress2());
                contact.setCity(petContactType.getCity() == null ? "" : petContactType.getCity());
                contact.setEmail(petContactType.getEmail() == null ? "" : petContactType.getEmail());
                contact.setFax(petContactType.getFax() == null ? "" : petContactType.getFax());
                contact.setState(petContactType.getState() == null ? "" : petContactType.getState());
                contact.setZip(petContactType.getZip() == null ? "" : petContactType.getZip());
                contact.setPhone(petContactType.getPhone() == null ? "" : petContactType.getPhone());
                contact.setPetId(id);
                contactDao.insert(contact);


            }
            petsDb.setTransactionSuccessful();
        } finally {
            petsDb.endTransaction();
        }
    }
}
