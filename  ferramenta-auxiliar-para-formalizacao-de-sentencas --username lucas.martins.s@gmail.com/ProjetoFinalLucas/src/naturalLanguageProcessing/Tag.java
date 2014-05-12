package naturalLanguageProcessing;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe que representa um Tag.
 * @author Lucas
 *
 */
public class Tag 
{
	private String _Tipo;
	private List<String> _palavrasRelacionadas;
	private int _numeroPalavrasRelacionadas;
	/**
	 * Construtor da classe
	 * @param nome
	 * @author Lucas
	 */
	public Tag(String nome)
	{
		_palavrasRelacionadas = new ArrayList<String>();
		_Tipo = nome;
	}
	
	/**
	 *  Retorna o nome desta Tag
	 *  @return nome
	 */
	public String getNome()
	{
		return _Tipo;
	}
	
	/**
	 * Adiciona a palavra dada como entrada na lista interna.
	 * @param palavra
	 */
	public void addToInnerList(String palavra)
	{
		_palavrasRelacionadas.add(palavra);
		
	}
	
	/** 
	 * Retorna lista de palavras relacionadas ao tag.
	 * @return
	 */
	public List<String> getListPalavras()
	{
		return _palavrasRelacionadas;
	}
	
	public int getNumeroDeElementosNaLista()
	{
		return _palavrasRelacionadas.size();
	}

}
