package naturalLanguageProcessing;

import java.util.ArrayList;
import java.util.List;

public class ProposicaoAtomica 
{
	public List<DuplaTextoProcessado> _proposicaoTotal; // Corpo da frase
	public DuplaTextoProcessado _verbo; // Não estou usando por enquanto..
	public DuplaTextoProcessado _sujeito; // Não estou usando por enquanto..
	public List<DuplaTextoProcessado>  _predicados; // Não estou usando por enquanto..
	
	public ProposicaoAtomica()
	{
		
	}
	
	public ProposicaoAtomica ( DuplaTextoProcessado verb, DuplaTextoProcessado subject, List<DuplaTextoProcessado> predicate, List<DuplaTextoProcessado> proposicaoTotal)
	{
		_verbo = verb;
		_sujeito = subject;
		_predicados = new ArrayList<DuplaTextoProcessado>(predicate);
		_proposicaoTotal = new ArrayList<DuplaTextoProcessado>(proposicaoTotal);
	}

	public ProposicaoAtomica( DuplaTextoProcessado verbo, List<DuplaTextoProcessado> corpo)
	{
		_verbo = verbo;
		_proposicaoTotal = corpo;
	}
}
