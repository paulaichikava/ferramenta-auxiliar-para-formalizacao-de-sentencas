package naturalLanguageProcessing;

import java.util.ArrayList;
import java.util.List;


public class Heuristica 
{
	private String _inputText;
	private List<DuplaTextoProcessado> _duplas;
	private List<ProposicaoMolecular> _proposicoes;
	private List<ProposicaoAtomica> _atomicas;
	private DicionarioDeConectivos _dicionario;
	
	public Heuristica( String text )
	{
		_inputText = text;
		_duplas = this.processaTexto(_inputText);
		_dicionario = DicionarioDeConectivos.getInstance();
		_proposicoes = this.obtemListaDeProposicoes(_duplas); // Aqui populo a lista de frases com proposições moleculares ou atomicas.
		_atomicas = this.obtemProposicoesAtomicas(_proposicoes);  
	}
	
	public List<ProposicaoAtomica> getProposicoesAtomicas()
	{
		return _atomicas;
	}
	
	private List<ProposicaoAtomica> obtemProposicoesAtomicas(List<ProposicaoMolecular> proposicoes)
	{
		List<ProposicaoAtomica> atomicas = new ArrayList<ProposicaoAtomica>();
		for ( ProposicaoMolecular proposicao: proposicoes) 
		{
			proposicao.findAndChangeConectivos(_dicionario);
			if ( proposicao._conectivoPrincipal == null)
			{
				if ( proposicao._corpo.get(0)._palavra == " ")// Removo o espaco no inicio da proposicao.
					proposicao._corpo.remove(0);
				
				atomicas.add(new ProposicaoAtomica(null, proposicao._corpo));
			}
			
			//frase.findSubjects();
			//frase.findVerb();
			//frase.findPredicate();
			//for ( DuplaTextoProcessado dp : frase._subjects)
			//{
			//	axiomas.add(new ProposicaoAtomica(frase._verb, dp, frase._predicates, frase._corpo));
			//}
			 
		}
		return atomicas;
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
				 frase.add(item);
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
