package naturalLanguageProcessing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 *  Esta classe tem como objetivo gerenciar os tags sendo responsável por manipula-los..
 *  
 * @author Lucas
 *
 */
public class GerenciadorDeTags 
{
	
	private static GerenciadorDeTags _instance; // instancia do gerenciador
	private List<Tag> _tags = new ArrayList<Tag>();
	

	private GerenciadorDeTags ()
	{
		inicializaTags();


	}
	
	
	/**
	 * Classe para se obter uma instancia do gerenciador
	 * 
	 * 
	 * @author Lucas
	 * @return Instancia do {@link GerenciadorDeSimbolos}
	 */
	public static GerenciadorDeTags getInstance()
	{
		if ( _instance == null )
		{
			_instance = new GerenciadorDeTags();
		}
		return _instance;
	}
	
	
	/**
	 * Classe responsável por inicializar os tags com os padrões da aplicação
	 * 
	 * @author Lucas
	 * @return void
	 */
	private void inicializaTags()
	{
		_tags.add(new Tag("KC"));
		_tags.add(new Tag("V"));
		_tags.add(new Tag("VAUX"));
		_tags.add(new Tag("KS"));
		_tags.add(new Tag("ADV"));
		_tags.add(new Tag("NPROP"));
		_tags.add(new Tag("PROPESS"));
		_tags.add(new Tag("PDEN"));
		_tags.add(new Tag("PROP"));
		_tags.add(new Tag("ART"));
		_tags.add(new Tag("N"));
	}
	
	
	
	
	/**
	 * Dado um nome retorna uma tag contida neste gerenciador.
	 * @param nome
	 * @return
	 */
	public Tag getTag(String nome)
	{
		for ( Tag t : _tags)
		{
			if ( t.getNome().equals(nome) )
				return t;
		}
		return null;
	}
	
	/**
	 * Retorna lista contendo as tags.
	 * @return List<{@link Tag}>
	 */
	public List<Tag> getListTag ()
	{
		return _tags;
	}
	
	/**
	 *  Reseta o gerenciador apagando a memoria de todas as tags assim como as palavras contidas nelas.
	 *  @author Lucas
	 */
	public void resetGerenciador()
	{
		_tags.clear();
		this.inicializaTags();
	}

}
