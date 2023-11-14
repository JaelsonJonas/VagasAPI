package br.com.vagasapi.service;

import java.sql.SQLException;
import java.util.List;

import br.com.vagasapi.dao.EmpresaDao;
import br.com.vagasapi.exceptions.EmpresaException;
import br.com.vagasapi.model.Empresa;

public class EmpresaService {

	private EmpresaDao dao;

	public EmpresaService() {

		dao = new EmpresaDao();

	}

	public List<Empresa> listar() throws SQLException {
		return dao.listAll();
	}

	public Empresa cadastrar(Empresa empresa) {
		return dao.insert(empresa);
	}

	public int deletar(long id) {
		try {
			return dao.delete(id);
		} catch (EmpresaException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public Empresa atualizar(Empresa empresa, long id) throws EmpresaException {

		Empresa getEmpresa = dao.getById(id).orElseThrow(() -> new EmpresaException("Empresa not found"));

		getEmpresa.update(empresa);

		return dao.update(getEmpresa);
	}

	public Empresa empresaPorId(long id) throws EmpresaException {
		return dao.getById(id).orElseThrow(() -> new EmpresaException("Empresa not found"));
	}

}
