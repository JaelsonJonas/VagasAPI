package br.com.vagasapi.model;

public class Empresa {

	private Long id;

	private String nome;

	private Integer numFuncionarios;

	public Empresa() {

	}

	public Empresa(Long id, String nome, Integer numFuncionarios) {
		this.id = id;
		this.nome = nome;
		this.numFuncionarios = numFuncionarios;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getNumFuncionarios() {
		return numFuncionarios;
	}

	public void setNumFuncionarios(Integer numFuncionarios) {
		this.numFuncionarios = numFuncionarios;
	}

	public void update(Empresa empresa) {
		this.setNome(empresa.getNome());
		this.setNumFuncionarios(empresa.getNumFuncionarios());
	}

}
