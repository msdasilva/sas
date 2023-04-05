package com.example.demo.servico;

import com.example.demo.exception.EnderecoNaoEncontradoException;
import com.example.demo.exception.TelefoneNaoEncontradoException;
import com.example.demo.exception.UnicidadeCpfException;
import com.example.demo.exception.UnicidadeTelefoneException;
import com.example.demo.modelo.Endereco;
import com.example.demo.modelo.Pessoa;
import com.example.demo.modelo.Telefone;

public interface PessoaService {

    Pessoa salvar(Pessoa pessoa) throws UnicidadeCpfException, UnicidadeTelefoneException;

    Pessoa buscarPorTelefone(Telefone telefone) throws TelefoneNaoEncontradoException;

    Pessoa buscarPorEndereco(Endereco endereco) throws EnderecoNaoEncontradoException;

}
