/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AnalisadorLexico;

/**
 *
 * @author joaov
 */
public class ArithIndex {
    private final String combination, result;

    public ArithIndex(String combination, String result) {
        this.combination = combination;
        this.result = result;
    }

    public String getCombination() {
        return combination;
    }

    public String getResult() {
        return result;
    }

    @Override
    public String toString() {
        return "ArithIndex{" + "combination=" + combination + ", result=" + result + '}';
    }
}
