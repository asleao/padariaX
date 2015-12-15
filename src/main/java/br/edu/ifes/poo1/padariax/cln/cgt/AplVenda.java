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
import java.io.IOException;
import java.math.BigDecimal;
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
    private List<Venda> listaVendaNaoFiado;

    public AplVenda() {

    }

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
     * @throws java.io.IOException
     * @throws java.text.ParseException
     */
    public List<Venda> cadastroVenda(Arquivo file) throws IOException, ParseException {

        List<String> listaImportada = util.importar(file);

        for (String linha : listaImportada) {
            try (Scanner sc = new Scanner(linha)) {
                if (!linha.equals("")) {
                    sc.useDelimiter(";");
                    String registro = sc.next();
                    int cliente;

                    if (ehData(registro)) {
                        importaVendaNaoFiado(sc, registro);
                    } else {
                        cliente = Integer.parseInt(registro);
                        importaVendaFiado(cliente, sc);
                    }
                }
            }
        }

        listaVenda = vendasCadastradas(mapaVenda, listaVendaNaoFiado);

        return listaVenda;
    }

    /**
     * Função responsável por verificar se o registro lido do arquivo, é uma
     * data.
     *
     * @param registro
     * @return
     */
    private boolean ehData(String registro) {
        return registro.contains("/");
    }

    /**
     * Função responsável por adicionar a venda lida do arquivo na lista de
     * vendas não fiado.
     *
     * @param sc
     * @param registro
     */
    private void importaVendaNaoFiado(Scanner sc, String registro) throws ParseException {
        Venda vendaNova = criaVendaNaoFiado(sc, registro);
        listaVendaNaoFiado.add(vendaNova);
    }

    /**
     * Função responsável por adicionar a venda lida do arquivo no Map de vendas
     * fiado.
     *
     * @param cliente
     * @param sc
     * @throws NumberFormatException
     */
    private void importaVendaFiado(int cliente, Scanner sc) throws ParseException {

        if (mapaVenda.containsKey(cliente)) {

            Venda vendaExistente = (Venda) mapaVenda.get(cliente);

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
     * Função responsável por unir um Map de um List de vendas e retornar uma
     * lista com todas vendas importadas do arquivo.
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
    private Venda criaVendaFiado(Scanner sc, int clienteArquivo) throws ParseException {
        Venda vendaLocal = new Venda();
        List<Item> listaItem = new ArrayList();
        int quantidade = 0;

        Cliente cliente = (Cliente) mapaCliente.get(clienteArquivo);
        vendaLocal.setCliente(cliente);
        vendaLocal.setDataVenda(dateFormat.parse(sc.next()));
        Produto produto = (Produto) mapaProduto.get(Integer.parseInt(sc.next()));
        quantidade = Integer.parseInt(sc.next());
        Item item = new Item(produto, quantidade);
        listaItem.add(item);
        vendaLocal.setListaItens(listaItem);
        vendaLocal.setMeioPagamento(MeioPagamento.valueOf(sc.next()));

        return vendaLocal;
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
    private Venda criaVendaNaoFiado(Scanner sc, String dataVenda) throws ParseException {
        Venda vendaLocal = new Venda();
        List<Item> listaItem = new ArrayList();
        int quantidade;

        vendaLocal.setDataVenda(dateFormat.parse(dataVenda));
        Produto produto = (Produto) mapaProduto.get(Integer.parseInt(sc.next()));
        quantidade = Integer.parseInt(sc.next());
        Item item = new Item(produto, quantidade);
        listaItem.add(item);
        vendaLocal.setListaItens(listaItem);
        vendaLocal.setMeioPagamento(MeioPagamento.valueOf(sc.next()));

        return vendaLocal;
    }

    /**
     * Funcao responsavel por informar a quantidade de vendida passando um
     * produto como parametro.
     *
     * @param listaVendas
     * @param produto
     * @return
     */
    public int retornaQuantidadeProdutoVendida(List<Venda> listaVendas, Produto produto) {
        int quantidadeVendida = 0;
        Map mapProduto = new HashMap();

        for (Venda venda : listaVendas) {
            for (Item item : venda.getListaItens()) {
                if (mapProduto.containsKey(item.getProduto().getCodigo())) {

                    quantidadeVendida += item.getQuantidade();

                    mapProduto.put(item.getProduto(), quantidadeVendida);

                } else if (item.getProduto().getCodigo() == produto.getCodigo()) {
                    quantidadeVendida = item.getQuantidade();
                    mapProduto.put(item.getProduto().getCodigo(), quantidadeVendida);
                }
            }
        }

        return quantidadeVendida;
    }

    /**
     * Funcao responsavel por informar a receita bruta de vendida passando um
     * meio de pagamento como parametro.
     *
     * @param listaVendas
     * @param meioPagamento
     * @return
     */
    public double receitaBrutaPorMeioPagamento(List<Venda> listaVendas, MeioPagamento meioPagamento) {
        double receitaBruta = 0;
        Map mapProduto = new HashMap();

        for (Venda venda : listaVendas) {
            if (meioPagamento.equals(venda.getMeioPagamento())) {
                for (Item item : venda.getListaItens()) {
                    receitaBruta += item.getQuantidade() * item.getProduto().valorVenda();
                    mapProduto.put(item.getProduto().getCodigo(), receitaBruta);

                }
            }
        }

        return receitaBruta;
    }

    /**
     * Funcao responsavel por informar a lucro vendido passando um meio de
     * pagamento como parametro.
     *
     * @param listaVendas
     * @param meioPagamento
     * @return
     */
    public double lucroPorMeioPagamento(List<Venda> listaVendas, MeioPagamento meioPagamento) {
        double lucro = 0;
        Map mapProduto = new HashMap();

        for (Venda venda : listaVendas) {
            if (meioPagamento.equals(venda.getMeioPagamento())) {
                for (Item item : venda.getListaItens()) {
                    lucro += item.getQuantidade() * item.getProduto().getValorCusto();
                    mapProduto.put(item.getProduto().getCodigo(), lucro);
                }
            }
        }

        double receitaBruta = receitaBrutaPorMeioPagamento(listaVendas, meioPagamento);

        return receitaBruta - lucro;
    }

    /**
     * Funcao responsavel por retornar valor a Receber passando um objeto
     * cliente como parametro.
     *
     * @param listaVenda
     * @param cliente
     * @return
     */
    public BigDecimal retornaValorAReceber(List<Venda> listaVenda, Cliente cliente) {
        double valorReceber = 0;

        for (Venda vendaLocal : listaVenda) {
            if (cliente == vendaLocal.getCliente()) {
                valorReceber = vendaLocal.valorPago().doubleValue();
            }
        }
        return new BigDecimal(valorReceber).setScale(2, BigDecimal.ROUND_DOWN);
    }
}
