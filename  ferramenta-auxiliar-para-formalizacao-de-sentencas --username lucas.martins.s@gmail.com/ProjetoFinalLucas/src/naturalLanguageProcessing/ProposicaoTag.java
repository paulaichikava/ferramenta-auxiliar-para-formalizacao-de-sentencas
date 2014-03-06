package naturalLanguageProcessing;

import java.util.List;

public class ProposicaoTag 
{
	private String _matchCorpo; // Representa como a expressão é estruturada para a lista de regExp ( _listaRegExpDeProposicoesAtomicas) conseguir capturar as suas proposições atômicas.
	private List<String> _listaRegExpDeProposicoesAtomicas;
	private String _logicForm; // A logic form é a forma lógica dessa ProposicaoTag, ela representa cada proposicao atomica na mesma ordem que ela é descria na listaRegExpDeProposicoesAtomicas.
	
	public ProposicaoTag( String id, List<String> lista, String Logic )
	{
		_matchCorpo = id;
		_listaRegExpDeProposicoesAtomicas = lista;
		_logicForm = Logic;
	}
	
	public String getIdRegexp()
	{
		return _matchCorpo;
	}
	
	/**
	 *  Este método retona o número de proposições atômicas que podemos capturar com este modelo. ( ProposicaoTag )
	 * @return Número de proposicoes atômicas que podemos capturar com este Tag.
	 */
	public int getNumeroDeProposicoesAtomicas()
	{
		return _listaRegExpDeProposicoesAtomicas.size();
	}
	
	/**
	 *   Retorna a Expressao Regular necessaria para capturar a proposicao de numero X da proposicao molecular.
	 * @param numeroDaProp Número da proposição que vou capturar.
	 * @return
	 */
	public String getRexpForNprop( int numeroDaProp)
	{
		if ( numeroDaProp <= _listaRegExpDeProposicoesAtomicas.size() )
			return _listaRegExpDeProposicoesAtomicas.get(numeroDaProp);
		else
			return null;
	}
	
	public String getLogicFormDestaProposicaoTag()
	{
		return _logicForm;
	}
	
}
