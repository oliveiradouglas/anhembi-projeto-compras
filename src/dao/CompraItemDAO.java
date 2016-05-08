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
import entidades.CompraItem;

public class CompraItemDAO {
	public static final String CAMPOS = "id_compra, descricao, quantidade, valor";
	public static final String TABELA = "compra_item";
	
	public static boolean validarItem(CompraItem item){
		boolean itemValido = false;

		if (item.getDescricao().trim().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha a descrição do item!");
		} else if (item.getValor() <= 0) {
			JOptionPane.showMessageDialog(null, "Preencha o valor do item!");
		} else if (item.getQuantidade() <= 0) {
			JOptionPane.showMessageDialog(null, "Preencha a quantidade do item!");
		} else {
			itemValido = true;
		}
		
		return itemValido;
	}
	
	public void cadastrar(List<CompraItem> itens, int idCompra) {
		try {
			String sql = String.format("INSERT INTO %s (%s) VALUES (?,?,?,?)", TABELA, CAMPOS);
			Connection con = Conexao.getConexao();
			
			for (CompraItem item : itens) {
				PreparedStatement query = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				
				item.setIdCompra(idCompra);
				setValoresQuery(query, item);
				
				query.execute();
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao cadastrar os itens da compra!");
		}
	}
	
	private void setValoresQuery(PreparedStatement query, CompraItem item) throws SQLException {
		query.setInt(1, item.getIdCompra());
		query.setString(2, item.getDescricao());
		query.setInt(3, item.getQuantidade());
		query.setFloat(4, item.getValor());
	}
	
	public static List<CompraItem> carregarItens(int idCompra) {
		String sql = String.format("SELECT id, %s FROM %s WHERE id_compra = \'%d\'", CAMPOS, TABELA, idCompra);
		List<CompraItem> itens = new ArrayList<CompraItem>();
		
		try{
			Connection con = Conexao.getConexao();
			
			PreparedStatement stmt = con.prepareStatement(sql);
			ResultSet registro = stmt.executeQuery();
			
			while(registro.next()){
				itens.add(preencherDadosRegistro(registro));
			}
			
			Conexao.fecharConexao(con);
		} catch(SQLException e){
			JOptionPane.showMessageDialog(null, "Erro ao carregar os itens da compra!");
		}
		
		return itens;
	}
	
	private static CompraItem preencherDadosRegistro(ResultSet registro) {
		CompraItem item = new CompraItem();
		
		try {
			item.setDescricao(registro.getString("descricao"));
			item.setQuantidade(registro.getInt("quantidade"));
			item.setValor(registro.getFloat("valor"));
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Erro ao preencher os dados dos itens da compra!");
			System.exit(0);
		}
		
		return item;
	}
}
