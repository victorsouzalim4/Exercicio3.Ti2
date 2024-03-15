package DAO;

import java.sql.*;

public class Dao {
	protected Connection conexao;
	
	public Dao() {
		conexao = null;
	}
	
	public boolean conectar() {
		String driverName = "org.postgresql.Driver";
		String serverName = "localhost";
		String mydatabase = "postgres";
		int porta = 5432;
		String url = "jdbc:postgresql://" + serverName + ":" + porta + "/" + mydatabase;
		String username = "postgres";
		String password = "88138622an";
		boolean status = false;
		
		try {
			Class.forName(driverName);
			conexao = DriverManager.getConnection(url, username, password);
			status = (conexao == null);
			System.out.println("conexao efetuada com sucesso!");
		} catch (ClassNotFoundException e) {
			System.err.println("Driver de conexão não encontrado" + e.getMessage());
		} catch (SQLException e) {
			System.err.println("Conexão não efetuada com sucesso" + e.getMessage());
		}
		
		return status;
	}
	
	public boolean close() {
		boolean status = false;
		
		try {
			conexao.close();
			status = true;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		
		return status;
	}
}
