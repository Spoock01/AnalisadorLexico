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
   
   public void declaracaoVariaveis(){
       
       if(this.currentToken.getToken().equals("var")){
           nextToken();
           listaDeclaracaoVariaveis();
       }
       
   }
   
   public void listaDeclaracaoVariaveis(){
       
       
   }
   
   public void listaIdentificadores(){
       
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
       
   }
   
   public void argumentos(){
       
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
       
   }
   
   public void ativacaoProcedimento(){
       
   }
   
   public void listaExpressoes(){
       
   }
   
   public void expressao(){
       
   }
   
   public void expressaoSimples(){
       
   }
   
   public void termo(){
       
   }
   
   public void fator(){
       
       
   }
   
   public void sinal(){
        if(currentToken.getToken().equals("+") || currentToken.getToken().equals("-"))
            nextToken();      
        else
            System.out.println("Deu pau! esperando sinal + ou -");
   }
   
   public void opRelacional(){
       if(currentToken.getToken().equals("Operador relacional"))
           nextToken();
       else
           System.out.println("Deu pau! esperando operador relacional");
   }
   
   public void opAditivo(){
       if(currentToken.getToken().equals("Operador aditivo"))
           nextToken();
       else
           System.out.println("Deu pau! esperando operador aditivo");
   }
   
   public void opMultiplicativo(){
       if(currentToken.getToken().equals("Operador multiplicativo"))
           nextToken();
       else
           System.out.println("Deu pau! esperando operador multiplicativo");
   }
}
