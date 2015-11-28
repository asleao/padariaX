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
    private List<Venda> listaVendaNaoFiado;

    public AplVenda(Map mapaCliente, Map mapaProduto) {
        this.util = new Utilitario();
        this.mapaVenda = new HashMap();
        this.listaVenda = new ArrayList<>();
        this.dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);
        this.mapaCliente = mapaCliente;
        this.mapaProduto = mapaProduto;
        this.listaVendaNaoFiado = new ArrayList();
    }

    /**
     * Função responsável por transformar as linhas lidas do arquivo em uma List
     * de Venda. Ela cria um Map para vendas Fiado e um List para vendas não
     * fiado. Quando o objeto scanner lê o último registro, os 2 tipos de vendas
     * são unidos em uma única lista. 
     *
     * @param file - Caminho do arquivo
     * @return listaCliente - List de Vendas     
     */
    public List<Venda> cadastroVenda(Arquivo file) {
        List<String> listaImportada = util.importar(file);

        for (String linha : listaImportada) {
            Scanner sc = new Scanner(linha);
            sc.useDelimiter(";");
            String registro = sc.next();
            int cliente = 0;            

            if (ehData(registro)) {
                importaVendaNaoFiado(sc, registro);
            } else {
                cliente = Integer.parseInt(registro);
                importaVendaFiado(cliente, sc);
            }            

            if (ehUltimoRegistro(sc)) {
                listaVenda = vendasCadastradas(mapaVenda, listaVendaNaoFiado);
            }
        }

        return listaVenda;
    }

    /**
     * Função responsável por verificar se o scanner encontra-se no último 
     * registro do arquivo.
     * @param sc
     * @return 
     */
    private static boolean ehUltimoRegistro(Scanner sc) {
        return !sc.hasNext();
    }

    /**
     * Função responsável por verificar se o registro lido do arquivo, é uma 
     * data.
     * @param registro
     * @return 
     */
    private boolean ehData(String registro) {               
        return registro.indexOf("/") > 0;
    }

    /**
     * Função responsável por adicionar a venda lida do arquivo na lista de vendas
     * não fiado.
     * @param sc
     * @param registro 
     */
    private void importaVendaNaoFiado(Scanner sc, String registro) {
        Venda vendaNova = criaVendaNaoFiado(sc, registro);
        listaVendaNaoFiado.add(vendaNova);
    }

    /**
     * Função responsável por adicionar a venda lida do arquivo no Map de vendas
     * fiado.
     * @param cliente
     * @param sc
     * @throws NumberFormatException 
     */
    private void importaVendaFiado(int cliente, Scanner sc) throws NumberFormatException {
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
            Venda vendaNova = criaVendaFiado(sc, cliente);
            mapaVenda.put(vendaNova.getCliente().getCodigo(), vendaNova);
        }
    }

    /**
     * Função responsável por retornar uma lista com todas vendas importadas do
     * arquivo.
     *
     *
     * @param mapaVendasFiado
     * @param listaVendasNaoFiado
     * @return listaTodasVendas - Lista de Vendas
     */
    public List<Venda> vendasCadastradas(Map mapaVendasFiado, List<Venda> listaVendasNaoFiado) {
        List<Venda> listaTodasVendas = new ArrayList(mapaVendasFiado.values());

        for (Venda venda : listaVendasNaoFiado) {
            listaTodasVendas.add(venda);
        }
        return listaTodasVendas;
    }

    /**
     * Função responsável por criar um objeto Venda quando a venda lida do
     * aqruivo, tiver o meio de pagamento Fiado(F).
     *
     * @param sc - Objeto Scanner
     * @param clienteArquivo - Código do cliente lido do arquivo.
     * @return VendaLocal - Objeto Venda
     */
    private Venda criaVendaFiado(Scanner sc, int clienteArquivo) {
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

    /**
     * Função responsável por criar um objeto Venda quando a venda lida do
     * aqruivo, NÃO tiver meio de pagamento do tipo Fiado(F). Dessa maneira, o
     * objeto cliente não deve ser preenchido.
     *
     * @param sc - Objeto Scanner
     * @param dataVenda - Data da venda lida do arquivo.
     * @return VendaLocal - Objeto Venda
     */
    private Venda criaVendaNaoFiado(Scanner sc, String dataVenda) {
        Venda VendaLocal = new Venda();
        List<Item> listaItem = new ArrayList();

        try {
            VendaLocal.setDataVenda(dateFormat.parse(dataVenda));
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
