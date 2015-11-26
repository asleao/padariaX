/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifes.poo1.padariax.cln.cgt;

import br.edu.ifes.poo1.padariax.cln.cdp.Arquivo;
import br.edu.ifes.poo1.padariax.cln.cdp.Compra;
import br.edu.ifes.poo1.padariax.cln.cdp.Fornecedor;
import br.edu.ifes.poo1.padariax.cln.cdp.Produto;
import br.edu.ifes.poo1.padariax.cln.cdp.Item;
import br.edu.ifes.poo1.padariax.cln.util.Utilitario;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 *
 * @author aleao
 */
public class AplCompra {

    private Utilitario util;
    private Map mapaCompra;
    private Compra compra;
    private DateFormat dateFormat;
    private Map mapaFornecedor;
    private Map mapaProduto;

    public AplCompra(Map mapaFornecedor, Map mapaProduto) {
        this.util = new Utilitario();
        this.mapaCompra = new HashMap();
        this.dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);
        this.mapaFornecedor = mapaFornecedor;
        this.mapaProduto = mapaProduto;        
    }

    /**
     * Função responsável por transformar as linhas lidas do arquivo em uma Map
     * de Compra.
     *
     * @param file - Caminho do arquivo
     * @return listaCliente - Map de compras
     * @throws ParseException
     */
    public Map cadastroCompra(Arquivo file) {
        List<String> listaImportada = util.importar(file);

        for (String linha : listaImportada) {;
            Scanner sc = new Scanner(linha);
            sc.useDelimiter(";");
            int notaFiscal = Integer.parseInt(sc.next());
           
            if (mapaCompra.containsKey(notaFiscal)) {
                Compra compraExistente = (Compra) mapaCompra.get(notaFiscal);

//                Pattern pattern = Pattern.compile("^.[0-9]+;[0-9]+;[0-9]+/[0-9]+/[0-9]+;");
//                sc.skip(pattern);
                sc.next();
                sc.next();

                Produto produto = (Produto) mapaProduto.get(Integer.parseInt(sc.next()));
                Item item = new Item(produto, Integer.parseInt(sc.next()));
                
                compraExistente.getListaItens().add(item);
                mapaCompra.put(compraExistente.getNotaFiscal(), compraExistente);

            } else {
                Compra compraNova = criaCompra(sc,notaFiscal);
                mapaCompra.put(compraNova.getNotaFiscal(), compraNova);
            }

            
        }

        return mapaCompra;
    }

    private Compra criaCompra(Scanner sc, int notaFiscal) {
        Compra compraLocal = new Compra();
        List<Item> listaItem = new ArrayList();
        try {
            compraLocal.setNotaFiscal(notaFiscal);            
            Fornecedor fornecedor = (Fornecedor) mapaFornecedor.get(Integer.parseInt(sc.next()));
            compraLocal.setFornecedor(fornecedor);
            compraLocal.setDataCompra(dateFormat.parse(sc.next()));
            Produto produto = (Produto) mapaProduto.get(Integer.parseInt(sc.next()));
            Item item = new Item(produto, Integer.parseInt(sc.next()));
            listaItem.add(item);
            compraLocal.setListaItens(listaItem);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return compraLocal;
    }
}
