package com.example.hiltexample.ui.home;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hiltexample.R;
import com.example.hiltexample.databinding.ItemHomeBinding;
import com.example.hiltexample.model.Items;

import javax.inject.Inject;

import dagger.hilt.android.qualifiers.ActivityContext;
import dagger.hilt.android.scopes.ActivityScoped;

/**
 * Created by jsyoon on 2020/09/18.
 * description :
 */
@ActivityScoped
public class HomeAdapter extends ListAdapter<Items, HomeAdapter.ItemViewHolder> {

    private LayoutInflater layoutInflater;

    private Context context;

    @Inject
    public HomeAdapter(@ActivityContext Context context) {
        super(DIFF_CALLBACK);
        this.context = context;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        return new ItemViewHolder(
                DataBindingUtil.inflate(
                        layoutInflater,
                        R.layout.item_home,
                        parent,
                        false
                )
        , context);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Items item = getItem(position);
        if (item != null) {
            holder.bindTo(item);
        }
    }

    /**
     * Item View Holder
     */
    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        private ItemHomeBinding binding;

        private Context context;

        public ItemViewHolder(@NonNull ItemHomeBinding binding, Context context) {
            super(binding.getRoot());
            this.binding = binding;
            this.context = context;
        }

        void bindTo(Items item) {
            binding.setItem(item);
            binding.getRoot().setOnClickListener(v ->
                    context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(item.getHtml_url())))
            );
        }
    }

    /**
     * Time Diff
     */
    static DiffUtil.ItemCallback<Items> DIFF_CALLBACK = new DiffUtil.ItemCallback<Items>() {
        @Override
        public boolean areItemsTheSame(@NonNull Items oldItem, @NonNull Items newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public boolean areContentsTheSame(@NonNull Items oldItem, @NonNull Items newItem) {
            return oldItem.equals(newItem);
        }
    };

}
