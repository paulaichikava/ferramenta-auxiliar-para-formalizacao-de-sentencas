package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.BorderLayout;

import javax.swing.SwingConstants;

import java.awt.Font;

import javax.swing.JTextField;

import org.eclipse.wb.swing.FocusTraversalOnArray;

import fext.FEXT_Handler;

import java.awt.Component;

import javax.swing.JSplitPane;
import javax.swing.JPanel;

import java.awt.FlowLayout;
import java.awt.TextArea;

import javax.swing.JButton;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import naturalLanguageProcessing.GerenciadorDeSimbolos;
import naturalLanguageProcessing.ProposicaoAtomica;
import naturalLanguageProcessing.DuplaTextoProcessado;
import naturalLanguageProcessing.Heuristica;
import naturalLanguageProcessing.ProposicaoMolecular;
import net.miginfocom.swing.MigLayout;

import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.AbstractAction;

import java.awt.event.ActionEvent;

import javax.swing.Action;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import lxSuite.lxSuite_Handler;

public class Application {

	private JFrame frmProjetoFinalExemplo;
	private JTextField txtInput;
	
	//Meu código
	private Heuristica _heuristica;
	private FEXT_Handler handlerFext ;
	private lxSuite_Handler handlerLxSuite;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Application window = new Application();
					window.frmProjetoFinalExemplo.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Application() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() 
	{
		frmProjetoFinalExemplo = new JFrame();
		frmProjetoFinalExemplo.setTitle("Projeto Final");
		frmProjetoFinalExemplo.setBounds(100, 100, 800, 600);
		frmProjetoFinalExemplo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		final JRadioButton FEXTwsRadio = new JRadioButton("FEXT");
        final JRadioButton LXSuiteRadio = new JRadioButton("LX Suite");
        
        ButtonGroup group = new ButtonGroup();
        group.add(FEXTwsRadio);
        group.add(LXSuiteRadio);
		
		JLabel lblNewLabel = new JLabel("Insira uma frase em português:");
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		frmProjetoFinalExemplo.getContentPane().setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{lblNewLabel}));
		
		final TextArea textAreaResposta = new TextArea();
		
		JButton btnExecutar = new JButton("Executar");
		
		
		txtInput = new JTextField();
		txtInput.setText("Frase em portugues...");
		txtInput.setHorizontalAlignment(SwingConstants.LEFT);
		txtInput.setColumns(10);
		
		JButton btnExecutar_1 = new JButton("Executar");
		// Executar Button Event Code
		btnExecutar_1.addMouseListener(new MouseAdapter() 
		{
			@Override
			public void mouseClicked(MouseEvent arg0) 
			{
			//	JOptionPane.showMessageDialog(null, "My Goodness, this is so concise");
				String answer;
				String tipoLexica;
				if ( FEXTwsRadio.isSelected() )
				{
					handlerFext = FEXT_Handler.getInstance();
					answer =  handlerFext.EnrichText(txtInput.getText());
					tipoLexica = "fext";
				}
				else
				{
					handlerLxSuite = lxSuite_Handler.getInstance();
					answer =  handlerLxSuite.EnrichText(txtInput.getText());
					//answer = "teste";
					tipoLexica = "lxSuite";
				}
				
			    System.out.print(answer);
				GerenciadorDeSimbolos ger;
				_heuristica = new Heuristica(answer, tipoLexica);
				List<ProposicaoMolecular> propMol = new ArrayList<ProposicaoMolecular>(_heuristica.getProposicoes());
				String resp = "";
				int i = 0;
				for ( ProposicaoMolecular proposicao:propMol )
				{
					resp += "Para a proposicao: "+ proposicao.getCorpoDaProposicaoEmString()+ "\n";
					resp += "Temos:" + _heuristica.formaLogicaDaProposicao(i) + "\n";
					resp += "Onde:" + "\n";
					List<ProposicaoAtomica> propAtm = _heuristica.proposicoesAtomicasDaProposicao(i);
					ger = GerenciadorDeSimbolos.getInstance();
					for ( ProposicaoAtomica p : propAtm)
					{
						resp += ger.getSimboloDeProp(p) + ": "+ p.removeDuplaComNao().getCorpoDaProposicaoEmString()+ " ";
					}
					i++;	
					resp += "\n\n";
				}
				textAreaResposta.setText(resp);
				
			}
		});
		GroupLayout groupLayout = new GroupLayout(frmProjetoFinalExemplo.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(79)
					.addComponent(btnExecutar, GroupLayout.PREFERRED_SIZE, 122, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(513, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(39)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(txtInput, GroupLayout.PREFERRED_SIZE, 520, GroupLayout.PREFERRED_SIZE)
							.addGap(33)
							.addComponent(btnExecutar_1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addComponent(textAreaResposta, GroupLayout.PREFERRED_SIZE, 691, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel)
						.addGap(15)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(FEXTwsRadio)
							.addGap(33)
							.addComponent(LXSuiteRadio)))						
					.addGap(54))
		));
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(66)
					.addComponent(lblNewLabel)
					.addGap(50)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
							.addComponent(FEXTwsRadio)
							.addComponent(LXSuiteRadio))
					.addGap(46)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtInput, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnExecutar_1))
					.addGap(73)
					.addComponent(textAreaResposta, GroupLayout.PREFERRED_SIZE, 268, GroupLayout.PREFERRED_SIZE)
					.addGap(58)
					.addComponent(btnExecutar)
					.addContainerGap(233, Short.MAX_VALUE))
		);
		frmProjetoFinalExemplo.getContentPane().setLayout(groupLayout);
	}
	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "Executar");
			putValue(SHORT_DESCRIPTION, "Ao clicar os axiomas para a frase serão mostrados abaixo.");
		}
		public void actionPerformed(ActionEvent e) {
		}
	}
}
