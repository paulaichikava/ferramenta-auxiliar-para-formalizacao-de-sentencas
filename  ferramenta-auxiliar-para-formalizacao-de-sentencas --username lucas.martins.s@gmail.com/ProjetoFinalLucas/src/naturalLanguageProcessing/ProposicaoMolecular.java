package naturalLanguageProcessing;

import java.util.ArrayList;
import java.util.List;

public class ProposicaoMolecular extends Proposicao
{

	public List<DuplaTextoProcessado> _conectivos; // Conectivos
	public String _conectivoPrincipal; // Conectivo Principal
	
	
	public ProposicaoMolecular(List<DuplaTextoProcessado> corpo)
	{
		_corpo = new ArrayList<DuplaTextoProcessado>(corpo);
		_predicates = new ArrayList<DuplaTextoProcessado>();
		_subjects = new ArrayList<DuplaTextoProcessado>();
		_conectivos = new ArrayList<DuplaTextoProcessado>();
	}
	
	
	/**
	 *  < Obsoleto >
	 *  Encontra sujeitos nesta frase. Enquanto isto sujeito s�o apenas nomes pr�prios.
	 * 
	 */
	public void findSubjects()
	{
		for ( DuplaTextoProcessado dp: _corpo)
		{
			if ( dp._tag.contains("NPROP")) 
			{
				_subjects.add(dp); // Talvez eu coloque uma pergunta aqui!  ( Pergunta ao verbo: "Quem foi a praia?" )
			}
		}
	}
	
	
	/**
	 * 
	 * Este m�todo procura por todos os conectivos da proposi��o no dicionario e substitui os que conseguir encontrar.
	 * Caso n�o consiga encontrar ele far� uma pergunta ao usu�rio para tentar encontrar.
	 * 
	 * @param dicionario
	 */
	public void findAndChangeConectivos( DicionarioDeConectivos dicionario)
	{
		for ( DuplaTextoProcessado dp: _corpo)
		{
			if ( dp._tag.contains("KC")) // 
			{
				_conectivos.add(dp); 
			}
		}
		
		// N�o possu� conectivos a proposi��o � atomica.
		if ( _conectivos.size() == 0)
		{
			_conectivoPrincipal = null;
		}
		else if (_conectivos.size() == 1 )
			_conectivoPrincipal = _conectivos.get(0)._palavra;
		else // Caso contrario.
		{
			for ( DuplaTextoProcessado dp: _conectivos)
			{
				if ( dicionario.ObtemConectivo(dp._palavra) == null )
				{
					// Caso n�o consigo encontrar o conectivo.
					
					//#### IMPLEMENTAR ISTO DAQUI!!!
					
				}
				else // Encontrei o conectivo.
				{
					for ( DuplaTextoProcessado dp2: _corpo) // Substituo no corpo da proposicao o conectivo encontrado.
					{
						if ( dp2 == dp )
							dp2._palavra = dicionario.ObtemConectivo(dp._palavra);
					}
				}
				
			}
		}
	}

	/**
	 * 
	 * Este m�todo procura comparar esta proposi��o molecular a algum padr�o pr� definido. Para assim formaliza-la.
	 * 
	 */
	private String IdentifyPattern( DicionarioDePadroes dicionario)
	{
		String proposicao = "";
		
		// Monto uma string com todas as palavras do corpo da proposicao.
		for ( DuplaTextoProcessado dp: _corpo)
		{
			if ( dp._tag.equals("V") || dp._tag.equals("KC"))
				proposicao += dp._tag;
			else
				proposicao += dp._palavra;
			
			proposicao += " ";
		}
		proposicao = proposicao.trim(); // Remove espacos em branco.
		
	//	proposicao = DuplaTextoProcessado.convertListDuplaProcessadoToString(_corpo);
		
		int count = dicionario.getNumberOfElements();
		
		
		// Neste for eu procuro por um match na express�o do meu dicionario de padroes. Se eu encontrar significa que eu consigo formalizar a senten�a.
		for ( int i = 0; i <= count; i++)
		{
			if ( proposicao.matches(dicionario.ObtemIdDoElementoDoDicionario(i)))
				return dicionario.ObtemIdDoElementoDoDicionario(i);
		}
		
		return null;
	}
	
	/**
	 *   Este m�todo retorna a forma l�gica desta proposicao. ( Ainda n�o est� conclu�do )
	 *  
	 * @param dicionario
	 * @return
	 */
	public String createLogicForm(DicionarioDePadroes dicionario)
	{
	    // Pattern � a ProposicaoTag desta proposi��o.
		String idRegexp = IdentifyPattern(dicionario);
		
		ProposicaoTag t = dicionario.ObtemProposicaoTag(idRegexp);
		
		return t.getLogicFormDestaProposicaoTag();
		
	}
	
	/**
	 *   Este m�todo retorna uma lista com as proposi��es at�micas desta proposi��o. 
	 * @param dicionarioPadroes
	 * @return
	 */
	public List<ProposicaoAtomica> getListadeProposicoesAtomicas(DicionarioDePadroes dicionarioPadroes, DicionarioDeConectivos dicionarioConec)
	{
		String corpo = ""; // Lista de DuplaTextoProcessado
		// Monto uma string com todas as palavras do corpo da proposicao. � adicionado o n�mero das proposic�es.
		int m = 0; int n = 0;
		List<String> lstV = new ArrayList<String>();
		List<String> lstKC = new ArrayList<String>();
		for ( DuplaTextoProcessado dp: _corpo)
		{
			corpo += " ";
			if ( dp._tag.equals("V") )
			{
				corpo += dp._tag + Integer.toString(m);
				lstV.add(dp._palavra);
				m++;
			}
			else if (dp._tag.equals("KC") )
			{
				corpo += dp._tag + Integer.toString(n);
				lstKC.add(dp._palavra);
				n++;
			}
			else
				corpo += dp._palavra;	
			
		}
		corpo = corpo.trim(); // Remove espacos em branco.
		
		String proposicoesAtomicas = "";
		List<ProposicaoAtomica> lst;
		
		// Pattern � a ProposicaoTag desta proposi��o.
		String idRegexp = IdentifyPattern(dicionarioPadroes);
		ProposicaoTag Pattern = dicionarioPadroes.ObtemProposicaoTag(idRegexp);
			
		// N�mero de proposic�es atomicas que termos. ( Dado pelo Pattern)
		int numeroDeProposicoes = Pattern.getNumeroDeProposicoesAtomicas();
		proposicoesAtomicas = ""; // Esta variavel conter� todas as proposicoes at�micas desta proposicao concatenadas.
		
		// Este for executa numeroDeProposicoes vezes.
		for ( int i = 0; i < numeroDeProposicoes; i++) 
		{
			proposicoesAtomicas += corpo.replaceFirst(Pattern.getRexpForNprop(i), "");
		}
		
		// Devo trocar os nomes KC0, KC1 e etc.. pelos conectivos e os Verbos V0, V1, e etc.. pelos verbos
		m = 0; n = 0;
		for( String v : lstV)
		{
			proposicoesAtomicas = proposicoesAtomicas.replace("V"+Integer.toString(m), v);
			m++;
		}
		for( String kc : lstKC)
		{
			proposicoesAtomicas = proposicoesAtomicas.replace("KC"+Integer.toString(n), kc);
			n++;
		}
		
		
		// Neste momento eu tenho a string proposicoesAtomicas com todas as proposi��es atomicas. Vou separar elas em proposicoes atomicas.
		String proposicoesAtomicasEnriquecidas = Heuristica.enriquecerComFexTexto(proposicoesAtomicas);
		// Agora eu tenho uma lista de DuplaTextoProcessado com todas as proposi��es at�micas.
		List<DuplaTextoProcessado> listDuplas = DuplaTextoProcessado.processaTexto(proposicoesAtomicasEnriquecidas);
		List<ProposicaoMolecular> listMol = Proposicao.obtemListaDeProposicoes(listDuplas);
		lst = Proposicao.obtemProposicoesAtomicas(listMol, dicionarioConec);
		return lst;
	}
	
	/**
	 *  < Obsoleto >
	 * 
	 *  Encontra o verbo da proposi��o.
	 */
	public void findVerb()
	{
		for ( DuplaTextoProcessado dp: _corpo)
		{
			if ( dp._tag.contains("V"))
			{
				_verb =  dp; // Talvez eu coloque uma pergunta aqui!  ( "Este � mesmo o verbo?" )
			}
		}
	}
	
	
	/**
	 *  < Obsoleto >
	 *  
	 *  Encontra os predicados da proposi��o.
	 */
	public void findPredicate()
	{
		List<DuplaTextoProcessado> auxList  = new ArrayList<DuplaTextoProcessado>(_corpo);
		
		// Se ainda n�o tiver encontrado o verbo e o sujeito. Eu encontro eles agora.
		
		if ( _verb ==  null)
			findVerb();
		if ( _subjects.size() == 0)
			findSubjects();
		
		// Removo os sujeitos da minha frase.
		for ( DuplaTextoProcessado sub : _subjects  )
		{
			auxList.remove(sub);
		}
		
		
		// Removo o verbo 
		
		auxList.remove(_verb);
		
		
		// Acredito que tenha sobrado o predicado.
		
		for ( DuplaTextoProcessado dp: auxList)
		{
			if ( _corpo.indexOf(dp) > _corpo.indexOf(_verb) )  // Adiciono aos predicados todo mundo que sobrou e vem depois do verbo.
				_predicates.add(dp);
		}
	}
	
	
	

	
}