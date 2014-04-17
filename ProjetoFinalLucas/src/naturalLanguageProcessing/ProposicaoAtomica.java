package naturalLanguageProcessing;

import java.util.ArrayList;
import java.util.List;

public class ProposicaoAtomica extends Proposicao
{
	public DuplaTextoProcessado _verbo; // N�o estou usando por enquanto..
	public DuplaTextoProcessado _sujeito; // N�o estou usando por enquanto..
	public List<DuplaTextoProcessado>  _predicados; // N�o estou usando por enquanto..
	
	public ProposicaoAtomica()
	{
		
	}
	
	public ProposicaoAtomica ( DuplaTextoProcessado verb, DuplaTextoProcessado subject, List<DuplaTextoProcessado> predicate, List<DuplaTextoProcessado> proposicaoTotal)
	{
		_verbo = verb;
		_sujeito = subject;
		_predicados = new ArrayList<DuplaTextoProcessado>(predicate);
		_corpo = new ArrayList<DuplaTextoProcessado>(proposicaoTotal);
	}

	public ProposicaoAtomica( List<DuplaTextoProcessado> corpo)
	{
		_corpo = corpo;
	}
}
