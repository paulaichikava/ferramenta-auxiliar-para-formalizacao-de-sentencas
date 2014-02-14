package view;

import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;

import naturalLanguageProcessing.Axioma;
import naturalLanguageProcessing.DuplaTextoProcessado;
import naturalLanguageProcessing.Heuristica;
import fext.FEXT_Handler;

public class MainForm 
{

	 public static void main(String[] args)
	 {
		 FEXT_Handler handler = FEXT_Handler.getInstance();
		 
		 
		 JFrame  frame = new JFrame("Formalizador de sentenças");
		 
		 frame.setSize(800,600);
		 
		 frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 
		 
		 JLabel Label = new JLabel("Digite o texto em portugues abaixo:");

		 frame.add(Label);


		
		JLabel Label2 = new JLabel("Maria comprou um carro. Maria usa verde.");
		String answer = handler.EnrichText("Maria comprou um carro. Maria usa verde.");
		 
		frame.add(Label2);
		
		naturalLanguageProcessing.Heuristica heuristica = new Heuristica(answer);
		
		List<Axioma> ax = new ArrayList<Axioma>(heuristica.getAxioms());
		String resp = "";
		for ( Axioma axioma:ax )
		{
			resp += axioma._subject._palavra; resp += " ";
			resp += axioma._verb;resp += " ";
			for ( DuplaTextoProcessado duplaText : axioma._predicates)
			{
				resp+=duplaText._palavra;resp += " ";
			}
			
		}
		
		JLabel Label3 = new JLabel(resp);
		frame.add(Label3);
		
		 
		frame.setVisible(true);
		 
		frame.setLayout(new FlowLayout());
		 
		
	 }
}
