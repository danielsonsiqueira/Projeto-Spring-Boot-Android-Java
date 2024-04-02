package com.example.dtest.retrofit;

import com.example.dtest.model.Pessoa;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PessoaApi {

    /** Aqui são feitas as solicitações para os endpoints da API Spring Boot.
     * */

    @GET("/pessoa/get-all")
    Call<List<Pessoa>> getAllPessoa();

    @POST("/pessoa/save")
    Call<Pessoa> save(@Body Pessoa pessoa);

    @PUT("/pessoa/update/{id}")
    Call<Pessoa> update(@Path("id") int id, @Body Pessoa pessoa);

    @DELETE("/pessoa/delete")
    Call<Void> delete(@Query("id") int id);

}
