/*
 * Copyright (c) 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package com.honeacademy.petfinder.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;


import com.honeacademy.petfinder.R;


public class SearchFiltersAdapter extends RecyclerView.Adapter<SearchFiltersAdapter.ViewHolder> {
    private String[] mDataset={"german shepherd","rottweiler","labrador","husky"};

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder implements OnCheckedChangeListener {
        // each data item is just a string in this case
        public TextView mFilterLabel;
        public CheckBox mFilterCheckBox;

        public ViewHolder(View view) {
            super(view);

            mFilterLabel = (TextView) view.findViewById(R.id.filter_label);
            mFilterCheckBox = (CheckBox) view.findViewById(R.id.filter_checkbox);
        }

        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public SearchFiltersAdapter(String[] myDataset) {
       // mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public SearchFiltersAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                              int viewType) {
        // create a new view
        View root = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_filter_drawer, parent, false);

        ViewHolder vh = new ViewHolder(root);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.mFilterLabel.setText(mDataset[position]);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length;
    }
}



