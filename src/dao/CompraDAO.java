package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import com.mysql.jdbc.Statement;

import bancodedados.Conexao;
import entidades.Compra;
import entidades.FiltroCompra;

public class CompraDAO {
	public static final String CAMPOS = "id_cliente, data_compra";
	public static final String TABELA = "compra";
	
	public void cadastrar(Compra compra) {
		try	{
			String sql = String.format("INSERT INTO %s (%s) VALUES (?,?)", TABELA, CAMPOS);
			
			Connection con = Conexao.getConexao();
			PreparedStatement query = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			setValoresQuery(query, compra);
			
			query.execute();
			
			try (ResultSet generatedKeys = query.getGeneratedKeys()) {
	            if (generatedKeys.next()) {
	                compra.setId(generatedKeys.getInt(1));
	            } else {
	                JOptionPane.showMessageDialog(null, "Erro ao cadastrar compra!");
	            }
	        }			
			
			query.close();
			Conexao.fecharConexao(con);
			JOptionPane.showMessageDialog(null, "Compra cadastrada com sucesso!");
			
			CompraItemDAO compraItemDAO = new CompraItemDAO();
			compraItemDAO.cadastrar(compra.getItens(), compra.getId());
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage() + "\nErro ao cadastrar!");
		}
	}
	
	private void setValoresQuery(PreparedStatement query, Compra compra) throws SQLException {
		query.setInt(1, compra.getCliente().getId());
		query.setDate(2, new java.sql.Date(compra.getDataCompra().getTime()));
	}
	
	public static List<Compra> carregarCompras() {
		String sql = String.format("SELECT id, %s FROM %s", CAMPOS, TABELA);
		return pesquisarCompras(sql);
	}
	
	private static List<Compra> pesquisarCompras(String sql) {
		List<Compra> compras = new ArrayList<Compra>();
		
		try{
			Connection con = Conexao.getConexao();
			
			PreparedStatement stmt = con.prepareStatement(sql);
			ResultSet registro = stmt.executeQuery();
			
			while(registro.next()){
				compras.add(preencherDadosRegistro(registro));
			}
			
			Conexao.fecharConexao(con);
		} catch(SQLException e){
			JOptionPane.showMessageDialog(null, "Erro ao carregar as compras!");
		}
		
		return compras;
	}
	
	private static Compra preencherDadosRegistro(ResultSet registro) {
		Compra compra = new Compra();
		
		try {
			compra.setId(registro.getInt("id"));
			compra.setDataCompra(registro.getDate("data_compra"));
			
			try {
				compra.setCliente(ClienteDAO.buscarCliente(registro.getInt("id_cliente")));
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
			}
			
			compra.setItens(CompraItemDAO.carregarItens(compra.getId()));
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Erro ao preencher os dados da compra!");
			System.exit(0);
		}
		
		return compra;
	}
	
	public List<Compra> filtrar(FiltroCompra filtro) {
		return pesquisarCompras(filtro.getSqlFiltroCompra());
	}
}
