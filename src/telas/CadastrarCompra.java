package telas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

import dao.ClienteDAO;
import dao.CompraDAO;
import dao.CompraItemDAO;
import entidades.Cliente;
import entidades.Compra;
import entidades.CompraItem;
import helpers.Helper;
import interfaces.TelaInteface;

public class CadastrarCompra implements TelaInteface, ActionListener {
	private Compra compra = new Compra();
	
	private JLabel labelDataCompra = new JLabel("Data da compra");
	private JFormattedTextField campoDataCompra;
	
	private JLabel labelCliente = new JLabel("Cliente");
	private JComboBox<Cliente> selectCliente = new JComboBox<Cliente>();
	
	private JLabel labelBoxItens = new JLabel("Itens");
	
	private JLabel labelDescricaoItem = new JLabel("Descrição");
	private JTextField campoDescricaoItem = new JTextField();
	
	private JLabel labelValorItem = new JLabel("Valor");
	private JTextField campoValorItem = new JTextField();
	
	private JLabel labelQuantidadeItem = new JLabel("Quantidade");
	private JTextField campoQuantidadeItem = new JTextField();
	
	private JButton btnAdicionarItem = new JButton("Adicionar >>");
	private JButton btnCadastrarCompra = new JButton("Cadastrar");

	private DefaultListModel<CompraItem> modeloLista = new DefaultListModel<CompraItem>();
	private JList<CompraItem> listaItens = new JList<CompraItem>(modeloLista);
	
	@Override
	public JInternalFrame getConteudoTela() {
		JInternalFrame telaCadastroCompra = getPropriedadesContainer();
		
		aplicarMascaraInputs();
		setDimensaoLabels();
		setDimensaoInputs();
		setEstiloComponentes();
		adicionarComponentes(telaCadastroCompra);
		aplicarEventos();
		
		return telaCadastroCompra;
	}

	private JInternalFrame getPropriedadesContainer() {
		JInternalFrame telaCadastroCompra = new JInternalFrame("Cadastrar compra", false, false, false, false);
		telaCadastroCompra.setSize(Helper.DEFAULT_FRAME_WIDTH, Helper.DEFAULT_FRAME_HEIGTH);
		telaCadastroCompra.setLayout(new FlowLayout());
		telaCadastroCompra.setVisible(true);
		telaCadastroCompra.setBorder(BorderFactory.createEmptyBorder());
		
		return telaCadastroCompra;
	}
	
	private void aplicarMascaraInputs() {
		try {		
			MaskFormatter mfData = new MaskFormatter("##/##/####");
			campoDataCompra = new JFormattedTextField(mfData);
		} catch (ParseException e) {
			System.err.println("Erro ao aplicar as mascaras nos inputs!");
		}		
	}
	
	private void setDimensaoLabels() {
		Dimension dimensaoLabel = new Dimension(140, 15);
		
		labelDataCompra.setPreferredSize(dimensaoLabel);
		labelCliente.setPreferredSize(dimensaoLabel);
		labelBoxItens.setPreferredSize(dimensaoLabel);
		labelDescricaoItem.setPreferredSize(dimensaoLabel);
		labelValorItem.setPreferredSize(dimensaoLabel);
		labelQuantidadeItem.setPreferredSize(dimensaoLabel);
	}
	
	private void setDimensaoInputs() {
		Dimension dimensaoInput = new Dimension(135, 25);
		
		campoDataCompra.setPreferredSize(dimensaoInput);
		selectCliente.setPreferredSize(dimensaoInput);
		campoDescricaoItem.setPreferredSize(dimensaoInput);
		campoValorItem.setPreferredSize(dimensaoInput);
		campoQuantidadeItem.setPreferredSize(dimensaoInput);
	}
	
	private void setEstiloComponentes() {
		Color corBotao = new Color(50, 205, 50);
		
		btnCadastrarCompra.setBackground(corBotao);
		btnAdicionarItem.setBackground(corBotao);
	}
	
	private void adicionarComponentes(JInternalFrame telaCadastrarCompra) {
		JPanel container = new JPanel();
		JPanel painelSuperior = new JPanel();
		JPanel containerCentral = new JPanel();
		JPanel painelEsquerdo = new JPanel();
		JPanel painelDireito = new JPanel();
		JPanel painelInferior = new JPanel();
		
		container.setLayout(new BorderLayout());
        painelSuperior.setLayout(new FlowLayout());
        containerCentral.setLayout(new GridLayout(1, 2));
        painelEsquerdo.setLayout(new GridLayout(4,2));
        painelInferior.setLayout(new FlowLayout());
        
        List<Cliente> clientes = ClienteDAO.buscarClientes();
        for (Cliente cliente : clientes) {
        	selectCliente.addItem(cliente);
        }
        
        painelSuperior.add(labelDataCompra);
        painelSuperior.add(campoDataCompra);
        painelSuperior.add(labelCliente);
        painelSuperior.add(selectCliente);
        
        painelEsquerdo.add(prepararLinhaAdicionarItem(labelDescricaoItem, campoDescricaoItem));
        painelEsquerdo.add(prepararLinhaAdicionarItem(labelValorItem, campoValorItem));
        painelEsquerdo.add(prepararLinhaAdicionarItem(labelQuantidadeItem, campoQuantidadeItem));
        painelEsquerdo.add(prepararLinhaAdicionarItem(btnAdicionarItem, new JLabel()));

        
        listaItens.setPreferredSize(new Dimension(100, 100));
        JScrollPane scrollLista = new JScrollPane();
        scrollLista.setViewportView(listaItens);
        painelDireito.add(scrollLista);
        
        painelInferior.add(btnCadastrarCompra);
                
        containerCentral.add(painelEsquerdo);
        containerCentral.add(painelDireito);
        containerCentral.setBorder(BorderFactory.createTitledBorder("Itens"));
        
        container.add(painelSuperior, BorderLayout.NORTH);
        container.add(containerCentral, BorderLayout.CENTER);
        container.add(painelInferior, BorderLayout.SOUTH);
        
		telaCadastrarCompra.add(container);
	}	
	
	public JComponent prepararLinhaAdicionarItem(JComponent comp, JComponent comp2)
    {
        JPanel panel = new JPanel();
        panel.add(comp);
        panel.add(comp2);
        return panel;
    }

	public void aplicarEventos() {
		btnAdicionarItem.addActionListener(this);
		btnCadastrarCompra.addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnAdicionarItem) {
			CompraItem item = new CompraItem();
			item.setDescricao(campoDescricaoItem.getText());
			
			try {
				item.setValor(Float.parseFloat(campoValorItem.getText()));
			} catch (NumberFormatException exception) {
				JOptionPane.showMessageDialog(null, "O valor do item não foi preenchido corretamente!");
				return;
			}
			
			try {
				item.setQuantidade(Integer.parseInt(campoQuantidadeItem.getText()));
			} catch (NumberFormatException exception) {
				JOptionPane.showMessageDialog(null, "A quantidade não foi preenchida corretamente!");
				return;
			}			
			
			if (!CompraItemDAO.validarItem(item)) 
				return;
			
			modeloLista.addElement(item);
			compra.addItem(item);
			
			campoDescricaoItem.setText("");
			campoValorItem.setText("");
			campoQuantidadeItem.setText("");
		} else if (e.getSource() == btnCadastrarCompra) {
			if (!Helper.validarData(campoDataCompra.getText())) {
				JOptionPane.showMessageDialog(null, "A data de compra não foi preenchida corretamente!");
				return;
			}
			
			if (compra.getItens().size() == 0) {
				JOptionPane.showMessageDialog(null, "Nenhum item foi adicionado a compra!");
				return;
			}
			
			Cliente cliente = (Cliente) selectCliente.getSelectedItem();
			if (cliente == null) {
				JOptionPane.showMessageDialog(null, "Nenhum cliente foi selecionado!");
				return;
			}
			
			compra.setCliente(cliente);
			try {
				compra.setDataCompra(new SimpleDateFormat(Helper.DATE_FORMAT).parse(campoDataCompra.getText()));
			} catch (ParseException e1) {
				JOptionPane.showMessageDialog(null, "Erro ao converter data.");
				return;
			}
			
			CompraDAO compraDAO = new CompraDAO();
			compraDAO.cadastrar(compra);
			
			campoDataCompra.setText("");
			modeloLista.clear();
			compra.getItens().clear();
		}
	}	
}
