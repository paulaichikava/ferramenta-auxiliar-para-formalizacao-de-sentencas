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
			if ( dp._tag.contains("NPROP")) // 
			{
				_subjects.add(dp); // Talvez eu coloque uma pergunta aqui!  ( Pergunta ao verbo: "Quem foi a praia?" )
			}
		}
	}
	
	
	/**
	 * 
	 * Este metodo procura por todos os conectivos da proposição no dicionario e substitui os que conseguir encontrar.
	 * Caso não consiga encontrar ele fara uma pergunra ao usuário para tentar encontrar.
	 * 
	 * @param dicionario
	 */
	public void findConectivos( DicionarioDeConectivos dicionario)
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
