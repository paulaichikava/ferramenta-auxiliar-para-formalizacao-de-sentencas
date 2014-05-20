package naturalLanguageProcessing;

import java.util.HashMap;
import java.util.Map;

/**
 *  Esta classe tem como objetivo gerenciar os simbolos das proposicoes criadas 
 *  para sempre gerar um simbolo novo para uma proposicao nova.
 * @author Lucas
 *
 */
public class GerenciadorDeSimbolos 
{
	
	private static GerenciadorDeSimbolos _instance; // instancia do gerenciador
	private static Map<String, String> _mapSimboloProposicao; // Map Simbulo -> Proposicao
	private static Map<String, String> _mapProposicaoSimbolo; // Map Simbulo -> Proposicao
	private static String _ultimoSimboloUsado; // Ultimo simbolo gerado.

	private GerenciadorDeSimbolos ()
	{
		_ultimoSimboloUsado = "p";
		_mapSimboloProposicao = new HashMap<String, String>();
		_mapProposicaoSimbolo = new HashMap<String, String>();
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
	
	/**
	 *    Gera um novo simbolo para ser atribuido a uma proposição.
	 * @param proposicao proposição que eu uso para ter armazenada ao key que gerei.
	 * @return
	 */
	public String geraSimbolo( String proposicao)
	{
		if ( _mapProposicaoSimbolo.containsKey(proposicao) )
			return _mapProposicaoSimbolo.get(proposicao);
		
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
		_mapSimboloProposicao.put(_ultimoSimboloUsado, proposicao);
		_mapProposicaoSimbolo.put(proposicao, _ultimoSimboloUsado);
		return _ultimoSimboloUsado;		
	}
	
	public String getSimboloDeProp ( Proposicao p)
	{
		String str = p.getCorpoDaProposicaoEmString();
		return _mapProposicaoSimbolo.get(str);
	}
}
