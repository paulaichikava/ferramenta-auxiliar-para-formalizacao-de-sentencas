package naturalLanguageProcessing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;


 

/**
 *  ### Sobre ###
 *  	Está classe é um dicionario que atrela um inteiro a uma classe do tipo ProposicaoTag.
 * 
 *  ### Objetivo ###
 *  	O objetivo desta classe é servir de ponto de acesso a base de ProposicoesTags que a aplicação possuí.
 *  
 *  ### Observações ### 
 *  	Sempre que adicionar algo a _map adicionar este mesmo algo à _listTags
 *  
 * @version 1.00
 * @author Roiw
 * @see ProposicaoTag
 */
public class DicionarioDePadroes 
{
	private static DicionarioDePadroes _instance;
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
	public static DicionarioDePadroes getInstance()
	{
		if ( _instance == null )
		{
			_instance = new DicionarioDePadroes();
		}
		return _instance;
	}
	
	protected DicionarioDePadroes ()
	{
		_numeroProposicaoTagNoMap = 0;
		_listTags = new ArrayList<ProposicaoTag>();
		carregaDicionario();
	}
	
	
	void carregaDicionario()
	{
		_map = new HashMap<Integer, ProposicaoTag>();
		List<Pattern> lista;
		
		// Aqui sera adicionado leitura de um arquivo texto com uma lista.
		
		// Exemplo de como usar: 
		//						map.put("dog", "type of animal");
		//						System.out.println(map.get("dog"));
		
		// Ao fazer casos Hard Coded!!!
		// #### IMPORTANTE !!!!!  =====>  Os KC's e V's devem ser sempre numerados começando de 0. Para serem adicionados a lista que obterá as proposições atômicas. ####
		// Importante 2: Casos devem ser adicionados na sua devida ordem de importancia.( Numeros de "tags" ( KC, VAUX, V) usados.) Quanto maior a prioridade do caso mais abaixo ele deve ser adicionado. 
		// Importante 3: Casos somente podem ser quebrados em 2 proposicoes atomicas.
		// Importante 4: Casos como <Se Entao> e <Se e Somente Se> devem ter prioridade. ( Acredito que por causa da transitividade) Será? (21/04)
		
		//#####   Casos Hard Coded.  ####
		
		
		
		// Caso #1: Maria e Jorge gostam de novela.    ->  Maria KC Jorge V de novela.
		lista = new ArrayList<Pattern>();
		lista.add(Pattern.compile("KC0.*(?=V0)")); // Match "KC0 Jorge "
		lista.add(Pattern.compile(".*?(?<=KC0) ")); // Match "Maria KC0 "
		ProposicaoTag a1 = new ProposicaoTag(".*? KC .*? V .*?\\.", lista, "EOU", -1);
		_map.put(_numeroProposicaoTagNoMap,a1);
		_listTags.add(a1);
		_numeroProposicaoTagNoMap++;
		
		// Caso #2: Lucas e Matheus foram jogar bola.  -> Lucas KC Matheus VAUX V bola.
		lista = new ArrayList<Pattern>();
		lista.add(Pattern.compile("KC0.*(?=VAUX0)")); // Match "KC0 Matheus "
		lista.add(Pattern.compile(".*?(?<=KC0) ")); // Match "Lucas KC0 "
		ProposicaoTag a2 = new ProposicaoTag(".*? KC .*? VAUX V .*?\\.", lista, "EOU", -1);
		_map.put(_numeroProposicaoTagNoMap,a2);
		_listTags.add(a2);
		_numeroProposicaoTagNoMap++;
		
		// Caso #3: Foram jogar bola, Lucas e Matheus.  -> VAUX V bola, Lucas KC Matheus.
		lista = new ArrayList<Pattern>();
		lista.add(Pattern.compile("KC0.*(?=\\.)")); // Match "KC0 Matheus "
		lista.add(Pattern.compile("(?<=,) .*?(?<=KC0) ")); // Match "Lucas KC0 "
		ProposicaoTag a3 = new ProposicaoTag("VAUX V .*?,.*?KC.*?\\.", lista, "EOU", -1);
		_map.put(_numeroProposicaoTagNoMap,a3);
		_listTags.add(a3);
		_numeroProposicaoTagNoMap++;
		
		// Caso #4: Se Lucas foi jogar bola logo Matheus foi jogar bola.  -> KS Lucas VAUX V bola ADV Matheus VAUX V bola.
		lista = new ArrayList<Pattern>();
		lista.add(Pattern.compile("(?<=KS0 ).*? (?=ADV0)")); // Match "Lucas VAUX V bola"
		lista.add(Pattern.compile("(?<=ADV0 ).*?\\.")); // Match "Matheus VAUX V bola."
		ProposicaoTag a4 = new ProposicaoTag("KS .*? V .*? ADV .*?\\.", lista, "->",0);
		_map.put(_numeroProposicaoTagNoMap,a4);
		_listTags.add(a4);
		_numeroProposicaoTagNoMap++;
		
		// Caso #5: Maria gosta de novela ou Jorge gosta de novela.  -> Maria V de novela KC Jorge V de novela.
		lista = new ArrayList<Pattern>();
		lista.add(Pattern.compile(".*?(?=KC0)")); // Match "Maria V0 de novela"
		lista.add(Pattern.compile("(?<=KC0).*?$")); // Match " Jorge V0 de novela."
		ProposicaoTag a5 = new ProposicaoTag(".*?V.*?KC.*?$", lista, "EOU",0);
		_map.put(_numeroProposicaoTagNoMap,a5);
		_listTags.add(a5);
		_numeroProposicaoTagNoMap++;
		
		// Caso #6: Maria comprará frango se e somente se tiver dinheiro.  -> NPROP V frango PROPESS KC PDEN PROPESS V dinheiro.
		lista = new ArrayList<Pattern>();
		lista.add(Pattern.compile("PROPESS0.*?$")); // Match "PROPESS0 KC0 PDEN0 PROPESS1 V1 dinheiro."
		lista.add(Pattern.compile("V0.*?(?=V1)")); // Match "V frango PROPESS KC PDEN PROPESS "
		ProposicaoTag a6 = new ProposicaoTag("NPROP\\s*?V([a-z]|\\s)*?PROPESS\\s*?KC\\s*?PDEN\\s*?PROPESS.*?$", lista, "<->",-1);
		_map.put(_numeroProposicaoTagNoMap,a6);
		_listTags.add(a6);
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
	
	int getNumberOfElements()
	{
		return _map.size();
	}
	
	/**
	 *  Retorna o "idRegexp" da ProposicaoTag.
	 *  Um exemplo de usa utilizacao é dar um match no item certo do dicionário. 
	 *  
	 *  Este dicionario atribui um número inteiro a uma classe ProposicaoTag:
	 *  Cada ProposicaoTag é reconhecida(Seu ID) por uma expressão regular.
	 *  
	 *  Este método retorna esta expressão regular.
	 *  
	 * @param item Um número atrelado a uma ProposicaoTag
	 * @return String contendo uma expressão regular.
	 */
	String ObtemIdDoElementoDoDicionario(int item)
	{
		String temp = _map.get(item).getIdRegexp(); // Devolve o idRegexp da ProposicaoTag.
		return temp;
	}
	
	
	/**
	 * Dado uma idRegexp este metodo retorna a ProposicaoTag contida no dicionário. ( Na lista )
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
