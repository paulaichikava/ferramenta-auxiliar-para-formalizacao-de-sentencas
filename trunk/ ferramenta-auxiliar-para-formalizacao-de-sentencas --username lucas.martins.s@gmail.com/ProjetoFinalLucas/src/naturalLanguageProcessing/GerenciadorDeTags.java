package naturalLanguageProcessing;

import java.util.ArrayList;
import java.util.List;


/**
 *  Esta classe tem como objetivo gerenciar os tags sendo responsável por manipula-los..
 *  
 * @author Lucas
 *
 */
public class GerenciadorDeTags 
{
	
	private static GerenciadorDeTags _instance; // instancia do gerenciador
	private static List<Tag> _tags = new ArrayList<Tag>();
	private String _tipoLexico;
	

	private GerenciadorDeTags ( String tipoLexico)
	{
		if ( tipoLexico.equals("fext"))
		{
			inicializaTagsFext();
			_tipoLexico = "fext";
		}
		else if ( tipoLexico.equals("lxSuite"))
		{
			inicializaTagsLxSuite();
			_tipoLexico = "lxSuite";
		}


	}
	
	
	/**
	 * Classe para se obter uma instancia do gerenciador
	 * 
	 * 
	 * @author Lucas
	 * @return Instancia do {@link GerenciadorDeSimbolos}
	 */
	public static GerenciadorDeTags getInstance( String tipoLexico)
	{
		if ( _instance == null )
		{
			_instance = new GerenciadorDeTags(tipoLexico);
		}
		return _instance;
	}
	
	
	/**
	 * Classe responsável por inicializar os tags com os padrões Fext
	 * 
	 * @author Lucas
	 * @return void
	 */
	private void inicializaTagsFext()
	{
		_tags.clear();
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
	 * Classe responsável por inicializar os tags com os padrões lxSuite
	 * 
	 * @author Lucas
	 * @return void
	 */
	private void inicializaTagsLxSuite()
	{
		_tags.clear();
		_tags.add(new Tag("CJ"));
		_tags.add(new Tag("V"));
		_tags.add(new Tag("VAUX"));
		_tags.add(new Tag("CL"));
		_tags.add(new Tag("ADV"));
		_tags.add(new Tag("PNM"));
		_tags.add(new Tag("PRS"));
		_tags.add(new Tag("CL"));
		_tags.add(new Tag("VRG"));
		_tags.add(new Tag("DA"));
		_tags.add(new Tag("CN"));
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
	public static List<Tag> getListTag ()
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
		if ( this._tipoLexico == "lxSuite")
			this.inicializaTagsLxSuite();
		else
			this.inicializaTagsFext();
	}

}
