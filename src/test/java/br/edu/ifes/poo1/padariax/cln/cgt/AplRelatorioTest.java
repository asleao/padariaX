/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifes.poo1.padariax.cln.cgt;

import br.edu.ifes.poo1.padariax.cln.cdp.Arquivo;
import br.edu.ifes.poo1.padariax.cln.cdp.Cliente;
import br.edu.ifes.poo1.padariax.cln.cdp.Venda;
import br.edu.ifes.poo1.padariax.cln.util.Utilitario;
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
        this.arquivoCliente = new Arquivo("./src/test/java/br/edu/ifes/poo1/padariax/arquivos/teste_1/", "clientes.csv");
        this.arquivoFornecedor = new Arquivo("./src/test/java/br/edu/ifes/poo1/padariax/arquivos/teste_1/", "fornecedores.csv");
        this.arquivoProduto = new Arquivo("./src/test/java/br/edu/ifes/poo1/padariax/arquivos/teste_1/", "produtos.csv");
        this.arquivoCompra = new Arquivo("./src/test/java/br/edu/ifes/poo1/padariax/arquivos/teste_1/", "compras.csv");
        this.arquivoVenda = new Arquivo("./src/test/java/br/edu/ifes/poo1/padariax/arquivos/teste_1/", "vendas.csv");
        this.util = new Utilitario();
        this.aplFornecedor = new AplFornecedor();
        this.aplProduto = new AplProduto();
        this.aplRelatorio = new AplRelatorio();
        this.aplCliente = new AplCliente();
    }
    
   

    /**
     * Test of totalPagarFornecedor method, of class AplRelatorio.
     */
    @Test
    public void testTotalPagarFornecedor() {
       List<String> listaArquivo = util.importar(arquivoCompra);
        aplCompra = new AplCompra(aplFornecedor.cadastroFornecedor(arquivoFornecedor)
                                  , aplProduto.cadastroProduto(arquivoProduto));
        Map mapaCompra = aplCompra.cadastroCompra(arquivoCompra);
        List<String> listaTotalPagar = aplRelatorio.totalPagarFornecedor(mapaCompra);
        
        Collections.sort(listaTotalPagar);
        
        util.imprime(listaTotalPagar);
        
        
        assertNotNull(listaTotalPagar);
        
    }
    
    @Test
    public void testTotalReceberPorCliente() {
       List<String> listaArquivo = util.importar(arquivoVenda);
        aplVenda = new AplVenda(aplCliente.cadastroCliente(arquivoCliente)
                                  , aplProduto.cadastroProduto(arquivoProduto));
        List<Venda> listaTotalReceber = aplVenda.cadastroVenda(arquivoVenda);
        List<String> listaTotalPagar = aplRelatorio.totalReceberPorCliente(listaTotalReceber);
        
        Collections.sort(listaTotalPagar);
        
        util.imprime(listaTotalPagar);
        
        
        assertNotNull(listaTotalPagar);
        
    }
    
}
