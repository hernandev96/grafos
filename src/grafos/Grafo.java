/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grafos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;

/**
 *
 * @author pc
 */
public class Grafo {
    //matriz de adyacencia del grafo
    int [][] ady;
    //matriz de cierre transitivo del grafo
    int [][] cierre;
    //matriz de caminos(algoritmo de warshall
    int[][] warshall;
    //matriz de pesos(algoritmo floyd)
    int[][] floyd;
    
    
    public Grafo(){
        
    }
     public void imprimeMatriz(int[][] m){
        int i;
        int j;
        for(i=0;i<m.length;i++){
            for(j=0;j<m.length;j++){
                System.out.print(m[i][j]+"\t");
            }
            System.out.println();
        }
    }
    public void llenarMatriz(String nombre) throws FileNotFoundException, IOException, Exception{
        File f=new File(nombre);
        FileReader fr=new FileReader(f);
        BufferedReader br=new BufferedReader(fr);
        int a;
        int b;
        String linea;
        String tipo=br.readLine();
        String dim=br.readLine();
        int dimension=Integer.parseInt(dim);
        this.ady=new int[dimension][dimension];
        while((linea=br.readLine())!=null){
            char[] c=linea.toCharArray();
            if("D".equals(tipo)){
                a=Integer.parseInt(c[0]+"");
                b=Integer.parseInt(c[1]+"");
                if((a>=0 && b>=0)&&(a<=dimension-1 && b<=dimension-1)){
                    this.ady[a][b]=Integer.parseInt(c[2]+"");
                }
            }
            else{
                a=Integer.parseInt(c[0]+"");
                b=Integer.parseInt(c[1]+"");
                if((a>=0 && b>=0)&&(a<=dimension-1 && b<=dimension-1)){
                    this.ady[a][b]=Integer.parseInt(c[2]+"");
                    this.ady[b][a]=Integer.parseInt(c[2]+"");
                }
            }
        }
    }
    public void recorridoAnchura(int i){
        int[] marcados=new int[ady.length];
        Queue<Integer> cola=new LinkedList();
        int w;
        for(int j=0;j<ady.length;j++){
            marcados[j]=0;
        }
        marcados[i]=1;
        cola.add(i);
        while(!cola.isEmpty()){
            w=cola.poll();
            System.out.println(w);
            for(int q=0;q<ady.length;q++){
                if(ady[w][q]!=0 && marcados[q]!=1){
                    cola.add(q);
                    marcados[q]=1;
                }
            }
        }
    }
    public void recorridoProfundidad(int i){
        int[] marcados=new int[ady.length];
        Stack<Integer> pila=new Stack();
        int w;
        for(int j=0;j<ady.length;j++){
            marcados[j]=0;
        }
        marcados[i]=1;
        pila.push(i);
        while(!pila.isEmpty()){
            w=pila.pop();
            System.out.println(w);
            for(int q=0;q<ady.length;q++){
                if(ady[w][q]!=0 && marcados[q]!=1){
                    pila.push(q);
                    marcados[q]=1;
                }
            }
            
        }
    }
    public Set<Integer> recorridoAnchuraSet(int i){
        int[] marcados=new int[ady.length];
        Set<Integer> recorrido=new TreeSet();
        Queue<Integer> cola=new LinkedList();
        int w;
        for(int j=0;j<ady.length;j++){
            marcados[j]=0;
        }
        marcados[i]=1;
        cola.add(i);
        while(!cola.isEmpty()){
            w=cola.poll();
            recorrido.add(w);
            for(int q=0;q<ady.length;q++){
                if(ady[w][q]!=0 && marcados[q]!=1){
                    cola.add(q);
                    marcados[q]=1;
                }
            }
        }
        return recorrido;
    }
    public Set<Integer> recorridoProfundidadSet(int i){
        int[] marcados=new int[ady.length];
        Set<Integer> recorrido=new TreeSet();
        Stack<Integer> pila=new Stack();
        int w;
        for(int j=0;j<ady.length;j++){
            marcados[j]=0;
        }
        marcados[i]=1;
        pila.push(i);
        while(!pila.isEmpty()){
            w=pila.pop();
            recorrido.add(w);
            for(int q=0;q<ady.length;q++){
                if(ady[w][q]!=0 && marcados[q]!=1){
                    pila.push(q);
                    marcados[q]=1;
                }
            }
            
        }
        return recorrido;
    }
    public Grafo genInv(){
        Grafo aux=new Grafo();
        aux.ady=new int[this.ady.length][this.ady.length];
        for(int i=0;i<this.ady.length;i++){
            for(int j=0;j<this.ady.length;j++){
                aux.ady[j][i]=this.ady[i][j];
            }
        }
        return aux;
    }
    public List<Set<Integer>> componentesConexas(){
        int[] marcados=new int[ady.length];
        List<Set<Integer>> comp=new LinkedList();
        Set<Integer> componente=new TreeSet();
        for(int j=0;j<ady.length;j++){
            marcados[j]=0;
        }
        for(int i=0;i<ady.length;i++){
           if(!componente.contains(i)){
            componente=recorridoProfundidadSet(i);
            componente.stream().forEach((k) ->{
                marcados[k]=1;
            });
           }
           comp.add(componente);
        }
        return comp;
    }
    public List<Set<Integer>> componentesFConexas(){
        int[] marcados=new int[ady.length];
        List<Set<Integer>> components=new LinkedList();
        Set<Integer> descendientes=new TreeSet();
        Set<Integer> ascendientes=new TreeSet();
        Set<Integer> componentes=new TreeSet();
        Grafo inversa=genInv();
        for(int j=0;j<ady.length;j++){
            marcados[j]=0;
        }
        for(int i=0;i<ady.length;i++){
            if(!componentes.contains(i)){
                componentes=new TreeSet();
                descendientes=recorridoAnchuraSet(i);
                ascendientes=inversa.recorridoAnchuraSet(i);
                for(int a:ascendientes){
                    if(descendientes.contains(a)){
                    marcados[a]=1;
                    componentes.add(a);
                    }
                }
                components.add(componentes);
            }
        }
        return components;
    }
    public camino caminoCorto(int i,int f){
        System.out.println("entre " + i +" y "+f);
        camino actual=new camino();
        List<Integer> cam=new LinkedList();
        cam.add(i);
        caminoCortoR(i,f,0,cam,actual);
        return actual;
    }
    private void caminoCortoR(int inicio,int fin,int costo,List<Integer> l,camino actual){
        int i;
        for(i=0;i<ady.length;i++){
            if(l.contains(i) || ady[inicio][i]==0){
                continue;
            }
            l.add(i);
            costo +=ady[inicio][i];
            if(actual.getCosto()<costo){
                costo -=ady[inicio][i];
                continue;
            }
            if(i==fin){
                actual.clonar(l);
                break;
            }
            else{
                caminoCortoR(i,fin,costo,l,actual);
            }
            costo -=ady[inicio][i];
            l.remove(l.indexOf(i));
        }
    }
    public void matrizCierreT(){
        this.cierre=creaMCierreT();
    }
    private int[][] sumaMatrices(int[][] m,int[][] z){
        int[][] suma=new int[this.ady.length][this.ady.length];
        int i;
        int j;
        for(i=0;i<this.ady.length;i++){
            for(j=0;j<this.ady.length;j++){
                suma[i][j]=m[i][j] + z[i][j];
            }
        }
        return suma;
    }
    private int[][] multiplicaMatrices(int [][] m,int[][] aux){
        int[][] mult=new int[this.ady.length][this.ady.length];
        int i;
        int j;
        int k;
        for(i=0;i<this.ady.length;i++){
            for(j=0;j<this.ady.length;j++){
                for(k=0;k<this.ady.length;k++){
                    mult[i][j] +=m[i][k]*aux[k][j];
                }
            }
        }
        return mult;
    }
    private int[][] creaMCierreT(){
        int i;
        int j;
        int[][] m=new int[this.ady.length][this.ady.length];
        int[][] aux=new int[this.ady.length][this.ady.length];
        for(i=0;i<this.ady.length;i++){
            for(j=0;j<this.ady.length;j++){
                m[i][j]=this.ady[i][j];
            }
        }
        for(i=0;i<ady.length;i++){
            aux=multiplicaMatrices(m,aux);
            m=sumaMatrices(m,aux);
        }
        return m;
    }
    public void warshall(){
        this.warshall=new int[this.ady.length][this.ady.length];
        for(int i=0;i<this.ady.length;i++){
            for(int j=0;j<this.ady.length;j++){
                for(int k=0;k<this.ady.length;k++){
                    this.warshall[i][j]=(this.ady[i][j]==1 || (this.ady[i][k]==1 && this.ady[k][j]==1))?1:0;
                }
            }
        }
    }
    //metodo para encontrar el camino mas corto
    public void dijkstra(int j){
        int [][] D= new int[this.ady.length][2];
        boolean[] F= new boolean[this.ady.length];
        for(int i=0;i<this.ady.length;i++){
            if(i==j){
                F[i]=true;
                D[i][0]=0;
                D[i][1]=0;
            }else{
                F[i]=false;
            }
            //costo
            D[i][0]=0;
            //predecesor
            D[i][1]=0;
        }
        int costominimo;
        //variables usadas para ir guardando y despues modificar el nodo con el nodocon el costo minimo
        int nodominimo=j;
        int auxminimo=0;
        //variables para ir cambiando y guardando el costo 
        int costo=0;
        int aux=0;
        //numero de iteraciones por hacer
        for(int q=0;q<this.ady.length;q++){
            costominimo=Integer.MAX_VALUE-1000;
            //aqui checamos que le nodo a visitar no este en F
            for(int w=0;w<this.ady.length;w++){
                //en caso de que el nodo ya este en F se lo salta y continua
                if(F[w]){
                    continue;
                }
                //si el nodo aun no esta en f se le agrega el peso,en este caso si la posicion en la que se encuentra tiene un cero se considera como 
                //si no hubiera un camino
                if(this.ady[nodominimo][w]==0){
                    continue;
                }else{
                       D[w][0]=this.ady[nodominimo][w]+costo;
                }
            }
            //busco el camino menos costoso
            for(int k=0;k<this.ady.length;k++){
                if(F[k]){
                    continue;
                }
                if(D[k][0]<costominimo && D[k][0]>0){
                    aux=D[k][0];
                    costominimo=D[k][0];
                    //aqui verifico si el camino encontrado es marco corto que el anterior medido desde el nodo de partida
                    if(this.ady[j][k]!=0 && this.ady[j][k]<=aux) {
                	D[k][1]=j;
                	costo=0;
                    }else {
               		costo=0;
                    }
                    //guardo el nodo minimo en un auxiliar
                    auxminimo=k;
                }
            }
            //aqui me protego en caso de que llegue el ultimo nodo y este no tenga caminos directos hacie otros y ebite poner que su antecesor es el mismo
            if(auxminimo!=nodominimo){
                D[auxminimo][1]=nodominimo;
                costo=costo+aux;
            }
            //agreagamos el nodo ya visitado
            F[nodominimo=auxminimo]=true;
        }
        //imprimir el camino desde el ultimo nodo hasta el nodo origen 
        int cost=D[nodominimo][0];
        int b=0;
        System.out.println("el camino mas corto desde el nodo " +j+" es:");
        for(b=0;b<D.length;b++){
            System.out.println(nodominimo);
            nodominimo=D[nodominimo][1];            
        }
        System.out.println("el costo es:"+cost);
    }
    public void floyd() throws Exception{
        int[][] aux=new int[this.ady.length][this.ady.length];
        this.floyd=new int[this.ady.length][this.ady.length];
        //aqui lleno la matriz de adyacencia con costos
        llenarMatriz("grafo con pesos.txt");
        //aqui cambiamo los ceros de la matriz  por "infinito" para eso usamos Integer.MAX_VALUE-1000
        for(int i=0;i<this.ady.length;i++){
            for(int j=0;j<this.ady.length;j++){
                if(this.ady[i][j]==0){
                    aux[i][j]=Integer.MAX_VALUE-1000;
                }
                else{
                    aux[i][j]=this.ady[i][j];
                }
            }
        }
        //ahora aqui aplicamos el algoritmo de floyd
        for(int i=0;i<this.ady.length;i++){
            for(int j=0;j<this.ady.length;j++){
                for(int k=0;k<this.ady.length;k++){
                    this.floyd[i][j]=minimo(aux[i][j],aux[i][k],aux[k][j]);
                }
            }
        }
    }
    private int minimo(int a,int b,int c){
        if(b==Integer.MAX_VALUE-1000 || c==Integer.MAX_VALUE-1000){
            b=Integer.MAX_VALUE-1000;
        }
        else{
            b=b+c;
        }
        if(b<a){
            return b;
        }
        return a;
    }
    public void imprimeComponentes(List<Set<Integer>> lista){
            Iterator i=lista.iterator();
            Set<Integer> w;
            while(i.hasNext()){
                w=(Set<Integer>) i.next();
                System.out.println(w);
            }
    }
}
