package naturalLanguageProcessing;

import java.util.ArrayList;
import java.util.List;

public class ProposicaoAtomica 
{
	public List<DuplaTextoProcessado> _proposicaoTotal; // Corpo da frase
	public DuplaTextoProcessado _verb;
	public DuplaTextoProcessado _subject;
	public List<DuplaTextoProcessado>  _predicates;
	
	public ProposicaoAtomica()
	{
		
	}
	
	public ProposicaoAtomica ( DuplaTextoProcessado verb, DuplaTextoProcessado subject, List<DuplaTextoProcessado> predicate, List<DuplaTextoProcessado> proposicaoTotal)
	{
		_verb = verb;
		_subject = subject;
		_predicates = new ArrayList<DuplaTextoProcessado>(predicate);
		_proposicaoTotal = new ArrayList<DuplaTextoProcessado>(proposicaoTotal);
	}

}
