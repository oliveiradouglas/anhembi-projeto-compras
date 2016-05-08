package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import bancodedados.Conexao;
import entidades.Cliente;

public class ClienteDAO {
	public static final String CAMPOS = "nome, endereco, telefone, celular";
	public static final String TABELA = "cliente";
	
	public void salvar(Cliente cliente) {
		if (cliente.getId() != 0) {
			editar(cliente);
		} else {
			cadastrar(cliente);
		}
	}
	
	public void editar(Cliente cliente) {
		try	{
			String sql = String.format("UPDATE %s SET nome = ?, endereco = ?, telefone = ?, celular = ? WHERE id = %d", TABELA, cliente.getId());
			
			Connection con = Conexao.getConexao();
			PreparedStatement query = con.prepareStatement(sql);
			setValoresQuery(query, cliente);
			
			query.execute();
			query.close();
			Conexao.fecharConexao(con);
			
			JOptionPane.showMessageDialog(null, "Editado com sucesso!");
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage() + "\nErro ao editar!");
		}
	}
	
	public void cadastrar(Cliente cliente) {
		try	{
			String sql = String.format("INSERT INTO %s (%s) VALUES (?,?,?,?)", TABELA, CAMPOS);
			
			Connection con = Conexao.getConexao();
			PreparedStatement query = con.prepareStatement(sql);
			setValoresQuery(query, cliente);
			
			query.execute();
			query.close();
			Conexao.fecharConexao(con);
			
			JOptionPane.showMessageDialog(null, "Cadastrado com sucesso!");
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage() + "\nErro ao cadastrar!");
		}
	}
	
	private void setValoresQuery(PreparedStatement query, Cliente cliente) throws SQLException {
		query.setString(1, cliente.getNome());
		query.setString(2, cliente.getEndereco());
		query.setLong(3, cliente.getTelefone());
		query.setLong(4, cliente.getCelular());
	}	
	
	public static List<Cliente> buscarClientes() {
		String sql = String.format("SELECT id, %s FROM %s", CAMPOS, TABELA);
		return carregarClientes(sql);
	}
	
	private static List<Cliente> carregarClientes(String sql) {
		List<Cliente> clientes = new ArrayList<Cliente>();
		
		try{
			Connection con = Conexao.getConexao();
			
			PreparedStatement stmt = con.prepareStatement(sql);
			ResultSet registro = stmt.executeQuery();
			
			while(registro.next()){
				clientes.add(preencherDadosRegistro(registro));
			}
			
			Conexao.fecharConexao(con);
		} catch(SQLException e){
			JOptionPane.showMessageDialog(null, "Erro ao carregar o(s) cliente(s)");
		}
		
		return clientes;
	}
	
	public static Cliente buscarCliente(int idCliente) throws Exception {
		String sql = String.format("SELECT id, %s FROM %s WHERE id = \'%d\'", CAMPOS, TABELA, idCliente);
		List<Cliente> clientes = carregarClientes(sql);
		
		if (clientes.size() == 0) {
			throw new Exception("Nenhum cliente encontrado!");
		}
		
		return clientes.get(0);
	}
	
	private static Cliente preencherDadosRegistro(ResultSet registro) {
		Cliente cliente = new Cliente();
		
		try {
			cliente.setId(registro.getInt("id"));
			cliente.setNome(registro.getString("nome"));
			cliente.setEndereco(registro.getString("endereco"));
			cliente.setTelefone(registro.getLong("telefone"));
			cliente.setCelular(registro.getLong("celular"));
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Erro ao carregar os clientes!");
			System.exit(0);
		}
		
		return cliente;
	}
}
