package com.honeacademy.petfinder.di;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.honeacademy.petfinder.viewmodel.PetFinderViewModelFactory;
import com.honeacademy.petfinder.viewmodel.PetViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(PetViewModel.class)
    abstract ViewModel bindUserViewModel(PetViewModel userViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(PetFinderViewModelFactory factory);
}
