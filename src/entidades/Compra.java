package entidades;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Compra {
	private int id;
	private Date dataCompra;
	private Cliente cliente;
	private List<CompraItem> itens = new ArrayList<CompraItem>();
	
	public Compra() {}
	
	public Compra(Date dataCompra, Cliente cliente) {
		this.dataCompra = dataCompra;
		this.cliente = cliente;
	}	
	
	public Date getDataCompra() {
		return dataCompra;
	}
	
	public void setDataCompra(Date dataCompra) {
		this.dataCompra = dataCompra;
	}
	
	public Cliente getCliente() {
		return cliente;
	}
	
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	public void addItem(CompraItem item) {
		this.itens.add(item);
	}
	
	public CompraItem getItem(int indice) {
		return this.itens.get(indice);
	}
	
	public List<CompraItem> getItens() {
		return this.itens;
	}

	public void setItens(List<CompraItem> itens) {
		this.itens = itens;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public float getTotal() {
		float totalCompra = 0F;
		
		for (CompraItem item : itens) {
			totalCompra += item.getValor() * item.getQuantidade();
		}
		
		return totalCompra;
	}
}