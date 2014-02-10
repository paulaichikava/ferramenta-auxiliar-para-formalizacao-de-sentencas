package naturalLanguageProcessing;

import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

public class Heuristica 
{
	private String _inputText;
	private List<DuplaTextoProcessado> _duplas;
	private List<Frase> _frases;
	private List<Axioma> _axiomas;
	
	public Heuristica( String text )
	{
		_inputText = text;
		_duplas = this.separaTexto(_inputText);
		_frases = this.obtemListaDeFrases(_duplas);
		_axiomas = this.FindAxioms(_frases);
	}
	
	public List<Axioma> getAxioms()
	{
		return _axiomas;
	}
	
	private List<Axioma> FindAxioms(List<Frase> frases)
	{
		List<Axioma> axiomas = new ArrayList<Axioma>();
		for ( Frase frase: frases) 
		{
			frase.findSubjects();
			frase.findVerb();
			frase.findPredicate();
			for ( DuplaTextoProcessado dp : frase._subjects)
			{
				axiomas.add(new Axioma(frase._verb, dp, frase._predicates, frase._corpo));
			}
			 
		}
		return axiomas;
	}
	
	
	
	/**
	 * 
	 *  Retorna uma lista de Lista de Frases
	 * 
	 * @param duplas
	 * @return
	 */
	private List<Frase> obtemListaDeFrases ( List<DuplaTextoProcessado> duplas)
	{
		List<Frase> frases = new ArrayList<Frase>();
		List<DuplaTextoProcessado> frase = new ArrayList<DuplaTextoProcessado>();
		for ( DuplaTextoProcessado item: duplas) 
		{
			 if ( item._palavra.equals("."))
			 {
				 frases.add(new Frase(frase));
				 frase.clear();
			 }
			 else if (item._palavra == "?")
			 {
				 frases.add(new Frase(frase));
				 frase.clear();
			 }
			 else if (item._palavra == "!")
			 {
				 frases.add(new Frase(frase));
				 frase.clear();
			 }
			 else
				 frase.add(item);
		}
		
		return frases;
	}
	
	/**
	 * 
	 *  Retorna uma lista de objetos do tipo DuplaTextoProcessado que são uma lista de duplas, ( Text-SignificadoF-EXT ).
	 * 
	 *  A partir desta lista irei encontrar frases. E das frases irei separar axiomas.
	 * 
	 * @param text Texto já processado pelo F-EXT que gerará a lista de Duplas.
	 * @return Lista do objeto DuplaTextoProcessado que é um uma dupla ( texto / Significado )
	 */
	private List<DuplaTextoProcessado> separaTexto( String text )
	{
		 String[] itens;
		 String[] aux;
		 List<DuplaTextoProcessado> listParts = new ArrayList<DuplaTextoProcessado>();
		 int i = 0;
		 
		 itens =  text.split("\n");
		 
		 System.out.println(text);
		
		 for ( String elem: itens) 
		 {
			 if ( i > 1 & i < itens.length  )
			 {
				aux = elem.split(" ");
				DuplaTextoProcessado part = new DuplaTextoProcessado(aux[0], aux[aux.length-1]);
				listParts.add(part);
			 }i++;
		 }
		 
		 return listParts;
		 
	}
	
	

}
