package com.ams.sas.repository;

import com.ams.sas.modelo.Pessoa;
import com.ams.sas.repository.helper.PessoaRepositoryQueries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PessoaRepository extends JpaRepository<Pessoa, Long>, PessoaRepositoryQueries {

    Pessoa save(Pessoa pessoa);

    Optional<Pessoa> findByCpf(String cpf);

    @Query(value = "SELECT pessoa FROM Pessoa pessoa JOIN pessoa.telefoneList telefone JOIN pessoa.enderecoList endereco WHERE telefone.ddd =:ddd AND telefone.numero =:numero")
    Optional<Pessoa> findByTelfoneDddAndTelefoneNumero(@Param("ddd") String ddd, @Param("numero") String numero);

    @Query(value = "SELECT pessoa FROM Pessoa pessoa JOIN pessoa.enderecoList endereco WHERE endereco.logradouro =:logradouro AND endereco.numero =:numero")
    Optional<Pessoa> findByLogradouroAndNumero(@Param("logradouro") String logradouro, @Param("numero") String numero);

}
