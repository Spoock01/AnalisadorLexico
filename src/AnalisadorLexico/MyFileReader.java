
package AnalisadorLexico;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class MyFileReader {
    private String path;
    private ArrayList<String> arrayLine;

    public MyFileReader(String path) {
        this.path = path;
        this.arrayLine = new ArrayList<>();
    }
    
    public Boolean openFile(){
        
        String str;
        File file = new File(this.path.toString()); 
        BufferedReader br = null;
        
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
        
        return true;
    }
    
    public ArrayList<String> getFile (){
        return this.arrayLine;
    }
}
