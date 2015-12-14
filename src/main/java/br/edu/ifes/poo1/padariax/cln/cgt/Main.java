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
            menu(sc, fileChooser);
            gerarRelatorios(sc, fileChooser);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro de I/O.");
        }

    }

    /**
     * Metodo responsavel por perguntar o caminho onde se econtram os arquivos
     * de importacao e importa-los para dentro de uma Colecao(List e Map).
     *
     * @param sc - Objeto Scanner.
     * @param fileChooser - Objeto JFileChooser.
     * @throws Exception
     */
    public static void menu(Scanner sc, JFileChooser fileChooser) throws Exception {
        System.out.println("Informe o caminho dos arquivos de importação: ");
        int status = fileChooser.showOpenDialog(null);
        if (status == JFileChooser.APPROVE_OPTION) {
            String caminho = fileChooser.getSelectedFile().getPath() + "/";
            System.out.println(caminho);
            String arquivos = "clientes.csv,fornecedores.csv,produtos.csv,compras.csv,vendas.csv";

            Scanner linha = new Scanner(arquivos);
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
                }
            }
        } else {
            System.out.println("Saindo...");
            System.exit(0);
        }

    }

    private static Map importaCliente(String caminho) {
        arquivoCliente = new Arquivo(caminho, "clientes.csv");
        return mapaCliente = aplCliente.cadastroCliente(arquivoCliente);
    }

    private static Map importaFornecedor(String caminho) {
        arquivoFornecedor = new Arquivo(caminho, "fornecedores.csv");
        return mapaFornecedor = aplFornecedor.cadastroFornecedor(arquivoFornecedor);
    }

    private static Map importaProduto(String caminho) {
        arquivoProdutos = new Arquivo(caminho, "produtos.csv");
        return mapaProduto = aplProduto.cadastroProduto(arquivoProdutos);
    }

    private static List<Compra> importaCompra(String caminho) {
        aplCompra = new AplCompra(aplFornecedor.cadastroFornecedor(arquivoFornecedor),
                aplProduto.cadastroProduto(arquivoProdutos));
        arquivoCompras = new Arquivo(caminho, "compras.csv");
        return listaCompras = aplCompra.cadastroCompra(arquivoCompras);
    }

    private static List<Venda> importaVenda(String caminho) {
        aplVenda = new AplVenda(aplCliente.cadastroCliente(arquivoCliente),
                aplProduto.cadastroProduto(arquivoProdutos));
        arquivoVendas = new Arquivo(caminho, "vendas.csv");
        return listaVendas = aplVenda.cadastroVenda(arquivoVendas);
    }

    /**
     * Metodo responsavel por exibir um menu para a escolha da geracao dos
     * relatorios. Ao escolher o relatorio desejado, pergunta-se aonde o mesmo
     * devera ser salvo. Caso o usuario nao tenha permissao para escrever na
     * pasta o programa exibe lanca uma excecao e encerrado. Cada relatorio e
     * exportado individualmente.
     *
     * @param sc - Objeto Scanner.
     * @param fileChooser - Objeto JFileChooser.
     * @throws Exception
     */
    private static void gerarRelatorios(Scanner sc, JFileChooser fileChooser) throws Exception {
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

        switch (opc) {
            case "1":
                System.out.println("Informe o caminho em que o relatório será salvo: ");
                fileChooser.showSaveDialog(null);
                destino = fileChooser.getSelectedFile().getPath() + "/";
                System.out.println(destino);
                System.out.println("Gerando relatório...");
                List<String> listaTotalPagar = aplRelatorio.aPagarFornecedor(listaCompras);
                util.exportar(listaTotalPagar, new Arquivo(destino, "apagar.csv"), new APagarFornecedor().getCabecalho());
                System.out.println("Relatório gerado com sucesso!");
                gerarRelatorios(sc, fileChooser);
                break;
            case "2":
                System.out.println("Informe o caminho em que o relatório será salvo: ");
                fileChooser.showSaveDialog(null);
                destino = fileChooser.getSelectedFile().getPath() + "/";
                System.out.println(destino);
                System.out.println("Gerando relatório...");
                List<String> listaTotalReceber = aplRelatorio.aReceberPorCliente(listaVendas, new ArrayList(mapaCliente.values()));
                util.exportar(listaTotalReceber, new Arquivo(destino, "areceber.csv"), new AReceberCliente().getCabecalho());
                System.out.println("Relatório gerado com sucesso!");
                gerarRelatorios(sc, fileChooser);
                break;
            case "3":
                System.out.println("Informe o caminho em que o relatório será salvo: ");
                fileChooser.showSaveDialog(null);
                destino = fileChooser.getSelectedFile().getPath() + "/";
                System.out.println(destino);
                System.out.println("Gerando relatório...");
                List<String> listaTotalLucro = aplRelatorio.vendasLucroPorProduto(listaVendas, new ArrayList(mapaProduto.values()));
                util.exportar(listaTotalLucro, new Arquivo(destino, "vendasprod.csv"), new VendasLucroProduto().getCabecalho());
                System.out.println("Relatório gerado com sucesso!");
                gerarRelatorios(sc, fileChooser);
                break;
            case "4":
                System.out.println("Informe o caminho em que o relatório será salvo: ");
                fileChooser.showSaveDialog(null);
                destino = fileChooser.getSelectedFile().getPath() + "/";
                System.out.println(destino);
                System.out.println("Gerando relatório...");
                List<String> listaTotalPagamento = aplRelatorio.vendasLucroPorMeioPagamento(listaVendas);
                util.exportar(listaTotalPagamento, new Arquivo(destino, "vendaspgto.csv"), new VendasLucroMeioPagamento().getCabecalho());
                System.out.println("Relatório gerado com sucesso!");
                gerarRelatorios(sc, fileChooser);
                break;
            case "5":
                System.out.println("Informe o caminho em que o relatório será salvo: ");
                fileChooser.showSaveDialog(null);
                destino = fileChooser.getSelectedFile().getPath() + "/";
                System.out.println(destino);
                System.out.println("Gerando relatório...");
                listaVendas = importaVenda(arquivoVendas.getCaminho());
                List<String> listaBalanco = aplRelatorio.balancoMensal(listaVendas, listaCompras, new ArrayList(mapaProduto.values()));
                util.exportar(listaBalanco, new Arquivo(destino, "estoque.csv"), new BalancoMensal().getCabecalho());
                System.out.println("Relatório gerado com sucesso!");
                gerarRelatorios(sc, fileChooser);
                break;
            case "6":
                System.out.println("Informe o caminho em que o relatório será salvo: ");
                fileChooser.showSaveDialog(null);
                destino = fileChooser.getSelectedFile().getPath() + "/";
                System.out.println(destino);
                System.out.println("Gerando relatórios...");
                listaVendas = importaVenda(arquivoVendas.getCaminho());
                List<String> listaAPagar = aplRelatorio.aPagarFornecedor(listaCompras);
                util.exportar(listaAPagar, new Arquivo(destino, "apagar.csv"), new APagarFornecedor().getCabecalho());
                List<String> listaAReceber = aplRelatorio.aReceberPorCliente(listaVendas, new ArrayList(mapaCliente.values()));
                util.exportar(listaAReceber, new Arquivo(destino, "areceber.csv"), new AReceberCliente().getCabecalho());
                List<String> listaLucroProduto = aplRelatorio.vendasLucroPorProduto(listaVendas, new ArrayList(mapaProduto.values()));
                util.exportar(listaLucroProduto, new Arquivo(destino, "vendasprod.csv"), new VendasLucroProduto().getCabecalho());
                List<String> listaLucroPagamento = aplRelatorio.vendasLucroPorMeioPagamento(listaVendas);
                util.exportar(listaLucroPagamento, new Arquivo(destino, "vendaspgto.csv"), new VendasLucroMeioPagamento().getCabecalho());
                List<String> listaBalancoMensal = aplRelatorio.balancoMensal(listaVendas, listaCompras, new ArrayList(mapaProduto.values()));
                util.exportar(listaBalancoMensal, new Arquivo(destino, "estoque.csv"), new BalancoMensal().getCabecalho());
                System.out.println("Relatórios gerados com sucesso!");
                gerarRelatorios(sc, fileChooser);
                break;
            case "7":
                System.out.println("Saindo...");
                break;
            default:
                System.out.println("Opção inexistente.");
                break;
        }
    }
}
