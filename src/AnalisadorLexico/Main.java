package AnalisadorLexico;

import java.util.ArrayList;


public class Main {
    public static void main(String[] args) {
        
        final String pathFile = "src/Arquivo/Codigo.txt";
        final String pathReservedWords = "src/Arquivo/PalavrasReservadas.txt";
        ArrayList<Table> tokens;
        
        MyFileReader mfr = new MyFileReader(pathFile, pathReservedWords);
        mfr.openFile();
        AnalisadorLexico al = new AnalisadorLexico(mfr.getFile(), mfr.getReservedWords());
        tokens = al.generateFile();
        new Syntax(tokens).execute();
        
    }
}
