package br.com.vagasapi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import br.com.vagasapi.exceptions.EmpresaException;
import br.com.vagasapi.factory.ConnectionFactory;
import br.com.vagasapi.model.Empresa;

public class EmpresaDao {

	public List<Empresa> listAll() {

		Connection con = null;
		PreparedStatement stm = null;
		ResultSet rs = null;

		List<Empresa> empresas = new ArrayList<>();

		try {
			con = ConnectionFactory.getConnection();
			stm = con.prepareStatement("select * from tdss_tb_empresa order by cd_empresa");

			rs = stm.executeQuery();

			while (rs.next()) {
				empresas.add(parse(rs));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				ConnectionFactory.closeResources(con, stm, rs);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return empresas;

	}

	public Empresa insert(Empresa empresa) {

		Connection con = null;
		PreparedStatement stm = null;
		ResultSet rs = null;

		String sql = "INSERT INTO TDSS_TB_EMPRESA (CD_EMPRESA, NM_EMPRESA, NR_FUNCIONARIO)\r\n"
				+ "VALUES (SEQ_EMPRESA.NEXTVAL, ?, ?)";

		try {
			con = ConnectionFactory.getConnection();
			stm = con.prepareStatement(sql, new String[] { "CD_EMPRESA" });
			int i = 0;
			stm.setString(++i, empresa.getNome());
			stm.setInt(++i, empresa.getNumFuncionarios());

			stm.execute();

			rs = stm.getGeneratedKeys();

			if (rs.next()) {
				long id = rs.getInt(1);
				empresa.setId(id);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				ConnectionFactory.closeResources(con, stm, rs);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return empresa;

	}

	public int delete(long id) throws EmpresaException {
		Connection con = null;
		PreparedStatement stm = null;

		String sql = "DELETE from tdss_tb_empresa where cd_empresa = ?";
		int rows = 0;
		try {
			con = ConnectionFactory.getConnection();
			stm = con.prepareStatement(sql);
			stm.setLong(1, id);

			rows = stm.executeUpdate();

			if (rows <= 0)
				throw new EmpresaException("Empresa not found");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				ConnectionFactory.closeResources(con, stm, null);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return rows;
	}

	public Empresa update(Empresa empresa) throws EmpresaException {

		Connection con = null;
		PreparedStatement stm = null;

		String sql = "update tdss_tb_empresa set nm_empresa = ? , nr_funcionario = ? where cd_empresa= ?";

		try {
			con = ConnectionFactory.getConnection();

			stm = con.prepareStatement(sql);

			int i = 0;

			stm.setString(++i, empresa.getNome());
			stm.setInt(++i, empresa.getNumFuncionarios());
			stm.setLong(++i, empresa.getId());

			int rowUpdate = stm.executeUpdate();

			if (rowUpdate == 0)
				throw new EmpresaException("Empresa not found");

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				ConnectionFactory.closeResources(con, stm, null);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return empresa;

	}

	public Optional<Empresa> getById(long id) {
		Connection con = null;
		PreparedStatement stm = null;
		ResultSet rs = null;

		String sql = "select * from tdss_tb_empresa where cd_empresa = ?";

		try {

			con = ConnectionFactory.getConnection();
			stm = con.prepareStatement(sql);
			stm.setLong(1, id);

			rs = stm.executeQuery();

			if (rs.next())
				return Optional.of(parse(rs));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				ConnectionFactory.closeResources(con, stm, rs);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return Optional.empty();
	}

	private Empresa parse(ResultSet rs) throws SQLException {

		int i = 0;
		long id = rs.getLong(++i);
		String nome = rs.getString(++i);
		int num = rs.getInt(++i);

		return new Empresa(id, nome, num);
	}

}
