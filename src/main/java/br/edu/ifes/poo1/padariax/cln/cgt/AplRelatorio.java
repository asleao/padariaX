/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifes.poo1.padariax.cln.cgt;

import br.edu.ifes.poo1.padariax.cln.cdp.Compra;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author aleao
 */
public class AplRelatorio {
    private AplFornecedor aplFornecedor;
    
    /**
     *
     * @param mapaCompras
     * @return
     */
    public List<String> totalPagarFornecedor(Map mapaCompras){
        List<String> listaTotalPagar = new ArrayList();
        List<Compra> listaCompras = new ArrayList(mapaCompras.values());
        
        for(Compra compra: listaCompras){
            listaTotalPagar.add(compra.toString());
        }        
        
        return listaTotalPagar;
    }
    
    
    
}
