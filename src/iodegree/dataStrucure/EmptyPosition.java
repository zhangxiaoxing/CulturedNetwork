/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package iodegree.dataStrucure;

/**
 *
 * @author Libra
 */
public class EmptyPosition {
    int id;
    int dist;
    
    public EmptyPosition(int id, int dist){
        this.id=id;
        this.dist=dist;
        
    }
    
    public int getId(){
        return id;
    }
    
    public int getDist(){
        return dist;
    }
}
