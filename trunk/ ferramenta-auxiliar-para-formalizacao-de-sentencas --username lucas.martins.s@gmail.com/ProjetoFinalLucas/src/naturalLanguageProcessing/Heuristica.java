package naturalLanguageProcessing;

import java.util.ArrayList;
import java.util.List;


public class Heuristica 
{
	private String _inputText;
	private List<DuplaTextoProcessado> _duplas;
	private List<ProposicaoMolecular> _frases;
	private List<ProposicaoAtomica> _atomicas;
	private DicionarioDeConectivos _dicionario;
	
	public Heuristica( String text )
	{
		_inputText = text;
		_duplas = this.processaTexto(_inputText);
		_dicionario = DicionarioDeConectivos.getInstance();
		_frases = this.obtemListaDeProposicoes(_duplas);
		_atomicas = this.obtemProposicoesAtomicas(_frases);
	}
	
	public List<ProposicaoAtomica> getProposicoesAtomicas()
	{
		return _atomicas;
	}
	
	private List<ProposicaoAtomica> obtemProposicoesAtomicas(List<ProposicaoMolecular> frases)
	{
		List<ProposicaoAtomica> axiomas = new ArrayList<ProposicaoAtomica>();
		for ( ProposicaoMolecular frase: frases) 
		{
			frase.findAndChangeConectivos(_dicionario);
			frase.findSubjects();
			frase.findVerb();
			frase.findPredicate();
			for ( DuplaTextoProcessado dp : frase._subjects)
			{
				axiomas.add(new ProposicaoAtomica(frase._verb, dp, frase._predicates, frase._corpo));
			}
			 
		}
		return axiomas;
	}
	
	
	
	/**
	 * 
	 *  < Aprimorar este metodo > 
	 * 
	 *  Retorna uma lista de Lista de proposições. 
	 * 
	 * @param duplas
	 * @return
	 */
	private List<ProposicaoMolecular> obtemListaDeProposicoes ( List<DuplaTextoProcessado> duplas)
	{
		List<ProposicaoMolecular> frases = new ArrayList<ProposicaoMolecular>();
		List<DuplaTextoProcessado> frase = new ArrayList<DuplaTextoProcessado>();
		for ( DuplaTextoProcessado item: duplas) 
		{
			 if ( item._palavra.equals("."))
			 {
				
				 frases.add(new ProposicaoMolecular(frase));
				 frase.clear();
				 
			 }
			 else if (item._palavra == "?")
			 {
				// frases.add(new ProposicaoMolecular(frase)); Expressões não declarativas não podem ser proposicoes.
				 frase.clear();
			 }
			 else if (item._palavra == "!")
			 {
				 //frases.add(new ProposicaoMolecular(frase));  Expressões não declarativas não podem ser proposicoes.
				 frase.clear();
			 }
			 else
				 frase.add(item);
		}
		
		return frases;
	}
	
	/**
	 * 
	 *  Retorna uma lista de objetos do tipo DuplaTextoProcessado que são duplas texto-tag, ( Text-SignificadoF-EXT ).
	 * 
	 *  A partir desta lista irei encontrar as proposicoes. E das que forem moleculares irei separar proposicoes atomicas.
	 * 
	 * @param text Texto já processado pelo F-EXT que gerará a lista de Duplas.
	 * @return Lista do objeto DuplaTextoProcessado que é um uma dupla ( texto / Significado )
	 */
	private List<DuplaTextoProcessado> processaTexto( String text )
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
