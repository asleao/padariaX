/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifes.poo1.padariax.cln.cgt;

import br.edu.ifes.poo1.padariax.cln.cdp.Arquivo;
import br.edu.ifes.poo1.padariax.cln.cdp.Cliente;
import br.edu.ifes.poo1.padariax.cln.cdp.Item;
import br.edu.ifes.poo1.padariax.cln.cdp.MeioPagamento;
import br.edu.ifes.poo1.padariax.cln.cdp.Produto;
import br.edu.ifes.poo1.padariax.cln.cdp.Venda;
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
public class AplVenda {

    private Utilitario util;
    private Map mapaVenda;
    private DateFormat dateFormat;
    private Map mapaCliente;
    private Map mapaProduto;   
    private List<Venda> listaVenda;

    public AplVenda(Map mapaCliente, Map mapaProduto) {
        this.util = new Utilitario();
        this.mapaVenda = new HashMap();
        this.listaVenda = new ArrayList<>();
        this.dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);
        this.mapaCliente = mapaCliente;
        this.mapaProduto = mapaProduto;
    }

    /**
     * Função responsável por transformar as linhas lidas do arquivo em uma Map
     * de Venda para vendas Fiado.
     *
     * @param file - Caminho do arquivo
     * @return listaCliente - Map de Vendas
     * @throws ParseException
     */
    public Map cadastroVendaFiado(Arquivo file) {
        List<String> listaImportada = util.importar(file);

        for (String linha : listaImportada) {;
            Scanner sc = new Scanner(linha);
            sc.useDelimiter(";");
            int cliente = Integer.parseInt(sc.next());

            if (mapaVenda.containsKey(cliente)) {

                Venda vendaExistente = (Venda) mapaVenda.get(cliente);

//                Pattern pattern = Pattern.compile("^.[0-9]+;[0-9]+;[0-9]+/[0-9]+/[0-9]+;");
//                sc.skip(pattern);
                sc.next();                

                Produto produto = (Produto) mapaProduto.get(Integer.parseInt(sc.next()));
                Item item = new Item(produto, Integer.parseInt(sc.next()));

                vendaExistente.getListaItens().add(item);
                mapaVenda.put(vendaExistente.getCliente().getCodigo(), vendaExistente);

            } else if (cliente != 0) {
                Venda vendaNova = criaVenda(sc, cliente);
                mapaVenda.put(vendaNova.getCliente().getCodigo(), vendaNova);
            } 
        }

        return mapaVenda;
    }
    
    /**
     * Função responsável por transformar as linhas lidas do arquivo em uma Map
     * de Venda para vendas Fiado.
     *
     * @param file - Caminho do arquivo
     * @return listaCliente - Map de Vendas
     * @throws ParseException
     */
    public List<Venda> cadastroVendaNaoFiado(Arquivo file) {
        List<String> listaImportada = util.importar(file);

        for (String linha : listaImportada) {;
            Scanner sc = new Scanner(linha);
            sc.useDelimiter(";");
            int cliente = Integer.parseInt(sc.next());

            if (cliente != 0) {
                Venda vendaNova = criaVenda(sc, cliente);
                listaVenda.add(vendaNova);
            } 
        }

        return listaVenda;
    }

    /**
     * Função responsável por retornar todas vendas importadas do arquivo.
     *
     * @param file - Caminho do arquivo
     * @return listaCliente - Map de Vendas
     * @throws ParseException
     */
    public List<Venda> vendasCadastradas(Map mapaVendasFiado,List<Venda> listaVendasNaoFiado){
        List<Venda> listaTodasVendas = new ArrayList(mapaVendasFiado.values());
        
        for(Venda venda:listaVendasNaoFiado){
            listaTodasVendas.add(venda);
        }
        return listaTodasVendas;
    }
    private Venda criaVenda(Scanner sc, int clienteArquivo) {
        Venda VendaLocal = new Venda();
        List<Item> listaItem = new ArrayList();
        
        try {
            Cliente cliente = (Cliente) mapaCliente.get(clienteArquivo);
            VendaLocal.setCliente(cliente);
            VendaLocal.setDataVenda(dateFormat.parse(sc.next()));
            Produto produto = (Produto) mapaProduto.get(Integer.parseInt(sc.next()));
            Item item = new Item(produto, Integer.parseInt(sc.next()));
            listaItem.add(item);
            VendaLocal.setListaItens(listaItem);
            VendaLocal.setMeioPagamento(MeioPagamento.valueOf(sc.next()));

        } catch (Exception e) {
            e.printStackTrace();
        }

        return VendaLocal;
    }
}
