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

package com.honeacademy.petfinder.di;

import android.app.Application;
import android.arch.persistence.room.Room;


import com.honeacademy.petfinder.BuildConfig;
import com.honeacademy.petfinder.api.PetFinderService;
import com.honeacademy.petfinder.dao.ContactDao;
import com.honeacademy.petfinder.dao.ImageDao;
import com.honeacademy.petfinder.dao.PetDao;
import com.honeacademy.petfinder.db.PetsDb;
import com.honeacademy.petfinder.util.LiveDataCallAdapterFactory;
import com.squareup.moshi.FromJson;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.ToJson;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

@Module(includes = ViewModelModule.class)
class AppModule {
    final String API_KEY = BuildConfig.API_KEY ;

    @Singleton
    @Provides
    PetFinderService providePetFinderService() {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.API_URL)
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                .client(getClient())
                .build()
                .create(PetFinderService.class);


    }

    Object CustomDateAdapter = new Object() {
        final DateFormat dateFormat;

        {
            dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        }

        @ToJson
        synchronized String dateToJson(Date d) {
            return dateFormat.format(d);
        }

        @FromJson
        synchronized Date dateToJson(String s) throws ParseException {
            return dateFormat.parse(s);
        }
    };


    @Singleton
    @Provides
    PetsDb provideDb(Application app) {
        return Room.databaseBuilder(app, PetsDb.class, "pets.db").build();
    }

    @Singleton
    @Provides
    Moshi provideMoshi() {
        Moshi moshi = new Moshi.Builder().add(CustomDateAdapter).build();
        return moshi;
    }

    @Singleton
    @Provides
    PetDao provideUserDao(PetsDb db) {
        return db.petDao();
    }

    @Singleton
    @Provides
    ImageDao provideImageDao(PetsDb db) {
        return db.imageDao();
    }

    @Singleton
    @Provides
    ContactDao provideContactDao(PetsDb db) {
        return db.contactDao();
    }


    private OkHttpClient getClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {

                        Request request = chain.request();
                        HttpUrl url = request.url()
                                .newBuilder()
                                .addQueryParameter("key", API_KEY)
                                .addQueryParameter("format", "xml")
                                .build();
                        request = request.newBuilder().url(url).build();

                        Response response = chain.proceed(request);

                        return response;
                    }
                }).build();
        return client;
    }

}
