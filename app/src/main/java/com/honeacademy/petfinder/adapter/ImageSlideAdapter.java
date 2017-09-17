package com.honeacademy.petfinder.adapter;

import android.content.Context;
import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.honeacademy.petfinder.R;
import com.honeacademy.petfinder.databinding.SlidingImageBinding;
import com.honeacademy.petfinder.model.Image;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jmaina on 9/14/17.
 */

public class ImageSlideAdapter extends PagerAdapter {


    private List<?> images = new ArrayList<>();
    private LayoutInflater inflater;
    private Context context;
    private final DataBindingComponent dataBindingComponent;

    public ImageSlideAdapter(DataBindingComponent dataBindingComponent, Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.dataBindingComponent = dataBindingComponent;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        SlidingImageBinding binding = DataBindingUtil.inflate(inflater, R.layout.sliding_image, null, false, dataBindingComponent);
        Image image = (Image) getImages().get(position);
        binding.setSlidingImage(image);
        view.addView(binding.getRoot());

        return binding.getRoot();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

    /**
     * This way, when you call notifyDataSetChanged(),
     * the view pager will remove all views and reload them all. As so the reload effect is obtained.
     *
     * @param object
     * @return
     */
    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    public List<?> getImages() {
        return images;
    }

    public void setImages(List images) {
        this.images = images;
    }
}
