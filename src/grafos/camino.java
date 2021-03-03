/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grafos;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author alumnocbi
 */
public class camino {
    private int costo;
    public List<Integer> camino;
    
    public camino(){
        this.costo=Integer.MAX_VALUE;
        this.camino=new LinkedList();
    }
    public int getCosto(){
        return this.costo;
    }
    public void imprime(){
        Iterator i=this.camino.iterator();
        int cam;
        while(i.hasNext()){
            cam= (int) i.next();
            System.out.println(cam);
        }
    }
    public void clonar(List<Integer> l){
        Iterator i=l.iterator();
        int cam;
        while(i.hasNext()){
            cam= (int) i.next();
            this.camino.add(cam);
        }
    }
    
}
