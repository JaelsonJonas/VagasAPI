package br.com.vagasapi.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class ConnectionFactory {

	public static Connection getConnection() throws SQLException {

		String url = "jdbc:oracle:thin:@oracle.fiap.com.br:1521:orcl";
		String user = "rm96320";
		String password = "291293";

		return DriverManager.getConnection(url, user, password);

	}

	public static void closeResources(Connection con, PreparedStatement stm, ResultSet rs) throws SQLException {

		if (Objects.nonNull(stm))
			stm.close();
		if (Objects.nonNull(rs))
			rs.close();
		if (Objects.nonNull(con))
			con.close();

	}
}