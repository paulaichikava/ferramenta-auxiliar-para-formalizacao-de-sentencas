package naturalLanguageProcessing;

import java.util.ArrayList;
import java.util.List;

import fext.FEXT_Handler;


public class Heuristica 
{
	private String _inputText;
	private String _tipoLexica;
	private List<DuplaTextoProcessado> _duplas;
	private List<ProposicaoMolecular> _proposicoes;
	private List<ProposicaoAtomica> _atomicas;
	private DicionarioDeConectivos _dicionarioConectivos;
	private DicionarioDePadroes _dicionarioPadroes;
	private GerenciadorDeTags _gerenciadorDeTags;
	private static FEXT_Handler _fextHandler;
	
	public Heuristica( String text, String tipoLexica )
	{
		_inputText = text;
		_tipoLexica = tipoLexica;
		_gerenciadorDeTags = GerenciadorDeTags.getInstance(_tipoLexica); 
		_duplas = DuplaTextoProcessado.processaTexto(text, _tipoLexica); // Modificar & Programmar Hard
		_dicionarioConectivos = DicionarioDeConectivos.getInstance(); 
		_dicionarioPadroes = DicionarioDePadroes.getInstance(_tipoLexica);
		_proposicoes = Proposicao.obtemListaDeProposicoes(_duplas); // Aqui populo a lista de frases com proposições moleculares ou atomicas.
		_atomicas = Proposicao.obtemProposicoesAtomicas(_proposicoes,_dicionarioConectivos);  
	}
	
	public List<ProposicaoAtomica> getProposicoesAtomicas()
	{
		return _atomicas;
	}
	
	public String formaLogicaDaProposicao(int n)
	{
		ProposicaoMolecular prop = _proposicoes.get(n);
		return prop.createLogicForm(_dicionarioPadroes,_dicionarioConectivos, _gerenciadorDeTags);		
	}
	
	public List<ProposicaoAtomica> proposicoesAtomicasDaProposicao(int n)
	{
		ProposicaoMolecular prop = _proposicoes.get(n);
		List<ProposicaoAtomica> lst = prop.getListadeProposicoesAtomicas(_dicionarioPadroes, _dicionarioConectivos, _gerenciadorDeTags);
		if ( lst == null ) // caso error
			return null;
		List<ProposicaoAtomica> lst2 =  new ArrayList<>(lst);
		List<ProposicaoAtomica> lst3 =  new ArrayList<>();
		
		// Encontrando proposições idênticas
		for ( ProposicaoAtomica prp : lst)
		{
			lst2.remove(prp);
			for ( ProposicaoAtomica prp1 : lst2)
			{
				if (prp.IsEqual(prp1))
				{
					lst3.add(prp1);
				}
			}
		}
		// Removendo proposições idênticas
		for ( ProposicaoAtomica prp : lst3)
		{
			lst.remove(prp);
		}
		
		return lst;
	}
	
	/**
	 *   Retorna as proposições do texto inserido nesta classe {@link Heuristica}
	 * @return Uma lista de {@link ProposicaoMolecular}
	 */
	public List<ProposicaoMolecular> getProposicoes()
	{
		return _proposicoes;
	}
	public static String enriquecerComFexTexto( String texto )
	{
		_fextHandler = FEXT_Handler.getInstance();
		String answer = _fextHandler.EnrichText(texto);
		return answer;
	}
	
	
	
	
	
	
	

}
