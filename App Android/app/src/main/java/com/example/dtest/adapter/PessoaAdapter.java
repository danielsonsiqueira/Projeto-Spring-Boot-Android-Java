package com.example.dtest.adapter;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dtest.EntradaValoresActivity;
import com.example.dtest.model.Pessoa;
import com.example.dtest.retrofit.PessoaApi;
import com.example.dtest.retrofit.RetrofitService;

import com.example.dtest.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PessoaAdapter extends RecyclerView.Adapter<PessoaAdapter.PessoaHolder> {

    private List<Pessoa> pessoaList;
    private Pessoa pessoa = new Pessoa();

    public PessoaAdapter(List<Pessoa> lista) {
        this.pessoaList = lista;
    }
    public class PessoaHolder extends RecyclerView.ViewHolder {
        TextView nome, local, tipo;
        Button btnExcluir;
        public PessoaHolder(@NonNull View itemView) {
            super(itemView);

            nome = itemView.findViewById(R.id.pessoaListItemNome);
            local = itemView.findViewById(R.id.pessoaListItemLocal);
            tipo = itemView.findViewById(R.id.pessoaListItemTipo);
            btnExcluir = itemView.findViewById(R.id.btnExcluir);

            /** Squi é feita a solicitação de exclusão com Retrofit.
             * */

            RetrofitService retrofitService = new RetrofitService();
            PessoaApi pessoaApi = retrofitService.getRetrofit().create(PessoaApi.class);

            btnExcluir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(v.getContext());
                    alertDialog.setTitle("Confirmar Exclusão");
                    alertDialog.setMessage("Tem certeza que deseja excluir esse Id?");
                    alertDialog.setPositiveButton("Excluir", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            try {
                                pessoaApi.delete(pessoa.getId()).enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {
                                        if (response.isSuccessful()) {
                                            removerPessoaAdapter(pessoa);
                                            Toast.makeText(v.getContext(), "Pessoa excluída com sucesso!", Toast.LENGTH_SHORT).show();
                                        } else {
                                            //Teste de erros
                                            int code = response.code();
                                            String message = response.message();
                                            if (code == 404) {
                                                Toast.makeText(v.getContext(), "Pessoa não encontrada!", Toast.LENGTH_SHORT).show();
                                            } else if (code == 500) {
                                                Toast.makeText(v.getContext(), "Erro no servidor!", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(v.getContext(), "Erro ao excluir a pessoa!", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Void> call, Throwable t) {
                                        Toast.makeText(v.getContext(), "Erro ao excluir a pessoa!", Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }catch(Exception e){
                                String erro = "Ocorreu o seguinte erro: ";
                                Toast.makeText(v.getContext(), erro + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

                    alertDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(v.getContext(), "Cancelado!", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();

                        }
                    });

                    alertDialog.create().show();

                }
            });

            //
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), EntradaValoresActivity.class);
                    intent.putExtra("pessoaId", pessoaList.get(getAdapterPosition()));
                    v.getContext().startActivity(intent);
                }
            });

        }
    }

    @NonNull
    @Override
    public PessoaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_pessoa_item, parent, false);

        return new PessoaHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PessoaHolder holder, int position) {

        pessoa = pessoaList.get(position);
        holder.nome.setText(pessoa.getNome());
        holder.local.setText(pessoa.getLocal());
        holder.tipo.setText(pessoa.getTipo());

    }
    private void removerPessoaAdapter(Pessoa pessoa){

        int posicao = pessoaList.indexOf(pessoa);
        pessoaList.remove(posicao);
        notifyItemRemoved(posicao);

    }

    @Override
    public int getItemCount() {
        return pessoaList.size();
    }

}
