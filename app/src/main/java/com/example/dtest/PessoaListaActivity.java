package com.example.dtest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.dtest.adapter.PessoaAdapter;
import com.example.dtest.model.Pessoa;
import com.example.dtest.retrofit.PessoaApi;
import com.example.dtest.retrofit.RetrofitService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PessoaListaActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pessoa_lista);

        recyclerView = findViewById(R.id.employeeRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        FloatingActionButton fabAddPessoa = findViewById(R.id.fabAddPessoa);
        fabAddPessoa.setOnClickListener(view ->{
            Intent intent = new Intent(this, EntradaValoresActivity.class);
            startActivity(intent);
        });

        loadPessoas();

    }

    @Override
    protected void onResume() {
        super.onResume();
        loadPessoas();
    }

    /** Obter lista com Retrofit
     * */

    private void loadPessoas() {
        RetrofitService retrofitService = new RetrofitService();
        PessoaApi employeeApi = retrofitService.getRetrofit().create(PessoaApi.class);
        employeeApi.getAllPessoa()
                .enqueue(new Callback<List<Pessoa>>() {
                    @Override
                    public void onResponse(Call<List<Pessoa>> call, Response<List<Pessoa>> response) {
                        populateListView(response.body());
                    }

                    @Override
                    public void onFailure(Call<List<Pessoa>> call, Throwable t) {
                        Toast.makeText(PessoaListaActivity.this, "Erro!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void populateListView(List<Pessoa> pessoaList) {
        PessoaAdapter pessoaAdapter = new PessoaAdapter(pessoaList);
        recyclerView.setAdapter(pessoaAdapter);
    }
}