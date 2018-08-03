package AnalisadorLexico;

/**
 *
 * @author joaov
 */
public class Table {
    
    private String Token;
    private String Classificacao;
    private int Line;

    public Table(String Token, String Classificacao, int Line) {
        this.Token = Token;
        this.Classificacao = Classificacao;
        this.Line = Line;
    }
    
    @Override
    public String toString(){        
        return this.Token + "\t" + this.Classificacao + "\t" + this.Line + "\t";
    }
    
    
    
}
