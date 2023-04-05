package com.ams.sas.repository;

import com.ams.sas.modelo.Pessoa;
import com.ams.sas.repository.filtro.PessoaFiltro;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Sql(value = "/load-database.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = "/clean-database.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource("classpath:application-test.properties")
public class PessoaRepositoryTest {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Test
    @DisplayName(value = "deve_procurar_pessoa_pelo_cpf")
    public void should_search_person_by_cpf() {
        Optional<Pessoa> pessoa = pessoaRepository.findByCpf("86730543540");
        assertThat(pessoa).isPresent();

        assertThat(pessoa.get().getNome().equals("Iago"));
    }

    @Test
    @DisplayName(value = "nao_deve_encontrar_pessoa_com_cpf_inexistente")
    public void not_must_find_person_with_nonexistent_cpf() throws Exception {
        Optional<Pessoa> pessoa = pessoaRepository.findByCpf("82220543540");
        assertThat(pessoa).isEmpty();
    }

    @Test
    @DisplayName(value = "deve_retornar_pessoa_pelo_telefone")
    public void must_return_person_by_phone() throws Exception {
        Optional<Pessoa> pessoa = pessoaRepository.findByTelfoneDddAndTelefoneNumero("82", "39945903");
        assertThat(pessoa).isPresent();

        assertThat(pessoa.get().getNome().equals("Pedro"));
    }

    @Test
    @DisplayName(value = "nao_deve_retornar_pessoa_pelo_ddd_e_telefone_nao_cadastrado")
    public void not_must_find_person_whose_ddd_and_telephone_are_not_registered() throws Exception {
        Optional<Pessoa> pessoa = pessoaRepository.findByTelfoneDddAndTelefoneNumero("21", "39945903");
        assertThat(pessoa).isEmpty();
    }

    @Test
    @DisplayName(value = "deve_filtrar_pessoas_por_parte_do_nome")
    public void must_filter_people_by_part_of_name() throws Exception {
        PessoaFiltro pessoaFiltro = new PessoaFiltro();
        pessoaFiltro.setNome("a");

        List<Pessoa> pessoaList = pessoaRepository.filtrar(pessoaFiltro);
        assertThat(pessoaList.size()).isEqualTo(3);
    }

    @Test
    @DisplayName(value = "deve_filtrar_pessoas_por_parte_do_cpf")
    public void should_filter_people_by_part_of_cpf() throws Exception {
        PessoaFiltro pessoaFiltro = new PessoaFiltro();
        pessoaFiltro.setCpf("78");

        List<Pessoa> pessoaList = pessoaRepository.filtrar(pessoaFiltro);
        assertThat(pessoaList.size()).isEqualTo(3);
    }

    @Test
    @DisplayName(value = "deve_filtrar_pessoas_por_parte_do_nome_cpf")
    public void must_filter_people_by_part_of_cpf_name() throws Exception {
        PessoaFiltro pessoaFiltro = new PessoaFiltro();
        pessoaFiltro.setNome("a");
        pessoaFiltro.setCpf("78");

        List<Pessoa> pessoaList = pessoaRepository.filtrar(pessoaFiltro);
        assertThat(pessoaList.size()).isEqualTo(2);
    }

    @Test
    @DisplayName(value = "deve_filtra_pessoa_pelo_ddd_do_telefone")
    public void must_filter_person_by_phone_ddd() throws Exception {
        PessoaFiltro pessoaFiltro = new PessoaFiltro();
        pessoaFiltro.setDdd("21");

        List<Pessoa> pessoaList = pessoaRepository.filtrar(pessoaFiltro);
        assertThat(pessoaList.size()).isEqualTo(1);
    }

    @Test
    @DisplayName(value = "deve_filtra_pessoa_pelo_numero_telefone")
    public void must_filter_person_by_phone_number() throws Exception {
        PessoaFiltro pessoaFiltro = new PessoaFiltro();
        pessoaFiltro.setTelefone("38416");

        List<Pessoa> pessoaList = pessoaRepository.filtrar(pessoaFiltro);
        assertThat(pessoaList.size()).isEqualTo(1);
    }
}
