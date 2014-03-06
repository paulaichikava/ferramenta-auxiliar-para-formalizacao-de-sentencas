package naturalLanguageProcessing;

import java.util.ArrayList;
import java.util.List;

public abstract class Proposicao 
{
	public List<DuplaTextoProcessado> _corpo; // Proposicao total.
	public List<DuplaTextoProcessado> _subjects; // Sujeitos
	public DuplaTextoProcessado _verb;  // Verbos
	public List<DuplaTextoProcessado> _predicates; // Predicados
	
	/**
	 *   Obt�m o corpo desta proposi��o no formato de String
	 * @return
	 */
	public String  getCorpoDaProposicaoEmString()
	{
		return DuplaTextoProcessado.convertListDuplaProcessadoToString(_corpo);
	}
	
	
	
	// ### M�todos est�ticos a partir daqui.
	
	
	/**
	 *  Destingue proposi��es moleculares de proposi��es at�micas.  ( Olhar isto mais tarde. Nesta lista existem "falsas" moleculares.)
	 * @param proposicoes
	 * @param dicionario
	 * @return
	 */
	public static List<ProposicaoAtomica> obtemProposicoesAtomicas(List<ProposicaoMolecular> proposicoes, DicionarioDeConectivos dicionario)
	{
		List<ProposicaoAtomica> atomicas = new ArrayList<ProposicaoAtomica>();
		for ( ProposicaoMolecular proposicao: proposicoes) 
		{
			proposicao.findAndChangeConectivos(dicionario);
			if ( proposicao._conectivoPrincipal == null)
			{
				if ( proposicao._corpo.get(0)._palavra.equals(" "))// Removo o espaco no inicio da proposicao.
					proposicao._corpo.remove(0);
				
				atomicas.add(new ProposicaoAtomica(null, proposicao._corpo));
			}
			 
		}
		return atomicas;
	}
	
	
	/**
	 * 
	 *  < Aprimorar este metodo > 
	 * 
	 *  Retorna uma lista de Lista de proposi��es. 
	 * 
	 * @param Uma lista de {@link DuplaTextoProcessado}
	 * @return Uma lista de {@link ProposicaoMolecular}
	 */
	public static List<ProposicaoMolecular> obtemListaDeProposicoes ( List<DuplaTextoProcessado> duplas)
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
				// frases.add(new ProposicaoMolecular(frase)); Express�es n�o declarativas n�o podem ser proposicoes.
				 frase.clear();
			 }
			 else if (item._palavra == "!")
			 {
				 //frases.add(new ProposicaoMolecular(frase));  Express�es n�o declarativas n�o podem ser proposicoes.
				 frase.clear();
			 }
			 else
				 frase.add(item);
		}
		
		return frases;
	}

	
}
