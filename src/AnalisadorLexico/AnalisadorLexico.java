package AnalisadorLexico;

import java.util.ArrayList;
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
        
        commentAnalyzer();
        codeAnalyzer();
        analyzer();
        
        /* TO DO LIST
        
            Usar o arraylist para gerar o arquivo de saída do analisador
        
        */
        
        
    }
    
    public int isSimbol(char c, char d){

        if((c == ':' && d == '=') || (c  == '<' || d == '=') || 
           (c == '>' && d == '=') || (c == '<' && d == '>'))
            return 1;
        
        else if(c == ',' || c == ';' || c == '.' || c == ':' || c == '(' || c == ')')
            return 0;
        
        
        return -1;
    }
    
    public void codeAnalyzer(){
        
        /*String string = "abcdef";
        StringBuilder stringBuilder = new StringBuilder(string);
        stringBuilder.insert(string.length() - 2, ',');
        System.out.println(stringBuilder.toString());
        */
        StringBuilder stringBuilder;
        
        for(int i = 0; i < file.size(); i++){
            
            String line = file.get(i);
            int length = line.length();
            String aux = line;
           
            for(int j = 0; j < (length - 1); j++){
                
                int desloc = isSimbol(aux.charAt(j), aux.charAt(j + 1));
                int size = 0;
                
                //System.out.println("Testando char '"+ aux.charAt(j) +"' e '" + aux.charAt(j + 1) + "'");
                //System.out.println("Resultado do isSimbol: " + desloc);
                
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
                    if(aux.charAt(j-1) != ' '){
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
                   
                    if(aux.charAt(j - 1) != ' '){
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
                j+= size;

            }
            if(aux.charAt(aux.length() - 1) == ';'){
                stringBuilder = new StringBuilder(aux);
                stringBuilder.insert(aux.length() - 1, ' ');
                aux = stringBuilder.toString();
            }    
            //System.out.println("Line: " + aux);
            file.set(i, aux);
     
        }

    }
    
    public void commentAnalyzer(){
        
        int open = 0;
        int close = 0;
        
        for(int i = 0; i < file.size(); i++){
            
            String line1 = file.get(i);
            int size = line1.length();
            char[] line = line1.toCharArray();
            
            for(int j = 0; j < size; j++){
                
                if(line[j] == '{'){
                    open++;
                    this.isComment = true;
                }
         
                if(line[j] == '}'){
                    close++;
                    this.isComment = false;
                    line[j] = '¨';
                }
                
                if(this.isComment == true){
                    line[j] = '¨';
                }             
            }
            String result = String.valueOf(line);
            result = result.replace('¨', Character.MIN_VALUE);
            file.set(i, result);
 
        }
        if(open != close){
            System.out.println("O código possui comentário inválido");
            System.exit(0);
        }
        
    
            
    }
    
    private void classifier (String token, int line){
      
    }
    
    private void analyzer(){
    
        for (int line = 0; line < file.size(); line++){
            
            String[] Tokens;
                
            Tokens = file.get(line).split(" ");
            for(int tok = 0; tok < Tokens.length; tok++){
                System.out.println(Tokens[tok]);
                classifier(Tokens[tok], tok);
            }
     
        }
        
        
        
    }
    
}
