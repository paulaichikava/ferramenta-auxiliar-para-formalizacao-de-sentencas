package naturalLanguageProcessing;

import java.util.ArrayList;
import java.util.List;

public class ProposicaoMolecular 
{

	public List<DuplaTextoProcessado> _corpo;
	public List<DuplaTextoProcessado> _subjects;
	public DuplaTextoProcessado _verb;
	public List<DuplaTextoProcessado> _predicates;
	public List<DuplaTextoProcessado> _conectivos;
	public String _conectivoPrincipal;
	
	public ProposicaoMolecular(List<DuplaTextoProcessado> corpo)
	{
		_corpo = new ArrayList<DuplaTextoProcessado>(corpo);
		_predicates = new ArrayList<DuplaTextoProcessado>();
		_subjects = new ArrayList<DuplaTextoProcessado>();
		_conectivos = new ArrayList<DuplaTextoProcessado>();
	}
	
	
	/**
	 *  < Obsoleto >
	 *  Encontra sujeitos nesta frase. Enquanto isto sujeito são apenas nomes próprios.
	 * 
	 */
	public void findSubjects()
	{
		for ( DuplaTextoProcessado dp: _corpo)
		{
			if ( dp._tag.contains("NPROP")) 
			{
				_subjects.add(dp); // Talvez eu coloque uma pergunta aqui!  ( Pergunta ao verbo: "Quem foi a praia?" )
			}
		}
	}
	
	
	/**
	 * 
	 * Este metodo procura por todos os conectivos da proposição no dicionario e substitui os que conseguir encontrar.
	 * Caso não consiga encontrar ele fara uma pergunta ao usuário para tentar encontrar.
	 * 
	 * @param dicionario
	 */
	public void findAndChangeConectivos( DicionarioDeConectivos dicionario)
	{
		for ( DuplaTextoProcessado dp: _corpo)
		{
			if ( dp._tag.contains("KC")) // 
			{
				_conectivos.add(dp); 
			}
		}
		
		// Não possuí conectivos a proposição é atomica.
		if ( _conectivos.size() == 0)
		{
			_conectivoPrincipal = null;
		}
		else // Caso contrario.
		{
			for ( DuplaTextoProcessado dp: _conectivos)
			{
				if ( dicionario.ObtemConectivo(dp._palavra) == null )
				{
					// Caso não consigo encontrar o conectivo.
					
					//#### IMPLEMENTAR ISTO DAQUI!!!
					
				}
				else // Encontrei o conectivo.
				{
					for ( DuplaTextoProcessado dp2: _corpo) // Substituo no corpo da proposicao o conectivo encontrado.
					{
						if ( dp2 == dp )
							dp2._palavra = dicionario.ObtemConectivo(dp._palavra);
					}
				}
				
			}
		}
	}

	/**
	 * 
	 * Este método procura comparar esta proposição molecular a algum padrão pré definido. Para assim formaliza-la.
	 * 
	 */
	private String IdentifyPattern( DicionarioDePadroes dicionario)
	{
		String proposicao = null;
		
		// Monto uma string com todas as palavras do corpo da proposicao.
		for ( DuplaTextoProcessado dp: _corpo)
		{
			if ( dp._tag == "V" || dp._tag == "KC")
				proposicao += dp._tag;
			else
				proposicao += dp._palavra;
			
			proposicao += " ";
		}
		proposicao = proposicao.trim(); // Remove espacos em branco.
		
		int count = dicionario.getNumberOfElements();
		
		
		// Neste for eu procuro por um match na expressão do meu dicionario de padroes. Se eu encontrar significa que eu consigo formalizar a sentença.
		for ( int i = 0; i == count; i++)
		{
			if ( proposicao.matches(dicionario.ObtemConectivo(i)))
				return dicionario.ObtemConectivo(i);
		}
		
		return null;
	}
	
	public String createLogicForm(DicionarioDePadroes dicionario)
	{
		
		String pattern = IdentifyPattern(dicionario);
		
		
		
		return "aa";
	}
	
	
	/**
	 *  < Obsoleto >
	 * 
	 *  Encontra o verbo da proposição.
	 */
	public void findVerb()
	{
		for ( DuplaTextoProcessado dp: _corpo)
		{
			if ( dp._tag.contains("V"))
			{
				_verb =  dp; // Talvez eu coloque uma pergunta aqui!  ( "Este é mesmo o verbo?" )
			}
		}
	}
	
	
	/**
	 *  < Obsoleto >
	 *  
	 *  Encontra os predicados da proposição.
	 */
	public void findPredicate()
	{
		List<DuplaTextoProcessado> auxList  = new ArrayList<DuplaTextoProcessado>(_corpo);
		
		// Se ainda não tiver encontrado o verbo e o sujeito. Eu encontro eles agora.
		
		if ( _verb ==  null)
			findVerb();
		if ( _subjects.size() == 0)
			findSubjects();
		
		// Removo os sujeitos da minha frase.
		for ( DuplaTextoProcessado sub : _subjects  )
		{
			auxList.remove(sub);
		}
		
		
		// Removo o verbo 
		
		auxList.remove(_verb);
		
		
		// Acredito que tenha sobrado o predicado.
		
		for ( DuplaTextoProcessado dp: auxList)
		{
			if ( _corpo.indexOf(dp) > _corpo.indexOf(_verb) )  // Adiciono aos predicados todo mundo que sobrou e vem depois do verbo.
				_predicates.add(dp);
		}
	}
	
	
	
	
}
