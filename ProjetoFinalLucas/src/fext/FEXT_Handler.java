package fext;


import java.rmi.RemoteException;

import br.org.fplf.learn.baixo.axis.services.FEXT20.GatewayFEXT;
import br.org.fplf.learn.baixo.axis.services.FEXT20.GatewayFEXTProxy;


/**
 * Esta classe conversa com o serviço FEXT oferecido pelo laboratório do professor Ruy.  ( LEARN ) PUC-RIO
 * 
 * 
 * @author Lucas
 *
 */
public class FEXT_Handler 
{

	// I`m using singleton on FEXT_Handler
	private static FEXT_Handler instance = null;

	private  GatewayFEXTProxy _gatewayFextProxy; // utilizado para obter o proxy
	private  GatewayFEXT _gatewayFext; // utilizado para acessar o serviço Web

	private static String _username = "vitor.vpa";
	private static String _password = "vitor$vpa#";
	private static String _language = "por";
	private static String _linguisticInfo = "pos";
	
	
	// Method for calling the singleton class
	public static FEXT_Handler getInstance()
	{
		if ( instance ==  null )
		{
			instance =  new FEXT_Handler();
		}
		return instance;
	}
	
	
	// Method used to prepare the F-EXT.
	private void InitiateFext()
	{
		try
		{
			// cria os objetos para acessar o serviço Web FEXT
			_gatewayFextProxy = new GatewayFEXTProxy();
			_gatewayFext = _gatewayFextProxy.getGatewayFEXT();
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
		}
	}
	
	protected FEXT_Handler()
	{
		InitiateFext();
	}
	
	// Return the string after processing it with the desired liguisticInfo
	public String EnrichText(String text)
	{
		String answer;
		int output = -1;
		
		try
		{
			output = this._gatewayFext.runTask(_username, _password, _language, _linguisticInfo, text);
		}
		catch(RemoteException e)
		{
			System.out.println("Error trying to execute F-EXT procedures.");
			e.printStackTrace();
			return null;
		}
		
		if ( output < 0)
		{
			// Some error just happened!
			String error = "";
			try
			{
				error = _gatewayFext.getErrorMessage(output);
			}
			catch(RemoteException e)
			{
				System.out.println("Error trying to get error message from F-EXT.");
				e.printStackTrace();
				return null;
			}
			
			System.out.println("There was an error during F-EXT procedures the error was: "+ error);
			
			// Return a null string case error happened
			
			return null;
		}
		else
		{ 	
			// At this point everything went well with the F-EXT
			
			try
			{
				answer = _gatewayFext.getResult(_username, _password, output);
			}
			catch (RemoteException e)
			{
				System.out.println("Fail to retrieve processed text from F-EXT");
				e.printStackTrace();
				return null;
			}
			return answer;
		}
		
	}
	
	
	
}
