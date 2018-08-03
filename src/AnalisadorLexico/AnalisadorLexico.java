package AnalisadorLexico;

import java.util.ArrayList;

public class AnalisadorLexico {
    
    private ArrayList<String> file;
    
    public AnalisadorLexico(ArrayList<String> file) {
        this.file = file;

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
        System.out.println("");
        
        /* TO DO LIST
        
            Usar o arraylist para gerar o arquivo de saída do analisador
        
        */
        
        
    }
    
    
}
