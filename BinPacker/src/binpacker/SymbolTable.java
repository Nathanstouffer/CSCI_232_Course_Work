/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this itemlate file, choose Tools | Templates
 * and open the itemlate in the editor.
 */
package binpacker;

/**
 *
 * @author natha
 * @param <Key>
 */
public class SymbolTable <Key extends Comparable<Key>>{
    private Key[] bst;
    
    SymbolTable(){ bst = (Key[]) new Comparable[20]; }
    
    /**
     * Public method to insert values into BST
     * Duplicate keys are put to the left child of the root
     * @param item 
     */
    public void insert(Key item){
        int i = 1;
        boolean inserted = false;
        while (!inserted){
            if (i > bst.length){
                doubleTreeSize();
            }
            if (bst[i] == null){
                bst[i] = item;
                inserted = true;
            }
            else if (item.compareTo(bst[i]) <= 0){
                i = 2*i;
            }
            else{
                i = 2*i + 1;
            }
        }
    }
    
    public Key bestFit(int item_size){
        
    }
    
    public Key worstFit(int item_size){
        
    }
    
    /**
     * Private method to double size of the BST
     */
    private void doubleTreeSize(){
        Key[] item = (Key[]) new Comparable[2*bst.length];
        
        for (int i = 0; i < bst.length; i++){
            item[i] = bst[i];
        }
        
        bst = item;
    }
}
