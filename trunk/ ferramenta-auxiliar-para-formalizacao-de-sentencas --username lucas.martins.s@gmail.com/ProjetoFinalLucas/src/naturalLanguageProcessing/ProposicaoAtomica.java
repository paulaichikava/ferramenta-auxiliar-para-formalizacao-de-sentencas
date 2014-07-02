package naturalLanguageProcessing;

import java.util.ArrayList;
import java.util.List;

public class ProposicaoAtomica extends Proposicao
{
	
	public ProposicaoAtomica()
	{
		
	}
	
	public ProposicaoAtomica ( DuplaTextoProcessado verb, DuplaTextoProcessado subject, List<DuplaTextoProcessado> predicate, List<DuplaTextoProcessado> proposicaoTotal)
	{	
		_corpo = new ArrayList<DuplaTextoProcessado>(proposicaoTotal);
	}

	public ProposicaoAtomica( List<DuplaTextoProcessado> corpo)
	{
		_corpo = corpo;
	}
	
	public boolean IsEqual (ProposicaoAtomica  prop)
	{
		ProposicaoAtomica prop1 = new ProposicaoAtomica(prop.removeDuplaComNao()._corpo);
		ProposicaoAtomica prop2 = new ProposicaoAtomica(this.removeDuplaComNao()._corpo);
		if ( DuplaTextoProcessado.convertListDuplaProcessadoToString(prop1._corpo).equals(DuplaTextoProcessado.convertListDuplaProcessadoToString(prop2._corpo)))
			return true;
		else
			return false;
	}
}
