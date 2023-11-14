package br.com.vagasapi.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Vaga {

	private Long id;

	private String titulo;

	private String vaga;

	private BigDecimal salario;

	private LocalDate publicacao;

	private Empresa empresa;

	public Vaga() {

	}

	public Vaga(Long id, String titulo, String vaga, BigDecimal salario, LocalDate publicacao) {
		this.id = id;
		this.titulo = titulo;
		this.vaga = vaga;
		this.salario = salario;
		this.publicacao = publicacao;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getVaga() {
		return vaga;
	}

	public void setVaga(String vaga) {
		this.vaga = vaga;
	}

	public BigDecimal getSalario() {
		return salario;
	}

	public void setSalario(BigDecimal salario) {
		this.salario = salario;
	}

	public LocalDate getPublicacao() {
		return publicacao;
	}

	public void setPublicacao(LocalDate publicacao) {
		this.publicacao = publicacao;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public void update(Vaga vaga) {
		this.setTitulo(vaga.getTitulo());
		this.setVaga(vaga.getVaga());
		this.setSalario(vaga.getSalario());
		this.setPublicacao(vaga.getPublicacao());
		this.setEmpresa(vaga.getEmpresa());
	}

}
