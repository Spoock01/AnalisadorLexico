
package AnalisadorLexico;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class MyFileReader {
    private final String pathFile;
    private final String pathReservedWords;
    private final String pathArithmeticTable;
    private final ArrayList<String> arrayLine;
    private final ArrayList<String> reservedWords;

    public MyFileReader(String path, String pathReservedWords, String pathArithmeticTable) {
        this.pathFile = path;
        this.pathReservedWords = pathReservedWords;
        this.pathArithmeticTable = pathArithmeticTable;
        this.arrayLine = new ArrayList<>();
        this.reservedWords = new ArrayList<>();
    }
    
    /*
        Abre arquivo e adiciona cada linha do arquivo em uma posição do
        arraylist.
        
        Retorna FALSE caso ocorra algum erro em relação ao arquivo.
     */
    
    public Boolean openFile(){
        
        String str;
        File file = new File(this.pathFile); 
        BufferedReader br = null;
        
        // Lendo arquivo do código em pascal
        
        try {
            br = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException ex) {
            System.out.println("Arquivo não encontrado!\n");
            return false;
        }        
        
        try {
            while ((str = br.readLine()) != null)
                arrayLine.add(str);
        } catch (IOException ex) {
            System.out.println("Erro durante a leitura do arquivo!\n");
            return false;
        } 
        
        // Lendo arquivo de palavras reservadas
        
        file = new File(this.pathReservedWords.toString()); 
        
        try {
            br = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException ex) {
            System.out.println("Arquivo de palavras reservadas não encontrado!\n");
            return false;
        }        
        
        try {
            while ((str = br.readLine()) != null)
                reservedWords.add(str);
        } catch (IOException ex) {
            System.out.println("Erro durante a leitura do arquivo de palavras reservadas!\n");
            return false;
        }
        
        file = new File(this.pathArithmeticTable); 
        
        try {
            br = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException ex) {
            System.out.println("Arquivo de combinações aritméticas não encontrado!\n");
            return false;
        }             
        
        return true;
    }
    
    
    public ArrayList<String> getFile (){
        return this.arrayLine;
    }
    
    public ArrayList<String> getReservedWords(){
        return this.reservedWords;
    }
}
