package com.example.demo.repository.helper;

import com.example.demo.modelo.Pessoa;
import com.example.demo.repository.filtro.PessoaFiltro;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class PessoaRepositoryImpl implements PessoaRepositoryQueries {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Pessoa> filtrar(PessoaFiltro pessoaFiltro) {

        final StringBuilder stringBuilder = new StringBuilder();
        final Map<String, Object> parameters = new HashMap<>();

        stringBuilder.append("SELECT pessoa ");
        stringBuilder.append("FROM Pessoa pessoa ");
        stringBuilder.append("JOIN pessoa.telefoneList telefone ");
        stringBuilder.append("JOIN pessoa.enderecoList endereco ");
        stringBuilder.append("WHERE 1=1");

        preencherNomeCasoNecessario(pessoaFiltro, stringBuilder, parameters);
        preencherCpfCasoNecessario(pessoaFiltro, stringBuilder, parameters);
        preencherDddCasoNecessario(pessoaFiltro, stringBuilder, parameters);
        preencherNumeroTelefoneCasoNecessario(pessoaFiltro, stringBuilder, parameters);

        Query query = entityManager.createQuery(stringBuilder.toString(), Pessoa.class);
        preencherQueryCasoNecessario(parameters, query);

        return query.getResultList();
    }

    private void preencherQueryCasoNecessario(Map<String, Object> parameters, Query query) {
        for(Map.Entry<String, Object> param : parameters.entrySet()) {
            query.setParameter(param.getKey(), param.getValue());
        }
    }

    private void preencherCpfCasoNecessario(PessoaFiltro pessoaFiltro, StringBuilder stringBuilder, Map<String, Object> parameters) {
        if(StringUtils.hasText(pessoaFiltro.getCpf())) {
            stringBuilder.append(" AND pessoa.cpf LIKE :cpf ");
            parameters.put("cpf", "%" + pessoaFiltro.getCpf() + "%");
        }
    }

    private void preencherNomeCasoNecessario(PessoaFiltro pessoaFiltro, StringBuilder stringBuilder, Map<String, Object> parameters) {
        if(StringUtils.hasText(pessoaFiltro.getNome())) {
            stringBuilder.append(" AND pessoa.nome LIKE :nome ");
            parameters.put("nome", "%" + pessoaFiltro.getNome() + "%");
        }
    }

    private void preencherDddCasoNecessario(PessoaFiltro pessoaFiltro, StringBuilder stringBuilder, Map<String, Object> parameters) {
        if(StringUtils.hasText(pessoaFiltro.getDdd())) {
            stringBuilder.append(" AND telefone.ddd LIKE :ddd ");
            parameters.put("ddd", "%" + pessoaFiltro.getDdd() + "%");
        }
    }

    private void preencherNumeroTelefoneCasoNecessario(PessoaFiltro pessoaFiltro, StringBuilder stringBuilder, Map<String, Object> parameters) {
        if(StringUtils.hasText(pessoaFiltro.getTelefone())) {
            stringBuilder.append(" AND telefone.numero LIKE :numero ");
            parameters.put("numero", "%" + pessoaFiltro.getTelefone() + "%");
        }
    }
}
