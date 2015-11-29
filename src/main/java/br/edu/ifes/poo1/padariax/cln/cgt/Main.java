/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifes.poo1.padariax.cln.cgt;

import br.edu.ifes.poo1.padariax.cln.cdp.Arquivo;
import br.edu.ifes.poo1.padariax.cln.util.Utilitario;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author aleao
 */
public class Main {

    private static Reader fr;

    /**
     * @param args the command line arguments
     * @throws java.io.FileNotFoundException
     * @throws java.text.ParseException
     */
    public static void main(String[] args) throws FileNotFoundException, IOException, ParseException {
        Arquivo arquivoCompras = new Arquivo("./src/test/java/br/edu/ifes/poo1/padariax/arquivos/teste_1/", "compras.csv");
        Arquivo arquivoFornecedor = new Arquivo("./src/test/java/br/edu/ifes/poo1/padariax/arquivos/teste_1/", "fornecedores.csv");
        Arquivo arquivoProduto = new Arquivo("./src/test/java/br/edu/ifes/poo1/padariax/arquivos/teste_1/", "produtos.csv");
        Arquivo destinoCompras = new Arquivo("/home/aleao/Documents/", "compras.csv");

        AplRelatorio aplRelatorio = new AplRelatorio();
        AplFornecedor aplFornecedor = new AplFornecedor();
        AplProduto aplProduto = new AplProduto();
        Utilitario util = new Utilitario();

        AplCompra aplCompra = new AplCompra(aplFornecedor.cadastroFornecedor(arquivoFornecedor), aplProduto.cadastroProduto(arquivoProduto));
        Map mapaCompras = aplCompra.cadastroCompra(arquivoCompras);

        try {
            util.exportar(aplRelatorio.totalPagarFornecedor(mapaCompras), destinoCompras);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
