package com.example.springbootexemplo;

import com.example.springbootexemplo.model.Pessoa;
import com.example.springbootexemplo.repository.PessoaDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class PraticarApplicationTests {

	//se eu quiser testar apenas 1 comando deixar apenas comente o @test
	@Autowired
	private PessoaDAO pessoaDAO;

	//@Test
	void addPessoaTest() {

		Pessoa pessoa = new Pessoa();
		pessoa.setNome("Dan");
		pessoa.setTipo("Dev");
		pessoa.setLocal("Quipas");
		pessoaDAO.save(pessoa);

	}

	//@Test
	void getAllPessoas(){
		List<Pessoa> pessoas = pessoaDAO.getAllPessoas();
		System.out.println(pessoas);
	}

	//@Test
	void getAllPessoasAndDeleteAll(){
		List<Pessoa> pessoas = pessoaDAO.getAllPessoas();
		for(Pessoa pessoa: pessoas){
			pessoaDAO.delete(pessoa);
		}
	}

}
