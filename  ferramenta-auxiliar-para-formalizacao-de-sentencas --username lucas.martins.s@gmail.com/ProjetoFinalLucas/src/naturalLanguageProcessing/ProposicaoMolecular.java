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
		
		// N�o possu� conectivos a proposi��o � atomica. Ou temos um <Se Entao> ou <Se e somente se>
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
			if ( dp._tag.equals("V") || dp._tag.equals("KC") || dp._tag.equals("VAUX"))
				proposicao += dp._tag;
			else
				proposicao += dp._palavra;
			
			proposicao += " ";
		}
		proposicao = proposicao.trim(); // Remove espacos em branco.
		
	//	proposicao = DuplaTextoProcessado.convertListDuplaProcessadoToString(_corpo);
		
		int count = dicionario.getNumberOfElements();
		
		
		// Neste for eu procuro por um match na express�o do meu dicionario de padroes. Se eu encontrar significa que eu consigo formalizar a senten�a.
		for ( int i = count-1; i >= 0; i--)
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
	public String createLogicForm(DicionarioDePadroes dicionarioPadroes, DicionarioDeConectivos dicionarioConec)
	{	
		String formaLogica = this.coreDaCriacaoDeFormasLogicas(dicionarioPadroes, dicionarioConec);
		
		return formaLogica;
		
	}
	
	/**
	 *   M�todo recursivo que retorna uma lista com as proposi��es at�micas desta proposi��o. 
	 * @param dicionarioPadroes
	 * @return
	 */
	public List<ProposicaoAtomica> getListadeProposicoesAtomicas(DicionarioDePadroes dicionarioPadroes, DicionarioDeConectivos dicionarioConec)
	{
		// Teremos uma recurs�o aqui. Talvez esta linha abaixo tenha que ser um parametro!
		List<ProposicaoAtomica> lst = new ArrayList<ProposicaoAtomica>();

		if ( this.isAtomic(this) )
		{
			// Caso BASE!
			lst.add(new ProposicaoAtomica(_corpo));
			return lst;
		}
		// Obtenho string com as proposicoes atomicas nesse nivel.
		String proposicoesAtomicas = this.getStringdeProposicoesAtomicasSemSeAprofundar(dicionarioPadroes, dicionarioConec);
		// Transformo em uma lista de dupla texto processado.
		List<DuplaTextoProcessado> listDuplas = DuplaTextoProcessado.recuperaTagsDaString(proposicoesAtomicas, _corpo);	
		// Obtenho uma lista de proposicoes moleculares a partir da lista de DuplaTextoPocessado acima.
		List<ProposicaoMolecular> listMol = Proposicao.obtemListaDeProposicoes(listDuplas);
		
		// Para cada proposicao molecular da lista acima chamo esta fun��o novamente.
		for ( ProposicaoMolecular mol : listMol)
		{
			lst.addAll(mol.getListadeProposicoesAtomicas(dicionarioPadroes, dicionarioConec));
		}
		
		return lst;
	}
	
	/**
	 *    Este m�todo obtem uma String com todas as expressoes atomicas que consigo capturar sem me aprofundar. (  Sem usar recurss�o )
	 *    Exemplo:
	 *            Maria e Jorge e Roberto compram doces. Me retornar�: " Maria compram doces. Jorge e Roberto compram doces. "
	 * @param dicionarioPadroes
	 * @param dicionarioConec
	 * @return
	 */
	private String getStringdeProposicoesAtomicasSemSeAprofundar(DicionarioDePadroes dicionarioPadroes, DicionarioDeConectivos dicionarioConec)
	{
		String corpo = "";
		// Monto uma string com todas as palavras do corpo da proposicao. � adicionado o n�mero das proposic�es.
		int m = 0; int n = 0; int o = 0;
		List<String> lstV = new ArrayList<String>();
		List<String> lstKC = new ArrayList<String>();
		List<String> lstVAUX = new ArrayList<String>();
		for ( DuplaTextoProcessado dp: _corpo)
		{
			if ( dp._tag.equals("V") )
			{
				corpo += " ";
				corpo += dp._tag + Integer.toString(m);
				lstV.add(dp._palavra);
				m++;
			}
			else if (dp._tag.equals("KC") )
			{
				corpo += " ";
				corpo += dp._tag + Integer.toString(n);
				lstKC.add(dp._palavra);
				n++;
			}
			else if (dp._tag.equals("VAUX") )
			{
				corpo += " ";
				corpo += dp._tag + Integer.toString(o);
				lstVAUX.add(dp._palavra);
				o++;
			}
			else if (dp._tag.equals("\\."))
				corpo += dp._palavra;	
			else
			{
				corpo += " ";
				corpo += dp._palavra;	
			}
			
		}
		corpo = corpo.trim(); // Remove espacos em branco.
		
		String proposicoesAtomicas = "";
				
		// Pattern � a ProposicaoTag desta proposi��o.
		String idRegexp = IdentifyPattern(dicionarioPadroes);
		ProposicaoTag Pattern = dicionarioPadroes.ObtemProposicaoTag(idRegexp);
			
		// N�mero de proposic�es atomicas que termos. (Dado pelo Pattern)
		int numeroDeProposicoes = Pattern.getNumeroDeProposicoesAtomicas();
		proposicoesAtomicas = ""; // Esta variavel conter� todas as proposicoes at�micas desta proposicao concatenadas.
		
		// Este for executa numeroDeProposicoes vezes.
		for ( int i = 0; i < numeroDeProposicoes; i++) 
		{
			// Obtem a primeira Proposicao
			proposicoesAtomicas += " " + corpo.replaceFirst(Pattern.getRexpForNprop(i), " ");
			
			// Devo trocar os nomes KC0, KC1 e etc.. pelos conectivos e os Verbos V0, V1, e etc.. pelos verbos
			m = 0; n = 0; o = 0;
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
			for( String vaux : lstVAUX)
			{
				proposicoesAtomicas = proposicoesAtomicas.replace("VAUX"+Integer.toString(o), vaux);
				o++;
			}
			
		}
		
		proposicoesAtomicas = proposicoesAtomicas.trim();
		proposicoesAtomicas = proposicoesAtomicas.replaceAll("  ", " ");
		return proposicoesAtomicas;
	}
	
	private String coreDaCriacaoDeFormasLogicas(DicionarioDePadroes dicionarioPadroes, DicionarioDeConectivos dicionarioConec)
	{
		// Caso base
		if ( this.isAtomic(this) )
		{
			return "[X]";
		}
		
		String logicForm = "";
		String operador = "";
		// Obtenho string com as proposicoes atomicas nesse nivel.
		String proposicoesAtomicas = this.getStringdeProposicoesAtomicasSemSeAprofundar(dicionarioPadroes, dicionarioConec);
		// Transformo em uma lista de dupla texto processado.
		List<DuplaTextoProcessado> listDuplas = DuplaTextoProcessado.recuperaTagsDaString(proposicoesAtomicas, _corpo);	
		// Obtenho uma lista de proposicoes moleculares a partir da lista de DuplaTextoPocessado acima.
		List<ProposicaoMolecular> listMol = Proposicao.obtemListaDeProposicoes(listDuplas);
		
		
	    // Pattern � a ProposicaoTag desta proposi��o.
		String idRegexp = this.IdentifyPattern(dicionarioPadroes);
		ProposicaoTag Pattern = dicionarioPadroes.ObtemProposicaoTag(idRegexp);
		
		for ( ProposicaoMolecular p : listMol)
		{
			logicForm +=  p.createLogicForm(dicionarioPadroes, dicionarioConec) + " " +  Pattern.getOperadorLogico() + " " ;
		}
		
		// Caso esteja com problemas para gerar a forma l�gica correta checar se o operador que vc est� usando n�o precisa ser escapado.
		if ( Pattern.getOperadorLogico().equals("EOU"))
		{
			if ( !_conectivos.isEmpty() )
			{
				operador = _conectivos.get(0)._palavra;
				_conectivos.remove(0);
			}
		}	
		else
			operador = Pattern.getOperadorLogico();
			
		logicForm = logicForm.replaceFirst(operador+" $", "");
		
		logicForm = logicForm.trim();
		
		return logicForm;
		
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
