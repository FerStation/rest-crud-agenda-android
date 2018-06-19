package com.example.etec.agendaapk;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by Fernando on 07/08/17.
 */

public interface ContatoService {

    @GET("contatos/")
    Call<List<Contato>> listContatos();

    @GET("contato/{id}")
    Call<Contato> carregarContato(@Path("id") int contatoID);

    @POST("contato")
    Call<Contato> salvarContato(@Body Contato contato);

    @PUT("contato/{id}")
    Call<Contato> alterarContato(@Path("id") int id, @Body Contato contato);

    @DELETE("contato/{id}")
    Call<Void> excluirContato(@Path("id") int id);

}
