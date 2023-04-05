package com.ams.sas.modelo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "servico")
public class Servico implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Enum tipoServico;
    private String descricaoServico;
    private String nomeAtendente;
    private LocalDateTime dataAtendimento;
    private LocalDateTime dataInicioAtendimento;
    private LocalDateTime dataFimAtendimento;
    private LocalDateTime created;
    private LocalDateTime modifed;
    private Boolean ativo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_solicitante")
    @JsonIgnore
    private Solicitante solicitante;

    public Servico() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Enum getTipoServico() {
        return tipoServico;
    }

    public void setTipoServico(Enum tipoServico) {
        this.tipoServico = tipoServico;
    }

    public String getDescricaoServico() {
        return descricaoServico;
    }

    public void setDescricaoServico(String descricaoServico) {
        this.descricaoServico = descricaoServico;
    }

    public String getNomeAtendente() {
        return nomeAtendente;
    }

    public void setNomeAtendente(String nomeAtendente) {
        this.nomeAtendente = nomeAtendente;
    }

    public LocalDateTime getDataAtendimento() {
        return dataAtendimento;
    }

    public void setDataAtendimento(LocalDateTime dataAtendimento) {
        this.dataAtendimento = dataAtendimento;
    }

    public LocalDateTime getDataInicioAtendimento() {
        return dataInicioAtendimento;
    }

    public void setDataInicioAtendimento(LocalDateTime dataInicioAtendimento) {
        this.dataInicioAtendimento = dataInicioAtendimento;
    }

    public LocalDateTime getDataFimAtendimento() {
        return dataFimAtendimento;
    }

    public void setDataFimAtendimento(LocalDateTime dataFimAtendimento) {
        this.dataFimAtendimento = dataFimAtendimento;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getModifed() {
        return modifed;
    }

    public void setModifed(LocalDateTime modifed) {
        this.modifed = modifed;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Solicitante getSolicitante() {
        return solicitante;
    }

    public void setSolicitante(Solicitante solicitante) {
        this.solicitante = solicitante;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Servico servico = (Servico) o;
        return id.equals(servico.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
