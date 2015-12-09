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

/**
 *
 * @author aleao
 */
public class AplCompra {

    private Utilitario util;
    private Map mapaCompraIgual;    
    private Map mapaCompraDiferente;    
    private DateFormat dateFormat;
    private Map mapaFornecedor;
    private Map mapaProduto;

    public AplCompra() {
    }

    public AplCompra(Map mapaFornecedor, Map mapaProduto) {
        this.util = new Utilitario();
        this.mapaCompraIgual = new HashMap();
        this.mapaCompraDiferente = new HashMap();
        this.dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);
        this.mapaFornecedor = mapaFornecedor;
        this.mapaProduto = mapaProduto;
    }

    /**
     * Metodo responsável por transformar as linhas lidas do arquivo em um List
     * de Compra. Este quebra cada linha em tokens utilizando o delimitador ";".
     * Existem dois objetos Map: 
     * - mapaCompraIgual: Armazena todas as notas fiscais que sao unicas.
     * - mapaCompraDiferente: Podem existir notas fiscais que possuem mesmo numero
     * porem tem fornecedores diferentes. Este Map armazena todas as notas que possuem
     * mesmo numero no mapaCompraIgual porem com fornecedores diferentes.
     * No final da importacao os dois Map sao transformados em objetos List<Compra>
     * e mesclados.
     *
     * @param file - Caminho do arquivo
     * @return listaCliente - Map de compras
     */
    public List<Compra> cadastroCompra(Arquivo file) {
        List<String> listaImportada = util.importar(file);

        for (String linha : listaImportada) {
            Scanner sc = new Scanner(linha);
            sc.useDelimiter(";");
            int notaFiscal = Integer.parseInt(sc.next());

            if (mapaCompraIgual.containsKey(notaFiscal)) {
                Compra compraExistente = (Compra) mapaCompraIgual.get(notaFiscal);

                int codigoFornecedor = Integer.parseInt(sc.next());

                if (codigoFornecedor != compraExistente.getFornecedor().getCodigo()) {
                    if (mapaCompraDiferente.containsKey(notaFiscal)) {
                        Compra compraExistenteDif = (Compra) mapaCompraDiferente.get(notaFiscal);

                        sc.next();

                        Produto produto = (Produto) mapaProduto.get(Integer.parseInt(sc.next()));
                        Item item = new Item(produto, Integer.parseInt(sc.next()));

                        compraExistenteDif.getListaItens().add(item);
                        mapaCompraDiferente.put(compraExistenteDif.getNotaFiscal(), compraExistenteDif);
                    } else {
                        Compra compraDif = criaCompra(sc, notaFiscal, codigoFornecedor);
                        mapaCompraDiferente.put(compraDif.getNotaFiscal(), compraDif);
                    }
                } else {
                    sc.next();

                    Produto produto = (Produto) mapaProduto.get(Integer.parseInt(sc.next()));
                    Item item = new Item(produto, Integer.parseInt(sc.next()));

                    compraExistente.getListaItens().add(item);
                    mapaCompraIgual.put(compraExistente.getNotaFiscal(), compraExistente);
                }
            } else {
                Compra compraNova = criaCompra(sc, notaFiscal);
                mapaCompraIgual.put(compraNova.getNotaFiscal(), compraNova);
            }

        }

        List<Compra> listaCompra= transformaMap2List(mapaCompraIgual,mapaCompraDiferente);

        return listaCompra;
    }

    /**
     * Metodo responsavel em transformar 2 Map em List e uni-los.
     * @return 
     */
    private List<Compra> transformaMap2List(Map mapa1, Map mapa2) {
        List<Compra> listaCompra= new ArrayList(mapa1.values());
        List<Compra> listaDiferente = new ArrayList(mapa2.values());
        listaCompra.addAll(listaDiferente);
        return listaCompra;
    }
    
    /**
     * Metodo responsavel por criar um objeto compra com os tokens
     * lidos de cada linha do arquivo de compras importado.
     * @param sc
     * @param notaFiscal
     * @return 
     */
    public Compra criaCompra(Scanner sc, int notaFiscal) {
        Compra compraLocal = new Compra();
        List<Item> listaItem = new ArrayList();
        int quantidade;
        try {
            compraLocal.setNotaFiscal(notaFiscal);
            Fornecedor fornecedor = (Fornecedor) mapaFornecedor.get(Integer.parseInt(sc.next()));
            compraLocal.setFornecedor(fornecedor);
            compraLocal.setDataCompra(dateFormat.parse(sc.next()));
            Produto produto = (Produto) mapaProduto.get(Integer.parseInt(sc.next()));
            quantidade = Integer.parseInt(sc.next());
            Item item = new Item(produto, quantidade);
            listaItem.add(item);
            compraLocal.setListaItens(listaItem);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return compraLocal;
    }
    
    /**
     * Este metodo e uma sobrecarga do acima. Ele foi nescessario pois pode existir
     * a possibilidade de se importar compras com notas fiscais iguais e fornecedores
     * diferentes.
     * @param sc
     * @param notaFiscal
     * @param codigoFornecedor
     * @return 
     */    
     public Compra criaCompra(Scanner sc, int notaFiscal, int codigoFornecedor) {
        Compra compraLocal = new Compra();
        List<Item> listaItem = new ArrayList();
        int quantidade;
        try {
            compraLocal.setNotaFiscal(notaFiscal);
            Fornecedor fornecedor = (Fornecedor) mapaFornecedor.get(codigoFornecedor);
            compraLocal.setFornecedor(fornecedor);
            compraLocal.setDataCompra(dateFormat.parse(sc.next()));
            Produto produto = (Produto) mapaProduto.get(Integer.parseInt(sc.next()));
            quantidade = Integer.parseInt(sc.next());
            Item item = new Item(produto, quantidade);
            listaItem.add(item);
            compraLocal.setListaItens(listaItem);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return compraLocal;
    }
    

    /**
     * Metodo responsavel por informar a quantidade de comprada passando um
     * produto como parametro.
     */
    public int retornaQuantidadeProdutoComprada(List<Compra> listaCompra, Produto produto) {
        int quantidadeComprada = 0;
        Map mapProduto = new HashMap();

        for (Compra compraLocal : listaCompra) {
            for (Item item : compraLocal.getListaItens()) {
                if (mapProduto.containsKey(item.getProduto().getCodigo())) {

                    quantidadeComprada += item.getQuantidade();

                    mapProduto.put(item.getProduto(), quantidadeComprada);

                } else if (item.getProduto().getCodigo() == produto.getCodigo()) {
                    quantidadeComprada = item.getQuantidade();
                    mapProduto.put(item.getProduto().getCodigo(), quantidadeComprada);
                }
            }
        }

        return quantidadeComprada;
    }
}
