/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifes.poo1.padariax.cln.cgt;

import br.edu.ifes.poo1.padariax.cln.cdp.Arquivo;
import br.edu.ifes.poo1.padariax.cln.cdp.Venda;
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
    private static Map mapaCompras;
    private static List<Venda> listaVendas;
    private static AplCliente aplCliente;
    private static AplFornecedor aplFornecedor;
    private static AplCompra aplCompra;
    private static AplVenda aplVenda;
    private static Utilitario util;

    /**
     * @param args the command line arguments
     *
     */
    public static void main(String[] args) {
        aplCliente = new AplCliente();
        aplFornecedor = new AplFornecedor();
        aplCompra = new AplCompra();
        aplVenda = new AplVenda();
        util = new Utilitario();

        Scanner sc = new Scanner(System.in);
        menu(sc);

    }

    public static void menu(Scanner sc) {
        System.out.println("Informe o caminho dos arquivos de importação: ");
        String caminho = sc.nextLine();
        System.out.println("Informe o nome dos arquivos de importação na seguinte ordem (Separados por vírgula): ");
        System.out.println("1 - Cadastro de Clientes");
        System.out.println("2 - Cadastro de Fornecedores");
        System.out.println("3 - Registro de Compras");
        System.out.println("4 - Registro de vendas");
        String arquivos = sc.nextLine();

        Scanner linha = new Scanner(arquivos);
        linha.useDelimiter(",");

        try {
            while (linha.hasNext()) {
                String opc = linha.next();
                switch (opc) {
                    case "clientes.csv":
                        Arquivo arquivoCliente = new Arquivo(caminho, "clientes.csv");
                        System.out.println(arquivoCliente.toString());
                        mapaCliente = aplCliente.cadastroCliente(arquivoCliente);
                        break;
                    case "fornecedores.csv":
                        Arquivo arquivoFornecedor = new Arquivo(caminho, "fornecedores.csv");
                        mapaFornecedor = aplFornecedor.cadastroFornecedor(arquivoFornecedor);
                        break;
                    case "compras.csv":
                        Arquivo arquivoCompras = new Arquivo(caminho, "compras.csv");
                        mapaCompras = aplCompra.cadastroCompra(arquivoCompras);
                        break;
                    case "vendas.csv":
                        Arquivo arquivoVendas = new Arquivo(caminho, "vendas.csv");
                        listaVendas = aplVenda.cadastroVenda(arquivoVendas);
                        break;
                    default:
                        System.out.println("Arquivo \""+opc+"\" não existe! \n"
                                + "As opções possíveis são: clientes.csv,fornecedores.csv,compras.csv,vendas.csv. \n"
                                + "Favor verificar!");
                        throw new Exception("Erro de I/O");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro de I/O.");
        }

    }

}
