package com.example.hiltexample.util;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.hiltexample.R;
import com.example.hiltexample.model.Items;
import com.example.hiltexample.ui.home.HomeAdapter;

import java.util.List;

/**
 * Created by jsyoon on 2020/09/21.
 * description :
 */
public class BindingUtils {

    @BindingAdapter(value = {"homeAdapter"}, requireAll = false)
    public static void addHomeAdapter(RecyclerView recyclerView, List<Items> items){
        System.out.println("debug    items    "   + items);
        HomeAdapter homeAdapter = (HomeAdapter) recyclerView.getAdapter();
        if (homeAdapter != null){
            homeAdapter.submitList(items);
        }
    }



    /**
     * Url 이미지를 변환해준다.
     *
     * @param imageView
     * @param imageUrl
     */
    @BindingAdapter(value = {"imageUrl", "corner"}, requireAll = false)
    public static void loadImage(ImageView imageView, String imageUrl, float corner) {


        RequestOptions options = new RequestOptions();
        options.placeholder(R.drawable.ic_launcher_foreground);
        options.error(R.drawable.ic_launcher_foreground);

        if (imageUrl != null) {
            try {

                Glide.with(imageView.getContext())
                        .load(imageUrl)
                        .apply(options)
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {

                                String error = "Error : ";
                                if (e != null) {
                                    error += e.getMessage();
                                }
                                error += "     Image Url : " + imageUrl;

                                Toast.makeText(imageView.getContext(), error, Toast.LENGTH_SHORT).show();
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                return false;
                            }
                        })
                        .into(imageView);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
