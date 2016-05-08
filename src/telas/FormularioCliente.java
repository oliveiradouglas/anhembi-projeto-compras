package telas;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

import dao.ClienteDAO;
import entidades.Cliente;
import helpers.Helper;
import interfaces.TelaInteface;

public class FormularioCliente implements ActionListener, TelaInteface {
	Cliente cliente;
		
	private JLabel labelNome = new JLabel("Nome");
	private JTextField campoNome = new JTextField();
	
	private JLabel labelEndereco = new JLabel("Endereço");
	private JTextField campoEndereco = new JTextField();
	
	private JLabel labelTelefone = new JLabel("Telefone");
	private JFormattedTextField campoTelefone;
	
	private JLabel labelCelular = new JLabel("Celular");
	private JFormattedTextField campoCelular;
	
	private JButton btnSalvarCliente = new JButton("Salvar dados");
	
	public FormularioCliente() {}
	
	public FormularioCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	public JInternalFrame getConteudoTela() {
		JInternalFrame telaFormularioCliente = getPropriedadesContainer();
		
		aplicarMascaraInputs();
		setDimensaoLabels();
		setDimensaoInputs();
		setEstiloComponentes();
		adicionarComponentes(telaFormularioCliente);
		adicionarEventos();
		
		if (cliente != null) {
			preencherFormulario();
		}
		
		return telaFormularioCliente;
	}
	
	private JInternalFrame getPropriedadesContainer() {
		JInternalFrame telaFormularioCliente = new JInternalFrame(getAcaoTela() + " cliente", false, false, false, false);
		telaFormularioCliente.setSize(Helper.DEFAULT_FRAME_WIDTH, Helper.DEFAULT_FRAME_HEIGTH);
		telaFormularioCliente.setLayout(new FlowLayout());
		telaFormularioCliente.setVisible(true);
		telaFormularioCliente.setBorder(BorderFactory.createEmptyBorder());
		
		return telaFormularioCliente;
	}
	
	private String getAcaoTela() {
		return (cliente == null ? "Cadastrar" : "Editar"); 
	}
		
	private void setDimensaoLabels() {
		Dimension dimensaoLabel = new Dimension(200, 10);
		
		labelNome.setPreferredSize(dimensaoLabel);
		labelEndereco.setPreferredSize(dimensaoLabel);
		labelTelefone.setPreferredSize(dimensaoLabel);
		labelCelular.setPreferredSize(dimensaoLabel);
	}
	
	private void setDimensaoInputs() {
		Dimension dimensaoInput = new Dimension(280, 25);
		
		campoNome.setPreferredSize(dimensaoInput);
		campoEndereco.setPreferredSize(dimensaoInput);
		campoTelefone.setPreferredSize(dimensaoInput);
		campoCelular.setPreferredSize(dimensaoInput);
	}
	
	private void aplicarMascaraInputs() {
		String mascaraTelefone = "(##)####-####";
		try {
			MaskFormatter mfTelefone = new MaskFormatter(mascaraTelefone);
			mfTelefone.setPlaceholderCharacter('_');
			campoTelefone = new JFormattedTextField(mfTelefone);
			
			MaskFormatter mfCelular = new MaskFormatter(mascaraTelefone + "#");
			mfCelular.setPlaceholderCharacter('_');
			campoCelular = new JFormattedTextField(mfCelular);
		} catch (ParseException e) {
			System.err.println("Erro ao aplicar mascara de telefone!");
		}		
	}
	
	private void setEstiloComponentes() {
		btnSalvarCliente.setBackground(new Color(50, 205, 50));
	}
	
	private void adicionarComponentes(JInternalFrame telaFormularioCliente) {
		telaFormularioCliente.add(labelNome);
		telaFormularioCliente.add(campoNome);
		
		telaFormularioCliente.add(labelEndereco);
		telaFormularioCliente.add(campoEndereco);
		
		telaFormularioCliente.add(labelTelefone);
		telaFormularioCliente.add(campoTelefone);
		
		telaFormularioCliente.add(labelCelular);
		telaFormularioCliente.add(campoCelular);
		
		telaFormularioCliente.add(btnSalvarCliente);
	}

	private void adicionarEventos() {
		btnSalvarCliente.addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnSalvarCliente) {
			if (!validarFormulario()) {
				return;
			}
			
			if (this.cliente == null) {
				this.cliente = new Cliente();
			}
			
			this.cliente.setNome(campoNome.getText());
			this.cliente.setEndereco(campoEndereco.getText());
			
			Long telefone = 0L, celular = 0L;
			try {
				telefone = Long.parseLong(removerMascaraTelefone(campoTelefone.getText())); 
				celular  = Long.parseLong(removerMascaraTelefone(campoCelular.getText()));				
			} catch (Exception exception) {}
			
			this.cliente.setTelefone(telefone);
			this.cliente.setCelular(celular);
			
			ClienteDAO clienteDAO = new ClienteDAO();
			clienteDAO.salvar(this.cliente);			
			
			if (cliente.getId() == 0) {
				limparCamposFormulario();
			}
		}
	}
	
	private boolean validarFormulario() {
		if (campoNome.getText().trim().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Você precisa preencher o nome do cliente!");
			return false;
		}
		
		return true;
	}
	
	public String removerMascaraTelefone(String palavra) {
		return palavra.replace('(', ' ').replace(')', ' ').replace('-', ' ').replaceAll("\\s", "");
	}
	
	private void limparCamposFormulario() {
		campoNome.setText("");
		campoEndereco.setText("");
		campoTelefone.setText("");
		campoCelular.setText("");
	}

	private void preencherFormulario() {
		campoNome.setText(cliente.getNome());
		campoEndereco.setText(cliente.getEndereco());
		
		String telefone = Long.toString(cliente.getTelefone());
		if (!telefone.equals("0")) {
			campoTelefone.setText(telefone);
		}
		
		String celular = Long.toString(cliente.getCelular());
		if (!celular.equals("0")) {
			campoCelular.setText(celular);
		}
	}
}
