package entidades;

public class Cliente {
	private int id;
	private String nome;
	private String endereco;
	private long telefone;
	private long celular;
		
	public Cliente() {
	}
	
	public Cliente(String nome, String endereco, long telefone, long celular) {
		this.nome = nome;
		this.endereco = endereco;
		this.telefone = telefone;
		this.celular = celular;
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	
	public long getTelefone() {
		return telefone;
	}
	
	public void setTelefone(long telefone) {
		this.telefone = telefone;
	}
	
	public long getCelular() {
		return celular;
	}
	
	public void setCelular(long celular) {
		this.celular = celular;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		return getNome();
	}
}
