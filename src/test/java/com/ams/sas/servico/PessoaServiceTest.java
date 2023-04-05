package com.ams.sas.servico;

import com.ams.sas.exception.TelefoneNaoEncontradoException;
import com.ams.sas.exception.UnicidadeCpfException;
import com.ams.sas.exception.UnicidadeTelefoneException;
import com.ams.sas.modelo.Endereco;
import com.ams.sas.modelo.Pessoa;
import com.ams.sas.modelo.Telefone;
import com.ams.sas.repository.PessoaRepository;
import com.ams.sas.servico.impl.PessoaServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class PessoaServiceTest {

    private final static String NOME = "Mario Santos";
    private final static String CPF = "07570680731";
    private final static String DDD = "021";
    private final static String NUMERO = "335";

    private final static String LOGRADOURO = "Rua Noronha Torrezão";
    private final static String COMPLEMENTO = "Bloco 4 - Apto 1401";
    private final static String BAIRRO = "Santa Rosa";
    private final static String CIDADE = "Niteroi";
    private final static String ESTADO = "RJ";
    private final static String CEP = "24240181";

    private Pessoa pessoa;
    private Telefone telefone;
    private Endereco endereco;
    private PessoaService pessoaService;

    @MockBean
    private PessoaRepository pessoaRepository;

    @Before
    public void setUp() throws Exception {

        this.pessoaService = new PessoaServiceImpl(this.pessoaRepository);

        pessoa = new Pessoa();
        pessoa.setNome(NOME);
        pessoa.setCpf(CPF);

        telefone = new Telefone();
        telefone.setDdd(DDD);
        telefone.setNumero(NUMERO);

        List<Telefone> telefoneList = new ArrayList<>();
        telefoneList.add(telefone);

        pessoa.setTelefoneList(telefoneList);

        //
        endereco = new Endereco();
        endereco.setLogradouro(LOGRADOURO);
        endereco.setNumero(NUMERO);
        endereco.setComplemento(COMPLEMENTO);
        endereco.setBairro(BAIRRO);
        endereco.setCidade(CIDADE);
        endereco.setEstado(ESTADO);
        endereco.setCep(CEP);

        List<Endereco> enderecoList = new ArrayList<>();
        enderecoList.add(endereco);

        pessoa.setEnderecoList(enderecoList);

        when(this.pessoaRepository.findByCpf(CPF)).thenReturn(Optional.empty());
        when(this.pessoaRepository.findByTelfoneDddAndTelefoneNumero(DDD, NUMERO)).thenReturn(Optional.empty());
        when(this.pessoaRepository.findByLogradouroAndNumero(LOGRADOURO, NUMERO)).thenReturn(Optional.empty());
    }

    @Test
    public void deveSalvarPessoaNoRepository() throws Exception {
        this.pessoaService.salvar(pessoa);
        verify(this.pessoaRepository).save(pessoa);
    }

    @Test(expected = UnicidadeCpfException.class)
    public void naoDeveSalvarDuasPessoasComOMesmoCPF() throws Exception {
        when(this.pessoaRepository.findByCpf(CPF)).thenReturn(Optional.of(pessoa));
        this.pessoaService.salvar(pessoa);
    }

    @Test(expected = UnicidadeTelefoneException.class)
    public void naoDeveSalvarDuasPessoasComOMesmoTelefone() throws Exception {
        when(this.pessoaRepository.findByTelfoneDddAndTelefoneNumero(DDD, NUMERO)).thenReturn(Optional.of(pessoa));
        this.pessoaService.salvar(pessoa);
    }

    @Test
    public void deveProcurarPessoaPeloDddENumeroDoTelefone() throws Exception {
        when(this.pessoaRepository.findByTelfoneDddAndTelefoneNumero(DDD, NUMERO)).thenReturn(Optional.of(pessoa));

        Pessoa pessoaExiste = this.pessoaService.buscarPorTelefone(telefone);

        verify(this.pessoaRepository).findByTelfoneDddAndTelefoneNumero(DDD, NUMERO);

        assertThat(pessoaExiste).isNotNull();
        assertThat(pessoaExiste.getNome()).isEqualTo(NOME);
        assertThat(pessoaExiste.getCpf()).isEqualTo(CPF);

    }

    @Test
    public void deveProcurarPessoaPeloLogradouroENumero() throws Exception {
        when(this.pessoaRepository.findByLogradouroAndNumero(LOGRADOURO, NUMERO)).thenReturn(Optional.of(pessoa));

        Pessoa pessoaExiste = this.pessoaService.buscarPorEndereco(endereco);

        verify(this.pessoaRepository).findByLogradouroAndNumero(LOGRADOURO, NUMERO);

        assertThat(pessoaExiste).isNotNull();
        assertThat(pessoaExiste.getNome()).isEqualTo(NOME);
        assertThat(pessoaExiste.getCpf()).isEqualTo(CPF);

    }

    @Test(expected = TelefoneNaoEncontradoException.class)
    public void deveRetornarExcecaoDeNaoEncontradoQuandoNaoExistirPessoaComDddNumeroDeTelefone() throws Exception {
        this.pessoaService.buscarPorTelefone(telefone);
    }

}
