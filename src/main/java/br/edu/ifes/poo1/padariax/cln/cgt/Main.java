/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifes.poo1.padariax.cln.cgt;

import br.edu.ifes.poo1.padariax.cln.cdp.Arquivo;
import br.edu.ifes.poo1.padariax.cln.cdp.Venda;
import br.edu.ifes.poo1.padariax.cln.cdp.relatorios.APagarFornecedor;
import br.edu.ifes.poo1.padariax.cln.util.Utilitario;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

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

        Scanner sc = new Scanner(System.in);
        try {
            menu(sc);
            gerarRelatorios(sc);
        } catch (Exception e) {
        }

    }

    public static void menu(Scanner sc) {
        System.out.println("Informe o caminho dos arquivos de importação: ");
        String caminho = sc.nextLine();
        System.out.println("Informe o nome dos arquivos de importação na seguinte ordem (Separados por vírgula): ");
        System.out.println("1 - Cadastro de Clientes");
        System.out.println("2 - Cadastro de Fornecedores");
        System.out.println("3 - Cadastro de Produtos");
        System.out.println("4 - Registro de Compras");
        System.out.println("5 - Registro de vendas");
        String arquivos = sc.nextLine();

        Scanner linha = new Scanner(arquivos);
        linha.useDelimiter(",");

        try {
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
                    default:
                        System.out.println("Arquivo \"" + opc + "\" não existe! \n"
                                + "As opções possíveis são: clientes.csv,fornecedores.csv,produtos.csv,compras.csv,vendas.csv. \n"
                                + "Favor verificar!");
                        throw new Exception("Erro de I/O");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro de I/O.");
        }
    }

    private static void gerarRelatorios(Scanner sc) { 
        System.out.println("Relatórios:");
        System.out.println("1 - Total a pagar por fornecedor");
        System.out.println("2 - Total a receber por cliente");
        System.out.println("3 - Vendas e lucro por produto");
        System.out.println("4 - Vendas e lucro por forma de pagamento");
        System.out.println("5 - Gerar todos relatórios");
        System.out.println("6 - Sair");
        System.out.println("Escolha uma opção: ");
        String opc = sc.nextLine();

        try {

            switch (opc) {
                case "1":                    
                    System.out.println("Informe o caminho em que o relatório será salvo: ");
                    String caminho = sc.nextLine();
                    System.out.println("Gerando relatório...");
                    List<String> listaTotalPagar = aplRelatorio.aPagarFornecedor(mapaCompras);
                    util.exportar(listaTotalPagar, new Arquivo(caminho, "apagar.csv"), new APagarFornecedor().getCabecalho());
                    System.out.println("Relatório gerado com sucesso!");
                    gerarRelatorios(sc);
                    break;
                case "2":

                    break;
                case "3":

                    break;
                case "4":

                    break;
                case "5":

                    break;
                case "6":
                    break;
                default:
                    System.out.println("Opção inexistente.");
                    throw new Exception("Erro de I/O");
            }

        } catch (Exception e) {
        }
    }

}
