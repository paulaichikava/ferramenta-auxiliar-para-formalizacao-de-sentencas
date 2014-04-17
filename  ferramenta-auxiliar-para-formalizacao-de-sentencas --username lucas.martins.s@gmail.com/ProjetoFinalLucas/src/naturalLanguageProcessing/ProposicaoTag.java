package naturalLanguageProcessing;

import java.util.List;
import java.util.regex.Pattern;

public class ProposicaoTag 
{
	private String _matchCorpo; // Representa como a express�o � estruturada para a lista de regExp ( _listaRegExpDeProposicoesAtomicas) conseguir capturar as suas proposi��es at�micas.
	private List<Pattern> _listaRegExpDeProposicoesAtomicas;
	private int _tipoDeExtracao; // Aqui eu defino se poso usar o match para conseguir exatamente o que quero ou se preciso subtrair ele da string "pai". Caso esse int seja 1 subtraio, caso 0 uso o match puro.
	private String _operadorLogico; // A logic form � a forma l�gica dessa ProposicaoTag, ela representa cada proposicao atomica na mesma ordem que ela � descria na listaRegExpDeProposicoesAtomicas.
	
	public ProposicaoTag( String id, List<Pattern> lista, String Logic , int tipo )
	{
		_matchCorpo = id;
		_listaRegExpDeProposicoesAtomicas = lista;
		_operadorLogico = Logic;
		_tipoDeExtracao = tipo;
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
	public Pattern getRexpForNprop( int numeroDaProp)
	{
		if ( numeroDaProp <= _listaRegExpDeProposicoesAtomicas.size() )
			return _listaRegExpDeProposicoesAtomicas.get(numeroDaProp);
		else
			return null;
	}
	
	public String getOperadorLogico()
	{
		return _operadorLogico;
	}
	
	public int getTipoDeExtracao()
	{
		return _tipoDeExtracao;
	}
	
}
