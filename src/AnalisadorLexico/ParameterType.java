
package AnalisadorLexico;

import java.util.ArrayList;

public class ParameterType {
    
    private String name;
    private ArrayList<IdentifierType> list;

    public ParameterType(String name) {
        this.name = name;
        this.list = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setList(ArrayList<IdentifierType> list1) {
        
        for(IdentifierType it : list1){
            IdentifierType iz = new IdentifierType(it.getIdentifier(),it.getType());
            this.list.add(iz);
        }
        
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public void add(IdentifierType it){
        this.list.add(it);
    }
    
    public int getSizeList(){
        return this.list.size();
    }
    
   
    public boolean search (ArrayList<IdentifierType> list2){
        
        if(this.list.size() != list2.size()){
            System.out.println("Quantidade de argumentos incorreta.");
            return false;
        }
        int i = 0;
        
        for(i = 0; i < this.list.size(); i++){
            if(!list.get(i).getType().equals(list2.get(i).getType())){
                System.out.println("Esperando: {" + list.get(i).getType()
                + "} e recebendo: {" + list2.get(i).getType() + "} na função {"
                + this.name + "}");
                return false;
            }
        }

        return true;        
    }
}
