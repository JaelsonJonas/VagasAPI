package br.com.vagasapi.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import br.com.vagasapi.exceptions.VagaException;
import br.com.vagasapi.factory.ConnectionFactory;
import br.com.vagasapi.model.Empresa;
import br.com.vagasapi.model.Vaga;

public class VagaDao {

	public List<Vaga> listAll() {

		Connection con = null;
		PreparedStatement stm = null;
		ResultSet rs = null;

		List<Vaga> vagas = new ArrayList<>();

		try {
			con = ConnectionFactory.getConnection();
			stm = con.prepareStatement("select * from tdss_tb_vaga order by cd_vaga");
			rs = stm.executeQuery();
			while (rs.next()) {
				vagas.add(parse(rs));
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

		return vagas;
	}

	public Vaga insert(Vaga vaga) {

		Connection con = null;
		PreparedStatement stm = null;
		ResultSet rs = null;

		String sql = "INSERT INTO TDSS_TB_VAGA (CD_VAGA, DS_TITULO, DS_VAGA, VL_SALARIO, DT_PUBLICACAO, CD_EMPRESA)"
				+ "VALUES (SEQ_VAGA.NEXTVAL, ?, ?, ?, ?, ?)";

		try {
			con = ConnectionFactory.getConnection();
			stm = con.prepareStatement(sql, new String[] { "CD_VAGA" });

			int i = 0;
			stm.setString(++i, vaga.getTitulo());
			stm.setString(++i, vaga.getVaga());
			stm.setBigDecimal(++i, vaga.getSalario());
			stm.setDate(++i, Date.valueOf(vaga.getPublicacao()));
			stm.setLong(++i, vaga.getEmpresa().getId());

			stm.executeUpdate();

			rs = stm.getGeneratedKeys();
			if (rs.next()) {
				long id = rs.getInt(1);
				vaga.setId(id);
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

		return vaga;
	}

	public int delete(long id) throws VagaException {

		Connection con = null;
		PreparedStatement stm = null;
		ResultSet rs = null;

		String sql = "DELETE from tdss_tb_vaga where cd_vaga = ?";
		int rows = 0;
		try {
			con = ConnectionFactory.getConnection();

			stm = con.prepareStatement(sql);
			stm.setLong(1, id);

			rows = stm.executeUpdate();

			if (rows <= 0)
				throw new VagaException("Vaga not found");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				ConnectionFactory.closeResources(con, stm, rs);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return rows;
	}

	public Optional<Vaga> getById(long id) {

		Connection con = null;
		PreparedStatement stm = null;
		ResultSet rs = null;

		String sql = "select * from tdss_tb_vaga where cd_vaga = ?";

		try {
			con = ConnectionFactory.getConnection();

			stm = con.prepareStatement(sql);
			stm.setLong(1, id);

			rs = stm.executeQuery();

			if (rs.next()) {
				return Optional.of(parse(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return Optional.empty();

	}

	public Vaga update(Vaga vaga) throws VagaException {

		Connection con = null;
		PreparedStatement stm = null;

		String sql = "update tdss_tb_vaga set ds_titulo = ?, ds_vaga = ?, vl_salario = ?, dt_publicacao = ? where cd_empresa = ?";
		try {
			con = ConnectionFactory.getConnection();
			stm = con.prepareStatement(sql);
			int i = 0;
			stm.setString(++i, vaga.getTitulo());
			stm.setString(++i, vaga.getVaga());
			stm.setBigDecimal(++i, vaga.getSalario());
			stm.setDate(++i, Date.valueOf(vaga.getPublicacao()));
			stm.setLong(++i, vaga.getEmpresa().getId());

			int x = stm.executeUpdate();

			if (x == 0)
				throw new VagaException("Usuario nao encontrado");

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				ConnectionFactory.closeResources(con, stm, null);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return vaga;
	}

	private Vaga parse(ResultSet rs) throws SQLException {

		int i = 0;
		long id = rs.getLong(++i);
		String titulo = rs.getString(++i);
		String vaga = rs.getString(++i);
		BigDecimal salario = rs.getBigDecimal(++i);
		LocalDate publicacao = rs.getDate(++i).toLocalDate();
		long empresa = rs.getLong(++i);

		Vaga parse = new Vaga(id, titulo, vaga, salario, publicacao);

		if (empresa != 0) {
			Empresa empresaParse = new Empresa();
			empresaParse.setId(empresa);
			parse.setEmpresa(empresaParse);
		}

		return parse;
	}

}
