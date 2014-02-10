package naturalLanguageProcessing;

import java.util.ArrayList;
import java.util.List;

public class Axioma 
{
	public List<DuplaTextoProcessado> _mainFrase; // Corpo da frase
	public DuplaTextoProcessado _verb;
	public DuplaTextoProcessado _subject;
	public List<DuplaTextoProcessado>  _predicates;
	
	public Axioma()
	{
		
	}
	
	public Axioma ( DuplaTextoProcessado verb, DuplaTextoProcessado subject, List<DuplaTextoProcessado> predicate, List<DuplaTextoProcessado> mainFrase)
	{
		_verb = verb;
		_subject = subject;
		_predicates = new ArrayList<DuplaTextoProcessado>(predicate);
		_mainFrase = new ArrayList<DuplaTextoProcessado>(mainFrase);
	}

}
