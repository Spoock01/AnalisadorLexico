
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

    public void setList(ArrayList<IdentifierType> list) {
        this.list = list;
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
    
    public void toString1(){
        for(IdentifierType it : list ){
            System.out.println(this.name + " " + it.getIdentifier() + " " + it.getType());
        }
    }
    
    public boolean search (ArrayList<IdentifierType> list2){
        
        if(this.list.size() != list2.size()){
            System.out.println("Tamanho diferente: " + list.size() + " " + list2.size());
            return false;
        }
        int i = 0;
        
        for(IdentifierType it : list)
            if(!it.getType().equals(list2.get(i).getType())){
             
                System.out.println("Testando: " + it.getType() + " " + list2.get(i).getType());
                i++;
                return false;
            }
        return true;        
    }
}
