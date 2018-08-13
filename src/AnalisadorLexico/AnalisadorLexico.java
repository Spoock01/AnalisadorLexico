package AnalisadorLexico;

import java.util.ArrayList;
import java.util.StringJoiner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        
        commentAnalyzer();      // Faz analise de comentarios e caracteres fora da linguagem
        codeAnalyzer();         // Faz analise de codigo e separa quase todo o codigo em token
        teste();
        identifierAnalyzer();   // Faz analise de identificadores para retirar identificadores começados por numeros
        analyzer();             // Faz analise de todos os tokens e os classifica
        
    }
    
    public String separando(String line, String regex, String regex2){
        
        int offset = 0;
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(line);
        Pattern pattern2 = Pattern.compile(regex2);
        Matcher matcher2 = pattern2.matcher(line);
        StringBuilder str = new StringBuilder(line);
        
        
        while(matcher.find()  && !matcher2.find()){
            
                str.insert(matcher.end()   + offset, ' ');
                str.insert(matcher.start() + offset, ' ');
                offset += 2;
        }
        return str.toString();
    }
    
    public String nonFloat(String line){
        
        StringBuilder str = new StringBuilder(line);
        int length = line.length();
        
        for(int i = 0; i < length; i++){
            
            if(line.charAt(i) == '.'){
                
                if(i == 0 || !Character.isDigit(line.charAt(i - 1))){
                    str.insert(i + 1, ' ');
                    str.insert(i, ' ');
                    length++;
                    i++;
                    line = str.toString();
                }
            }
        }
        
        
        return str.toString();
    }
    
    public void teste(){
        
        for(int i = 0; i < file.size(); i++){
            
            String line = file.get(i);
            String regex3d = "\\d+[.]\\d*x\\d+[.]\\d*y\\d+[.]\\d*z";
            String regexIdent = "[a-zA-Z]\\w*\\d*";
            String regexFloat = "\\d+[.]\\d*";
            
            line = separando(line, regex3d, "");
            line = separando(line, regexIdent, regex3d);
            line = separando(line, regexFloat,regex3d);
            line = nonFloat(line);
            
            file.set(i, line);
        }
    }
    
    public int isSymbol(char c, char d){
        
        if((c == ':' && d == '=') || (c  == '<' || d == '=') || 
           (c == '>' && d == '=') || (c == '<' && d == '>'))
            return 1;
        
        else if(c == ',' || c == ';' || c == ':' || c == '(' || c == ')' ||
                c == '/' || c == '*' || c == '+' || c == '-')
            return 0;
        
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
                switch (desloc) {
                    case 0:
                        stringBuilder = new StringBuilder(aux);
                        if(aux.charAt(j + 1) != ' '){
                            stringBuilder.insert(j + 1, ' ');
                            size++;
                        }   if(aux.charAt(j-1 > 0? j - 1: 0) != ' '){
                            stringBuilder.insert(j, ' ');
                            size++;
                        }   aux = stringBuilder.toString();
                        length = aux.length();
                        break;
                    case 1:
                        stringBuilder = new StringBuilder(aux);
                        if(aux.charAt(j - 1 > 0 ? j - 1 : 0) != ' '){
                            stringBuilder.insert(j , ' ');
                            size++;
                        }   if (aux.charAt(j+3) != ' '){
                            stringBuilder.insert(j+3 , ' ');
                            size++;
                        }   aux = stringBuilder.toString();
                        length = aux.length();
                        break;
                    default:
                        break;
                }
                j+= size;

            }
            
            /*
                Retornando string modificada para o arraylist
            */
            file.set(i, aux.trim());
     
        }
    }
  
    public Boolean isInvalid (char c){
        
        if((c >= 'A' && c <= 'Z') ||
          (c >= 'a' && c <= 'z') ||
          (c >= '0' && c <= '>') ||
          (c >= '(' && c <= '/') ||
          (c == '{' || c == '}' || c == ' ' || c == '_'))
                return false;
             
        
        return true;
    }
    
    public void identifierAnalyzer() {
        for (int line = 0; line < file.size(); line++){
            
            /*
                Verificando se um identificador começa com inteiros            
            */
            String[] Tokens;
            String regex = "\\d+\\w+";
            StringBuilder stringBuilder;
                
            Tokens = file.get(line).split(" ");
            for(int tok = 0; tok < Tokens.length; tok++){
                
                String str = Tokens[tok];
                stringBuilder = new StringBuilder(str);
                
                /*
                    Se começar com um inteiro, uma busca é feita pra saber
                    onde esse inteiro termina. Ao fim disso, um espaço é 
                    colocado nessa posição
                */
                if(str.matches(regex)){
                    for(int i = 0; i < Tokens[tok].length(); i++){
                        if(!(str.charAt(i) >= '0' && str.charAt(i) <= '9')){
                            stringBuilder.insert(i, ' ');
                            
                            /*
                                Devolvendo a string montada para o token
                                E juntando todos os tokens para uma posterior
                                analise de classificação dos tokens
                            */
                            Tokens[tok] = stringBuilder.toString();
                            StringJoiner sj = new StringJoiner(" ");
                            for(String s:Tokens) 
                                sj.add(s);
                            
                            file.set(line > 0 ? line : 0, sj.toString());
                            line = line > 0 ? line -1: line;
                            break;
                        }
                    }
                }
            }
        }
    }
    
    public void commentAnalyzer(){
        
        for(int i = 0; i < file.size(); i++){
            
            String line1 = file.get(i);
            /*
                Retirando do texto todos os '\t' e '\n'
            */
            line1 = line1.replace("\\t", "");
            line1 = line1.replace("\\n", "");
            
            int size = line1.length();
            char[] line = line1.toCharArray();
            int test = 0;
            
            /*
                Uma verificação é feita caso o caractere '{' aparece no codigo.
                
            */
            
            for(int j = 0; j < size; j++){
                
                if(line[j] == '{' && this.isComment == false){
                    this.isComment = true;
                }
                if (j + 1 < size){
                    if (line[j] == '/' && line[j+1] == '/' && this.isComment == false){
                         this.isComment = true;
                         test = 1;
                    }
                }
                if(isInvalid(line[j]) && !this.isComment){
                    System.out.println("Caractere: \"" + line[j] + "\" invalido na linha: " + (i + 1));
                    System.exit(0);
                }
                
                if(line[j] == '}' && this.isComment && test != 1){
                    this.isComment = false;
                    line[j] = Character.MIN_VALUE;
                }else if(line[j] == '}' && !this.isComment){
                    System.out.println("O código possui comentário inválido! Na linha: "+ (i + 1));
                    System.exit(0);
                }
                
                if(this.isComment == true){
                    line[j] = Character.MIN_VALUE;
                }             
            }
            if(test == 1 && this.isComment == true){
                this.isComment = false;
                test = 0;
            }
            
            String result = String.valueOf(line);
            result += " ";
            file.set(i, result);
 
        }
        if(this.isComment){
            System.out.println("Comentário aberto e não fechado!");
            System.exit(0);
        }
            
    }
    
    public Boolean isReservedWord(String token){
        return reservedWords.contains(token);
    }
    
    private void classifier (String token, int line){
        
        String regexInteger = "\\d+";
        String regexReal = "\\d+\\.\\d*";
        String identifier = "\\w+";
        String real3d = "\\d+[.]\\d*x\\d+[.]\\d*y\\d+[.]\\d*z";
        String result = "";
        
        if(token.isEmpty())
            return;
        
        if(token.matches(real3d))
            result = "Real 3D";
        else if(token.matches(regexInteger))
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
        }else
            return;
        
        table.add(new Table(token, result, line));

    }
    
    private void analyzer(){
    
        for (int line = 0; line < file.size(); line++){
            
            String[] Tokens;
                
            Tokens = file.get(line).split(" ");
            for(int tok = 0; tok < Tokens.length; tok++){
                //System.out.println(Tokens[tok]);
                classifier(Tokens[tok], line + 1);
            }
        }
        
        for(int i = 0; i < table.size(); i++)
            System.out.println(table.get(i).toString());
        
    }
}