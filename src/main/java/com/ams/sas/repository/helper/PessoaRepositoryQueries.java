package com.ams.sas.repository.helper;

import com.ams.sas.modelo.Pessoa;
import com.ams.sas.repository.filtro.PessoaFiltro;

import java.util.List;

public interface PessoaRepositoryQueries {

    List<Pessoa> filtrar(PessoaFiltro pessoaFiltro);
}
