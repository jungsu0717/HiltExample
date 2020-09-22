package com.example.hiltexample.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.hiltexample.R;
import com.example.hiltexample.databinding.FragmentHomeBinding;
import com.example.hiltexample.ui.gallery.GalleryViewModel;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import dagger.hilt.android.scopes.FragmentScoped;

@AndroidEntryPoint
public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    private FragmentHomeBinding binding;

    @Inject
    HomeAdapter homeAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // ViewModel 주입
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        // 바인딩 설정
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        binding.setLifecycleOwner(this);
        binding.setViewModel(homeViewModel);

        // searchView 설정
        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // 유저 검색
                homeViewModel.getQuery().setValue(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        // Search Query 감지
        homeViewModel.getQuery().observe(getViewLifecycleOwner(), query -> homeViewModel.getSearchUser());

        // Loading 상태 감지
        homeViewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading -> binding.swipeRefresh.setRefreshing(isLoading));

        // Swipe Refresh 새로고침
        binding.swipeRefresh.setOnRefreshListener(() -> homeViewModel.getSearchUser());

        // Adapter 설정
        binding.rvSearchList.setAdapter(homeAdapter);

        return binding.getRoot();
    }
}