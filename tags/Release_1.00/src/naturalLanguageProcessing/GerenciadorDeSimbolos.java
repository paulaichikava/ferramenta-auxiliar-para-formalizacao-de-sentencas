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
	private static Map<String, String> _mapProposicaoSimbolo; // Map Proposicao -> Simbulo
	private static String _ultimoSimboloUsado; // Ultimo simbolo gerado.
	private static int _numeroSimbolos;

	private GerenciadorDeSimbolos ()
	{
		_ultimoSimboloUsado = "p";
		_numeroSimbolos = 1;
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
		proposicao = proposicao.replaceAll("não", ""); // Servem para negar, não impactam a proposição.
		proposicao = proposicao.replaceAll("Não", "");
		
		
		
		
		if ( _mapProposicaoSimbolo.containsKey(proposicao) ) // Verifico se já existe um simbolo para esta proposição
			return _mapProposicaoSimbolo.get(proposicao);
		
		char a = _ultimoSimboloUsado.charAt(_ultimoSimboloUsado.length() - 1);
		if ( a == 'z' && _numeroSimbolos == 1 )
		{
			_ultimoSimboloUsado = "pp";
		}
		else if ( a == 'z') // caso eu precise adicionar uma nova letra.
		{
			sumSimbol(_ultimoSimboloUsado, _numeroSimbolos);
			a = 'p';
			_ultimoSimboloUsado = _ultimoSimboloUsado + a;
			_numeroSimbolos++;
		}
		else if ( _ultimoSimboloUsado.length() == 1) // Caso  tenha tamanho 1
	    {
			a++;
			_ultimoSimboloUsado = Character.toString(a);
		}
		else // caso eu só precise incrementar a última letra.
		{
			a++;
			_ultimoSimboloUsado = _ultimoSimboloUsado.substring(0, _ultimoSimboloUsado.length()-1); //removo ultimo caracter
			_ultimoSimboloUsado = _ultimoSimboloUsado + a;
			
		}
//		if ( _ultimoSimboloUsado.length() == 1) // Caso  tenha tamanho 1
//		{
//			_ultimoSimboloUsado = Character.toString(a);
//		}
//		else if ( a != 'z')
//		{
//			_ultimoSimboloUsado = _ultimoSimboloUsado.substring(0, _ultimoSimboloUsado.length()-2); //removo ultimo caracter
//			_ultimoSimboloUsado = _ultimoSimboloUsado + a;
//		} 
//		else
//		{
//			a ='q';
//			_ultimoSimboloUsado = _ultimoSimboloUsado.substring(0, _ultimoSimboloUsado.length()-2); //removo ultimo caracter
//			_ultimoSimboloUsado = _ultimoSimboloUsado + a + a;
//		}
		_mapSimboloProposicao.put(_ultimoSimboloUsado, proposicao);
		_mapProposicaoSimbolo.put(proposicao, _ultimoSimboloUsado);
		return _ultimoSimboloUsado;		
	}
	
	public String getSimboloDeProp ( Proposicao p)
	{
		String str = p.removeDuplaComNao().getCorpoDaProposicaoEmString();
		return _mapProposicaoSimbolo.get(str);
	}
	
	private String sumSimbol(String simbol, int tamanho)
	{
		String newSimbol;
		tamanho--;
		
		if ( tamanho == -1 )
			return simbol; // Caso base, fim do tamanho da string
		
		char ultimo = simbol.charAt(tamanho);
		
		if ( ultimo == 'z') // Caso precise voltar a letra para p
		{
			ultimo = 'p';
			newSimbol = simbol.substring(0,tamanho-1)+ultimo;
			try
			{
				newSimbol = newSimbol + simbol.substring(tamanho+1);
				simbol = newSimbol;
			}
			catch( Exception e)
			{
				simbol = newSimbol;
			}
		}
		else // Caso só precise incrementar a letra
		{
			ultimo++;
			newSimbol = simbol.substring(0,tamanho-1)+ultimo;
			try
			{
				newSimbol = newSimbol + simbol.substring(tamanho+1);
				simbol = newSimbol;
			}
			catch( Exception e)
			{
				simbol = newSimbol;
			}
		}
		
		sumSimbol(simbol,tamanho);
		
		return simbol; // esta linha não deve ser chamada.
		
	}
}
