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

package com.honeacademy.petfinder.adapter;

import com.honeacademy.petfinder.R;
import com.honeacademy.petfinder.databinding.PetItemBinding;
import com.honeacademy.petfinder.model.PetDTO;
import com.honeacademy.petfinder.util.DataBoundListAdapter;
import com.honeacademy.petfinder.util.Objects;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.ViewGroup;

public class PetAdapter
        extends DataBoundListAdapter<PetDTO, PetItemBinding> {

    private final DataBindingComponent dataBindingComponent;
    private final PetClickCallback callback;

    public PetAdapter(DataBindingComponent dataBindingComponent,
                      PetClickCallback callback) {
        this.dataBindingComponent = dataBindingComponent;
        this.callback = callback;
    }

    @Override
    protected PetItemBinding createBinding(ViewGroup parent) {
        PetItemBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.pet_item, parent, false,
                        dataBindingComponent);
        binding.petImage.setOnClickListener(v -> {
            PetDTO pet = binding.getPet();
            if (pet != null && callback != null) {
                callback.onClick(pet);
            }
        });
        return binding;
    }

    @Override
    protected void bind(PetItemBinding binding, PetDTO item) {
        binding.setPet(item);
    }

    @Override
    protected boolean areItemsTheSame(PetDTO oldItem, PetDTO newItem) {
        return Objects.equals(oldItem.getId(), newItem.getId());
    }

    @Override
    protected boolean areContentsTheSame(PetDTO oldItem, PetDTO newItem) {
        return Objects.equals(oldItem.getId(), newItem.getId());
    }

    public interface PetClickCallback {
        void onClick(PetDTO pet);
    }
}
