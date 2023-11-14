package br.com.vagasapi.service;

import java.sql.SQLException;
import java.util.List;

import br.com.vagasapi.dao.EmpresaDao;
import br.com.vagasapi.dao.VagaDao;
import br.com.vagasapi.exceptions.VagaException;
import br.com.vagasapi.model.Empresa;
import br.com.vagasapi.model.Vaga;

public class VagaService {

	private VagaDao dao;
	private EmpresaDao empresaDao;

	public VagaService() throws SQLException {

		dao = new VagaDao();
		empresaDao = new EmpresaDao();
	}

	public List<Vaga> listar() throws SQLException {
		List<Vaga> vagas = dao.listAll();

		for (Vaga v : vagas) {
			getEmpresaByVaga(v);
		}

		return vagas;
	}

	public Vaga cadastrar(Vaga vaga) {
		return dao.insert(vaga);
	}

	public int deletar(long id) {
		try {
			return dao.delete(id);
		} catch (VagaException e) {

			e.printStackTrace();
			return 0;
		}
	}

	public Vaga atualizar(Vaga vaga, long id) throws VagaException {

		Vaga getVaga = dao.getById(id).orElseThrow(() -> new VagaException("Vaga not Found"));

		getVaga.update(vaga);
		getEmpresaByVaga(getVaga);

		return dao.update(getVaga);
	}

	public Vaga getById(long id) throws VagaException {
		return getEmpresaByVaga(dao.getById(id).orElseThrow(() -> new VagaException("Vaga not found")));
	}

	private Vaga getEmpresaByVaga(Vaga v) {
		Empresa e = empresaDao.getById(v.getEmpresa().getId()).get();
		v.setEmpresa(e);

		return v;
	}

}
