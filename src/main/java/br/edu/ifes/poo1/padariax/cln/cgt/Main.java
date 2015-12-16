/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifes.poo1.padariax.cln.cgt;

import br.edu.ifes.poo1.padariax.cln.cdp.Arquivo;
import br.edu.ifes.poo1.padariax.cln.cdp.Compra;
import br.edu.ifes.poo1.padariax.cln.cdp.Venda;
import br.edu.ifes.poo1.padariax.cln.cdp.relatorios.APagarFornecedor;
import br.edu.ifes.poo1.padariax.cln.cdp.relatorios.AReceberCliente;
import br.edu.ifes.poo1.padariax.cln.cdp.relatorios.BalancoMensal;
import br.edu.ifes.poo1.padariax.cln.cdp.relatorios.VendasLucroMeioPagamento;
import br.edu.ifes.poo1.padariax.cln.cdp.relatorios.VendasLucroProduto;
import br.edu.ifes.poo1.padariax.cln.util.Utilitario;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import javax.swing.JFileChooser;

/**
 *
 * @author aleao
 */
public class Main {

    private static Map mapaCliente;
    private static Map mapaFornecedor;
    private static Map mapaProduto;
    private static List<Compra> listaCompras;
    private static List<Venda> listaVendas;
    private static AplCliente aplCliente;
    private static AplFornecedor aplFornecedor;
    private static AplProduto aplProduto;
    private static AplCompra aplCompra;
    private static AplVenda aplVenda;
    private static AplRelatorio aplRelatorio;
    private static Arquivo arquivoCliente;
    private static Arquivo arquivoFornecedor;
    private static Arquivo arquivoCompras;
    private static Arquivo arquivoVendas;
    private static Arquivo arquivoProdutos;
    private static Utilitario util;
    private static final String CAMINHOENTRADA = "Informe o caminho dos arquivos de importação: ";
    private static final String CAMINHOSAIDA = "Informe o caminho em que o relatório será salvo: ";
    private static final String GERANDO = "Gerando relatório...";
    private static final String SUCESSO = "Relatório gerado com sucesso!";
    private static final String CANCELADO = "Relatório cancelado...";
    private static final String INEXISTENTE = "Opção inexistente...";
    private static final String ENCERRANDO = "Saindo...";
    private static final String ERRO = "Erro de I/O.";

    /**
     * @param args the command line arguments
     *
     */
    public static void main(String[] args) {
        aplCliente = new AplCliente();
        aplFornecedor = new AplFornecedor();
        aplProduto = new AplProduto();
        aplRelatorio = new AplRelatorio();
        util = new Utilitario();

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        try (Scanner sc = new Scanner(System.in)) {
            menu(fileChooser);
            gerarRelatorios(sc, fileChooser);
        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println(ERRO);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }

    }

    /**
     * Metodo responsavel por perguntar o caminho onde se econtram os arquivos
     * de importacao e importa-los para dentro de uma Colecao(List e Map).
     *
     * @param fileChooser - Objeto JFileChooser.
     * @throws java.io.IOException
     * @throws java.text.ParseException
     */
    public static void menu(JFileChooser fileChooser) throws IOException, ParseException {
        System.out.println(CAMINHOENTRADA);
        int status = fileChooser.showOpenDialog(null);
        if (status == JFileChooser.APPROVE_OPTION) {
            String caminho = fileChooser.getSelectedFile().getPath() + "/";
            System.out.println(caminho);
            String arquivos = "clientes.csv,fornecedores.csv,produtos.csv,compras.csv,vendas.csv";

            try (Scanner linha = new Scanner(arquivos)) {
                linha.useDelimiter(",");

                while (linha.hasNext()) {
                    String opc = linha.next();
                    switch (opc) {
                        case "clientes.csv":
                            importaCliente(caminho);
                            break;
                        case "fornecedores.csv":
                            importaFornecedor(caminho);
                            break;
                        case "produtos.csv":
                            importaProduto(caminho);
                            break;
                        case "compras.csv":
                            importaCompra(caminho);
                            break;
                        case "vendas.csv":
                            importaVenda(caminho);
                            break;
                        default:
                            System.out.println("Arquivo" + opc + " não faz parte do escopo do sistema.");
                            break;
                    }
                }
            }
        } else {
            System.out.println(ENCERRANDO);
            System.exit(0);
        }

    }

    /**
     * Método responsável por importar o arquivo de clientes para um Map.
     *
     * @param caminho
     * @return
     */
    private static Map importaCliente(String caminho) throws IOException, ParseException {
        arquivoCliente = new Arquivo(caminho, "clientes.csv");
        mapaCliente = aplCliente.cadastroCliente(arquivoCliente);
        return mapaCliente;
    }

    /**
     * Método responsável por importar o arquivo de fornecedores para um Map.
     *
     * @param caminho
     * @return
     */
    private static Map importaFornecedor(String caminho) throws IOException {
        arquivoFornecedor = new Arquivo(caminho, "fornecedores.csv");
        mapaFornecedor = aplFornecedor.cadastroFornecedor(arquivoFornecedor);
        return mapaFornecedor;
    }

    /**
     * Método responsável por importar o arquivo de produtos para um Map.
     *
     * @param caminho
     * @return
     */
    private static Map importaProduto(String caminho) throws IOException {
        arquivoProdutos = new Arquivo(caminho, "produtos.csv");
        mapaProduto = aplProduto.cadastroProduto(arquivoProdutos);
        return mapaProduto;
    }

    /**
     * Método responsável por importar o arquivo de compras para um
     * List<Compra>.
     *
     * @param caminho
     * @return
     */
    private static List<Compra> importaCompra(String caminho) throws IOException, ParseException {
        aplCompra = new AplCompra(aplFornecedor.cadastroFornecedor(arquivoFornecedor),
                aplProduto.cadastroProduto(arquivoProdutos));
        arquivoCompras = new Arquivo(caminho, "compras.csv");
        listaCompras = aplCompra.cadastroCompra(arquivoCompras);
        return listaCompras;
    }

    /**
     * Método responsável por importar o arquivo de vendas para um List<Venda>.
     *
     * @param caminho
     * @return
     */
    private static List<Venda> importaVenda(String caminho) throws IOException, ParseException {
        aplVenda = new AplVenda(aplCliente.cadastroCliente(arquivoCliente),
                aplProduto.cadastroProduto(arquivoProdutos));
        arquivoVendas = new Arquivo(caminho, "vendas.csv");
        listaVendas = aplVenda.cadastroVenda(arquivoVendas);
        return listaVendas;
    }

    /**
     * Metodo responsavel por exibir um menu para a escolha da geracao dos
     * relatorios. Ao escolher o relatorio desejado, pergunta-se aonde o mesmo
     * devera ser salvo. Caso o usuario nao tenha permissao para escrever na
     * pasta o programa exibe lanca uma excecao e encerrado.
     *
     * @param sc - Objeto Scanner.
     * @param fileChooser - Objeto JFileChooser.
     */
    private static void gerarRelatorios(Scanner sc, JFileChooser fileChooser) throws FileNotFoundException, IOException, ParseException {
        System.out.println("Relatórios:");
        System.out.println("1 - Total a pagar por fornecedor");
        System.out.println("2 - Total a receber por cliente");
        System.out.println("3 - Vendas e lucro por produto");
        System.out.println("4 - Vendas e lucro por forma de pagamento");
        System.out.println("5 - Estado do estoque");
        System.out.println("6 - Gerar Todos");
        System.out.println("7 - Sair");
        System.out.println("Escolha uma opção: ");

        String opc = sc.nextLine();

        String destino;
        int status;

        switch (opc) {
            case "1":
                System.out.println(CAMINHOSAIDA);
                status = fileChooser.showSaveDialog(null);
                if (status == JFileChooser.APPROVE_OPTION) {
                    destino = fileChooser.getSelectedFile().getPath() + "/";
                    System.out.println(destino);
                    System.out.println(GERANDO);
                    List<String> listaTotalPagar = aplRelatorio.aPagarFornecedor(listaCompras);
                    util.exportar(listaTotalPagar, new Arquivo(destino, "1-apagar.csv"), new APagarFornecedor().getCabecalho());
                    System.out.println(SUCESSO);
                    gerarRelatorios(sc, fileChooser);
                } else {
                    System.out.println(CANCELADO);
                    gerarRelatorios(sc, fileChooser);
                }
                break;
            case "2":
                System.out.println(CAMINHOSAIDA);
                status = fileChooser.showSaveDialog(null);
                if (status == JFileChooser.APPROVE_OPTION) {
                    destino = fileChooser.getSelectedFile().getPath() + "/";
                    System.out.println(destino);
                    System.out.println(GERANDO);
                    List<String> listaTotalReceber = aplRelatorio.aReceberPorCliente(listaVendas, new ArrayList(mapaCliente.values()));
                    util.exportar(listaTotalReceber, new Arquivo(destino, "2-areceber.csv"), new AReceberCliente().getCabecalho());
                    System.out.println(SUCESSO);
                    gerarRelatorios(sc, fileChooser);
                } else {
                    System.out.println(CANCELADO);
                    gerarRelatorios(sc, fileChooser);
                }
                break;
            case "3":
                System.out.println(CAMINHOSAIDA);
                status = fileChooser.showSaveDialog(null);
                if (status == JFileChooser.APPROVE_OPTION) {
                    destino = fileChooser.getSelectedFile().getPath() + "/";
                    System.out.println(destino);
                    System.out.println(GERANDO);
                    List<String> listaTotalLucro = aplRelatorio.vendasLucroPorProduto(listaVendas, new ArrayList(mapaProduto.values()));
                    util.exportar(listaTotalLucro, new Arquivo(destino, "3-vendasprod.csv"), new VendasLucroProduto().getCabecalho());
                    System.out.println(SUCESSO);
                    gerarRelatorios(sc, fileChooser);
                } else {
                    System.out.println(CANCELADO);
                    gerarRelatorios(sc, fileChooser);
                }
                break;
            case "4":
                System.out.println(CAMINHOSAIDA);
                status = fileChooser.showSaveDialog(null);
                if (status == JFileChooser.APPROVE_OPTION) {
                    destino = fileChooser.getSelectedFile().getPath() + "/";
                    System.out.println(destino);
                    System.out.println(GERANDO);
                    List<String> listaTotalPagamento = aplRelatorio.vendasLucroPorMeioPagamento(listaVendas);
                    util.exportar(listaTotalPagamento, new Arquivo(destino, "4-vendaspgto.csv"), new VendasLucroMeioPagamento().getCabecalho());
                    System.out.println(SUCESSO);
                    gerarRelatorios(sc, fileChooser);
                } else {
                    System.out.println(CANCELADO);
                    gerarRelatorios(sc, fileChooser);
                }
                break;
            case "5":
                System.out.println(CAMINHOSAIDA);
                status = fileChooser.showSaveDialog(null);
                if (status == JFileChooser.APPROVE_OPTION) {
                    destino = fileChooser.getSelectedFile().getPath() + "/";
                    System.out.println(destino);
                    System.out.println(GERANDO);
                    listaVendas = importaVenda(arquivoVendas.getCaminho());
                    List<String> listaBalanco = aplRelatorio.balancoMensal(listaVendas, listaCompras, new ArrayList(mapaProduto.values()));
                    util.exportar(listaBalanco, new Arquivo(destino, "5-estoque.csv"), new BalancoMensal().getCabecalho());
                    System.out.println(SUCESSO);
                    gerarRelatorios(sc, fileChooser);
                } else {
                    System.out.println(CANCELADO);
                    gerarRelatorios(sc, fileChooser);
                }
                break;
            case "6":
                System.out.println(CAMINHOSAIDA);
                status = fileChooser.showSaveDialog(null);
                if (status == JFileChooser.APPROVE_OPTION) {
                    destino = fileChooser.getSelectedFile().getPath() + "/";
                    System.out.println(destino);
                    System.out.println(GERANDO);
                    listaVendas = importaVenda(arquivoVendas.getCaminho());
                    List<String> listaAPagar = aplRelatorio.aPagarFornecedor(listaCompras);
                    util.exportar(listaAPagar, new Arquivo(destino, "1-apagar.csv"), new APagarFornecedor().getCabecalho());
                    List<String> listaAReceber = aplRelatorio.aReceberPorCliente(listaVendas, new ArrayList(mapaCliente.values()));
                    util.exportar(listaAReceber, new Arquivo(destino, "2-areceber.csv"), new AReceberCliente().getCabecalho());
                    List<String> listaLucroProduto = aplRelatorio.vendasLucroPorProduto(listaVendas, new ArrayList(mapaProduto.values()));
                    util.exportar(listaLucroProduto, new Arquivo(destino, "3-vendasprod.csv"), new VendasLucroProduto().getCabecalho());
                    List<String> listaLucroPagamento = aplRelatorio.vendasLucroPorMeioPagamento(listaVendas);
                    util.exportar(listaLucroPagamento, new Arquivo(destino, "4-vendaspgto.csv"), new VendasLucroMeioPagamento().getCabecalho());
                    List<String> listaBalancoMensal = aplRelatorio.balancoMensal(listaVendas, listaCompras, new ArrayList(mapaProduto.values()));
                    util.exportar(listaBalancoMensal, new Arquivo(destino, "5-estoque.csv"), new BalancoMensal().getCabecalho());
                    System.out.println(SUCESSO);
                    gerarRelatorios(sc, fileChooser);
                } else {
                    System.out.println(CANCELADO);
                    gerarRelatorios(sc, fileChooser);
                }
                break;
            case "7":
                System.out.println(ENCERRANDO);
                break;
            default:
                System.out.println(INEXISTENTE);
                break;
        }
    }
}
