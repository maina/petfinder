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

package com.honeacademy.petfinder.dao;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.util.SparseIntArray;


import com.honeacademy.petfinder.model.Pet;
import com.honeacademy.petfinder.model.PetDTO;

import java.util.Collections;
import java.util.List;

/**
 * Interface for database access on Pet related operations.
 */
@Dao
public abstract class PetDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract long insert(Pet pet);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract List<Long> insertPets(List<Pet> pets);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract long createPetIfNotExists(Pet pets);

    //Notice that the load method returns a LiveData<User>. Room knows when the database is modified and it will automatically notify
    // all active observers when the data changes.
    // Because it is using LiveData, this will be efficient because it will update the data only if there is at least one active observer.
    @Query("SELECT * FROM pets WHERE location = :location AND name = :animalType")
    public abstract LiveData<List<Pet>> getPets(String location, String animalType);

    @Query("SELECT p.*,i.* FROM pets p left join images i on i.pet_id=p.id WHERE p.id = :id and i.size !='t' ")
    public abstract LiveData<PetDTO> getPetById(Long id);


    @Query("SELECT * FROM pets "
            + "WHERE location = :location "
            + "ORDER BY name DESC")
    public abstract LiveData<List<Pet>> loadPets(String location);

    @Query("SELECT p.*,i.url as imageUrl,c.* from pets p left join images i on p.id=i.pet_id left join contacts c on c.pet_id_contact=p.id where i.size='x' and lower(type) = :type LIMIT 25")
    public abstract LiveData<List<PetDTO>> loadPetsByType(String type);

    @Query("SELECT * from pets where lower(type) = :type LIMIT 25")
    public abstract List<PetDTO> loadPetsType(String type);




    public LiveData<List<Pet>> loadOrdered(List<Integer> petIds) {
        SparseIntArray order = new SparseIntArray();
        int index = 0;
        for (Integer petId : petIds) {
            order.put(petId, index++);
        }
        return Transformations.map(loadById(petIds), pets -> {
            Collections.sort(pets, (r1, r2) -> {
                int pos1 = order.get((int) r1.id);
                int pos2 = order.get((int) r2.id);
                return pos1 - pos2;
            });
            return pets;
        });
    }

    @Query("SELECT * FROM pets WHERE id in (:petIds)")
    protected abstract LiveData<List<Pet>> loadById(List<Integer> petIds);

}
