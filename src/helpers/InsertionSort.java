package helpers;
import java.util.List;

import entidades.Compra;

public class InsertionSort {
    public static void ordenar(List<Compra> array){
        for(int i = 1; i < array.size(); i++) {
            int j = i;
            
            while (j > 0 && array.get(j-1).getCliente().getNome().compareToIgnoreCase(array.get(j).getCliente().getNome()) > 0) {
                trocarPosicao(array, j);
                j--;
            }
        }
    }
    
    private static void trocarPosicao(List<Compra> array, int posicaoAtual) {
        Compra compraPosicaoAtual = array.get(posicaoAtual);
        array.set(posicaoAtual, array.get(posicaoAtual-1));
        array.set(posicaoAtual-1, compraPosicaoAtual);
    }
}
