package naturalLanguageProcessing;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

public class ProposicaoMolecular extends Proposicao
{

	public List<DuplaTextoProcessado> _conectivos; // Conectivos
	public String _conectivoPrincipal; // Conectivo Principal
	private List<String> _tags;
	
	
	public ProposicaoMolecular(List<DuplaTextoProcessado> corpo)
	{
		_corpo = new ArrayList<DuplaTextoProcessado>(corpo);
		_predicates = new ArrayList<DuplaTextoProcessado>();
		_subjects = new ArrayList<DuplaTextoProcessado>();
		_conectivos = new ArrayList<DuplaTextoProcessado>();
		this.findConectivos();
	}
	
	/**
	 *  Este método procura todos os conectivos e popula corretamente a lista _conectivos.
	 */
	public void findConectivos()
	{
		_conectivos.clear();
		for ( DuplaTextoProcessado dp: _corpo)
		{
			if ( dp._tag.contains("KC")) // 
			{
				_conectivos.add(dp); 
			}
		}
	}
	/**
	 * 
	 * Este método procura por todos os conectivos da proposição no dicionario e substitui os que conseguir encontrar.
	 * Caso não consiga encontrar ele fará uma pergunta ao usuário para tentar encontrar.
	 * 
	 * @param dicionario
	 */
	public void findAndChangeConectivos( DicionarioDeConectivos dicionario)
	{
		_conectivos.clear();
		for ( DuplaTextoProcessado dp: _corpo)
		{
			if ( dp._tag.contains("KC")|| dp._tag.contains("CJ")) // 
			{
				_conectivos.add(dp); 
			}
		}
		
		// Não possuí conectivos a proposição é atomica. Ou temos um <Se Entao> ou <Se e somente se>
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
					// Caso não consigo encontrar o conectivo.
					
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
	 * Este método procura comparar esta proposição molecular a algum padrão pré definido. Para assim formaliza-la.
	 * 
	 */
	private String IdentifyPattern( DicionarioDePadroes dicionario, GerenciadorDeTags gerenciador)
	{
		String proposicao = "";
		
		// Monto uma string com todas as palavras do corpo da proposicao.
		for ( DuplaTextoProcessado dp: _corpo)
		{
			if ( gerenciador.getTag(dp._tag) != null)
				proposicao += dp._tag;
			else
				proposicao += dp._palavra;
			
			proposicao += " ";
		}
		proposicao = proposicao.trim(); // Remove espacos em branco.
		
	//	proposicao = DuplaTextoProcessado.convertListDuplaProcessadoToString(_corpo);
		
		int count = dicionario.getNumberOfElements();
		
		
		// Neste for eu procuro por um match na expressão do meu dicionario de padroes. Se eu encontrar significa que eu consigo formalizar a sentença.
		for ( int i = count-1; i >= 0; i--)
		{
			if ( proposicao.matches(dicionario.ObtemIdDoElementoDoDicionario(i)))
				return dicionario.ObtemIdDoElementoDoDicionario(i);
		}
		
		return null;
	}
	
	/**
	 *   Este método retorna a forma lógica desta proposicao. ( Ainda não está concluído )
	 *  
	 * @param dicionario
	 * @return
	 */
	public String createLogicForm(DicionarioDePadroes dicionarioPadroes, DicionarioDeConectivos dicionarioConec, GerenciadorDeTags gerenciadorTags)
	{	
		GerenciadorDeSimbolos gerenciadorSimbolo = GerenciadorDeSimbolos.getInstance();
		String simbolo = "";
		String formaLogica = this.coreDaCriacaoDeFormasLogicas(dicionarioPadroes, dicionarioConec, gerenciadorTags);
		
		// Substituo as proposicoes por simbolos.
		List<ProposicaoAtomica> lst = this.getListadeProposicoesAtomicas(dicionarioPadroes, dicionarioConec,  gerenciadorTags);
		for (ProposicaoAtomica pa : lst)
		{
			simbolo = gerenciadorSimbolo.geraSimbolo(pa.removeDuplaComNao().getCorpoDaProposicaoEmString());
			if ( pa.getCorpoDaProposicaoEmString().contains(" Não ")||  pa.getCorpoDaProposicaoEmString().contains(" não "))
				simbolo = '¬' + simbolo;
			formaLogica = formaLogica.replaceFirst("\\[X\\]", simbolo);
		}
		
		return formaLogica;
		
	}
	
	/**
	 *   Método recursivo que retorna uma lista com as proposições atômicas desta proposição. 
	 * @param dicionarioPadroes
	 * @return
	 */
	public List<ProposicaoAtomica> getListadeProposicoesAtomicas(DicionarioDePadroes dicionarioPadroes, DicionarioDeConectivos dicionarioConec, GerenciadorDeTags gerenciadorTags)
	{
	
		// Teremos uma recursão aqui. Talvez esta linha abaixo tenha que ser um parametro!
		List<ProposicaoAtomica> lst = new ArrayList<ProposicaoAtomica>();

		if ( this.isAtomic(this) )
		{
			// Caso BASE!
			lst.add(new ProposicaoAtomica(_corpo));
			return lst;
		}
		// Obtenho string com as proposicoes atomicas nesse nivel.
		String proposicoesAtomicas = this.getStringdeProposicoesAtomicasSemSeAprofundar(dicionarioPadroes, dicionarioConec, gerenciadorTags);
		// Transformo em uma lista de dupla texto processado.
		List<DuplaTextoProcessado> listDuplas = DuplaTextoProcessado.recuperaTagsDaString(proposicoesAtomicas, _corpo);	
		// Obtenho uma lista de proposicoes moleculares a partir da lista de DuplaTextoPocessado acima.
		List<ProposicaoMolecular> listMol = Proposicao.obtemListaDeProposicoes(listDuplas);
		
		// Para cada proposicao molecular da lista acima chamo esta função novamente.
		for ( ProposicaoMolecular mol : listMol)
		{
			lst.addAll(mol.getListadeProposicoesAtomicas(dicionarioPadroes, dicionarioConec, gerenciadorTags));
		}
		
		return lst;
	}
	
	/**
	 *    Este método obtem uma String com todas as expressoes atômicas que consigo capturar sem me aprofundar. (  Sem usar recurssão )
	 *    Exemplo:
	 *            Maria e Jorge e Roberto compram doces. Me retornará: " Maria compram doces. Jorge e Roberto compram doces. "
	 * @param dicionarioPadroes
	 * @param dicionarioConec
	 * @return
	 */
	private String getStringdeProposicoesAtomicasSemSeAprofundar(DicionarioDePadroes dicionarioPadroes, DicionarioDeConectivos dicionarioConec, GerenciadorDeTags gerenciadorTag)
	{
		gerenciadorTag.resetGerenciador();
		String corpo = "";
		// Monto uma string com todas as palavras do corpo da proposicao. É adicionado o número das proposicões.
		for ( DuplaTextoProcessado dp: _corpo)
		{
			if ( gerenciadorTag.getTag(dp._tag) != null )
			{
				Tag tag = gerenciadorTag.getTag(dp._tag);
				corpo += " ";
				corpo += dp._tag + Integer.toString(tag.getNumeroDeElementosNaLista());		
				tag.addToInnerList(dp._palavra);
			}
			else
			{
				corpo += " ";
				corpo += dp._palavra;
			}
			
		}
		corpo = corpo.trim(); // Remove espacos em branco.
		
		String proposicoesAtomicas = "";
				
		// Pattern é a ProposicaoTag desta proposição.
		String idRegexp = IdentifyPattern(dicionarioPadroes, gerenciadorTag);
		ProposicaoTag Pattern = dicionarioPadroes.ObtemProposicaoTag(idRegexp);
			
		// Número de proposicões atomicas que termos. (Dado pelo Pattern)
		int numeroDeProposicoes = Pattern.getNumeroDeProposicoesAtomicas();
		proposicoesAtomicas = ""; // Esta variavel conterá todas as proposicoes atômicas desta proposicao concatenadas.
		
		// Este for executa numeroDeProposicoes vezes.
		for ( int i = 0; i < numeroDeProposicoes; i++) 
		{
			// Obtem a primeira Proposicao
			Matcher matcher = Pattern.getRexpForNprop(i).matcher(corpo); // Realizo o Match
			if (matcher.find()) // Se consegui sucesso
			{
				// Agora testo para cada caso. ( 1 ou 0 respectivamente, Extracao ou uso do match puro)
				if ( Pattern.getTipoDeExtracao() == -1)
				{
					// Caso extraçao ( Usado em e's e ou's )
					System.out.println(matcher.group(0));
					proposicoesAtomicas += " " + corpo.replaceFirst(matcher.group(0), " "); // Faço o replace da string que obtive com o match.
				}
				else
				{
					// Caso uso do match
					proposicoesAtomicas +=  " " + matcher.group(Pattern.getTipoDeExtracao()); // Tenho que me certificar se dependerá do grupo 0 ou não.
					
				}
			}
			else
				System.out.println("Nao consegui dar match nessa frase!");
			
			// Devo trocar os nomes KC0, KC1 e etc.. pelos conectivos e os Verbos V0, V1, e etc.. pelos verbos
	
			List<Tag> lstTag = GerenciadorDeTags.getListTag();
			int aux = 0;
			for ( Tag tag : lstTag )
			{
				List<String> lst  = tag.getListPalavras();
				aux = 0;
				for ( String string: lst)
				{
					proposicoesAtomicas = proposicoesAtomicas.replace(" "+tag.getNome()+ Integer.toString(aux), " " + string);
					aux++;
				}
			}
			
			
			// Devo checar se fechei a proposicao adicionando um '.' caso nao exista.
			
			String proposicoesAtomicasSemEspacos = proposicoesAtomicas.replace(" ", "");
			if ( proposicoesAtomicasSemEspacos.charAt(proposicoesAtomicasSemEspacos.length()-1) != '.')
			{
				proposicoesAtomicas = proposicoesAtomicas + '.';
			}
			
		}
		
		proposicoesAtomicas = proposicoesAtomicas.trim();
		proposicoesAtomicas = proposicoesAtomicas.replaceAll("  ", " ");
		return proposicoesAtomicas;
	}
	
	private String coreDaCriacaoDeFormasLogicas(DicionarioDePadroes dicionarioPadroes, DicionarioDeConectivos dicionarioConec, GerenciadorDeTags gerenciadorTags)
	{
		// Caso base
		if ( this.isAtomic(this) )
		{
			return "[X]";
		}
		
		String logicForm = "";
		String operador = "";
		// Obtenho string com as proposicoes atomicas nesse nivel.
		String proposicoesAtomicas = this.getStringdeProposicoesAtomicasSemSeAprofundar(dicionarioPadroes, dicionarioConec, gerenciadorTags);
		// Transformo em uma lista de dupla texto processado.
		List<DuplaTextoProcessado> listDuplas = DuplaTextoProcessado.recuperaTagsDaString(proposicoesAtomicas, _corpo);	
		// Obtenho uma lista de proposicoes moleculares a partir da lista de DuplaTextoPocessado acima.
		List<ProposicaoMolecular> listMol = Proposicao.obtemListaDeProposicoes(listDuplas);
		
		
	    // Pattern é a ProposicaoTag desta proposição.
		String idRegexp = this.IdentifyPattern(dicionarioPadroes, gerenciadorTags);
		ProposicaoTag Pattern = dicionarioPadroes.ObtemProposicaoTag(idRegexp);
		
		this.findAndChangeConectivos(dicionarioConec);
		
		// Caso esteja com problemas para gerar a forma lógica correta checar se o operador que vc está usando não precisa ser escapado.
		
		if ( Pattern.getOperadorLogico().equals("EOU"))
		{
			//Este tratamento só acontece no caso EOU por ser um caso mais dificil de indentificar. Caso comum a E e ao OU.
			if ( !_conectivos.isEmpty() )
			{
				operador = dicionarioConec.ObtemConectivo(_conectivos.get(0)._palavra);
				_conectivos.remove(0);
				
				if ( operador.equals("e"))
					operador = "^";
				else
					operador = "v";
			}
		}	
		else
			operador = Pattern.getOperadorLogico();
		
		
		for ( ProposicaoMolecular p : listMol)
		{
			logicForm +=  p.coreDaCriacaoDeFormasLogicas(dicionarioPadroes, dicionarioConec, gerenciadorTags) + " " +  operador + " " ;
		}
		
		if ( operador.equals("^")) 
			operador = "\\^";
					
		logicForm = logicForm.replaceFirst(operador+" $", "");
		
		logicForm = logicForm.trim();
		
		return logicForm;
		
	}
		
}
