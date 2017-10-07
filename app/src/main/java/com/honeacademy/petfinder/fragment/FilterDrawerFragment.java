package com.honeacademy.petfinder.fragment;

import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.honeacademy.petfinder.R;
import com.honeacademy.petfinder.di.Injectable;

/**
 * Created by jmaina on 8/13/17.
 */

public class FilterDrawerFragment extends Fragment implements LifecycleRegistryOwner, Injectable {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    private final LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static FilterDrawerFragment newInstance(int sectionNumber) {
        FilterDrawerFragment fragment = new FilterDrawerFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.schedule_filter_drawer, container, false);
        return rootView;
    }

    @Override
    public LifecycleRegistry getLifecycle() {
        return lifecycleRegistry;
    }
}
