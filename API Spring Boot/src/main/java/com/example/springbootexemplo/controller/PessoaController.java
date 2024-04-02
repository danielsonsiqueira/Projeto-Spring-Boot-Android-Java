package com.example.springbootexemplo.controller;

import com.example.springbootexemplo.model.Pessoa;
import com.example.springbootexemplo.repository.PessoaDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PessoaController {

    /** endpoints que fazem relação com o Aplicativo android
     * */

    @Autowired
    private PessoaDAO pessoaDAO;

    @GetMapping("/pessoa/get-all")
    public List<Pessoa> getAllPessoas() {
        return pessoaDAO.getAllPessoas();
    }

    @PostMapping("/pessoa/save")
    public Pessoa save(@RequestBody Pessoa pessoa) {
        return pessoaDAO.save(pessoa);
    }

    @DeleteMapping("/pessoa/delete")
    public void delete(Pessoa pessoa) {
        pessoaDAO.delete(pessoa);
    }

    @PutMapping("/pessoa/update/{id}")
    public ResponseEntity<Pessoa> update(@PathVariable("id") int id, @RequestBody Pessoa pessoa) {
        Pessoa pessoaAtualizada = pessoaDAO.atualizarPessoa(pessoa);

        if (pessoaAtualizada != null) {
            return ResponseEntity.ok().body(pessoaAtualizada);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
