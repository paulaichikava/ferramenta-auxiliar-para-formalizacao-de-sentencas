package moduloTeste;

import java.util.ArrayList;
import java.util.List;

import fext.*;
import naturalLanguageProcessing.*;

public class testMain 
{
	 public static void main(String[] args)
	 {
		 String answer;
		 List<DuplaTextoProcessado> listParts = new ArrayList<DuplaTextoProcessado>();
		 FEXT_Handler handler = FEXT_Handler.getInstance();
		 answer = handler.EnrichText("Maria e Jose s�o portugueses.");  //  B AND P 
		 
		 
		 // Futuro m�todo!
		
	
		 System.out.println(answer);
		 
		 
		 // Inicio do teste ( parte mais recente do c�digo )
		

		
		
		
		 // Fim futuro m�todo
	    
	    
	 }

}
