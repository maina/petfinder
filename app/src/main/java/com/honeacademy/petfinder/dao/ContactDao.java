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
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.honeacademy.petfinder.model.Contact;

import java.util.List;

/**
 * Interface for database access on Contact related operations.
 */
@Dao
public abstract class ContactDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract long insert(Contact contact);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract List<Long> insertContacts(List<Contact> contacts);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract long createContactIfNotExists(Contact contacts);

    //Notice that the load method returns a LiveData<User>. Room knows when the database is modified and it will automatically notify
    // all active observers when the data changes.
    // Because it is using LiveData, this will be efficient because it will update the data only if there is at least one active observer.
    @Query("SELECT * FROM contacts WHERE pet_id_contact = :petId")
    public abstract LiveData<List<Contact>> getContactsByPetId(long petId);

    @Query("SELECT * FROM contacts WHERE pet_id_contact = :petId")
    public abstract List<Contact> getPetContacts(long petId);


    @Query("SELECT * FROM contacts WHERE contact_id in (:contactIds)")
    protected abstract LiveData<List<Contact>> loadById(List<Integer> contactIds);

}
