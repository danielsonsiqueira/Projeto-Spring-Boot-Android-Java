package com.example.springbootexemplo.repository;

import com.example.springbootexemplo.model.Pessoa;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PessoaDAO {

    /** Aqui estão os métodos que irão interagir com o banco de dados
     * relacionados a entidade modelo Pessoa através do repositório.
     * */
    @Autowired
    private PessoaRepository pessoaRepository;
    @Autowired
    private EntityManager entityManager;

    //salvar
    public Pessoa save(Pessoa pessoa){
        return pessoaRepository.save(pessoa);
    }

    public List<Pessoa> getAllPessoas(){
        List<Pessoa> pessoas = new ArrayList<>();
        Streamable.of(pessoaRepository.findAll()).
                forEach(pessoa ->{
                    pessoas.add(pessoa);
                });
        return pessoas;
    }

    //deletar
    public void delete(Pessoa pessoa){
        pessoaRepository.deleteById(pessoa.getId());
    }

    //atualizar
    @Transactional
    public Pessoa atualizarPessoa(Pessoa pessoaAtualizada) {
        if (pessoaAtualizada == null) {
            throw new IllegalArgumentException("A pessoa atualizada não pode ser nula.");
        }

        int pessoaId = pessoaAtualizada.getId();
        Optional<Pessoa> optionalPessoaExistente = pessoaRepository.findById(pessoaId);
        Pessoa pessoaExistente = optionalPessoaExistente.orElseThrow(() ->
                new EntityNotFoundException("Pessoa com ID " + pessoaId + " não encontrada."));

        pessoaExistente.setNome(pessoaAtualizada.getNome());
        pessoaExistente.setLocal(pessoaAtualizada.getLocal());
        pessoaExistente.setTipo(pessoaAtualizada.getTipo());

        return pessoaRepository.save(pessoaExistente);
    }

}
