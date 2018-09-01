package AnalisadorLexico;

import java.util.ArrayList;

public class Syntax {
   private ArrayList<Table> tokens;
   private Table currentToken;
   private int nextTokenIndex;
   
   public Syntax(ArrayList<Table> tokens){
       this.tokens = tokens;
       this.nextTokenIndex = 0;
   }
   
   private void previousToken(){
       
       if(nextTokenIndex - 1 >= 0)
           this.currentToken = tokens.get(--nextTokenIndex);
       else
           System.out.println("Tem como voltar n, chefe.");
   }
   
   private void nextToken(){
        if(nextTokenIndex + 1 < tokens.size())
            this.currentToken = tokens.get(nextTokenIndex++);
        else
            System.out.println("É só isso\n"
                            + "não tem mais jeito\n"
                            + "Acabou\n"
                            + "Boa sorte");
   }
   
   public void execute(){
       
       if(this.tokens == null)
           System.exit(0);
       
       nextToken();
       
       if(currentToken.getToken().equals("Program")){
           
           nextToken();
           
           if(currentToken.getClassificacao().equals("Identificador")){
               nextToken();
               
               if(currentToken.getToken().equals(";")){
                   nextToken();
                   
                   
               }
               
           }
       }
   }
   
   public boolean declaracaoVariaveis(){
       
       if(this.currentToken.getToken().equals("var")){
           nextToken();
           return listaDeclaracaoVariaveis();
       }
       
       return false;
       
   }
   
   public boolean listaDeclaracaoVariaveis(){
       // TA F0D4
       
       return true;
   }
   
   public boolean listaIdentificadores(){
       // TA F0D4
       
       return true;
   }
   
   public void tipo(){
       if(currentToken.getClassificacao().equals("Palavra reservada") &&
          (currentToken.getToken().equals("integer") || currentToken.getToken().equals("real")
        || currentToken.getToken().equals("boolean")))
           nextToken();
       else
           System.out.println("DEU ERRO NO TIPO");
   }   
   public void declaracaoSubprogramas(){
       
   }
   
   public void declaracaoSubprograma(){
       
       if(currentToken.getToken().equals("procedure")){
           
           nextToken();
           
           if(currentToken.getClassificacao().equals("Identificador")){
               
               nextToken();
               
               if(argumentos()){
                   
               }else{
                   
                   System.out.println("Tem alguma coisa errada nos argumentos!!");
                   
               }
               
           }else{
               
               System.out.println("Faltou o identificador, moral!!");
               
           }
           
       }
       
   }
   
   public boolean argumentos(){
       
       if(currentToken.getToken().equals("(")){
           
           nextToken();
           
           
       }
       
       return true;
       
   }
   
   public void listaParametros(){
       
   }
   
   public void comandoComposto(){
        if (currentToken.getToken().equals("begin")){
           nextToken();
           comandosOpcionais();
           if(currentToken.getToken().equals("end"))
               nextToken();
           else
                System.out.println("Deu pau! esperando end");
       }else{
            System.out.println("DEU PAU! esperando begin");
        }
   }
   
   public void comandosOpcionais(){
       
       
   }
   
   public void listaComandos(){
       
   }
   
   public void comando(){

       
   }
   
   public void parteElse(){
       
   }
   
   public void variavel(){
       if(currentToken.getClassificacao().equals("Identificador")){
           nextToken();
       }else
           System.out.println("DEU PAU NA VARIAVEL! Esperando Identificador.");
   }
   
   public void ativacaoProcedimento(){
       
   }
   
   public Boolean listaExpressoes(){
       nextToken();
       if(expressao()){
           nextToken();
       }else if(listaExpressoes()){
           nextToken();
       }else{
           System.out.println("Deu pau na lista de expressoes");
           return false;
       }
       return true;
   }
   
   public Boolean expressao(){
       
       if(expressaoSimples()){
           if(opRelacional() && expressaoSimples()){
               nextToken();
               return true;
           }else{
               nextToken();
               return true;
           }
       }else{
           return false;
       }
   }
   
   public Boolean expressaoSimples(){
       // TA F0D4
       return true;
   }
   
   public Boolean termo_(){
       
        if(opMultiplicativo())
            if(fator() && termo_()){
                nextToken();
                return true;
            }else
                return false;       
        else
            return true;

   }
   
    public Boolean termo(){
      if(fator() && termo_()){
          return true;
      }else{
          System.out.println("Deu erro no termo.");
          return false;
      }
    }
   public Boolean fator(){
       
       if(currentToken.getClassificacao().equals("Número inteiro") ||
          currentToken.getClassificacao().equals("Número real") ||
          currentToken.getClassificacao().)
       
       return true;
   }
   
   public void sinal(){
        if(currentToken.getToken().equals("+") || currentToken.getToken().equals("-"))
            nextToken();      
        else
            System.out.println("Deu pau! esperando sinal + ou -");
   }
   
   public Boolean opRelacional(){
        if(currentToken.getToken().equals("Operador relacional")){
           nextToken();
           return true;
        }    
        else{
            previousToken();
            System.out.println("Deu pau! esperando operador relacional");
        }
        
        return false;
   }
   
   public Boolean opAditivo(){
        if(currentToken.getToken().equals("Operador aditivo")){
           nextToken();
           return true;
        }    
        else{
            System.out.println("Deu pau! esperando operador aditivo");
            previousToken();
        }
       return false;
   }
   
   public Boolean opMultiplicativo(){
        if(currentToken.getToken().equals("Operador multiplicativo")){
           nextToken();
           return true;
        }    
        else{
            previousToken();
            System.out.println("Deu pau! esperando operador multiplicativo");
        }

       return false;
   }
}
