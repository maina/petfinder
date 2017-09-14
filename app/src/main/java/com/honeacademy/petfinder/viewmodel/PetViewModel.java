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

package com.honeacademy.petfinder.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.os.AsyncTask;
import android.support.annotation.VisibleForTesting;

import com.honeacademy.petfinder.dao.PetDao;
import com.honeacademy.petfinder.model.Pet;
import com.honeacademy.petfinder.model.PetDTO;
import com.honeacademy.petfinder.repository.PetRepository;
import com.honeacademy.petfinder.util.AbsentLiveData;
import com.honeacademy.petfinder.util.Objects;
import com.honeacademy.petfinder.util.Resource;

import java.util.List;

import javax.inject.Inject;

public class PetViewModel extends ViewModel {
    @VisibleForTesting
    private final LiveData<Resource<List<PetDTO>>> pets;
    @VisibleForTesting
    final MutableLiveData<PetSearch> petSearch;

    private final PetDao petDao;

    private final LiveData<PetDTO> pet;


    @SuppressWarnings("unchecked")
    @Inject
    public PetViewModel(PetRepository petRepository, PetDao petDao) {
        this.petDao = petDao;
        this.petSearch = new MutableLiveData<>();

        pets = Transformations.switchMap(petSearch, input -> {
            if (input.isEmpty()) {
                return AbsentLiveData.create();
            } else {
                return petRepository.loadPets(input.animal, input.location);
            }
        });
        pet = Transformations.switchMap(petSearch, input -> {
            if (input == null) {
                return AbsentLiveData.create();
            } else {
                return petRepository.loadPet(input.id);
            }
        });
    }


    public LiveData<PetDTO> getPet() {
        return pet;
    }

    public LiveData<Resource<List<PetDTO>>> getPets() {
        return pets;
    }


    public void setId(Long id, String animal, String location) {
        PetSearch update = new PetSearch(animal, location, id);
        if (Objects.equals(petSearch.getValue(), update)) {
            return;
        }
        petSearch.setValue(update);
    }

    public void retry() {
        PetSearch current = petSearch.getValue();
        if (current != null && !current.isEmpty()) {
            petSearch.setValue(current);
        }
    }

    @VisibleForTesting
    static class PetSearch {
        public final Long id;
        public final String animal;
        public final String location;

        PetSearch(String animal, String location, Long id) {
            this.animal = animal == null ? null : animal.trim();
            this.location = location == null ? null : location.trim();
            this.id = id;
        }

        boolean isEmpty() {
            return animal == null || location == null || animal.length() == 0 || location.length() == 0;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            PetSearch petSearch = (PetSearch) o;

            if (animal != null ? !animal.equals(petSearch.animal) : petSearch.animal != null) {
                return false;
            }
            return location != null ? location.equals(petSearch.location) : petSearch.location == null;
        }

        @Override
        public int hashCode() {
            int result = animal != null ? animal.hashCode() : 0;
            result = 31 * result + (location != null ? location.hashCode() : 0);
            return result;
        }
    }
}
