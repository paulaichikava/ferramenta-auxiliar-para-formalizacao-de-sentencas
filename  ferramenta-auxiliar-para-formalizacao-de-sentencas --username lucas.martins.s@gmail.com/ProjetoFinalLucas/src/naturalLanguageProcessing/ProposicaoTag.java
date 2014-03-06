package naturalLanguageProcessing;

import java.util.List;

public class ProposicaoTag 
{
	private String _matchCorpo; // Representa como a express�o � estruturada para a lista de regExp ( _listaRegExpDeProposicoesAtomicas) conseguir capturar as suas proposi��es at�micas.
	private List<String> _listaRegExpDeProposicoesAtomicas;
	private String _logicForm; // A logic form � a forma l�gica dessa ProposicaoTag, ela representa cada proposicao atomica na mesma ordem que ela � descria na listaRegExpDeProposicoesAtomicas.
	
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
	 *  Este m�todo retona o n�mero de proposi��es at�micas que podemos capturar com este modelo. ( ProposicaoTag )
	 * @return N�mero de proposicoes at�micas que podemos capturar com este Tag.
	 */
	public int getNumeroDeProposicoesAtomicas()
	{
		return _listaRegExpDeProposicoesAtomicas.size();
	}
	
	/**
	 *   Retorna a Expressao Regular necessaria para capturar a proposicao de numero X da proposicao molecular.
	 * @param numeroDaProp N�mero da proposi��o que vou capturar.
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
