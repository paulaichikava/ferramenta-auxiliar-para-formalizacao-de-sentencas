package naturalLanguageProcessing;

import java.util.HashMap;
import java.util.Map;

import fext.FEXT_Handler;

public class DicionarioDeConectivos 
{
	private static DicionarioDeConectivos _instance;
	private Map<String, String> _map;
	
	/**
	 * Classe para se obter uma instancia do dicionario de conectivos.
	 * 
	 * 
	 * @author Lucas
	 * @return Instancia do DicionarioDeConectivos
	 */
	public static DicionarioDeConectivos getInstance()
	{
		if ( _instance == null )
		{
			_instance = new DicionarioDeConectivos();
		}
		return _instance;
	}
	
	protected DicionarioDeConectivos ()
	{
		CarregaConectivos();
	}
	
	
	void CarregaConectivos()
	{
		_map = new HashMap<String, String>();
		
		// Aqui sera adicionado leitura de um arquivo texto com uma lista de conectivos.
		
		// Exemplo de como usar: 
		//						map.put("dog", "type of animal");
		//						System.out.println(map.get("dog"));
		
		
		_map.put("e","e");
		_map.put("mas", "e");
		_map.put("entretanto", "e");
		
		_map.put("ou", "ou");
		
		
		
	}
	
	String ObtemConectivo(String conectivo)
	{
		String temp = _map.get(conectivo);
		return temp;
	}
	
}
