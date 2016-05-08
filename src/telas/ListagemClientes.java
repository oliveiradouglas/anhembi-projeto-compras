package telas;

import java.util.List;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import dao.ClienteDAO;
import entidades.Cliente;
import helpers.Helper;
import interfaces.TelaInteface;

public class ListagemClientes implements ActionListener, TelaInteface {
	JTable tabelaClientes;
	
	List<Cliente> clientes = ClienteDAO.buscarClientes();
	JLabel labelTitulo = new JLabel("Clientes");
	JButton btnEditarCliente = new JButton("Editar");
	
	public JInternalFrame getConteudoTela() {
		JInternalFrame telaListagemClientes = getPropriedadesContainer();
		
		telaListagemClientes.add(labelTitulo);
		montarTabelaListagemClientes(telaListagemClientes);
		aplicarEstiloComponentes();
		telaListagemClientes.add(btnEditarCliente);
		
		return telaListagemClientes;
	}
	
	private JInternalFrame getPropriedadesContainer() {
		JInternalFrame telaListagemClientes = new JInternalFrame("Listagem de clientes", false, false, false, false);
		telaListagemClientes.setSize(Helper.DEFAULT_FRAME_WIDTH, Helper.DEFAULT_FRAME_HEIGTH);
		telaListagemClientes.setLayout(new FlowLayout());
		telaListagemClientes.setVisible(true);
		telaListagemClientes.setBorder(BorderFactory.createEmptyBorder());
		
		return telaListagemClientes;
	}
	
	private void montarTabelaListagemClientes(JInternalFrame telaListagemClientes) {		
		DefaultTableModel tabelaModeloClientes = new DefaultTableModel(null, new String[]{"Nome", "Endereco", "Telefone"});
		tabelaClientes = new JTable(tabelaModeloClientes);
		
		JScrollPane scrollClientes = new JScrollPane(tabelaClientes);
		scrollClientes.setPreferredSize(new Dimension(Helper.DEFAULT_FRAME_WIDTH-10, Helper.DEFAULT_FRAME_HEIGTH/2));
		telaListagemClientes.add(scrollClientes);
		
		definirLarguraColunasTabela();
		poupularClientesTabela(tabelaModeloClientes);
		
		btnEditarCliente.addActionListener(this);
	}
	
	private void definirLarguraColunasTabela() {
		TableColumnModel colunaModelo = tabelaClientes.getColumnModel();
		
		int larguraColuna = Helper.DEFAULT_FRAME_WIDTH-10 / 3;
		for (int i = 0; i < 3; i++) {
			colunaModelo.getColumn(i).setMaxWidth(larguraColuna);
		}		
	}
	
	private void poupularClientesTabela(DefaultTableModel tabelaModeloClientes) {
		int i = 0;
		String[] campos = new String[]{null, null, null};
		for (Cliente cliente : clientes) {
			tabelaModeloClientes.addRow(campos);
			tabelaModeloClientes.setValueAt(cliente.getNome(), i, 0);
			tabelaModeloClientes.setValueAt(cliente.getEndereco(), i, 1);
			
			if (cliente.getTelefone() > 0) {
				try {
					tabelaModeloClientes.setValueAt(Helper.aplicarMascaraTelefone(cliente.getTelefone()), i, 2);
				} catch (ParseException e) {
					JOptionPane.showMessageDialog(null, "Ocorreu algum erro ao aplicar a mascara no telefone.");
				}
			}
			
			++i;
		}
	}
	
	private void aplicarEstiloComponentes() {
		btnEditarCliente.setBackground(new Color(50, 205, 50));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnEditarCliente) {
			if (tabelaClientes.getSelectedRow() == -1) {
				JOptionPane.showMessageDialog(null, "Você não selecionou nenhum cliente!");
				return;
			}
			
			Cliente cliente = clientes.get(tabelaClientes.getSelectedRow());
			
			FormularioCliente telaEditarCliente = new FormularioCliente(cliente);
			Index.getInstancia().setTelaFilha(telaEditarCliente.getConteudoTela());
		}
	}
}
