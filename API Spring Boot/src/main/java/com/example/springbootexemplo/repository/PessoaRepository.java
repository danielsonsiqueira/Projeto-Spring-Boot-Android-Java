package com.example.springbootexemplo.repository;

import com.example.springbootexemplo.model.Pessoa;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/** Inicialização do repositório.
 * */
@Repository
public interface PessoaRepository extends CrudRepository<Pessoa, Integer> {
}
