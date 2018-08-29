//Testando 1...2...3
package AnalisadorLexico;


public class Main {
    public static void main(String[] args) {
        
        final String pathFile = "src/Arquivo/Codigo.txt";
        final String pathReservedWords = "src/Arquivo/PalavrasReservadas.txt";
        
        MyFileReader mfr = new MyFileReader(pathFile, pathReservedWords);
        mfr.openFile();
        AnalisadorLexico al = new AnalisadorLexico(mfr.getFile(), mfr.getReservedWords());
        al.generateFile();
        
    }
}
