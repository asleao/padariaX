/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifes.poo1.padariax.cln.cgt;

import br.edu.ifes.poo1.padariax.cln.cdp.Arquivo;
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
    private static Map mapaCompras;
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

    public static void menu(Scanner sc, JFileChooser fileChooser) throws Exception {
        System.out.println("Informe o caminho dos arquivos de importação: ");
        fileChooser.showOpenDialog(null);
        String caminho = fileChooser.getSelectedFile().getPath()+ "/";        
        String arquivos = "clientes.csv,fornecedores.csv,produtos.csv,compras.csv,vendas.csv";

        Scanner linha = new Scanner(arquivos);
        linha.useDelimiter(",");

        while (linha.hasNext()) {
            String opc = linha.next();
            switch (opc) {
                case "clientes.csv":
                    arquivoCliente = new Arquivo(caminho, "clientes.csv");
                    System.out.println(arquivoCliente.toString());
                    mapaCliente = aplCliente.cadastroCliente(arquivoCliente);
                    break;
                case "fornecedores.csv":
                    arquivoFornecedor = new Arquivo(caminho, "fornecedores.csv");
                    mapaFornecedor = aplFornecedor.cadastroFornecedor(arquivoFornecedor);
                    break;
                case "produtos.csv":
                    arquivoProdutos = new Arquivo(caminho, "produtos.csv");
                    mapaProduto = aplProduto.cadastroProduto(arquivoProdutos);
                    break;
                case "compras.csv":
                    aplCompra = new AplCompra(aplFornecedor.cadastroFornecedor(arquivoFornecedor),
                            aplProduto.cadastroProduto(arquivoProdutos));
                    arquivoCompras = new Arquivo(caminho, "compras.csv");
                    mapaCompras = aplCompra.cadastroCompra(arquivoCompras);
                    break;
                case "vendas.csv":
                    aplVenda = new AplVenda(aplCliente.cadastroCliente(arquivoCliente),
                            aplProduto.cadastroProduto(arquivoProdutos));
                    arquivoVendas = new Arquivo(caminho, "vendas.csv");
                    listaVendas = aplVenda.cadastroVenda(arquivoVendas);
                    break;
            }
        }

    }

    private static void gerarRelatorios(Scanner sc, JFileChooser fileChooser) throws Exception{
        System.out.println("Relatórios:");
        System.out.println("1 - Total a pagar por fornecedor");
        System.out.println("2 - Total a receber por cliente");
        System.out.println("3 - Vendas e lucro por produto");
        System.out.println("4 - Vendas e lucro por forma de pagamento");
        System.out.println("5 - Estado do estoque");
        System.out.println("6 - Sair");
        System.out.println("Escolha uma opção: ");

        String opc = sc.nextLine();

        String caminho;

        switch (opc) {
            case "1":
                System.out.println("Informe o caminho em que o relatório será salvo: ");
                fileChooser.showSaveDialog(null);
                caminho = fileChooser.getSelectedFile().getPath() + "/";
                System.out.println("Gerando relatório...");
                List<String> listaTotalPagar = aplRelatorio.aPagarFornecedor(mapaCompras);
                util.exportar(listaTotalPagar, new Arquivo(caminho, "apagar.csv"), new APagarFornecedor().getCabecalho());
                System.out.println("Relatório gerado com sucesso!");
                gerarRelatorios(sc, fileChooser);
                break;
            case "2":
                System.out.println("Informe o caminho em que o relatório será salvo: ");
                fileChooser.showSaveDialog(null);
                caminho = fileChooser.getSelectedFile().getPath() + "/";
                System.out.println("Gerando relatório...");
                List<String> listaTotalReceber = aplRelatorio.aReceberPorCliente(listaVendas, new ArrayList(mapaCliente.values()));
                util.exportar(listaTotalReceber, new Arquivo(caminho, "areceber.csv"), new AReceberCliente().getCabecalho());
                System.out.println("Relatório gerado com sucesso!");
                gerarRelatorios(sc, fileChooser);
                break;
            case "3":
                System.out.println("Informe o caminho em que o relatório será salvo: ");
                fileChooser.showSaveDialog(null);
                caminho = fileChooser.getSelectedFile().getPath() + "/";
                System.out.println("Gerando relatório...");
                List<String> listaTotalLucro = aplRelatorio.vendasLucroPorProduto(listaVendas, new ArrayList(mapaProduto.values()));
                util.exportar(listaTotalLucro, new Arquivo(caminho, "vendasprod.csv"), new VendasLucroProduto().getCabecalho());
                System.out.println("Relatório gerado com sucesso!");
                gerarRelatorios(sc, fileChooser);
                break;
            case "4":
                System.out.println("Informe o caminho em que o relatório será salvo: ");
                fileChooser.showSaveDialog(null);
                caminho = fileChooser.getSelectedFile().getPath() + "/";
                System.out.println("Gerando relatório...");
                List<String> listaTotalPagamento = aplRelatorio.vendasLucroPorMeioPagamento(listaVendas);
                util.exportar(listaTotalPagamento, new Arquivo(caminho, "vendaspgto.csv"), new VendasLucroMeioPagamento().getCabecalho());
                System.out.println("Relatório gerado com sucesso!");
                gerarRelatorios(sc, fileChooser);
                break;
            case "5":
                System.out.println("Informe o caminho em que o relatório será salvo: ");
                fileChooser.showSaveDialog(null);
                caminho = fileChooser.getSelectedFile().getPath() + "/";
                System.out.println("Gerando relatório...");
                List<String> listaBalanco = aplRelatorio.balancoMensal(listaVendas, new ArrayList(mapaCompras.values()), new ArrayList(mapaProduto.values()));
                util.exportar(listaBalanco, new Arquivo(caminho, "estoque.csv"), new BalancoMensal().getCabecalho());
                System.out.println("Relatório gerado com sucesso!");
                gerarRelatorios(sc, fileChooser);
                break;
            case "6":
                System.out.println("Saindo...");
                break;
            default:
                System.out.println("Opção inexistente.");
                break;
        }
    }
}
