/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grafos;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alumnocbi
 */
public class Grafos {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Grafo gra=new Grafo();
        try {
           gra.llenarMatriz("prueba.txt");
           gra.imprimeMatriz(gra.ady);
           System.out.println("recorrido en anchura:");
           gra.recorridoAnchura(5);
           System.out.println("recorrido en profundidad:");
           gra.recorridoProfundidad(3);
           System.out.println("Componentes conexas:");
           List<Set<Integer>> lista=gra.componentesConexas();
           gra.imprimeComponentes(lista);
           System.out.println("Componentes Fuertemente Conexas:");
           List<Set<Integer>> lista2=gra.componentesFConexas();
           gra.imprimeComponentes(lista2);
           System.out.print("camino mas corto ");
           camino corto=gra.caminoCorto(3,6);
           corto.imprime();
           System.out.println("Matiz de Cierre Transitivo:");
           gra.matrizCierreT();
           gra.imprimeMatriz(gra.cierre);
           System.out.println("matriz de cierre transitivo (algoritmo de Warshall):");
           gra.warshall();
           gra.imprimeMatriz(gra.warshall);
           System.out.println("Matriz de caminos (Algoritmo dde Floyd)");
           gra.floyd();
           gra.imprimeMatriz(gra.floyd);
           System.out.println("Algoritmo de Dijkstra");
           gra.llenarMatriz("dijkstra.txt");
           gra.dijkstra(0);
                       
        } catch (IOException ex) {
            Logger.getLogger(Grafos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Grafos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
