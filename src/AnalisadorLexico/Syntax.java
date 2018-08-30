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
       
       
   }
   
   public void opRelacional(){
       
   }
   
   public void opAditivo(){
       
       
   }
   
   public void opMultiplicativo(){
       
   }
}
