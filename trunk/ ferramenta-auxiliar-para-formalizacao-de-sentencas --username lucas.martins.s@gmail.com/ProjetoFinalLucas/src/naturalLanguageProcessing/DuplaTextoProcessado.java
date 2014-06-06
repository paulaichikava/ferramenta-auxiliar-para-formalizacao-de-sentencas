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
	 *  A Partir de uma lista de {@link DuplaTextoProcessado} e de uma string, recupero os tags da string usando a lista como análise.
	 *  Retornando uma lista de {@link DuplaTextoProcessado} que representa a string processada pelo F-EXT.
	 * @param str String
	 * @param listDP Lista de {@link DuplaTextoProcessado} que será usada como base.
	 * @return
	 */
	public static List<DuplaTextoProcessado> recuperaTagsDaString(String str, List<DuplaTextoProcessado> listDP)
	{
		List<DuplaTextoProcessado> lst = new ArrayList<DuplaTextoProcessado>();
		
		// Proxima linha existe para garatir que a ordem das palavras na lista é fiel a string Str
	//	str = str.replaceAll("\\.", " \\. ");
		str = str+" ";
		String palavra = str.substring(0, str.indexOf(" "));  // Obtenho a 1 palavra da string. Quero adicionar ela.
		
		int i = 0;
		while ( palavra != null)
		{
			if ( i == listDP.size() )
				i = 0;
			DuplaTextoProcessado dp = listDP.get(i);
			if (palavra.equals(dp._palavra))
			{
				lst.add(dp);
				str = str.replaceFirst(dp._palavra +" ", "");
				if ( !str.equals(""))
					palavra = str.substring(0, str.indexOf(" ")); // Obtenho a proxima palavra da minha lista.
				else
					palavra = null;
			}
			i++;
		}
		return lst;		
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
	public static List<DuplaTextoProcessado> processaTexto( String text , String tipoLexica)
	{
		 String[] itens;
		 String[] aux;
		 List<DuplaTextoProcessado> listParts = new ArrayList<DuplaTextoProcessado>();
		 int i = 0;
		 if ( tipoLexica.equals("fext"))
		 {
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
		 }
		 else if (tipoLexica.equals("lxSuite"))
		 {
			 text = text.replaceAll("<p>", "");
			 text = text.replaceAll("<s>", "");
			 text = text.replaceAll("</p>", "");
			 text = text.replaceAll("</s>", "");
			 itens = text.split(" ");
			 DuplaTextoProcessado part ;
			 
			 for ( String elem: itens) 
			 {
				 if ( itens.length > 3  )
				 {
					aux = elem.split("/");
					if ( aux.length == 2 )
					{
						part = new DuplaTextoProcessado(aux[0], aux[aux.length-1]);
						listParts.add(part);
					}
					if ( aux.length > 2 )
					{
						String aux2 = aux[aux.length-1];
						aux2 = aux2.replaceFirst("#.*?$", "");
						if(aux[2].equals("PNT"))
						{
							aux[0] = aux[0].replaceFirst("\\*", "");
						}
						part = new DuplaTextoProcessado(aux[0], aux2);
						listParts.add(part);
					}
				 }i++;
			 }
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
