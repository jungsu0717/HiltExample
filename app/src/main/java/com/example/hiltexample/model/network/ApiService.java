package com.example.hiltexample.model.network;

import com.example.hiltexample.model.Result;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {


    /**
     * Github 유저 정보 검색
     */
    @GET("/search/users")
    Call<Result> getUserInfo(@Query("q") String q,              // 검색 키워드
                             @Query("sort") String sort,        // sort 정렬
                             @Query("order") String order);     // order 정렬

}
