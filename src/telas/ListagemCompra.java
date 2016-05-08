package telas;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.text.MaskFormatter;

import dao.ClienteDAO;
import dao.CompraDAO;
import entidades.Cliente;
import entidades.Compra;
import entidades.FiltroCompra;
import helpers.Helper;
import helpers.InsertionSort;
import interfaces.TelaInteface;

public class ListagemCompra implements TelaInteface, ActionListener {
	private JScrollPane scrollCompras;
	private DefaultTableModel tabelaModeloCompras = new DefaultTableModel(null, new String[]{"Cliente", "Data", "Valor"});
	private JTable tabelaCompras;
	
	private JLabel labelDe = new JLabel("De: ");
	private JFormattedTextField campoPeriodoDe;
	
	private JLabel labelAte = new JLabel("Até: ");
	private JFormattedTextField campoPeriodoAte;
	
	private JLabel labelCliente = new JLabel("Cliente");
	private JComboBox<Cliente> selectClientes = new JComboBox<Cliente>();
	
	private JButton btnFiltrar = new JButton("Filtrar");
	
	public JInternalFrame getConteudoTela() {
		JInternalFrame telaListagemCompras = getPropriedadesContainer();
		
		aplicarMascaraInputs();
		montarTabelaListagemCompras();
		aplicarEstiloComponentes();
		setDimensaoLabels();
		setDimensaoInputs();
		popularSelectClientes();
		adicionarComponentesTela(telaListagemCompras);
		
		return telaListagemCompras;
	}
	
	private JInternalFrame getPropriedadesContainer() {
		JInternalFrame telaListagemCompras = new JInternalFrame("Listagem de compras", false, false, false, false);
		telaListagemCompras.setSize(Helper.DEFAULT_FRAME_WIDTH, Helper.DEFAULT_FRAME_HEIGTH);
		telaListagemCompras.setLayout(new FlowLayout());
		telaListagemCompras.setVisible(true);
		telaListagemCompras.setBorder(BorderFactory.createEmptyBorder());
		
		return telaListagemCompras;
	}
	
	private void aplicarMascaraInputs() {
		try {		
			MaskFormatter mfData = new MaskFormatter("**/**/****");

			campoPeriodoDe = new JFormattedTextField(mfData);
			campoPeriodoAte = new JFormattedTextField(mfData);
		} catch (ParseException e) {
			System.err.println("Erro ao aplicar as mascaras nos inputs!");
		}		
	}
	
	private void montarTabelaListagemCompras() {
		tabelaCompras = new JTable(tabelaModeloCompras);
		scrollCompras = new JScrollPane(tabelaCompras);
		scrollCompras.setPreferredSize(new Dimension(Helper.DEFAULT_FRAME_WIDTH-10, Helper.DEFAULT_FRAME_HEIGTH/2));
		
		definirLarguraColunasTabela();
		poupularComprasTabela(CompraDAO.carregarCompras());
		
		btnFiltrar.addActionListener(this);
	}
		
	private void definirLarguraColunasTabela() {
		TableColumnModel colunaModelo = tabelaCompras.getColumnModel();
		
		int larguraColuna = Helper.DEFAULT_FRAME_WIDTH-10 / 3;
		for (int i = 0; i < 3; i++) {
			colunaModelo.getColumn(i).setMaxWidth(larguraColuna);
		}		
	}
	
	private void poupularComprasTabela(List<Compra> compras) {
		InsertionSort.ordenar(compras);
		
		int i = 0;
		String[] campos = new String[]{null, null, null};
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		for (Compra compra : compras) {
			tabelaModeloCompras.addRow(campos);
			tabelaModeloCompras.setValueAt(compra.getCliente().getNome(), i, 0);
			tabelaModeloCompras.setValueAt(dateFormat.format(compra.getDataCompra()), i, 1);
			tabelaModeloCompras.setValueAt(NumberFormat.getCurrencyInstance().format(compra.getTotal()), i, 2);
			++i;
		}
	}
	
	private void aplicarEstiloComponentes() {
		btnFiltrar.setBackground(new Color(50, 205, 50));
	}
	
	private void setDimensaoLabels() {
		Dimension dimensaoLabel = new Dimension(120, 15);
		
		labelDe.setPreferredSize(dimensaoLabel);
		labelAte.setPreferredSize(dimensaoLabel);
		labelCliente.setPreferredSize(dimensaoLabel);
	}

	private void setDimensaoInputs() {
		Dimension dimensaoInput = new Dimension(140, 25);
		
		campoPeriodoDe.setPreferredSize(dimensaoInput);
		campoPeriodoAte.setPreferredSize(dimensaoInput);
		selectClientes.setPreferredSize(dimensaoInput);
	}
	
	private void popularSelectClientes() {
		selectClientes.addItem(null);
		for (Cliente cliente : ClienteDAO.buscarClientes()) {
			selectClientes.addItem(cliente);
		}
	}
	
	private void adicionarComponentesTela(JInternalFrame telaListagemCompras) {
		telaListagemCompras.add(labelDe);
		telaListagemCompras.add(campoPeriodoDe);
		telaListagemCompras.add(labelAte);
		telaListagemCompras.add(campoPeriodoAte);
		telaListagemCompras.add(labelCliente);
		telaListagemCompras.add(selectClientes);
		telaListagemCompras.add(btnFiltrar);
		telaListagemCompras.add(scrollCompras);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnFiltrar) {
			if (campoPeriodoDe.getText().trim().isEmpty() && campoPeriodoAte.getText().trim().isEmpty() && selectClientes.getSelectedIndex() == -1) {
				JOptionPane.showMessageDialog(null, "Você não informou nenhum filtro para pesquisa!");
				return;
			}
			
			tabelaModeloCompras.setRowCount(0);
			FiltroCompra filtro = new FiltroCompra(campoPeriodoDe.getText(), campoPeriodoAte.getText(), (Cliente) selectClientes.getSelectedItem());
			CompraDAO compraDAO = new CompraDAO();
			poupularComprasTabela(compraDAO.filtrar(filtro));
		}
	}
}
