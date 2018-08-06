package AnalisadorLexico;

import java.util.ArrayList;
import java.util.StringJoiner;

public class AnalisadorLexico {
    
    private ArrayList<String> file;
    private ArrayList<String> reservedWords;
    private ArrayList<Table> table;
    private Boolean isComment;
    
    public AnalisadorLexico(ArrayList<String> file, ArrayList<String> reservedWords) {
        this.file = file;
        this.reservedWords = reservedWords;
        table = new ArrayList<>();
        this.isComment = false;
    }
    
    private Boolean checkFile() {
        
        /* 
            Retorna TRUE caso o arquivo tenha algum texto.
        */
        
        return this.file.size() > 0;
                    
    }
    
    public void generateFile (){
        
        if (!checkFile()){
            System.out.println("O arquivo não tem texto suficiente!\n");
            return;
        }
        
        commentAnalyzer();
        codeAnalyzer();
        identifierAnalyzer();
        analyzer();
        
        /* TO DO LIST
        
            Usar o arraylist para gerar o arquivo de saída do analisador**
            
            +-
            
        */
        
        
    }
    
    public int isSymbol(char c, char d){
        
        if((c == ':' && d == '=') || (c  == '<' || d == '=') || 
           (c == '>' && d == '=') || (c == '<' && d == '>'))
            return 1;
        
        else if(c == ',' || c == ';' || c == ':' || c == '(' || c == ')' ||
                c == '/' || c == '*')
            return 0;
        else if (c == '.')
            return 2;
        else if(c == '+' || c == '-') // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
            return 3;
        
            
        
        
        return -1;
    }
    
    public void codeAnalyzer(){

        StringBuilder stringBuilder;
        
        for(int i = 0; i < file.size(); i++){
            
            String line = file.get(i);
            int length = line.length();
            String aux = line;
            
            for(int j = 0; j < (length - 1); j++){
                
                int desloc = isSymbol(aux.charAt(j), aux.charAt(j + 1));
                int size = 0;
                
                /*
                    Se o 'desloc' for 0:
                    insere um espaço antes do simbolo e verifica se ja existe um
                    espaço depois. Caso nao exista, insere um espaço depois do
                    simbolo tambem.                
                */
                if(desloc == 0){
                    stringBuilder = new StringBuilder(aux);
                    
                    if(aux.charAt(j + 1) != ' '){
                        stringBuilder.insert(j + 1, ' ');
                        size++;
                    }
                    if(aux.charAt(j-1 > 0? j - 1: 0) != ' '){
                        stringBuilder.insert(j, ' ');
                        size++;
                    }                       
                    aux = stringBuilder.toString();
                    length = aux.length();
                } 
                
                /*
                    Se o 'desloc' for 1:
                    Insere um espaço antes do simbolo composto e verifica se ja
                    existe um espaço depois do simbolo composto. Caso nao exista,
                    insere um espaço tambem depois do simbolo composto;
                
                */
                else if(desloc == 1){
                    
                    stringBuilder = new StringBuilder(aux);
                   
                    if(aux.charAt(j - 1 > 0 ? j - 1 : 0) != ' '){
                        stringBuilder.insert(j , ' ');
                        size++;
                    }
                    if (aux.charAt(j+3) != ' '){
                        stringBuilder.insert(j+3 , ' ');
                        size++;
                    }
                    
                    aux = stringBuilder.toString();
                    length = aux.length();

                }
                else if (desloc == 2){
                    stringBuilder = new StringBuilder(aux);
                    int jMenos1 = j - 1;
                    
                    /*
                        Caso jMenos1 seja maior que zero, significa que o
                        ponto não se encontra no inicio da linha
                    */
                    
                    if(jMenos1 > 0){
                        
                        
                        if(aux.charAt(jMenos1) >= '0' && aux.charAt(jMenos1) <= '9'){
                            int help = jMenos1;

                            /*
                                Se for um digito, o laço irá voltar até o primeiro
                                número e então colocará um espaço antes desse número
                             */
                            while(help >= 1 && aux.charAt(help) >= '0' && aux.charAt(help) <= '9'){
                                help--;

                            }
   
                            stringBuilder.insert(help == 0 ? 0 : help + 1 , ' ');
                            size++;
                            
                            /*
                                Seguindo a mesma ideia, procurará o ultimo numero
                                (do numero real) e colocará um espaço no final
                            */
                            help = j + 1;
                            while(aux.charAt(help) >= '0' && aux.charAt(help) <= '9')
                                help++;
                            
                            stringBuilder.insert(help + 1 , ' ');
                            size++;
                            
                        }
                        /*
                            Caso não seja um numero, apenas coloca um espaço antes
                        */
                        else if (aux.charAt(j - 1) <= '0' || aux.charAt(j - 1) >= '9'){
                            stringBuilder.insert(j , ' ');
                            stringBuilder.insert(j+2 , ' ');
                            size+=2;
                        }
                        /*
                        
                        */
                        else{
                            stringBuilder.insert(j + 1 , ' ');
                            size++;
                        }
                            
                        aux = stringBuilder.toString();
                        length = aux.length();
                        
                        
                    }else{
                        stringBuilder.insert(j + 1 , ' ');
                        size++;
                        aux = stringBuilder.toString();
                        length = aux.length();
                    }
                }
                else if( desloc == 3){
                    stringBuilder = new StringBuilder(aux);
                    
                    int nextIndex  = j;
                    
                    while(true){
                        if(aux.charAt(nextIndex) != ' ' || aux.charAt(nextIndex) != '\n')
                            nextIndex++;
                        if(nextIndex >= aux.length())
                            break;
                    }
                    
                    String substr = aux.substring(j,nextIndex);
                    
                    int nextIndex1 = isRealNumber(substr);
                    
                    if(nextIndex1 == -1){
                        stringBuilder.insert(j + 1, ' ');
                        stringBuilder.insert(j, ' ');
                        size += 2;
                        aux = stringBuilder.toString();
                    }else{
                        stringBuilder.insert(nextIndex1 + j, ' ');
                        stringBuilder.insert(j > 0 ? j : 0, ' ');
                        aux = stringBuilder.toString();
                        size = size + nextIndex1;
                        length = aux.length();
                    }
                    
                    
                }
                j+= size;

            }
            
            /*
                Caso o caractere final seja um ; (ponto e virgula)
                então é inserido um espaço antes do ; para separar
                do identificador.
            */
            
            if(aux.charAt(aux.length() - 1) == ';'){
                stringBuilder = new StringBuilder(aux);
                stringBuilder.insert(aux.length() - 1, ' ');
                aux = stringBuilder.toString();
            }    
            
            /*
                Retornando string modificada para o arraylist
            */
            //System.out.println(aux);
            file.set(i, aux.trim());
     
        }
    }
    
    public int isRealNumber (String realNumber){
        int index = 0;
        try{
            if (!(realNumber.charAt(index) == '+' || realNumber.charAt(index) == '-'))
                return -1;
            index++;
            
            while(true){
                if (realNumber.charAt(index) >= '0' && realNumber.charAt(index) <= '9')
                    index++;
                else{
                    if(realNumber.charAt(index++) == 'e')
                        break;
                    else
                        return -1;
                }    
            }
            if(realNumber.charAt(index) == '+' || realNumber.charAt(index) == '-')
                index++;
            else
                return -1;
            
            while(true){
                if (realNumber.charAt(index) >= '0' && realNumber.charAt(index) <= '9')
                    index++;
                else if(realNumber.charAt(index) == ' ' || realNumber.charAt(index) == '\n'){
                    break;
                }else
                    break;
                
                if(index == realNumber.length())
                    break;
            }
            
        }catch(Exception e){
            return -1;
        }

        
        return index;
    }
    
    public Boolean isInvalid (char c){
        
        if((c >= 'A' && c <= 'Z') ||
          (c >= 'a' && c <= 'z') ||
          (c >= '0' && c <= '>') ||
          (c >= '(' && c <= '/') ||
          (c == '{' || c == '}' || c == ' '))
                return false;
             
        
        return true;
    }
    
    public void identifierAnalyzer() {
        for (int line = 0; line < file.size(); line++){
            
            /*StringBuilder stringBuilder = new StringBuilder(string);
            stringBuilder.insert(string.length() - 2, ',');
            System.out.println(stringBuilder.toString());
            */
            
            String[] Tokens;
            String regex = "\\d+\\w+";
            StringBuilder stringBuilder;
                
            Tokens = file.get(line).split(" ");
            for(int tok = 0; tok < Tokens.length; tok++){
                
                String str = Tokens[tok];
                stringBuilder = new StringBuilder(str);
                
                if(str.matches(regex)){
                   // System.out.println("DEu match: " + str);
                    for(int i = 0; i < Tokens[tok].length(); i++){
                       // System.out.println("Testando o char: " + str.charAt(i));
                        if(!(str.charAt(i) >= '0' && str.charAt(i) <= '9')){
                            stringBuilder.insert(i, ' ');
                            Tokens[tok] = stringBuilder.toString();
                           // System.out.println("Tudo ocorreu aqui: " + stringBuilder.toString());
                            
                            StringJoiner sj = new StringJoiner(" ");
                            for(String s:Tokens) sj.add(s);
                            file.set(line, sj.toString());
                            line--;
                            break;
                        }
                            
                    }
                    
                    
                }
                
                
            }
     
        }
    }
    
    public void commentAnalyzer(){
        
        int open = 0;
        
        for(int i = 0; i < file.size(); i++){
            
            String line1 = file.get(i);
            
            line1 = line1.replace("\\t", "");
            line1 = line1.replace("\\n", "");
            
            int size = line1.length();
            char[] line = line1.toCharArray();
            
            for(int j = 0; j < size; j++){
                
                if(line[j] == '{'){
                    open++;
                    this.isComment = true;
                }
                
                if(isInvalid(line[j]) && !this.isComment){
                    System.out.println("Caractere: \"" + line[j] + "\" invalido na linha: " + (i + 1));
                    System.exit(0);
                }
                
                if(line[j] == '}'){
                    open--;
                    this.isComment = false;
                    line[j] = '¨';
                }
                
                if(open < 0 || open > 1){
                    System.out.println("O código possui comentário inválido! Na linha: "+ (i + 1));
                    System.exit(0);
                }
                
                
                if(this.isComment == true){
                    line[j] = '¨';
                }             
            }
            String result = String.valueOf(line);
            result = result.replace('¨', Character.MIN_VALUE);
            result += " ";
            file.set(i, result);
 
        }
   
    
            
    }
    
    public Boolean isReservedWord(String token){
        
        return reservedWords.contains(token);
 
    }
    
    private void classifier (String token, int line){
        
        String regexInteger = "\\d+";
        String regexReal = "\\d+\\.\\d*";
        String identifier = "\\w+";
        String result = "";
        
        if(token.isEmpty())
            return;
        
        if(token.matches(regexInteger))
            result = "Número inteiro	";
        
        else if(token.matches(regexReal))
            result = "Número real	";
        
        else if(token.equals("*") || token.equals("/") || token.equals("and"))
            result = "Operador multiplicativo";
        
        else if (token.equals(":="))
            result = "Atribuição	";
        
        else if (token.equals("+") || token.equals("-") || token.equals("or"))
            result = "Operador aditivo";
        else if(token.equals("<") || token.equals(">") || token.equals("=") ||
                token.equals("<=") ||token.equals(">=") ||token.equals("<>"))
            result = "Operador relacional";
        else if(token.equals(":") ||token.equals(";") ||token.equals(".") ||
                token.equals(",") ||token.equals("(") || token.equals(")"))
            result = "Delimitador	";
        else if(token.matches(identifier)){
            if(isReservedWord(token))
                result = "Palavra reservada";
            else
                result = "Identificador     ";
            
        }else if(isRealNumber(token) != -1)
                result = "Novo Real   ";
        else
            return;
        
        table.add(new Table(token, result, line));
        

    }
    
    private void analyzer(){
    
        for (int line = 0; line < file.size(); line++){
            
            String[] Tokens;
                
            Tokens = file.get(line).split(" ");
            for(int tok = 0; tok < Tokens.length; tok++){
                //System.out.println("Token: " + Tokens[tok]);
                classifier(Tokens[tok], line + 1);
            }
     
        }
        
        for(int i = 0; i < table.size(); i++)
            System.out.println(table.get(i).toString());
        
    }
    
}