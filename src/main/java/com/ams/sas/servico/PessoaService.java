package com.ams.sas.servico;

import com.ams.sas.exception.EnderecoNaoEncontradoException;
import com.ams.sas.exception.TelefoneNaoEncontradoException;
import com.ams.sas.modelo.Pessoa;
import com.ams.sas.exception.UnicidadeCpfException;
import com.ams.sas.exception.UnicidadeTelefoneException;
import com.ams.sas.modelo.Endereco;
import com.ams.sas.modelo.Telefone;

public interface PessoaService {

    Pessoa salvar(Pessoa pessoa) throws UnicidadeCpfException, UnicidadeTelefoneException;

    Pessoa buscarPorTelefone(Telefone telefone) throws TelefoneNaoEncontradoException;

    Pessoa buscarPorEndereco(Endereco endereco) throws EnderecoNaoEncontradoException;

}
