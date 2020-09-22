package com.example.hiltexample.ui.home;

import androidx.databinding.Bindable;
import androidx.hilt.Assisted;
import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.example.hiltexample.model.Items;
import com.example.hiltexample.model.Result;
import com.example.hiltexample.model.network.ApiService;

import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Inject;

import dagger.hilt.android.scopes.ActivityRetainedScoped;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@ActivityRetainedScoped
public class HomeViewModel extends ViewModel {

    @Inject
    ApiService apiService;

    private MutableLiveData<List<Items>> items;

    private MutableLiveData<String> query;

    private MutableLiveData<Boolean> isLoading;

    @ViewModelInject
    public HomeViewModel(@Assisted SavedStateHandle savedStateHandle, ApiService apiService) {
        items = new MutableLiveData<>();
        query = new MutableLiveData<>();
        isLoading = new MutableLiveData<>();
        this.apiService = apiService;
    }


    public void getSearchUser() {

        apiService.getUserInfo(this.query.getValue(), "", "").enqueue(new Callback<Result>() {
            @Override
            public void onResponse(@Nullable Call<Result> call, @Nullable Response<Result> response) {
                isLoading.setValue(false);
                if (response != null && response.body() != null){
                    items.setValue(response.body().getItems());
                }
            }

            @Override
            public void onFailure(@Nullable Call<Result> call, @Nullable Throwable t) {
                isLoading.setValue(false);
                System.out.println("실패  " + t.getMessage());
            }
        });
    }

    public MutableLiveData<List<Items>> getItems() {
        return items;
    }

    public MutableLiveData<String> getQuery() {
        return query;
    }

    public MutableLiveData<Boolean> getIsLoading() {
        return isLoading;
    }
}

