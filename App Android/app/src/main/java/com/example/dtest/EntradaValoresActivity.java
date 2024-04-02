package com.example.dtest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dtest.model.Pessoa;
import com.example.dtest.retrofit.PessoaApi;
import com.example.dtest.retrofit.RetrofitService;

import java.util.logging.Level;
import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EntradaValoresActivity extends AppCompatActivity {

    private Button btnEnviar;
    private EditText etName;
    private EditText etLocal;
    private EditText etTipo;
    private TextView txtTitulo;

    Pessoa pessoaAtual = new Pessoa();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrada_valores);

        inicializeComponents();

        pessoaAtual = (Pessoa) getIntent().getSerializableExtra("pessoaId");
        if(pessoaAtual != null){
            String editar = "Editar Valores";
            txtTitulo.setText(editar);
            etName.setText(pessoaAtual.getNome());
            etLocal.setText(String.valueOf(pessoaAtual.getLocal()));
            etTipo.setText(String.valueOf(pessoaAtual.getTipo()));

        }

    }

    private void inicializeComponents() {
        btnEnviar = findViewById(R.id.btnEnviar);
        etName = findViewById(R.id.etNome);
        etLocal = findViewById(R.id.etLocal);
        etTipo = findViewById(R.id.etTipo);
        txtTitulo = findViewById(R.id.txtTitulo);

        btnEnviar.setOnClickListener(view -> {

            Pessoa pessoa = new Pessoa();

            pessoa.setNome(etName.getText().toString());
            pessoa.setLocal(etLocal.getText().toString());
            pessoa.setTipo(etTipo.getText().toString());

            if (pessoaAtual != null) {
                pessoa.setId(pessoaAtual.getId());
                editar(pessoa);
            } else {
                salvar(pessoa);
            }
        });

    }

    /** Métodos salvar() e editar() com Retrofit.
     */

    private void salvar(Pessoa pessoa){

        RetrofitService retrofitService = new RetrofitService();
        PessoaApi pessoaApi = retrofitService.getRetrofit().create(PessoaApi.class);

        try {
            pessoaApi.save(pessoa)
                    .enqueue(new Callback<Pessoa>() {
                        @Override
                        public void onResponse(Call<Pessoa> call, Response<Pessoa> response) {
                            Toast.makeText(EntradaValoresActivity.this, "Salvou!", Toast.LENGTH_SHORT).show();
                            finish();
                        }

                        @Override
                        public void onFailure(Call<Pessoa> call, Throwable t) {
                            String erro = "Ocorreu o erro: ";
                            Toast.makeText(EntradaValoresActivity.this, erro + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

        }catch(Exception e){
            String erro = "Ocorreu o seguinte erro: ";
            Toast.makeText(this, erro + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    private void editar(Pessoa pessoa) {

        RetrofitService retrofitService = new RetrofitService();
        PessoaApi pessoaApi = retrofitService.getRetrofit().create(PessoaApi.class);

        int id = pessoa.getId();
        Call<Pessoa> pessoaCall = pessoaApi.update(id, pessoa);

        try {
            pessoaCall.enqueue(new Callback<Pessoa>() {
                @Override
                public void onResponse(Call<Pessoa> call, Response<Pessoa> response) {
                    Toast.makeText(EntradaValoresActivity.this, "Editou!", Toast.LENGTH_SHORT).show();
                    finish();
                }

                @Override
                public void onFailure(Call<Pessoa> call, Throwable t) {
                    String erro = "Ocorreu o erro: ";
                    Toast.makeText(EntradaValoresActivity.this, erro + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
            String erro = "Ocorreu o seguinte erro: ";
            Toast.makeText(this, erro + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

}