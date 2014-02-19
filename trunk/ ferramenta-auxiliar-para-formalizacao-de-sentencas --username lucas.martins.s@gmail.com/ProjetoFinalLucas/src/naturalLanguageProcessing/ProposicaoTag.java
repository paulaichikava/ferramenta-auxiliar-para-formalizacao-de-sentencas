package naturalLanguageProcessing;

import java.util.List;

public class ProposicaoTag 
{
	private String _idRegexp;
	private List<String> _listaRegExpDeProposicoesAtomicas;
	
	public ProposicaoTag( String id, List<String> lista )
	{
		_idRegexp = id;
		_listaRegExpDeProposicoesAtomicas = lista;
	}
	
	public String getIdRegexp()
	{
		return _idRegexp;
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
