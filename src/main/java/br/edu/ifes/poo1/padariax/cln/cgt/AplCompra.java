/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifes.poo1.padariax.cln.cgt;

import br.edu.ifes.poo1.padariax.cln.cdp.Arquivo;
import br.edu.ifes.poo1.padariax.cln.cdp.Compra;
import br.edu.ifes.poo1.padariax.cln.util.Utilitario;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author aleao
 */
public class AplCompra {
    private Utilitario aplArquivo;
    
    public AplCompra(){
        this.aplArquivo= new Utilitario();
    }
    
    /**
     * Função responsável por transformar as linhas lidas do arquivo em uma
     * lista de Compra. 
     *
     * @param file - Caminho do arquivo
     * @return listaCliente - lista de compras
     * @throws ParseException
     */
    public List<Compra> cadastroCompra(Arquivo file) {
        List<Compra> listaCompra = new ArrayList();
        List<String> listaImportada = aplArquivo.importar(file);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        
        for (String linha : listaImportada) {
            Scanner sc = new Scanner(linha);
            sc.useDelimiter(";");
            
            listaCompra.add(criaProduto(sc, sdf));
        }
        
        return listaCompra;
    }

    private Compra criaProduto(Scanner sc, SimpleDateFormat sdf) {
        Compra compra =  new Compra();
        
        try {
            compra.setNotaFiscal(Integer.parseInt(sc.next()));
//            compra.setFornecedor(fornecedor);
            
        } catch (Exception e) {
        }
        
        return compra;
    }
}
