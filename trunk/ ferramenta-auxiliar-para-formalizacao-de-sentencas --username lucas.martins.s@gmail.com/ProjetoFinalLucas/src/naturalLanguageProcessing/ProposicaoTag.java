package naturalLanguageProcessing;

import java.util.List;

public class ProposicaoTag 
{
	private String _matchCorpo; // Representa como a express�o � estruturada para a lista de regExp ( _listaRegExpDeProposicoesAtomicas) conseguir capturar as suas proposi��es at�micas.
	private List<String> _listaRegExpDeProposicoesAtomicas;
	
	public ProposicaoTag( String id, List<String> lista )
	{
		_matchCorpo = id;
		_listaRegExpDeProposicoesAtomicas = lista;
	}
	
	public String getIdRegexp()
	{
		return _matchCorpo;
	}
	
	
	// Retorna a Expressao Regular necessaria para capturar a proposicao de numero X da proposicao molecular.
	public String getRexpForNprop( int numeroDaProp)
	{
		if ( numeroDaProp <= _listaRegExpDeProposicoesAtomicas.size() )
			return _listaRegExpDeProposicoesAtomicas.get(numeroDaProp);
		else
			return null;
	}
	
}
