package naturalLanguageProcessing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *  Esta classe tem como objetivo gerencia os simbolos das proposicoes criadas 
 *  para sempre gerar um simbulo novo para uma proposicao nova.
 * @author Lucas
 *
 */
public class GerenciadorDeSimbolos 
{
	
	private static GerenciadorDeSimbolos _instance; // instancia do gerenciador
	private Map<String, String> _map; // Map Simbulo -> Proposicao
	private String _ultimoSimboloUsado; // Ultimo simbolo gerado.

	private GerenciadorDeSimbolos ()
	{
		_ultimoSimboloUsado = "q";
		_map = new HashMap<String, String>();
	}
	
	
	/**
	 * Classe para se obter uma instancia do gerenciador
	 * 
	 * 
	 * @author Lucas
	 * @return Instancia do {@link GerenciadorDeSimbolos}
	 */
	public static GerenciadorDeSimbolos getInstance()
	{
		if ( _instance == null )
		{
			_instance = new GerenciadorDeSimbolos();
		}
		return _instance;
	}
	
	public String geraSimbolo()
	{
		char a = _ultimoSimboloUsado.charAt(_ultimoSimboloUsado.length() - 1);
		a++;
		if ( _ultimoSimboloUsado.length() == 1)
		{
			_ultimoSimboloUsado = Character.toString(a);
		}
		else if ( a != 'z')
		{
			_ultimoSimboloUsado = _ultimoSimboloUsado.substring(0, _ultimoSimboloUsado.length()-2); //removo ultimo caracter
			_ultimoSimboloUsado = _ultimoSimboloUsado + a;
		} 
		else
		{
			a ='q';
			_ultimoSimboloUsado = _ultimoSimboloUsado.substring(0, _ultimoSimboloUsado.length()-2); //removo ultimo caracter
			_ultimoSimboloUsado = _ultimoSimboloUsado + a + a;
		}
		
		return _ultimoSimboloUsado;		
	}
}
