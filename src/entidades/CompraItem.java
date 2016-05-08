package entidades;

public class CompraItem {
	private int idCompra;
	private String descricao;
	private int quantidade;
	private float valor;
	
	public CompraItem() {}
	
	public CompraItem(String descricao, int quantidade, float valor) {
		this.descricao = descricao;
		this.quantidade = quantidade;
		this.valor = valor;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public int getQuantidade() {
		return quantidade;
	}
	
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
	
	public float getValor() {
		return valor;
	}
	
	public void setValor(float valor) {
		this.valor = valor;
	}
	
	@Override
	public String toString() {
		return getDescricao() + " | Valor: " + getValor() + " | Qtd: " + getQuantidade() ;
	}

	public int getIdCompra() {
		return idCompra;
	}

	public void setIdCompra(int idCompra) {
		this.idCompra = idCompra;
	}
}
