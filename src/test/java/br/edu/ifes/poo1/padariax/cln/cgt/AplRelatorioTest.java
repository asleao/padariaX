/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifes.poo1.padariax.cln.cgt;

import br.edu.ifes.poo1.padariax.cln.cdp.Arquivo;
import br.edu.ifes.poo1.padariax.cln.cdp.Cliente;
import br.edu.ifes.poo1.padariax.cln.cdp.Compra;
import br.edu.ifes.poo1.padariax.cln.cdp.Produto;
import br.edu.ifes.poo1.padariax.cln.cdp.Venda;
import br.edu.ifes.poo1.padariax.cln.util.Utilitario;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author aleao
 */
public class AplRelatorioTest {

    private Utilitario util;
    private Arquivo arquivo;
    private AplCompra aplCompra;
    private AplVenda aplVenda;
    private AplProduto aplProduto;
    private AplFornecedor aplFornecedor;
    private AplCliente aplCliente;
    private Arquivo arquivoFornecedor;
    private Arquivo arquivoProduto;
    private Arquivo arquivoCompra;
    private Arquivo arquivoVenda;
    private Arquivo arquivoCliente;
    private AplRelatorio aplRelatorio;

    @Before
    public void setUp() {
        this.arquivoCliente = new Arquivo("./src/test/java/br/edu/ifes/poo1/padariax/arquivos/teste_4/", "clientes.csv");
        this.arquivoFornecedor = new Arquivo("./src/test/java/br/edu/ifes/poo1/padariax/arquivos/teste_4/", "fornecedores.csv");
        this.arquivoProduto = new Arquivo("./src/test/java/br/edu/ifes/poo1/padariax/arquivos/teste_4/", "produtos.csv");
        this.arquivoCompra = new Arquivo("./src/test/java/br/edu/ifes/poo1/padariax/arquivos/teste_4/", "compras.csv");
        this.arquivoVenda = new Arquivo("./src/test/java/br/edu/ifes/poo1/padariax/arquivos/teste_4/", "vendas.csv");
        this.util = new Utilitario();
        this.aplFornecedor = new AplFornecedor();
        this.aplProduto = new AplProduto();
        this.aplCliente = new AplCliente();
        this.aplRelatorio = new AplRelatorio();
    }

    /**
     * Test of totalPagarFornecedor method, of class AplRelatorio.
     */
    @Test
    public void testTotalPagarFornecedor() throws IOException, ParseException {
        List<String> listaArquivo = util.importar(arquivoCompra);
        aplCompra = new AplCompra(aplFornecedor.cadastroFornecedor(arquivoFornecedor), aplProduto.cadastroProduto(arquivoProduto));
        List<Compra> listaCompra = aplCompra.cadastroCompra(arquivoCompra);

        List<String> listaTotalPagar = aplRelatorio.aPagarFornecedor(listaCompra);

//        util.imprime(listaTotalPagar);
        assertNotNull(listaTotalPagar);

    }

    @Test
    public void testTotalReceberPorCliente() throws IOException, ParseException {

        Map mapaCliente = aplCliente.cadastroCliente(arquivoCliente);
        List<Cliente> listaCliente = new ArrayList(mapaCliente.values());
        aplVenda = new AplVenda(mapaCliente, aplProduto.cadastroProduto(arquivoProduto));

        List<Venda> listaVendas = aplVenda.cadastroVenda(arquivoVenda);

        List<String> listaTotalReceber = aplRelatorio.aReceberPorCliente(listaVendas, listaCliente);

//        util.imprime(listaTotalReceber);
        assertNotNull(listaTotalReceber);

    }

    @Test
    public void testVendasLucroPorProduto() throws IOException, ParseException {

        Map mapaProduto = aplProduto.cadastroProduto(arquivoProduto);
        List<Produto> listaProduto = new ArrayList(mapaProduto.values());

        aplVenda = new AplVenda(aplCliente.cadastroCliente(arquivoCliente), aplProduto.cadastroProduto(arquivoProduto));

        List<Venda> listaVendas = aplVenda.cadastroVenda(arquivoVenda);

        List<String> listaTotalLucro = aplRelatorio.vendasLucroPorProduto(listaVendas, listaProduto);

        Collections.sort(listaTotalLucro);
//        util.imprime(listaTotalLucro);

        assertNotNull(listaTotalLucro);

    }

    @Test
    public void testVendasLucroPorMeioPagamento() throws IOException, ParseException {

        aplVenda = new AplVenda(aplCliente.cadastroCliente(arquivoCliente), aplProduto.cadastroProduto(arquivoProduto));

        List<Venda> listaVendas = aplVenda.cadastroVenda(arquivoVenda);

        List<String> listaTotalLucro = aplRelatorio.vendasLucroPorMeioPagamento(listaVendas);

        Collections.sort(listaTotalLucro);
//        util.imprime(listaTotalLucro);

        assertNotNull(listaTotalLucro);

    }

    @Test
    public void testBalancoMensal() throws IOException, ParseException {
        Map mapaProduto = aplProduto.cadastroProduto(arquivoProduto);
        List<Produto> listaProduto = new ArrayList(mapaProduto.values());

        aplVenda = new AplVenda(aplCliente.cadastroCliente(arquivoCliente), aplProduto.cadastroProduto(arquivoProduto));
        aplCompra = new AplCompra(aplFornecedor.cadastroFornecedor(arquivoFornecedor), aplProduto.cadastroProduto(arquivoProduto));

        List<Compra> listaCompras = aplCompra.cadastroCompra(arquivoCompra);
        List<Venda> listaVendas = aplVenda.cadastroVenda(arquivoVenda);

        List<String> listaBalanco = aplRelatorio.balancoMensal(listaVendas, listaCompras, listaProduto);

        Collections.sort(listaBalanco);

//        util.imprime(listaBalanco);
        assertNotNull(listaBalanco);

    }
}
