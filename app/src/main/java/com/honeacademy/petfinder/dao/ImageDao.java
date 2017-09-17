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

import com.honeacademy.petfinder.model.Image;

import java.util.List;

/**
 * Interface for database access on Image related operations.
 */
@Dao
public abstract class ImageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract long insert(Image image);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract List<Long> insertImages(List<Image> images);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract long createImageIfNotExists(Image images);

    //Notice that the load method returns a LiveData<User>. Room knows when the database is modified and it will automatically notify
    // all active observers when the data changes.
    // Because it is using LiveData, this will be efficient because it will update the data only if there is at least one active observer.
    @Query("SELECT * FROM images WHERE pet_id = :petId and (size !='t' and size !='pnt' and size !='fpm')")
    public abstract LiveData<List<Image>> getImagesByPetId(long petId);

    @Query("SELECT * FROM images WHERE pet_id = :petId ")
    public abstract List<Image> getPetImages(long petId);


    @Query("SELECT * FROM images WHERE id in (:imageIds)")
    protected abstract LiveData<List<Image>> loadById(List<Integer> imageIds);

}
