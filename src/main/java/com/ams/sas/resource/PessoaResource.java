package com.ams.sas.resource;

import com.ams.sas.exception.TelefoneNaoEncontradoException;
import com.ams.sas.modelo.Pessoa;
import com.ams.sas.modelo.Telefone;
import com.ams.sas.servico.PessoaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/pessoas")
public class PessoaResource {

    private final PessoaService pessoaService;

    public PessoaResource(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }

    @GetMapping(value = "/{ddd}/{numero}")
    public ResponseEntity<Pessoa> buscarPorDddENumeroTelefone(@PathVariable("ddd") String ddd, @PathVariable("numero") String numero) throws TelefoneNaoEncontradoException {
        return ResponseEntity.status(HttpStatus.OK).body(this.pessoaService.buscarPorTelefone(new Telefone(ddd, numero)));
    }
}
