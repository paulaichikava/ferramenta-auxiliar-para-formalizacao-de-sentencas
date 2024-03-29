package naturalLanguageProcessing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;


 

/**
 *  ### Sobre ###
 *  	Est� classe � um dicionario que atrela um inteiro a uma classe do tipo ProposicaoTag.
 * 
 *  ### Objetivo ###
 *  	O objetivo desta classe � servir de ponto de acesso a base de ProposicoesTags que a aplica��o possu�.
 *  
 *  ### Observa��es ### 
 *  	Sempre que adicionar algo a _map adicionar este mesmo algo � _listTags
 *  
 * @version 1.00
 * @author Roiw
 * @see ProposicaoTag
 */
public class DicionarioDePadroes 
{
	private static DicionarioDePadroes _instance;
	private static String _tipoLexico;
	private Map<Integer, ProposicaoTag> _map;
	private List<ProposicaoTag> _listTags;  //Esta lista acompanha o Map, sempre que algo for adicionado ao HashMap deve ser adcionado a lista.
	private int _numeroProposicaoTagNoMap;
	
	
	/**
	 * Classe para se obter uma instancia do dicionario
	 * 
	 * 
	 * @author Lucas
	 * @return Instancia do DicionarioDeConectivos
	 */
	public static DicionarioDePadroes getInstance(String tipoLexico)
	{
		if ( _instance == null || !tipoLexico.equals(_tipoLexico) )
		{
			_instance = new DicionarioDePadroes(tipoLexico);
		}
		return _instance;
	}
	
	protected DicionarioDePadroes (String tipoLexico)
	{
		_numeroProposicaoTagNoMap = 0;
		_listTags = new ArrayList<ProposicaoTag>();
		if ( tipoLexico.equals("fext"))
			carregaDicionarioFext();
		else if ( tipoLexico.equals("lxSuite"))
			carregaDicionariolxSuite();
	}
	
	
	void carregaDicionarioFext()
	{
		_map = new HashMap<Integer, ProposicaoTag>();
		List<Pattern> lista;
		
		// Aqui sera adicionado leitura de um arquivo texto com uma lista.
		
		// Exemplo de como usar: 
		//						map.put("dog", "type of animal");
		//						System.out.println(map.get("dog"));
		
		// Ao fazer casos Hard Coded!!!
		// #### IMPORTANTE !!!!!  =====>  Os KC's e V's devem ser sempre numerados come�ando de 0. Para serem adicionados a lista que obter� as proposi��es at�micas. ####
		// Importante 2: Casos devem ser adicionados na sua devida ordem de importancia.( Numeros de "tags" ( KC, VAUX, V) usados.) Quanto maior a prioridade do caso mais abaixo ele deve ser adicionado. 
		// Importante 3: Casos somente podem ser quebrados em 2 proposicoes atomicas.
		// Importante 4: Casos como <Se Entao> e <Se e Somente Se> devem ter prioridade. ( Acredito que por causa da transitividade) Ser�? (21/04)
		
		//#####   Casos Hard Coded.  ####
		
		
		// Caso #1: Maria gosta de novela ou Jorge gosta de novela.  -> NPROP V de N KC NPROP V de N.
		lista = new ArrayList<Pattern>();
		lista.add(Pattern.compile(".*?(?=KC0)")); // Match "Maria V0 de novela"
		lista.add(Pattern.compile("(?<=KC0).*?$")); // Match " Jorge V0 de novela."
		ProposicaoTag a5 = new ProposicaoTag(".*?V.*?KC.*?$", lista, "EOU",0);
		_map.put(_numeroProposicaoTagNoMap,a5);
		_listTags.add(a5);
		_numeroProposicaoTagNoMap++;
		
		
		// Caso #2: Maria e Jorge gostam de novela.    ->  NPROP KC NPROP V de N.
		lista = new ArrayList<Pattern>();
		lista.add(Pattern.compile("(KC0.*(?=N0))|(KC0.*(?=V0))")); // Match "KC0 Jorge " o ou serve para caso tenha um n�o na senten�a
		lista.add(Pattern.compile(".*?(?<=KC0) ")); // Match "Maria KC0 "
		//ProposicaoTag a1 = new ProposicaoTag(".*? KC .*? V .*?\\.", lista, "EOU", -1);
		ProposicaoTag a1 = new ProposicaoTag("NPROP KC NPROP .*?V .*?\\.", lista, "EOU", -1);
		_map.put(_numeroProposicaoTagNoMap,a1);
		_listTags.add(a1);
		_numeroProposicaoTagNoMap++;
		
		
		// Caso #3: Lucas e Matheus foram jogar bola.  -> NPROP KC NPROP VAUX V N.
		lista = new ArrayList<Pattern>();
		lista.add(Pattern.compile("((KC0.*(?=N0))|(KC0.*(?=VAUX0)))")); // Match "KC0 Matheus "
		lista.add(Pattern.compile(".*?(?<=KC0) ")); // Match "Lucas KC0 "
		//ProposicaoTag a2 = new ProposicaoTag(".*? KC .*? VAUX V .*?\\.", lista, "EOU", -1);
		ProposicaoTag a2 = new ProposicaoTag("NPROP KC NPROP .*? VAUX V .*?\\.", lista, "EOU", -1);
		_map.put(_numeroProposicaoTagNoMap,a2);
		_listTags.add(a2);
		_numeroProposicaoTagNoMap++;
		
		// Caso #4: Foram jogar bola, Lucas e Matheus.  -> VAUX V N, NPROP KC NPROP.
		lista = new ArrayList<Pattern>();
		lista.add(Pattern.compile("KC0.*(?=\\.)")); // Match "KC0 Matheus "
		lista.add(Pattern.compile("(?<=,) .*?(?<=KC0) ")); // Match "Lucas KC0 "
		ProposicaoTag a3 = new ProposicaoTag("VAUX V .*?,.*?KC.*?\\.", lista, "EOU", -1);
		_map.put(_numeroProposicaoTagNoMap,a3);
		_listTags.add(a3);
		_numeroProposicaoTagNoMap++;
		
		// Caso #5: Se Lucas foi jogar bola logo Matheus foi jogar bola.  -> KS Lucas VAUX V bola ADV Matheus VAUX V bola.
		lista = new ArrayList<Pattern>();
		lista.add(Pattern.compile("(?<=KS0 ).*? (?=ADV0)")); // Match "Lucas VAUX V bola"
		lista.add(Pattern.compile("(?<=ADV0 ).*?\\.")); // Match "Matheus VAUX V bola."
		ProposicaoTag a4 = new ProposicaoTag("KS .*? V .*? ADV .*?\\.", lista, "->",0);
		_map.put(_numeroProposicaoTagNoMap,a4);
		_listTags.add(a4);
		_numeroProposicaoTagNoMap++;
		
		// Caso #6: Maria comprar� frango se e somente se tiver dinheiro.  -> NPROP V frango PROPESS KC PDEN PROPESS V dinheiro.
		lista = new ArrayList<Pattern>();
		lista.add(Pattern.compile("PROPESS0.*?$")); // Match "PROPESS0 KC0 PDEN0 PROPESS1 V1 dinheiro."
		lista.add(Pattern.compile("V0.*?(?=V1)")); // Match "V frango PROPESS KC PDEN PROPESS "
		ProposicaoTag a6 = new ProposicaoTag("NPROP\\s*?V([a-z]|\\s)*?PROPESS\\s*?KC\\s*?PDEN\\s*?PROPESS.*?$", lista, "<->",-1);
		_map.put(_numeroProposicaoTagNoMap,a6);
		_listTags.add(a6);
		_numeroProposicaoTagNoMap++;
		
		// Caso #7: A menos que o carro seja novo o motor nao precisa de revisao.  -> KS KS KS o N V novo o motor ADV V de revisao.
		lista = new ArrayList<Pattern>();
		lista.add(Pattern.compile("N.*? (?=ART)")); // Match "N V novo"
		lista.add(Pattern.compile("(?<=ART[1-9] ).*?$")); // Match "N ADV V de revisao."
		ProposicaoTag a7 = new ProposicaoTag("KS\\s*?KS\\s*?KS\\s*?ART .*? V .*? ADV .*?\\.", lista, "->",0);
		_map.put(_numeroProposicaoTagNoMap,a7);
		_listTags.add(a7);
		_numeroProposicaoTagNoMap++;
	
		
//		// Caso #7:Maria gosta de frango e Maria comprou frango se e somente se tem dinheiro.  -> NPROP V de frango KC NPROP V frango PROPESS KC PDEN PROPESS V dinheiro.
//		lista = new ArrayList<Pattern>();
//		lista.add(Pattern.compile("PROPESS0.*?$")); // Match "PROPESS0 KC0 PDEN0 PROPESS1 V1 dinheiro."
//		lista.add(Pattern.compile("V0.*?(?=V2)")); // Match "V de frango KC NPROP V frango PROPESS KC PDEN PROPESS "
//		ProposicaoTag a7 = new ProposicaoTag("NPROP.*?NPROP\\s*?V.*?PROPESS\\s*?KC\\s*?PDEN\\s*?PROPESS.*?$", lista, "<->",-1);
//		_map.put(_numeroProposicaoTagNoMap,a7);
//		_listTags.add(a7);
//		_numeroProposicaoTagNoMap++;
		
//		// Caso #8: Maria e Jorge gostam de frango e Maria comprou frango se e somente se tem dinheiro.  -> NPROP V de frango KC NPROP V frango PROPESS KC PDEN PROPESS V dinheiro.
//		lista = new ArrayList<Pattern>();
//		lista.add(Pattern.compile("PROPESS0.*?$")); // Match "PROPESS0 KC0 PDEN0 PROPESS1 V1 dinheiro."
//		lista.add(Pattern.compile("V0.*?(?=V2)")); // Match "V de frango KC NPROP V frango PROPESS KC PDEN PROPESS "
//		ProposicaoTag a8 = new ProposicaoTag("NPROP.*?NPROP\\s*?V.*?PROPESS\\s*?KC\\s*?PDEN\\s*?PROPESS.*?$", lista, "<->",-1);
//		_map.put(_numeroProposicaoTagNoMap,a8);
//		_listTags.add(a8);
//		_numeroProposicaoTagNoMap++;
		
		
		
//		// Caso #5: Lucas foi jogar bola logo Matheus foi jogar bola.  -> Lucas VAUX V bola ADV Matheus VAUX V bola.   PARA ISSO FUNCIONAR OLHAR O isAtomic
//		lista = new ArrayList<Pattern>();
//		lista.add(Pattern.compile(".*? (?=ADV0)")); // Match "Lucas VAUX V bola"
//		lista.add(Pattern.compile("(?<=ADV0 ).*?\\.")); // Match "Matheus VAUX V bola."
//		ProposicaoTag a5 = new ProposicaoTag(".*? V .*? ADV .*?\\.", lista, "->",0);
//		_map.put(_numeroProposicaoTagNoMap,a5);
//		_listTags.add(a5);
//		_numeroProposicaoTagNoMap++;
		
		
		
//		// Caso #3: Se Lucas foi jogar bola logo Matheus foi jogar bola.  -> Lucas KC Matheus VAUX V bola.
//		lista = new ArrayList<String>();
//		lista.add("(?<=KS0 ).*?ADV0"); // Match "KC0 Matheus "
//		lista.add("(?<=ADV0 ).*?\\."); // Match "Lucas KC0 "
//		ProposicaoTag a2 = new ProposicaoTag(".*? KC .*? VAUX V .*?\\.", lista, "p ^ q");
//		_map.put(_numeroProposicaoTagNoMap,a2);
//		_listTags.add(a2);
//		_numeroProposicaoTagNoMap++;
//		
		
	}
	
	void carregaDicionariolxSuite()
	{
		_map = new HashMap<Integer, ProposicaoTag>();
		List<Pattern> lista;
		
		// Ao fazer casos Hard Coded!!!
		// #### IMPORTANTE !!!!!  =====>  Os KC's e V's devem ser sempre numerados come�ando de 0. Para serem adicionados a lista que obter� as proposi��es at�micas. ####
		// Importante 2: Casos devem ser adicionados na sua devida ordem de importancia.( Numeros de "tags" ( KC, VAUX, V) usados.) Quanto maior a prioridade do caso mais abaixo ele deve ser adicionado. 
		// Importante 3: Casos somente podem ser quebrados em 2 proposicoes atomicas.
		// Importante 4: Casos como <Se Entao> e <Se e Somente Se> devem ter prioridade. ( Acredito que por causa da transitividade) Ser�? (21/04)
		
		//#####   Casos Hard Coded.  ####
		
		
		// Caso #1: Maria gosta de novela ou Jorge gosta de novela.  -> NPROP V de N KC NPROP V de N.
		lista = new ArrayList<Pattern>();
		lista.add(Pattern.compile(".*?(?=CJ0)")); // Match "Maria V0 de novela"
		lista.add(Pattern.compile("(?<=CJ0).*?$")); // Match " Jorge V0 de novela."
		ProposicaoTag a5 = new ProposicaoTag(".*?V.*?CJ.*?$", lista, "EOU",0);
		_map.put(_numeroProposicaoTagNoMap,a5);
		_listTags.add(a5);
		_numeroProposicaoTagNoMap++;
		
		
		// Caso #2: Maria e Jorge gostam de novela.    ->  PNM CJ PNM V PREP CN.
		lista = new ArrayList<Pattern>();
		lista.add(Pattern.compile("((CJ0.*?(?=ADV0))|(CJ0.*?(?=V0)))")); // Match "CJ0 at� algo antes de um verbo"
		lista.add(Pattern.compile("PNM0.*?CJ0")); // Match "Maria CJ0 "
		//ProposicaoTag a1 = new ProposicaoTag(".*? KC .*? V .*?\\.", lista, "EOU", -1);
		ProposicaoTag a1 = new ProposicaoTag("PNM CJ PNM .*?V .*?\\.", lista, "EOU", -1);
		_map.put(_numeroProposicaoTagNoMap,a1);
		_listTags.add(a1);
		_numeroProposicaoTagNoMap++;
		
		// Caso #5: Se o c�mbio cair, temos infla��o.  -> CJ DA CN V VRG V CN.
		lista = new ArrayList<Pattern>();
		lista.add(Pattern.compile("(?<=CJ0 ).*? (?=VRG0)")); // Match "PNM V V CN"
		lista.add(Pattern.compile("(?<=VRG0 ).*?\\.")); // Match "PNM V V CN."
		ProposicaoTag a4 = new ProposicaoTag("^CJ .*? VRG .*?V .*?\\.", lista, "->",0);
		_map.put(_numeroProposicaoTagNoMap,a4);
		_listTags.add(a4);
		_numeroProposicaoTagNoMap++;
		
		// Caso #6: Se Lucas foi jogar bola, logo Matheus foi jogar bola.  -> CJ PNM V V CN VRG ADV PNM V V CN.
		lista = new ArrayList<Pattern>();
		lista.add(Pattern.compile("(?<=CJ0 ).*? (?=VRG0)")); // Match "PNM V V CN"
		lista.add(Pattern.compile("(?<=VRG0 ADV\\d ).*?\\.")); // Match "PNM V V CN."
		ProposicaoTag a3 = new ProposicaoTag("^CJ .*? VRG ADV .*?\\.", lista, "->",0);
		_map.put(_numeroProposicaoTagNoMap,a3);
		_listTags.add(a3);
		_numeroProposicaoTagNoMap++;
		
		// Caso #7: Maria comprar� frango se e somente se tiver dinheiro.  -> PNM  V CN CJ CJ  ADV  CL V CN.
		lista = new ArrayList<Pattern>();
		lista.add(Pattern.compile("CJ0.*?$")); // Match "CJ0 CJ1 ADV0 CL0 V1 CN1."
		lista.add(Pattern.compile("^.*?CJ0 CJ1 ADV0 CL0")); // Match "V CN CJ CJ ADV CL "
		ProposicaoTag a6 = new ProposicaoTag(".*?CJ\\s*?CJ\\s*?ADV\\s*?CL.*?$", lista, "<->",-1);
		_map.put(_numeroProposicaoTagNoMap,a6);
		_listTags.add(a6);
		_numeroProposicaoTagNoMap++;
		
		// Caso #8: A menos que o carro seja novo, o motor nao precisa de revisao.  -> DA ADV CJ DA CN V ADJ VRG DO CN ADV V PREP CN.
		lista = new ArrayList<Pattern>();
		lista.add(Pattern.compile("(?<=DA0 ADV0 CJ0).*?(?=VRG0)")); // Match "DA ADV V de revisao."
		lista.add(Pattern.compile("(?<=VRG0).*?$")); // Match "N V novo"
		ProposicaoTag a7 = new ProposicaoTag("DA\\s*?ADV\\s*?CJ.*?VRG .*?\\.", lista, "->",0);
		_map.put(_numeroProposicaoTagNoMap,a7);
		_listTags.add(a7);
		_numeroProposicaoTagNoMap++;
		
	}
	
	int getNumberOfElements()
	{
		return _map.size();
	}
	
	/**
	 *  Retorna o "idRegexp" da ProposicaoTag.
	 *  Um exemplo de usa utilizacao � dar um match no item certo do dicion�rio. 
	 *  
	 *  Este dicionario atribui um n�mero inteiro a uma classe ProposicaoTag:
	 *  Cada ProposicaoTag � reconhecida(Seu ID) por uma express�o regular.
	 *  
	 *  Este m�todo retorna esta express�o regular.
	 *  
	 * @param item Um n�mero atrelado a uma ProposicaoTag
	 * @return String contendo uma express�o regular.
	 */
	String ObtemIdDoElementoDoDicionario(int item)
	{
		String temp = _map.get(item).getIdRegexp(); // Devolve o idRegexp da ProposicaoTag.
		return temp;
	}
	
	
	/**
	 * Dado uma idRegexp este metodo retorna a ProposicaoTag contida no dicion�rio. ( Na lista )
	 * Esta lista acompanha o Map, sempre que algo for adicionado ao Map deve ser adcionado a lista.
	 * @param idRegexp
	 * @return
	 */
	
	ProposicaoTag ObtemProposicaoTag(String idRegexp)
	{
		for( ProposicaoTag t: _listTags)
		{
			if( t.getIdRegexp().equals(idRegexp))
				return t;
		}
		
		return null;
	}

}
