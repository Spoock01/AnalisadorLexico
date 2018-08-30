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
        return this.Token + "\t\t" + this.Classificacao + "\t" + this.Line + "\t\t";
    }

    public String getToken() {
        return Token;
    }

    public String getClassificacao() {
        return Classificacao;
    }

    public int getLine() {
        return Line;
    }
    
    
    
}
