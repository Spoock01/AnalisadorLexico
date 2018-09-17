package AnalisadorLexico;

import java.util.ArrayList;


public class Syntax {
   private final ArrayList<Table> tokens;
   private final ArithmeticTable arithmeticTable;
   private Table currentToken;
   private int nextTokenIndex;
   private final boolean showTokens = false;
   private ArrayList<IdentifierType> symbolTable;
   private ArrayList<Table> variableDeclaration; //Para poder atribuir os tipos a ele na hora da declaração
   private ArrayList<IdentifierType> stackAttribution;
   private String opArithmetic;
   
    public Syntax(ArrayList<Table> tokens, ArithmeticTable arithmeticTable){
        this.tokens = tokens;
        this.nextTokenIndex = 0;
        this.arithmeticTable = arithmeticTable;
        symbolTable = new ArrayList<>();
        variableDeclaration = new ArrayList<>();
        stackAttribution = new ArrayList<>();
        opArithmetic = "";
        //symbolTable.add(new IdentifierType("$", "scope identifier"));
    }

    
    void setSymbolType_(){
        
        for(int i = 0; i < variableDeclaration.size(); i++)
            for(int j = symbolTable.size() - 1; j >= 0; j--){
                
                if(variableDeclaration.get(i).getToken().equals(symbolTable.get(j).getIdentifier())){
                    
                    symbolTable.get(j).setType(currentToken.getToken());
                    break;
                    
                }
                
            }
        
        //printDeclaredVariables();
    }
    
    private void setAttributionType(){
        for(int i = 0; i < stackAttribution.size(); i++)
            for(int j = symbolTable.size() - 1; j >= 0; j--){
                
                if(stackAttribution.get(i).getIdentifier().equals(symbolTable.get(j).getIdentifier())){
                    
                    stackAttribution.get(i).setType(symbolTable.get(j).getType());
                    break;
                    
                }
                
            }
               
    }
    
    /*private void checkOperation(){
        
        setAssignmentType();
        for(int i=1; i < stackAssignment.size(); i++){
            
            if(stackAssignment.get(0).getType().equals("integer")){                
                if(!stackAssignment.get(i).getType().equals("integer")){
                    
                    System.out.println("Aqui so aceita integer!! Ta de brincadeira com minha cara, comédia!!");
                    System.exit(0);
                }
                
            }else if(stackAssignment.get(0).getType().equals("real")){               
                if(stackAssignment.get(i).getType().equals("boolean")){
                    
                    System.out.println("Ta viajando, fi? Aqui pega boolean não");
                    System.exit(0);
                    
                }
                
            }else if(stackAssignment.get(0).getType().equals("boolean")){               
                if(!stackAssignment.get(i).getType().equals("boolean")){
                    
                    System.out.println("Tu não estudou lógica com Andrei não?");
                    System.exit(0);
                    
                }               
            }else if(stackAssignment.get(0).getType().equals("unsigned")){
                
            }           
        }
        
        stackAssignment.clear();
    }*/
    
    private void checkAttribution(){
        int top = stackAttribution.size() - 1;
        int underTop = top - 1;
        
        setAttributionType();
        
        if(stackAttribution.get(underTop).getType().equals("integer") &&
           stackAttribution.get(top).getType().equals("integer")){
            
            stackAttribution.clear();
        }else if(stackAttribution.get(underTop).getType().equals("real") &&
                 (stackAttribution.get(top).getType().equals("integer") || 
                  stackAttribution.get(top).getType().equals("real"))){
            
            stackAttribution.clear();
        }else if(stackAttribution.get(underTop).getType().equals("boolean") &&
                 stackAttribution.get(top).getType().equals("boolean")){
            
            stackAttribution.clear();
        }else{
            
            System.out.println("Erro de tipos { "+stackAttribution.get(underTop).getType()+" := "+stackAttribution.get(top).getType()+" }");
            System.exit(0);
        }
        
    }
    
    private void checkRelational(){
        int top = stackAttribution.size() - 1;
        int underTop = top - 1;
        
        setAttributionType();
        
        if(stackAttribution.get(underTop).getType().equals("real") &&
           (stackAttribution.get(top).getType().equals("integer") || 
            stackAttribution.get(top).getType().equals("real"))){
            
            stackAttribution.remove(top);
            stackAttribution.remove(underTop);
            stackAttribution.add(new IdentifierType("unnamed", "boolean"));
            
        }else if(stackAttribution.get(underTop).getType().equals("integer") &&
                 (stackAttribution.get(top).getType().equals("integer") || 
                  stackAttribution.get(top).getType().equals("real"))){
            
            stackAttribution.remove(top);
            stackAttribution.remove(underTop);
            stackAttribution.add(new IdentifierType("unnamed", "boolean"));
            
        }else{
            
            System.out.println("Erro de tipos (Relacional) { "+stackAttribution.get(underTop).getType()+" "+stackAttribution.get(top).getType()+" }");
            System.exit(0);
        }
        
    }
    
    private void checkArithmetic(){
        int top = stackAttribution.size() - 1;
        int underTop = top - 1;
        
        setAttributionType();
        
        if(opArithmetic.equals("and") || opArithmetic.equals("or")){
            if(stackAttribution.get(underTop).getType().equals("boolean") &&
               stackAttribution.get(top).getType().equals("boolean")){
                
                stackAttribution.remove(top);
            }else{
                
                System.out.println("Erro de tipo (and || or) { "+stackAttribution.get(underTop).getType()+" "+stackAttribution.get(top).getType()+" }");
                System.exit(0);
            }
        }else{
            if(stackAttribution.get(underTop).getType().equals("integer") &&
               stackAttribution.get(top).getType().equals("integer")){
                
                stackAttribution.remove(top);
            }else if(stackAttribution.get(underTop).getType().equals("integer") &&
                     stackAttribution.get(top).getType().equals("real")){
                
                //aqui eu removo o penultimo elemento pra deixar o tipo real como topo
                stackAttribution.remove(underTop);
            }else if(stackAttribution.get(underTop).getType().equals("real") &&
                     (stackAttribution.get(top).getType().equals("integer") || 
                      stackAttribution.get(top).getType().equals("real"))){
                
                stackAttribution.remove(top);
            }else{
                
                System.out.println("Erro tipo (Aritmético) tem dois booleans");
                System.exit(0);
            }
        }
    }
    
    /*
    Aqui pega o boolean da condição(if ou while) e compara com a expressão
    */
    private void checkConditional(){
        int top = stackAttribution.size() - 1;
        int underTop = top - 1;
        
        setAttributionType();
        
        if(stackAttribution.get(underTop).getType().equals("boolean") &&
           stackAttribution.get(top).getType().equals("boolean")){
            
            stackAttribution.remove(top);
            stackAttribution.remove(underTop);
        }else{
            
            System.out.println("Erro tipo (Condicional): era pra ser dois booleans!!");
        }
    }
    
    void printDeclaredVariables(){
        for(int i = 0; i < symbolTable.size(); i++){
            //System.out.print(symbolTable.get(i).getIdentifier() + " ");
            System.out.println(symbolTable.get(i).getIdentifier() + " "+symbolTable.get(i).getType());
        }
        System.out.println("");
    }
    
    void checkDeclaration(){
        
        for(int i = 0; i < symbolTable.size(); i++){
            if(currentToken.getToken().equals(symbolTable.get(i).getIdentifier())){
                return;
            }
        }
        System.out.println("{" + currentToken.getToken() + "} undeclared. Line: "+ currentToken.getLine());
        System.exit(0);
        
    }
    
    void declaration(){
        IdentifierType pair = new IdentifierType(currentToken.getToken(), "undefined");
        //System.out.println("Declaring: " + currentToken.getToken());
        for(int i = symbolTable.size() - 1; i >= 0; i--){
            if(symbolTable.get(i).getIdentifier().equals("$")){
                symbolTable.add(pair);
                break;
            }else if(symbolTable.get(i).getIdentifier().equals(currentToken.getToken())){
                System.out.println("{"+currentToken.getToken() + "} already declared!");
                break;
            }
        }   
        //printDeclaredVariables();
    }
    
    public void enterScope(){
        
        //System.out.println("\n\nEntrando no escopo");
        //printDeclaredVariables();
        IdentifierType pair = new IdentifierType("$", "scope identifier");
        symbolTable.add(pair);   
        //printDeclaredVariables();
    }
    
    public void exitScope(){

        //System.out.println("\n\nSaindo do escopo");
        //printDeclaredVariables();
        for(int i = symbolTable.size() - 1; i >= 0; i--){
            if(symbolTable.get(i).getIdentifier().equals("$")){
                symbolTable.remove(i);
                break;
            }else
                symbolTable.remove(i);
        }
        
        //printDeclaredVariables();
    }
    
    private void nextToken(){
         if(nextTokenIndex + 1 <= tokens.size())
             this.currentToken = tokens.get(nextTokenIndex++);
             
         if(this.showTokens)
             System.out.println("TOKEN ATUAL: | " + this.currentToken.getToken()
                                + " | CLASSIFICACAO: " + this.currentToken.getClassificacao());
    }

    public void execute(){

        if(this.tokens == null)
            System.exit(0);

        nextToken();
        if(currentToken.getToken().equals("program")){
            enterScope();
            nextToken();
            if(currentToken.getClassificacao().equals("Identificador")){
                declaration();
                nextToken();
                if(currentToken.getToken().equals(";"))
                    nextToken();
                else
                    System.out.println("Esperando ; ao fim do identificador de programa.");
                
            }else
                System.out.println("Esperando identificador de programa");
            
        }else{
            System.out.println("Esperando palavra reservada \" program \"");
        }
        
        if(declaracaoVariaveis()){
            if(declaracaoSubprogramas()){
                if(comandoComposto())
                    if(currentToken.getToken().equals(".")){
                        System.out.println("Deu tudo certo!!");
                    }else
                        System.out.println("Faltou o . no final do programa");
               else
                    System.out.println("Erro comandoComposto no escopo principal");
            }else
                System.out.println("Erro declaracaoSubprogramas no escopo principal");

        }else
            System.out.println("Erro declaracaoVariaveis no escopo principal");
        
    }

    public boolean declaracaoVariaveis(){

        if(this.currentToken.getToken().equals("var")){
            nextToken();
            return listaDeclaracaoVariaveis();
        }else{
            return true;
        }
    }
    
    public boolean listaDeclaracaoVariaveis_(){
        // TA F0D4
        if(listaIdentificadores()){
            if(currentToken.getToken().equals(":")){
                nextToken();
                if(currentToken.getToken().equals("integer") ||
                   currentToken.getToken().equals("real") ||
                   currentToken.getToken().equals("boolean")){
                    setSymbolType_();
                    variableDeclaration.clear();
                    nextToken();
                    if(currentToken.getToken().equals(";")){
                        nextToken();
                        if(listaDeclaracaoVariaveis_()){
                            return true;
                        }else{
                            System.out.println("Esperando listaDeclaracaoVariaveis_ em listaDeclaracaoVariaveis_");
                            return false;
                        }
                        
                    }else{
                        System.out.println("Erro, esperando listaDeclaracaoVariaveis_");
                        return false;
                    }
                }else{
                    System.out.println("Erro esperando tipo em listaDeclaracaoVariaveis_");
                    return false;
                }
            }else{
                System.out.println("Esperando : em listaDeclaracaoVariaveis_");
                return false;
            }
        }else{
            return true;
        }
    }
    
    public boolean listaDeclaracaoVariaveis(){
        // TA F0D4
        
        if(listaIdentificadores()){
            if(currentToken.getToken().equals(":")){
                nextToken();
                if(currentToken.getToken().equals("integer") ||
                   currentToken.getToken().equals("real") ||
                   currentToken.getToken().equals("boolean")){
                    setSymbolType_();
                    variableDeclaration.clear();
                    nextToken();
                    if(currentToken.getToken().equals(";")){
                        nextToken();
                        if(listaDeclaracaoVariaveis_()){
                            return true;
                        }else{
                            System.out.println("Esperando listaDeclaracaoVariaveis_ em listaDeclaracaoVariaveis");
                            return false;
                        }
                        
                    }else{
                        System.out.println("Erro, esperando listaDeclaracaoVariaveis_");
                        return false;
                    }
                }else{
                    System.out.println("Erro esperando tipo em listaDeclaracaoVariaveis");
                    return false;
                }
            }else{
                System.out.println("Esperando : em listaDeclaracaoVariaveis");
                return false;
            }
        }else{
            System.out.println("Esperando lista de identificadores em listaDeclaracaoVariaveis");
            return false;
        }
    }
    
    public boolean listaIdentificadores_(){

        if(currentToken.getToken().equals(",")){
            nextToken();
            if(currentToken.getClassificacao().equals("Identificador")){
                declaration();
                variableDeclaration.add(currentToken);
                nextToken();
                if(listaIdentificadores_()){
                    return true;
                }else{
                    System.out.println("Esperando listaIdentificadores_ em listaIdentificadores_");
                    return false;
                }
                    
            }else{
                System.out.println("Esperando identificador em listaIdentificadores_");
                return false;
            }
        }else{
            return true;
        }
    }

    public boolean listaIdentificadores(){
        if(currentToken.getClassificacao().equals("Identificador")){
            declaration();
            variableDeclaration.add(currentToken);
            nextToken();
            if(listaIdentificadores_()){
                return true;
            }else{
                System.out.println("Esperando listaIdentificadores_() em listaIdentificadores");
                return false;
            }
        }else{
            return false;
        }

    }

   public boolean tipo(){
        if(currentToken.getClassificacao().equals("Palavra reservada") &&
          (currentToken.getToken().equals("integer") || currentToken.getToken().equals("real")
        || currentToken.getToken().equals("boolean"))){
           
           nextToken();
           return true;
           
        }
            
        System.out.println("DEU ERRO NO TIPO");
        return false;

   }   
    
   public boolean declaracaoSubprogramas(){
       
       if(declaracaoSubprogramas_()){
           return true;
       }
       return true;
       
   }
   
   public boolean declaracaoSubprogramas_(){
       
       if(declaracaoSubprograma()){
           
           if(currentToken.getToken().equals(";")){
               
               nextToken();
               return declaracaoSubprogramas_();
               
           }else{
               
               return false;
               
           }
           
       }
       
       return true;
   }
   
   public boolean declaracaoSubprograma(){
       
       if(currentToken.getToken().equals("procedure")){
           
           //enterScope();
           
           nextToken();
           if(currentToken.getClassificacao().equals("Identificador")){
                declaration();
                enterScope();
                nextToken();
                if(argumentos()){
                    if(currentToken.getToken().equals(";")){
                        nextToken();
                        if(declaracaoVariaveis()){
                            if(declaracaoSubprogramas()){
                                if(comandoComposto()){
                                    exitScope();
                                    return true;
                                }else{
                                    System.out.println("Erro comandoComposto em declaracaoSubprograma");
                                    return false;
                                }    
                            }else{
                                System.out.println("Erro declaracaoSubprogramas em declaracaoVariaveis");
                                return false;
                            }
                        }
                    }else{
                        System.out.println("Erro ; em argumentos");
                        return false;
                    } 
               }else{
                   System.out.println("Tem alguma coisa errada nos argumentos!!");
                   return false;
               }
           }else{
               System.out.println("Faltou o identificador, moral!!");
               return false;
           }
       }
       return false;
       
   }

   public boolean argumentos(){

       if(currentToken.getToken().equals("(")){
           
           nextToken();

           if(listaParametros()){
               
               if(currentToken.getToken().equals(")")){
                   
                   nextToken();
                   return true;
                   
               }else{
                   
                   System.out.println("Faltou ) nos argumentos");
                   return false;
                   
               }
               
           }else{
               
               System.out.println("Erro na listaParametros nos argumentos");
               return false;
               
           }
           
       }
       
       return true;
       
   }

   public boolean listaParametros(){

       if(listaIdentificadores()){
           if(currentToken.getToken().equals(":")){
               nextToken();
               if(tipo()){
                   return listaParametros_();
               }else{
                   System.out.println("Erro tipo na listaParametros");
                   return false;
               }
           }else{
               System.out.println("Erro : na listaParametros");
               return false;
           }
       }
       
       System.out.println("Erro listaIdentificadoes em listaParametros");
       return false;
       
   }
   
   public boolean listaParametros_(){
       
       if(currentToken.getToken().equals(";")){
           nextToken();
            if(listaIdentificadores()){
                if(currentToken.getToken().equals(":")){
                    nextToken();
                    if(tipo()){
                        return listaParametros_();
                    }else{
                        System.out.println("Erro tipo na listaParametros_");
                        return false;
                    }
                }else{
                    System.out.println("Erro : na listaParametros_");
                    return false;
                }
            }else{
                System.out.println("Erro listaIdentificador em listaParametros_");
                return false;
            }
       }
       return true;
   }

   public boolean comandoComposto(){
        if (currentToken.getToken().equals("begin")){
           nextToken();
           comandosOpcionais();
           if(currentToken.getToken().equals("end")){
               nextToken();
               return true;
            }else{
                System.out.println("Deu pau! esperando end");
                return false;
            }
        }else{
            return false;
        }
        
   }

    public boolean comandosOpcionais(){
        
        //Caso não tenha nada
        if(currentToken.getToken().equals("end"))
            return true;
        
        if(listaComandos()){
            return true;
        }else{
            return false;
        }
    }
    
    public boolean listaComandos(){
        return comando() && listaComandos_();
    }
    
    public boolean listaComandos_(){
        
        if(currentToken.getToken().equals(";")){
            nextToken();
            if(comando())
                if(listaComandos_()){
                    return true;
                }else{
                    System.out.println("Erro listaComandos_ em listaComandos_");
                    return false;
                }
        }
        return true;
    }

    public boolean comando(){
       
        if(variavel()){
            if(currentToken.getClassificacao().equals("Atribuição")){
                nextToken();
                if(expressao()){
                    //checkOperation();
                    checkAttribution();
                    return true;
                }else{
                    System.out.println("Erro na expressão em comando");
                    return false;
                }
            }else if(ativacaoProcedimento()){
                //checkOperation();
                return true;
            }else{
                System.out.println("Erro na atribuição em comando");
                return false;
            }
        }else if(currentToken.getToken().equals("if")){
            stackAttribution.add(new IdentifierType("unnamed", "boolean"));
            nextToken();
            if(expressao()){
                //checkOperation();
                checkConditional();
                if(currentToken.getToken().equals("then")){
                    nextToken();
                    //acho que pode ficar num loop aqui
                    if(comando()){
                        if(parteElse()){
                            nextToken();
                            return true;
                        }else{
                            System.out.println("Erro no else em comando");
                            return false;
                        }
                    }else{
                        System.out.println("Erro comando em comando");
                        return false;
                    }
                }else{
                    System.out.println("Erro then em comando");
                    return false;
                }
            }else{
                System.out.println("Erro expressao if em comando");
                return false;
            }
        }else if(currentToken.getToken().equals("while")){
            stackAttribution.add(new IdentifierType("unnamed", "boolean"));
            nextToken();
            if(expressao()){
                //checkOperation();
                checkConditional();
                if(currentToken.getToken().equals("do")){
                    nextToken();
                    if(comando()){
                        return true;
                    }else{
                        System.out.println("Erro comando while em comando");
                        return false;
                    }
                }else{
                    System.out.println("Erro do em comando");
                    return false;
                }
            }else{
                System.out.println("Erro expressao while em comando");
                return false;
            }
        }else if(currentToken.getToken().equals("do")){
            nextToken();
            if(comando()){
                if(currentToken.getToken().equals("while")){
                    stackAttribution.add(new IdentifierType("unnamed", "boolean"));
                    nextToken();
                    if(expressao()){
                        //checkOperation();
                        checkConditional();
                        return true;
                    }else{
                        System.out.println("Esperando expressao");
                        return false;
                    }
                }else{
                    System.out.println("Esperando while");
                    return false;
                }
            }else{
                System.out.println("Esperando comando");
                return false;
            }
        }
        
        if(comandoComposto()){
            return true;
        }else{
            return false;
        }
    }

    public boolean parteElse(){
        
        if(currentToken.getToken().equals("else")){
            
            return comando();
            
        }
        
        return true;
    }

    public boolean variavel(){
        if(currentToken.getClassificacao().equals("Identificador")){
            checkDeclaration();
            stackAttribution.add(new IdentifierType(currentToken.getToken(), "unsigned"));
            nextToken();
            return true;
        }else{
            return false;
        }
    }

    public boolean ativacaoProcedimento(){
        
        if(currentToken.getToken().equals("(")){
            nextToken();
            if(listaExpressoes()){
                if(currentToken.getToken().equals(")")){
                    nextToken();
                    return true;
                }else{
                    System.out.println("Esperando ) em ativacao procedimento");
                    return false;
                }
            }else{
                System.out.println("Esperando listaExpressoes em ativacaoProcedimento");
                return false;
            }
        }else{
            return true;
        }
        
    }

    public boolean listaExpressoes(){
        //nextToken();
        if(expressao()){
            //nextToken();
        }else if(listaExpressoes()){
            nextToken();
        }else{
            System.out.println("Deu pau na lista de expressoes");
            return false;
        }
        return true;
    }

    public boolean expressao(){

        if(expressaoSimples()){
            if(opRelacional() && expressaoSimples()){
                //checkOperation();
                checkRelational();
                return true;
            }else{
                return true;
            }
        }else{
            return false;
        }
    }

    public boolean expressaoSimples_(){
        
        if(opAditivo()){
            if(termo()){
                checkArithmetic();
                if(expressaoSimples_()){
                    //nextToken();
                    return true;
                }else{
                    System.out.println("deu erro em expressaoSimples_() esperando expressaoSimples_()");
                    return false;
                }
            }else{
                System.out.println("Esperando termo em expressaoSimples_()");
                return false;
            }
        }else{
            return true;
        }
    }

    public boolean expressaoSimples(){
        if(termo()){
            if(expressaoSimples_()){
                return true;
            }else{
                System.out.println("Esperando expressaoSimples_() em expressaoSimples()");
                return false;
            }
        }else if(sinal()){
            if(termo()){
                if(expressaoSimples_()){
                    return true;
                }else{
                    System.out.println("Deu erro em expressao simples, esperando expressaoSimples_");
                    return false;
                }
            }else{
                System.out.println("Deu erro esperando termo em expressaoSimples()");
                return false;
            }
        }else{
            return false;
        }

    }

    public boolean termo_(){
       
        if(opMultiplicativo()){
            if(fator()){
                checkArithmetic();
                if(termo_()){
                    //nextToken();
                    return true;
                }else{
                    System.out.println("Deu erro, esperando termo_()");
                    return false;
                }
            }else{
                System.out.println("Deu erro, esperando fator em termo_()");
                return false;
            }
        }else{
            return true;
        }
   }
   
    public boolean termo(){
      if(fator() && termo_()){
          return true;
      }else{
          System.out.println("Deu erro no termo.");
          return false;
      }
    }
    
    public boolean fator(){

        if(currentToken.getClassificacao().equals("Número inteiro")){
            stackAttribution.add(new IdentifierType(currentToken.getToken(), "integer"));
            nextToken();
            return true;
        }else if(currentToken.getClassificacao().equals("Número real") ||
                 currentToken.getClassificacao().equals("Real 3D")){
            stackAttribution.add(new IdentifierType(currentToken.getToken(), "real"));
            nextToken();
            return true;
        }else if(currentToken.getClassificacao().equals("boolean")){
            stackAttribution.add(new IdentifierType(currentToken.getToken(), "boolean"));
            nextToken();
            return true;
        }else if(currentToken.getClassificacao().equals("Identificador")){
            checkDeclaration();
            stackAttribution.add(new IdentifierType(currentToken.getToken(), "unsigned"));
            nextToken();
            if(currentToken.getToken().equals("(")){
                if(listaExpressoes()){
                    nextToken();
                    if(currentToken.getToken().equals(")")){
                        nextToken();
                        return true;
                    }else{
                        System.out.println("Deu erro em fator! Esperando )");
                        return false;
                    }
                }else{
                    System.out.println("Deu pau! Esperando lista de expressoes em fator");
                    return false;
                }
            }
            return true;
        }else if(currentToken.getToken().equals("not")){
            nextToken();
            if(fator()){
                nextToken();
                return true;
            }else{
                System.out.println("Deu erro depois do not. Esperando fator");
                return false;
            }
        }else if(currentToken.getToken().equals("(")){
            nextToken();
            if(expressao()){
                if(currentToken.getToken().equals(")")){
                    nextToken();
                    return true;
                }else{
                    System.out.println("Esperando ) depois da expressao");
                    return false;
                }
            }else{
                System.out.println("Esperando expressao");
                return false;
            }

        }else{
            return false;
        }

    }

    public boolean sinal(){
         if(currentToken.getToken().equals("+") || currentToken.getToken().equals("-")){
             nextToken(); 
             return true;
         }
         else{
             System.out.println("Deu pau! esperando sinal + ou -");
             return false;
         }
    }

    public boolean opRelacional(){
         if(currentToken.getClassificacao().equals("Operador relacional")){
            nextToken();
            return true;
         }    

         return false;
    }

    public boolean opAditivo(){
         if(currentToken.getClassificacao().equals("Operador aditivo")){
            opArithmetic = currentToken.getToken();
            nextToken();
            return true;
         }    
        return false;
    }

    public boolean opMultiplicativo(){
        if(currentToken.getClassificacao().equals("Operador multiplicativo")){
            opArithmetic = currentToken.getToken();
            nextToken();
            return true;
        }    
       return false;
   }
}
