package bancodedados;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;


public class Conexao {
	public static Connection getConexao(){
		Connection con = null;
		
		try{
			con = DriverManager.getConnection("jdbc:mysql://localhost/projeto_interdisciplinar_iv", "douglas_oliveira", "123456");
		} catch (SQLException e){
			JOptionPane.showMessageDialog(null, "Erro ao conectar ao banco!");
		}
		
		return con;
	}
	
	public static void fecharConexao(java.sql.Connection con){
		try{
			con.close();
		} catch(SQLException e){
			JOptionPane.showMessageDialog(null, "Erro ao desconectar do banco!");
		}
	}
}
