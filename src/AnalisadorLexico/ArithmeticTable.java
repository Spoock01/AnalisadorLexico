/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AnalisadorLexico;

import java.util.ArrayList;

/**
 *
 * @author joaov
 */
public class ArithmeticTable {
    
    private ArrayList<ArithIndex> list;
    
    public ArithmeticTable() {
        list = new ArrayList<>();
    }
    
    public void toStrings(){
        for(ArithIndex ai: list)
            System.out.println(ai.toString());
    }
    
    public void add(ArithIndex newIndex){
        list.add(newIndex);
    }
    
    public String getResult(String combination){
        
        for(ArithIndex c:list){
            if(c.getCombination().equals(combination)){
                return c.getResult();
            }
        }
        return "notFound";
    }

    
}
