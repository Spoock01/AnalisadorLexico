
package AnalisadorLexico;


public class Main {
    public static void main(String[] args) {
        
        final String path = "src/Arquivo/Codigo.txt";
        
        MyFileReader mfr = new MyFileReader(path);
        mfr.openFile();
        
    }
}
