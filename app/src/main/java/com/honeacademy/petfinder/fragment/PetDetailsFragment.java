package com.honeacademy.petfinder.fragment;

import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.honeacademy.petfinder.R;
import com.honeacademy.petfinder.adapter.ImageSlideAdapter;
import com.honeacademy.petfinder.adapter.PetAdapter;
import com.honeacademy.petfinder.binding.FragmentDataBindingComponent;
import com.honeacademy.petfinder.databinding.PetDetailFragmentBinding;
import com.honeacademy.petfinder.di.Injectable;
import com.honeacademy.petfinder.util.AutoClearedValue;
import com.honeacademy.petfinder.util.NavigationController;
import com.honeacademy.petfinder.viewmodel.PetViewModel;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;

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
    AutoClearedValue<ImageSlideAdapter> adapter;
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
        if (args != null && args.containsKey(ANIMAL_ID)) {
            petViewModel.setId(args.getLong(ANIMAL_ID), null, null);
        }
        petViewModel.getPet().observe(this, pet -> {
            binding.get().setPet(pet);
            // this is only necessary because espresso cannot read data binding callbacks.

            binding.get().executePendingBindings();
        });

        petViewModel.getImages().observe(this, images -> {
            // this is only necessary because espresso cannot read data binding callbacks.
            if (!images.isEmpty()) {
                adapter.get().setImages(images);
                adapter.get().notifyDataSetChanged();
                NUM_PAGES = images.size();

            } else {
                //noinspection ConstantConditions
                adapter.get().setImages(Collections.emptyList());
            }
            binding.get().executePendingBindings();
        });

        ImageSlideAdapter adapter = new ImageSlideAdapter(dataBindingComponent, getActivity());

        this.adapter = new AutoClearedValue<>(this, adapter);
        // binding.get().petsList.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.get().pager.setAdapter(adapter);
        initPager();
    }


    @Override
    public LifecycleRegistry getLifecycle() {
        return lifecycleRegistry;
    }

    int currentPage = 0;
    int NUM_PAGES = 0;

    private void initPager() {

        CirclePageIndicator indicator = binding.get().indicator;

        indicator.setViewPager(binding.get().pager);

        final float density = getResources().getDisplayMetrics().density;

//Set circle indicator radius
        indicator.setRadius(5 * density);


        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                if(binding.get()!=null) {
                    binding.get().pager.setCurrentItem(currentPage++, true);
                }
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 3000, 3000);

        // Pager listener over indicator
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                currentPage = position;

            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int pos) {

            }
        });

    }
}
