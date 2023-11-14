package br.com.vagasapi.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import br.com.vagasapi.model.Empresa;
import br.com.vagasapi.model.Vaga;

public record VagaResponse(Long id, String titulo, String vaga, BigDecimal salario, LocalDate publicacao,
		Empresa empresa) {

	public VagaResponse(Vaga v) {
		this(v.getId(), v.getTitulo(), v.getVaga(), v.getSalario(), v.getPublicacao(), v.getEmpresa());
	}

}
