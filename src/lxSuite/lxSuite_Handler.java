package lxSuite;


import pt.ul.fc.di.nlx.lxServiceClient.LXClient;
import pt.ul.fc.di.nlx.lxServiceClient.exceptions.LXException;




public class lxSuite_Handler 
{
	private LXClient client = null;
	private static lxSuite_Handler instance = null;
	// Method for calling the singleton class
	public static lxSuite_Handler getInstance()
	{
		if ( instance ==  null )
		{
			instance =  new lxSuite_Handler();
		}
		return instance;
	}
	
	
	// Method used to prepare the lxSuite
	private void InitiateLxSuite()
	{
		try
		{
			// cria os objetos para acessar o serviço Web FEXT
			client = new LXClient( "lmartins");
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
		}
	}
	
	protected lxSuite_Handler()
	{
		InitiateLxSuite();
	}
	
	public String EnrichText(String inputText)
	{
		try
		{
			String tokenizedText = client.tokenizes( inputText );
			
			// Calls the posTagger method
			String posTaggedText = client.posTagsTok( tokenizedText );
			
			// Prints the String returned by the service
			//System.out.println( "\nPOS Tagged text:\n" + posTaggedText );
			return posTaggedText;
		}
		catch (LXException e)
		{
			e.printStackTrace();
			return null;
		}
	
	}

}
