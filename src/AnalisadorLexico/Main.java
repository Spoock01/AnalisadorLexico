package AnalisadorLexico;

import java.util.ArrayList;


public class Main {
    public static void main(String[] args) {
        
        final String pathFile = "src/Arquivo/Codigo.txt";
        final String pathReservedWords = "src/Arquivo/PalavrasReservadas.txt";
        final String pathArithmeticTable = "src/Arquivo/arithmeticTable.txt";
        ArrayList<Table> tokens;
        ArithmeticTable table;
        
        MyFileReader mfr = new MyFileReader(pathFile, pathReservedWords, pathArithmeticTable);
        mfr.openFile();
        table = mfr.getArithmeticTable();
        AnalisadorLexico al = new AnalisadorLexico(mfr.getFile(), mfr.getReservedWords());
        tokens = al.generateFile();
        Syntax s = new Syntax(tokens, table);
        s.execute();
        
    }
}
