package telas;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import helpers.Helper;
import interfaces.TelaInteface;

public class Index implements ActionListener {
	private JFrame janela = new JFrame("Projeto Interdisciplinar IV");
	private JMenuBar barraMenu = new JMenuBar();

	JDesktopPane painel = new JDesktopPane();

	private JMenu menuCliente = new JMenu("Cliente");
	private JMenuItem opcaoListarClientes = new JMenuItem("Listar");
	private JMenuItem opcaoCadastrarCliente = new JMenuItem("Cadastrar");

	private JMenu menuCompra = new JMenu("Compra");
	private JMenuItem opcaoListarCompras = new JMenuItem("Listar");
	private JMenuItem opcaoCadastrarCompra = new JMenuItem("Cadastrar");
	
	private static Index instancia = null;
	
	private Index() {
		janela.setSize(Helper.DEFAULT_FRAME_WIDTH, Helper.DEFAULT_FRAME_HEIGTH);
		janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		janela.setJMenuBar(barraMenu);
		janela.getContentPane().add(painel);
		
		barraMenu.add(menuCliente);
		menuCliente.add(opcaoCadastrarCliente);
		menuCliente.add(opcaoListarClientes);
		
		barraMenu.add(menuCompra);
		menuCompra.add(opcaoCadastrarCompra);
		menuCompra.add(opcaoListarCompras);
		
		opcaoCadastrarCliente.addActionListener(this);
		opcaoListarClientes.addActionListener(this);
		opcaoCadastrarCompra.addActionListener(this);
		opcaoListarCompras.addActionListener(this);
		
		opcaoListarClientes.doClick();
		
		Helper.centralizarJanela(janela);
		janela.setVisible(true);
		janela.setResizable(false);
	}
		
	public static Index getInstancia() {
		if(instancia == null) {
	         instancia = new Index();
		}
		
	   return instancia;
	}
	
	public void setTelaFilha(JInternalFrame telaFilha) {
		int cor = 238;
		painel.setBackground(new Color(cor, cor, cor));
		painel.removeAll();
		painel.add(telaFilha);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		TelaInteface telaFilha = null;
		
		if (e.getSource() == opcaoCadastrarCliente) {
			telaFilha = new FormularioCliente();
		} else if (e.getSource() == opcaoListarClientes) {
			telaFilha = new ListagemClientes();
		} else if (e.getSource() == opcaoCadastrarCompra) {
			telaFilha = new CadastrarCompra();
		} else {
			telaFilha = new ListagemCompra();
		}
		
		setTelaFilha(telaFilha.getConteudoTela());
	}
}