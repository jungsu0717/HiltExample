package com.example.hiltexample.model.network;


import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Created by jsyoon on 2020/09/17.
 * description : OkHttp Interceptor 구현
 */
public class NetworkInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        //todo : Auth key, Header, 암호화 로직 등 Custom 하여 사용할 때 작업 필요
        return null;
    }
}
