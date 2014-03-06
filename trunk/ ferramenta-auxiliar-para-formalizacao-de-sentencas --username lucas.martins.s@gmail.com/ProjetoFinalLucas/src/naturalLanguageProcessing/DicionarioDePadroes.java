package naturalLanguageProcessing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		
		// Aqui sera adicionado leitura de um arquivo texto com uma lista.
		
		// Exemplo de como usar: 
		//						map.put("dog", "type of animal");
		//						System.out.println(map.get("dog"));
		
		// Ao fazer casos Hard Coded!!!
		// #### IMPORTANTE !!!!!  =====>  Os KC's e V's devem ser sempre numerados come�ando de 0. Para serem adicionados a lista que obter� as proposi��es at�micas. ####
		
		//#####   Casos Hard Coded.  ####
		
		// Caso #1: Maria e Jorge gostam de novela.    ->  Maria KC Jorge V de novela
		List<String> lista = new ArrayList<String>();
		lista.add("KC0.*(?=V0)"); // Match "KC0 Jorge "
		lista.add(".*?(?<=KC0) "); // Match "Maria KC0 "
		
		ProposicaoTag a1 = new ProposicaoTag(".*? KC .*? V .*?\\.", lista, "p ^ q");
		
		_map.put(_numeroProposicaoTagNoMap,a1);
		_listTags.add(a1);
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
