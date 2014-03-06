package naturalLanguageProcessing;

import java.util.ArrayList;
import java.util.List;

/**
 *   Esta classe relaciona duas strins _palavra e _tag.
 *   _palavra =  Uma palavra em portugues.
 *   _tag = Um tag que o F-EXT deu para a palavra.
 * @author Roiw
 *
 */
public class DuplaTextoProcessado 
{
	public String _palavra;
	public String _tag;
	
	public DuplaTextoProcessado( String value, String type )
	{
		_palavra = value;
		_tag = type;
	}
	
	/**
	 * 
	 *  Retorna uma lista de objetos do tipo DuplaTextoProcessado que são duplas texto-tag, ( Text-SignificadoF-EXT ).
	 * 
	 *  A partir desta lista irei encontrar as proposicoes. E das que forem moleculares irei separar proposicoes atomicas.
	 * 
	 * @param text Texto já processado pelo F-EXT que gerará a lista de Duplas.
	 * @return Lista do objeto DuplaTextoProcessado que é um uma dupla ( texto / Significado )
	 */
	public static List<DuplaTextoProcessado> processaTexto( String text )
	{
		 String[] itens;
		 String[] aux;
		 List<DuplaTextoProcessado> listParts = new ArrayList<DuplaTextoProcessado>();
		 int i = 0;
		 
		 itens =  text.split("\n");
		 
		 System.out.println(text);
		
		 for ( String elem: itens) 
		 {
			 if ( i > 1 & i < itens.length  )
			 {
				aux = elem.split(" ");
				DuplaTextoProcessado part = new DuplaTextoProcessado(aux[0], aux[aux.length-1]);
				listParts.add(part);
			 }i++;
		 }
		 
		 return listParts;
	}

	/**
	 *   Transforma uma lista de {@link DuplaTextoProcessado}em uma String.
	 * @param lst Lista de {@link DuplaTextoProcessado}
	 * @return
	 */
	public static String convertListDuplaProcessadoToString(List<DuplaTextoProcessado> lst)
	{
		String rtn = "";
		for ( DuplaTextoProcessado dp : lst)
		{
			rtn += dp._palavra;
			rtn +=" ";
		}
		rtn = rtn.trim();
		return rtn;
	}
	
}
