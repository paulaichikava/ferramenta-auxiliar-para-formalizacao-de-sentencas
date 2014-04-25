package naturalLanguageProcessing;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

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
		this.findConectivos();
	}
	
	/**
	 *  Este m�todo procura todos os conectivos e popula corretamente a lista _conectivos.
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
	 * Este m�todo procura por todos os conectivos da proposi��o no dicionario e substitui os que conseguir encontrar.
	 * Caso n�o consiga encontrar ele far� uma pergunta ao usu�rio para tentar encontrar.
	 * 
	 * @param dicionario
	 */
	public void findAndChangeConectivos( DicionarioDeConectivos dicionario)
	{
		_conectivos.clear();
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
			if ( dp._tag.equals("V") || dp._tag.equals("KC") || dp._tag.equals("VAUX") || dp._tag.equals("KS") || dp._tag.equals("ADV") || dp._tag.equals("PROPESS") || dp._tag.equals("PDEN")
					|| dp._tag.equals("NPROP") || dp._tag.equals("ART") || dp._tag.equals("N"))
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
		GerenciadorDeSimbolos gerenciadorSimbolo = GerenciadorDeSimbolos.getInstance();
		String simbolo = "";
		String formaLogica = this.coreDaCriacaoDeFormasLogicas(dicionarioPadroes, dicionarioConec);
		
		// Substituo as proposicoes por simbolos.
		List<ProposicaoAtomica> lst = this.getListadeProposicoesAtomicas(dicionarioPadroes, dicionarioConec);
		for (ProposicaoAtomica pa : lst)
		{
			simbolo = gerenciadorSimbolo.geraSimbolo(pa.getCorpoDaProposicaoEmString());
			formaLogica = formaLogica.replaceFirst("\\[X\\]", simbolo);
		}
		
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
		int m = 0; int n = 0; int o = 0; int p = 0; int q = 0; int r = 0; int s = 0; int t = 0 ; int u = 0 ; int v = 0 ;
		List<String> lstV = new ArrayList<String>();
		List<String> lstKC = new ArrayList<String>();
		List<String> lstVAUX = new ArrayList<String>();
		List<String> lstKS = new ArrayList<String>();
		List<String> lstADV = new ArrayList<String>();
		List<String> lstNPROP = new ArrayList<String>();
		List<String> lstPROPESS = new ArrayList<String>();
		List<String> lstPDEN = new ArrayList<String>();
		List<String> lstN = new ArrayList<String>();
		List<String> lstART = new ArrayList<String>();
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
			else if (dp._tag.equals("KS") )
			{
				corpo += " ";
				corpo += dp._tag + Integer.toString(p);
				lstKS.add(dp._palavra);
				p++;
			}
			else if (dp._tag.equals("ADV") )
			{
				corpo += " ";
				corpo += dp._tag + Integer.toString(q);
				lstADV.add(dp._palavra);
				q++;
			}
			else if (dp._tag.equals("NPROP") )
			{
				corpo += " ";
				corpo += dp._tag + Integer.toString(r);
				lstNPROP.add(dp._palavra);
				r++;
			}
			else if (dp._tag.equals("PROPESS") )
			{
				corpo += " ";
				corpo += dp._tag + Integer.toString(s);
				lstPROPESS.add(dp._palavra);
				s++;
			}
			else if (dp._tag.equals("PDEN") )
			{
				corpo += " ";
				corpo += dp._tag + Integer.toString(t);
				lstPDEN.add(dp._palavra);
				t++;
			}
			else if (dp._tag.equals("N") )
			{
				corpo += " ";
				corpo += dp._tag + Integer.toString(u);
				lstN.add(dp._palavra);
				u++;
			}
			else if (dp._tag.equals("ART") )
			{
				corpo += " ";
				corpo += dp._tag + Integer.toString(v);
				lstART.add(dp._palavra);
				v++;
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
			Matcher matcher = Pattern.getRexpForNprop(i).matcher(corpo); // Realizo o Match
			if (matcher.find()) // Se consegui sucesso
			{
				// Agora testo para cada caso. ( 1 ou 0 respectivamente, Extracao ou uso do match puro)
				if ( Pattern.getTipoDeExtracao() == -1)
				{
					// Caso extra�ao ( Usado em e's e ou's )
					System.out.println(matcher.group(0));
					proposicoesAtomicas += " " + corpo.replaceFirst(matcher.group(0), " "); // Fa�o o replace da string que obtive com o match.
				}
				else
				{
					// Caso uso do match
					proposicoesAtomicas +=  " " + matcher.group(Pattern.getTipoDeExtracao()); // Tenho que me certificar se depender� do grupo 0 ou n�o.
					
				}
			}
			else
				System.out.println("Nao consegui dar match nessa frase!");
			
			// Devo trocar os nomes KC0, KC1 e etc.. pelos conectivos e os Verbos V0, V1, e etc.. pelos verbos
			m = 0; n = 0; o = 0; p = 0; q = 0; r = 0; s = 0; t = 0; u = 0; v = 0;
			for( String ve : lstV)
			{
				proposicoesAtomicas = proposicoesAtomicas.replace(" V"+Integer.toString(m), " " + ve);
				m++;
			}
			for( String kc : lstKC)
			{
				proposicoesAtomicas = proposicoesAtomicas.replace(" KC"+Integer.toString(n), " " + kc);
				n++;
			}
			for( String vaux : lstVAUX)
			{
				proposicoesAtomicas = proposicoesAtomicas.replace(" VAUX"+Integer.toString(o), " " + vaux);
				o++;
			}
			for( String ks : lstKS)
			{
				proposicoesAtomicas = proposicoesAtomicas.replace(" KS"+Integer.toString(p), " " + ks);
				p++;
			}
			for( String adv : lstADV)
			{
				proposicoesAtomicas = proposicoesAtomicas.replace(" ADV"+Integer.toString(q), " " + adv);
				q++;
			}
			for( String nprop : lstNPROP)
			{
				proposicoesAtomicas = proposicoesAtomicas.replace(" NPROP"+Integer.toString(r), " " + nprop);
				r++;
			}
			for( String propess : lstPROPESS)
			{
				proposicoesAtomicas = proposicoesAtomicas.replace(" PROPESS"+Integer.toString(s), " " + propess);
				s++;
			}
			for( String pden : lstPDEN)
			{
				proposicoesAtomicas = proposicoesAtomicas.replace(" PDEN"+Integer.toString(t), " " + pden);
				t++;
			}
			for( String no : lstN)
			{
				proposicoesAtomicas = proposicoesAtomicas.replace(" N"+Integer.toString(u), " " + no);
				u++;
			}
			for( String art : lstART)
			{
				proposicoesAtomicas = proposicoesAtomicas.replace(" ART"+Integer.toString(v), " " + art);
				v++;
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
		
		
		
		// Caso esteja com problemas para gerar a forma l�gica correta checar se o operador que vc est� usando n�o precisa ser escapado.
		
		if ( Pattern.getOperadorLogico().equals("EOU"))
		{
			//Este tratamento s� acontece no caso EOU por ser um caso mais dificil de indentificar. Caso comum a E e ao OU.
			if ( !_conectivos.isEmpty() )
			{
				operador = _conectivos.get(0)._palavra;
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
			logicForm +=  p.coreDaCriacaoDeFormasLogicas(dicionarioPadroes, dicionarioConec) + " " +  operador + " " ;
		}
		
		if ( operador.equals("^")) 
			operador = "\\^";
					
		logicForm = logicForm.replaceFirst(operador+" $", "");
		
		logicForm = logicForm.trim();
		
		return logicForm;
		
	}
		
}