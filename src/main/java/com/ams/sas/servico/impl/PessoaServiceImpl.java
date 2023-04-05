package com.ams.sas.servico.impl;

import com.ams.sas.exception.EnderecoNaoEncontradoException;
import com.ams.sas.exception.TelefoneNaoEncontradoException;
import com.ams.sas.exception.UnicidadeCpfException;
import com.ams.sas.exception.UnicidadeTelefoneException;
import com.ams.sas.modelo.Pessoa;
import com.ams.sas.servico.PessoaService;
import com.ams.sas.modelo.Endereco;
import com.ams.sas.modelo.Telefone;
import com.ams.sas.repository.PessoaRepository;
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
