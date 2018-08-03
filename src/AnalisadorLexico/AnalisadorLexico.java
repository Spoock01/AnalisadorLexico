package AnalisadorLexico;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class AnalisadorLexico {
    
    private ArrayList<String> file;
    private ArrayList<String> reservedWords;
    private ArrayList<Table> table;
    
    public AnalisadorLexico(ArrayList<String> file, ArrayList<String> reservedWords) {
        this.file = file;
        this.reservedWords = reservedWords;
        table = new ArrayList<>();
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
        
        analyzer();
        
        /* TO DO LIST
        
            Usar o arraylist para gerar o arquivo de saída do analisador
        
        */
        
        
    }
    
    private void classifier (String token, int line){
        String regexInteger = "[0-9]+";
        String regexReal = "[0-9]+.[0-9]*";
        String regexIdentifier = "";
    }
    
    private void analyzer(){
    
        for (int line = 0; line < file.size(); line++){
            
            String[] Tokens = file.get(line).split(" ");
            
            for(int tok = 0; tok < Tokens.length; tok++){
                //System.out.println(Tokens[tok]);
            }
                

            
            
        }
        
        
        
    }
    
}
