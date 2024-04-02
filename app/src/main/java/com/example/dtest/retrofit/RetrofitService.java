package com.example.dtest.retrofit;

import com.google.gson.Gson;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class RetrofitService {

    /** Iniciando a configuração e conexão HTTP com Retrofit.
     * Configurar a solicitação de acordo com suas informações de IP.
     * */
    private Retrofit retrofit;
    private String solicitar = "http://192.168.0.101:8080"; //exemplo de rede local

    public RetrofitService(){
        initializeRetrofit();
    }

    private void initializeRetrofit() {

        retrofit = new Retrofit.Builder()
                .baseUrl(solicitar)
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }
}
