/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifes.poo1.padariax.cln.cgt;

import br.edu.ifes.poo1.padariax.cln.cdp.Arquivo;
import br.edu.ifes.poo1.padariax.cln.cdp.Compra;
import br.edu.ifes.poo1.padariax.cln.util.Utilitario;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import static org.junit.Assert.assertEquals;
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
        this.arquivoFornecedor = new Arquivo("./src/test/java/br/edu/ifes/poo1/padariax/arquivos/teste_1/", "fornecedores.csv");
        this.arquivoProduto = new Arquivo("./src/test/java/br/edu/ifes/poo1/padariax/arquivos/teste_1/", "produtos.csv");
        this.arquivoCompra = new Arquivo("./src/test/java/br/edu/ifes/poo1/padariax/arquivos/teste_1/", "compras.csv");
        this.util = new Utilitario();
        this.aplFornecedor = new AplFornecedor();
        this.aplProduto = new AplProduto();
    }

    @Test
    public void testCadastroCompra() {      
        List<String> listaArquivo = util.importar(arquivoCompra);
        aplCompra = new AplCompra(aplFornecedor.cadastroFornecedor(arquivoFornecedor)
                                  , aplProduto.cadastroProduto(arquivoProduto));
        Map mapaCompra = aplCompra.cadastroCompra(arquivoCompra);
        List<Compra> listaCompra = new ArrayList(mapaCompra.values());
        
        Collections.sort(listaCompra, new Compra());
        
        util.imprime(listaCompra);
        
        assertEquals(mapaCompra.size(), listaArquivo.size());
        
        
    }
}
