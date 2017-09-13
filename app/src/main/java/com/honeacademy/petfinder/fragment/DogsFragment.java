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
import com.honeacademy.petfinder.databinding.DogFragmentBinding;
import com.honeacademy.petfinder.di.Injectable;
import com.honeacademy.petfinder.model.PetDTO;
import com.honeacademy.petfinder.util.AutoClearedValue;
import com.honeacademy.petfinder.util.NavigationController;
import com.honeacademy.petfinder.viewmodel.PetViewModel;

import java.util.Collections;

import javax.inject.Inject;

/**
 * Created by jmaina on 8/13/17.
 */

public class DogsFragment extends Fragment implements LifecycleRegistryOwner, Injectable, PetAdapter.PetClickCallback {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private final LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);
    private static final String ANIMAL = "animal";
    private static final String LOCATION = "location";

    @Inject
    NavigationController navigationController;

    AutoClearedValue<DogFragmentBinding> binding;
    AutoClearedValue<PetAdapter> adapter;
    android.databinding.DataBindingComponent dataBindingComponent = new FragmentDataBindingComponent(this);


    private PetViewModel petViewModel;
    @Inject
    ViewModelProvider.Factory viewModelFactory;


    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static DogsFragment newInstance(String location, String animal) {
        DogsFragment fragment = new DogsFragment();
        Bundle args = new Bundle();
        args.putString(LOCATION, location);
        args.putString(ANIMAL, animal);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        DogFragmentBinding dataBinding = DataBindingUtil
                .inflate(inflater, R.layout.dog_fragment, container, false);
        dataBinding.setRetryCallback(() -> petViewModel.retry());
        binding = new AutoClearedValue<>(this, dataBinding);
        return dataBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        petViewModel = ViewModelProviders.of(this, viewModelFactory).get(PetViewModel.class);
        Bundle args = getArguments();
        if (args != null && args.containsKey(ANIMAL) &&
                args.containsKey(LOCATION)) {
            petViewModel.setId(args.getString(ANIMAL),
                    args.getString(LOCATION));
        } else {
            petViewModel.setId(null, null);
        }

        PetAdapter adapter = new PetAdapter(dataBindingComponent,
                this);
        this.adapter = new AutoClearedValue<>(this, adapter);
        // binding.get().petsList.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.get().petsList.setAdapter(adapter);

        initContributorList(petViewModel);
    }


    private void initContributorList(PetViewModel viewModel) {
        viewModel.getPets().observe(this, listResource -> {
            // we don't need any null checks here for the adapter since LiveData guarantees that
            // it won't call us if fragment is stopped or not started.
            if (listResource != null && listResource.data != null) {
                adapter.get().replace(listResource.data);

            } else {
                //noinspection ConstantConditions
                adapter.get().replace(Collections.emptyList());
            }
        });
    }


    @Override
    public LifecycleRegistry getLifecycle() {
        return lifecycleRegistry;
    }

    @Override
    public void onClick(PetDTO pet) {
        navigationController.navigateToPetProfile(pet.getId());
    }
}
