package entidades;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;

import dao.CompraDAO;
import helpers.Helper;

public class FiltroCompra {
	private Date de;
	private Date ate;
	private Cliente cliente;
	
	public FiltroCompra(String de, String ate, Cliente cliente) {
		try {
			if (!de.replaceAll("/", "").trim().isEmpty()) {
				this.de = new SimpleDateFormat(Helper.DATE_FORMAT).parse(de);
			}
			
			if (!ate.replaceAll("/", "").trim().isEmpty()) {
				this.ate = new SimpleDateFormat(Helper.DATE_FORMAT).parse(ate);
			}
			
			this.cliente = cliente;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "A data inserida no filtro é invalida!");
		}
	}
	
	public String getSqlFiltroCompra() {
		String query = String.format("SELECT id, %s FROM %s", CompraDAO.CAMPOS, CompraDAO.TABELA);
		
		if (de != null || ate != null || cliente != null) {
			query += " WHERE ";
		}
		
		if (de != null) {
			query += " data_compra >= \'" + new java.sql.Date(de.getTime()) + "\'";
		}
		
		if (ate != null) {
			if (de != null) {
				query += " AND ";
			}
			
			query += " data_compra <= \'" + new java.sql.Date(ate.getTime()) + "\'";
		}
		
		if (cliente != null) {
			if (de != null || ate != null) {
				query += " AND ";
			}
			
			query += " id_cliente = \'" + cliente.getId() + "\'";
		}
		
		query += ";";
		
		return query;
	}
}
