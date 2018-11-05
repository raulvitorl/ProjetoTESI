package br.ufac.academico.gui;

import javax.swing.*; 					//importando classes do Swing

import br.ufac.academico.db.*;
import br.ufac.academico.entity.Vendas;
import br.ufac.academico.exception.DataBaseGenericException;
import br.ufac.academico.exception.DataBaseNotConnectedException;
import br.ufac.academico.exception.EntityNotExistException;
import br.ufac.academico.exception.EntityTableIsEmptyException;
import br.ufac.academico.logic.*;

import java.awt.*; 						//importando classes do AWT
import java.awt.event.*; 				//importando classes de EVENTOS do AWT
import java.util.List;						//importando classes do JDBC
import java.util.ArrayList;						//importando classes do JDBC

class VendasConsulta extends JFrame {

	private Conexao cnx = null;
	Mercado pai;
	VendasLogic pl;

	VendasCadastro vendasCadastro;

	JTable tblQuery;
	JPanel pnlSuperior, pnlControles, pnlBotoes, pnlOperacoes, pnlRotulos, pnlChaves,pnlBotaoGroup,pnlCampos;
	//JComboBox cmbChaves;
	ButtonGroup gbOpcoes = new ButtonGroup();
	JRadioButton rbNomeCliente = new JRadioButton("Nome Cliente");
	JRadioButton rbCodigoCliente = new JRadioButton("C�digo Cliente");
	JRadioButton rbNomeAtendente = new JRadioButton("Nome Atendente");
	JRadioButton rbCodigoAtendente = new JRadioButton("Codigo Atendente");
	JRadioButton rbNomeBanco = new JRadioButton("Nome Banco");
	JRadioButton rbCodigoBanco = new JRadioButton("Codigo Banco");
	
	JTextField fldValor;
	JButton btnBuscar, btnSair, btnIncluir, btnEditar, btnExcluir;

	AcaoBuscar actBuscar = new AcaoBuscar();
	AcaoIncluir actIncluir = new AcaoIncluir();
	AcaoEditar actEditar = new AcaoEditar();	
	AcaoExcluir actExcluir = new AcaoExcluir();	
	AcaoSair actSair = new AcaoSair();	

	static final String imagesPath = new String("images/");	

	VendasConsulta(JFrame framePai, Conexao conexao){ // método construtor
		super("Consulta de Vendas"); // chamando construtor da classe mãe
		setSize(900, 450);				// definindo dimensões da janela
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		pai = (Mercado) framePai;		
		cnx = conexao;
		pl = new VendasLogic(cnx);
		vendasCadastro = new VendasCadastro(this, cnx);
		setLocationRelativeTo(null);
		
		

		tblQuery = new JTable(0,0);
		tblQuery.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tblQuery.addMouseListener(new HabilitarEdicaoExclusao());
		setLocationRelativeTo(null);
		
		
		fldValor = new JTextField();
		
		
		pnlBotaoGroup = new JPanel(new GridLayout(2,3,6,6));
		pnlBotaoGroup.add(rbNomeCliente);
		pnlBotaoGroup.add(rbCodigoCliente);
		pnlBotaoGroup.add(rbNomeAtendente);
		pnlBotaoGroup.add(rbCodigoAtendente);
		pnlBotaoGroup.add(rbNomeBanco);
		pnlBotaoGroup.add(rbCodigoBanco);
		
		
		
		pnlRotulos = new JPanel(new GridLayout(2,2,6,6));
		pnlRotulos.add(new JLabel("Filtrar por"));
		pnlRotulos.add(pnlBotaoGroup);
		
		pnlCampos = new JPanel(new GridLayout(1,1,6,6));
		pnlCampos.add(fldValor);
		
		pnlRotulos.add(new JLabel("Busca"));
		pnlRotulos.add(pnlCampos);
		
		gbOpcoes.add(rbNomeCliente);
		gbOpcoes.add(rbCodigoCliente);
		gbOpcoes.add(rbNomeAtendente);
		gbOpcoes.add(rbCodigoAtendente);
		gbOpcoes.add(rbNomeBanco);
		gbOpcoes.add(rbCodigoBanco);

		pnlControles = new JPanel(new BorderLayout(6,6));
		pnlControles.add(pnlRotulos/*, BorderLayout.WEST*/);
		//pnlControles.add(pnlChaves);

		btnBuscar = new JButton(actBuscar);
		btnSair = new JButton(actSair);
		btnIncluir = new JButton(actIncluir);
		btnEditar = new JButton(actEditar);
		btnEditar.setEnabled(false);
		btnExcluir = new JButton(actExcluir);
		btnExcluir.setEnabled(false);

		pnlOperacoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 6, 6));
		pnlOperacoes.add(btnIncluir);
		pnlOperacoes.add(btnEditar);
		pnlOperacoes.add(btnExcluir);

		pnlBotoes = new JPanel(new GridLayout(2,1));
		pnlBotoes.add(btnBuscar);
		pnlBotoes.add(btnSair);

		pnlSuperior = new JPanel(new BorderLayout());
		pnlSuperior.add(pnlBotoes, BorderLayout.EAST);
		pnlSuperior.add(pnlControles);

		buscar();
		add(pnlSuperior, BorderLayout.NORTH);
		add(new JScrollPane(tblQuery));
		add(pnlOperacoes, BorderLayout.SOUTH);		

	} //Fim do método construtor

	class AcaoBuscar extends AbstractAction{

		AcaoBuscar(){
			super("Buscar");
			putValue(MNEMONIC_KEY, KeyEvent.VK_B);
			putValue(SHORT_DESCRIPTION, 
					"Buscar registros de Fornecedores!");
			putValue(SMALL_ICON, 
					new ImageIcon(imagesPath+"general/Search24.gif"));

		}

		@Override
		public void actionPerformed(ActionEvent e) {

			buscar();

		}

	}

	class AcaoIncluir extends AbstractAction{

		AcaoIncluir(){
			super("Incluir");
			putValue(MNEMONIC_KEY, KeyEvent.VK_I);
			putValue(SHORT_DESCRIPTION, 
					"Incluir registro de Centro!");
			putValue(SMALL_ICON, 
					new ImageIcon(imagesPath+"general/New24.gif"));

		}

		@Override
		public void actionPerformed(ActionEvent e) {

			vendasCadastro.incluir();

		}

	}

	class AcaoEditar extends AbstractAction{

		AcaoEditar(){
			super("Editar");
			putValue(MNEMONIC_KEY, KeyEvent.VK_E);
			putValue(SHORT_DESCRIPTION, 
					"Editar registro de Centro!");
			putValue(SMALL_ICON, 
					new ImageIcon(imagesPath+"general/Edit24.gif"));

		}

		@Override
		public void actionPerformed(ActionEvent e) {

			int codigo;
			codigo = (int) tblQuery.getValueAt(tblQuery.getSelectedRow(), 0);
			try {
				vendasCadastro.editar(codigo);
			} catch (EntityTableIsEmptyException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}

	}

	class AcaoExcluir extends AbstractAction{

		AcaoExcluir(){
			super("Excluir");
			putValue(MNEMONIC_KEY, KeyEvent.VK_X);
			putValue(SHORT_DESCRIPTION, 
					"Excluir registro de Centro!");
			putValue(SMALL_ICON, 
					new ImageIcon(imagesPath+"general/Delete24.gif"));

		}

		// PATRICAMENTE IGUAL AO DA AcaoEditar
		@Override
		public void actionPerformed(ActionEvent e) {

			int codigo;
			codigo = (int) tblQuery.getValueAt(tblQuery.getSelectedRow(), 0);
			try {
				vendasCadastro.excluir(codigo);
			} catch (EntityTableIsEmptyException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}

	}	

	class AcaoSair extends AbstractAction{

		AcaoSair(){
			super("Sair");
			putValue(MNEMONIC_KEY, KeyEvent.VK_R);
			putValue(SHORT_DESCRIPTION, 
					"Fecha consulta de vendas!");
			putValue(SMALL_ICON, 
					new ImageIcon(imagesPath+"general/Stop24.gif"));

		}

		@Override
		public void actionPerformed(ActionEvent e) {

			VendasConsulta.this.setVisible(false);
			pai.setVisible(true);

		}

	}

	public void buscar() {

		List<Vendas> listaDeVendas = new ArrayList<Vendas>();
		int busca = 0;

		try {

			if(fldValor.getText().equals("")) {
				listaDeVendas = pl.getVendas();
			}else{
				if(rbCodigoCliente.isSelected()){
					try {
						busca = Integer.parseInt(fldValor.getText());
					} catch (NumberFormatException e) {
						JOptionPane.showMessageDialog(null, "Campo invalido para o filtro utilizado");
					}
					listaDeVendas = pl.getVendasPorClienteCodigo(busca);
				}else{
					if(rbNomeAtendente.isSelected()){
						listaDeVendas = pl.getVendasPorAtendenteNome(fldValor.getText());
		
					}else{
						if(rbCodigoAtendente.isSelected()){
							try {
								busca = Integer.parseInt(fldValor.getText());
							} catch (NumberFormatException e) {
								JOptionPane.showMessageDialog(null, "Campo invalido para o filtro utilizado");
							}
							listaDeVendas = pl.getVendasPorAtendenteCodigo(busca);
							
						}else{
							if(rbNomeCliente.isSelected()){
								listaDeVendas = pl.getVendasPorClienteNome(fldValor.getText());
							}else{
								if(rbNomeBanco.isSelected()){
									listaDeVendas = pl.getVendasPorBancoNome(fldValor.getText());
								}else{
									try {
										busca = Integer.parseInt(fldValor.getText());
									} catch (NumberFormatException e) {
										JOptionPane.showMessageDialog(null, "Campo invalido para o filtro utilizado");
									}
									listaDeVendas = pl.getVendasPorBancoCodigo(busca);
								}
							}
						}
					}
				}

			}

			tblQuery.setModel(new VendasTableModel(listaDeVendas));
			btnEditar.setEnabled(false);
			btnExcluir.setEnabled(false);

		} catch (DataBaseGenericException | 
				DataBaseNotConnectedException | 
				EntityTableIsEmptyException | 
				EntityNotExistException e) 
		{
			JOptionPane.showMessageDialog(this, e.getMessage(), 
					"Consulta de Vendas", JOptionPane.ERROR_MESSAGE);
		}

	}

	class HabilitarEdicaoExclusao extends MouseAdapter {

		public void mousePressed(MouseEvent e) {
			if (tblQuery.getSelectedRow() >= 0) {
				btnEditar.setEnabled(true);
				btnExcluir.setEnabled(true);
			}else {
				btnEditar.setEnabled(false);
				btnExcluir.setEnabled(false);
			}

		}

	}	

}//Fim da classe ProfessorConsulta



