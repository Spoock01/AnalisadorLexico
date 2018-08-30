package AnalisadorLexico;

import java.util.ArrayList;

public class Syntax {
   private ArrayList<Table> tokens;
   private Table currentToken;
   private int nextTokenIndex;
   
   private void nextToken(){
       this.currentToken = tokens.get(nextTokenIndex++);
   }
   
   public Syntax(ArrayList<Table> tokens){
       this.tokens = tokens;
       this.nextTokenIndex = 0;
       nextToken();
   }
   
   public void execute(){
       
       if(this.tokens == null)
           System.exit(0);
       
       if(currentToken.getClassificacao().equals("Palavra reservada")
       && currentToken.getToken().equals("Program")){
           
           nextToken();
           
           if(currentToken.getClassificacao().equals("Identificador")){
               nextToken();
               
               if(currentToken.getClassificacao().equals("Delimitador")
               && currentToken.getToken().equals(";")){
                   nextToken();
               }
               
           }
       }
   }
}
