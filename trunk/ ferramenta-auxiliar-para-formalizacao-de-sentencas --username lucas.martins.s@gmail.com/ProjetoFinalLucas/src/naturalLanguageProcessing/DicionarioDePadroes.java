package naturalLanguageProcessing;

import java.util.HashMap;
import java.util.Map;

public class DicionarioDePadroes 
{
	private static DicionarioDePadroes _instance;
	private Map<Integer, String> _map;
	private int _count;
	
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
		carregaDicionario();
	}
	
	
	void carregaDicionario()
	{
		_map = new HashMap<Integer, String>();
		
		// Aqui sera adicionado leitura de um arquivo texto com uma lista de conectivos.
		
		// Exemplo de como usar: 
		//						map.put("dog", "type of animal");
		//						System.out.println(map.get("dog"));
		
		
		_map.put(1,"e");
		_map.put(2, "e");
		_map.put(3, "e");
		
		_map.put(4, "ou");
		
		
		
	}
	
	int getNumberOfElements()
	{
		return _count;
	}
	
	String ObtemConectivo(int conectivo)
	{
		String temp = _map.get(conectivo);
		return temp;
	}

}
