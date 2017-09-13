package com.honeacademy.petfinder.api;

import android.arch.lifecycle.LiveData;

import com.honeacademy.petfinder.model.webservice.Petfinder;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by jmaina on 8/11/17.
 */

public interface PetFinderService {
    @GET("pet.find")
    LiveData<ApiResponse<Petfinder>> findPets(@Query("animal") String animal, @Query("location") String location);

}
