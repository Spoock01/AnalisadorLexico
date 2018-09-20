package AnalisadorLexico;

import java.util.ArrayList;


/*

    UFPB - CONSTRUÇÃO DE COMPILADORES I

    Alunos:
    Edson Alves Pereira Filho - 11512960
    João Vinícius Gomes de Lima - 11511191

*/

public class Main {
    public static void main(String[] args) {
        
        final String pathFile = "src/Arquivo/Codigo.txt";
        final String pathReservedWords = "src/Arquivo/PalavrasReservadas.txt";
        final String pathArithmeticTable = "src/Arquivo/arithmeticTable.txt";
        ArrayList<Table> tokens;
        
        MyFileReader mfr = new MyFileReader(pathFile, pathReservedWords, pathArithmeticTable);
        mfr.openFile();
        AnalisadorLexico al = new AnalisadorLexico(mfr.getFile(), mfr.getReservedWords());
        tokens = al.generateFile();
        Syntax s = new Syntax(tokens);
        s.execute();
        
    }
}
