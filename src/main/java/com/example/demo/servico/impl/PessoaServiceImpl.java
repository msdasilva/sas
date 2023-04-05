package com.example.demo.servico.impl;

import com.example.demo.exception.EnderecoNaoEncontradoException;
import com.example.demo.exception.TelefoneNaoEncontradoException;
import com.example.demo.exception.UnicidadeCpfException;
import com.example.demo.exception.UnicidadeTelefoneException;
import com.example.demo.modelo.Endereco;
import com.example.demo.modelo.Pessoa;
import com.example.demo.modelo.Telefone;
import com.example.demo.repository.PessoaRepository;
import com.example.demo.servico.PessoaService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PessoaServiceImpl implements PessoaService {

    private final PessoaRepository pessoaRepository;

    public PessoaServiceImpl(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    @Override
    public Pessoa salvar(Pessoa pessoa) throws UnicidadeCpfException, UnicidadeTelefoneException {

        Optional<Pessoa> optionalPessoa = this.pessoaRepository.findByCpf(pessoa.getCpf());
        if(optionalPessoa.isPresent())
            throw new UnicidadeCpfException();

        for(Telefone telefone : pessoa.getTelefoneList()) {
            optionalPessoa = this.pessoaRepository.findByTelfoneDddAndTelefoneNumero(telefone.getDdd(), telefone.getNumero());
            if(optionalPessoa.isPresent())
                throw new UnicidadeTelefoneException();
        }

        return this.pessoaRepository.save(pessoa);
    }

    @Override
    public Pessoa buscarPorTelefone(Telefone telefone) throws TelefoneNaoEncontradoException {
        return this.pessoaRepository.findByTelfoneDddAndTelefoneNumero(telefone.getDdd(), telefone.getNumero()).orElseThrow(TelefoneNaoEncontradoException::new);
    }

    @Override
    public Pessoa buscarPorEndereco(Endereco endereco) throws EnderecoNaoEncontradoException {
        return this.pessoaRepository.findByLogradouroAndNumero(endereco.getLogradouro(), endereco.getNumero()).orElseThrow(EnderecoNaoEncontradoException::new);
    }
}
