/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifes.poo1.padariax.cln.cgt;

import br.edu.ifes.poo1.padariax.cln.cdp.Arquivo;
import br.edu.ifes.poo1.padariax.cln.cdp.Compra;
import br.edu.ifes.poo1.padariax.cln.cdp.Item;
import br.edu.ifes.poo1.padariax.cln.cdp.Produto;
import br.edu.ifes.poo1.padariax.cln.util.Utilitario;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author aleao
 */
public class AplCompraTest {

    private Utilitario util;
    private Arquivo arquivo;
    private AplCompra aplCompra;
    private AplProduto aplProduto;
    private AplFornecedor aplFornecedor;
    private Arquivo arquivoFornecedor;
    private Arquivo arquivoProduto;
    private Arquivo arquivoCompra;

    @Before
    public void setUp() {
        this.arquivoFornecedor = new Arquivo("./src/test/java/br/edu/ifes/poo1/padariax/arquivos/teste_5/", "fornecedores.csv");
        this.arquivoProduto = new Arquivo("./src/test/java/br/edu/ifes/poo1/padariax/arquivos/teste_5/", "produtos.csv");
        this.arquivoCompra = new Arquivo("./src/test/java/br/edu/ifes/poo1/padariax/arquivos/teste_5/", "compras.csv");
        this.util = new Utilitario();
        this.aplFornecedor = new AplFornecedor();
        this.aplProduto = new AplProduto();
    }

    @Test
    public void testCadastroCompra() throws ParseException, IOException {
        List<String> listaArquivo = util.importar(arquivoCompra);
        AplRelatorio aplRelatorio = new AplRelatorio();
        Map mapaProduto = aplProduto.cadastroProduto(arquivoProduto);
        Map mapaFornecedor = aplFornecedor.cadastroFornecedor(arquivoFornecedor);
        aplCompra = new AplCompra(mapaFornecedor, mapaProduto);

        Map mapaCompra = new HashMap();
        Map mapaCompraDiferente = new HashMap();

        for (String linha : listaArquivo) {
            Scanner sc = new Scanner(linha);
            sc.useDelimiter(";");
            int notaFiscal = Integer.parseInt(sc.next());

            if (mapaCompra.containsKey(notaFiscal)) {
                Compra compraExistente = (Compra) mapaCompra.get(notaFiscal);

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
                        Compra compraDif = aplCompra.criaCompra(sc, notaFiscal, codigoFornecedor);
                        mapaCompraDiferente.put(compraDif.getNotaFiscal(), compraDif);
                    }
                } else {
                    sc.next();

                    Produto produto = (Produto) mapaProduto.get(Integer.parseInt(sc.next()));
                    Item item = new Item(produto, Integer.parseInt(sc.next()));

                    compraExistente.getListaItens().add(item);
                    mapaCompra.put(compraExistente.getNotaFiscal(), compraExistente);
                }
            } else {
                Compra compraNova = aplCompra.criaCompra(sc, notaFiscal);
                mapaCompra.put(compraNova.getNotaFiscal(), compraNova);
            }

        }

        List<Compra> listaCompra= new ArrayList(mapaCompra.values());
        List<Compra> listaDiferente = new ArrayList(mapaCompraDiferente.values());
        listaCompra.addAll(listaDiferente);
        
       List<String> lista = aplRelatorio.aPagarFornecedor(listaCompra);
        
//        util.imprime(lista);
//        Assert.assertNotNull(listaCompra);
//        Assert.assertEquals(4, listaCompra.size());
    }

    private void imprimeCompra(List<Compra> listaCompra) {
        for (Compra c : listaCompra) {
            c.exportaCompras();
        }
    }
}
