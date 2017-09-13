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

import com.honeacademy.petfinder.fragment.BarnYardsFragment;
import com.honeacademy.petfinder.fragment.CatsFragment;
import com.honeacademy.petfinder.fragment.DogsFragment;
import com.honeacademy.petfinder.fragment.HorsesFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract BarnYardsFragment contributeBarnYardsFragment();

    @ContributesAndroidInjector
    abstract CatsFragment contributeCatsFragment();

    @ContributesAndroidInjector
    abstract DogsFragment contributeDogsFragment();

    @ContributesAndroidInjector
    abstract HorsesFragment contributeHorsesFragment();
}
