package com.honeacademy.petfinder.fragment;

import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.honeacademy.petfinder.R;
import com.honeacademy.petfinder.adapter.PetAdapter;
import com.honeacademy.petfinder.binding.FragmentDataBindingComponent;
import com.honeacademy.petfinder.databinding.PetDetailFragmentBinding;
import com.honeacademy.petfinder.di.Injectable;
import com.honeacademy.petfinder.util.AutoClearedValue;
import com.honeacademy.petfinder.util.NavigationController;
import com.honeacademy.petfinder.viewmodel.PetViewModel;

import javax.inject.Inject;

/**
 * Created by jmaina on 8/13/17.
 */

public class PetDetailsFragment extends Fragment implements LifecycleRegistryOwner, Injectable {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private final LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);
    private static final String ANIMAL_ID = "animal_id";

    @Inject
    NavigationController navigationController;

    AutoClearedValue<PetDetailFragmentBinding> binding;
    AutoClearedValue<PetAdapter> adapter;
    android.databinding.DataBindingComponent dataBindingComponent = new FragmentDataBindingComponent(this);


    private PetViewModel petViewModel;
    @Inject
    ViewModelProvider.Factory viewModelFactory;


    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static PetDetailsFragment newInstance(Long animalId) {
        PetDetailsFragment fragment = new PetDetailsFragment();
        Bundle args = new Bundle();
        args.putLong(ANIMAL_ID, animalId);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        PetDetailFragmentBinding dataBinding = DataBindingUtil
                .inflate(inflater, R.layout.pet_detail_fragment, container, false);
        binding = new AutoClearedValue<>(this, dataBinding);
        return dataBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        petViewModel = ViewModelProviders.of(this, viewModelFactory).get(PetViewModel.class);
        Bundle args = getArguments();
        if (args != null && args.containsKey(ANIMAL_ID) ) {
            petViewModel.setId(args.getLong(ANIMAL_ID),null,null);
        }
        petViewModel.getPet().observe(this, pet -> {
            binding.get().setPet(pet);
            // this is only necessary because espresso cannot read data binding callbacks.
            binding.get().executePendingBindings();
        });
    }




    @Override
    public LifecycleRegistry getLifecycle() {
        return lifecycleRegistry;
    }

}
