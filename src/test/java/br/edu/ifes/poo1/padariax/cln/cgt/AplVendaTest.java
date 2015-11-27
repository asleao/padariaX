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
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author aleao
 */
public class AplVendaTest {
     private Utilitario util;
    private Arquivo arquivo;
    private AplVenda aplVenda;
    private AplProduto aplProduto;
    private AplCliente aplCliente;    
    private Arquivo arquivoCliente;
    private Arquivo arquivoProduto;
    private Arquivo arquivoVenda;

    @Before
    public void setUp() {
        this.arquivoCliente = new Arquivo("./src/test/java/br/edu/ifes/poo1/padariax/arquivos/teste_1/", "fornecedores.csv");
        this.arquivoProduto = new Arquivo("./src/test/java/br/edu/ifes/poo1/padariax/arquivos/teste_1/", "produtos.csv");
        this.arquivoVenda = new Arquivo("./src/test/java/br/edu/ifes/poo1/padariax/arquivos/teste_1/", "vendas.csv");
        this.util = new Utilitario();
        this.aplCliente = new AplCliente();
        this.aplProduto = new AplProduto();
    }
    
    @Test
    public void testCadastroVenda() { 
        List<String> listaArquivo = util.importar(arquivoVenda);
        
        aplVenda = new AplVenda(aplCliente.cadastroCliente(arquivoCliente),
                                aplProduto.cadastroProduto(arquivoProduto));
        
        Map compraFiado = aplVenda.cadastroVendaFiado(arquivoVenda);
        List<Venda> vendaNormal = aplVenda.cadastroVendaNaoFiado(arquivoVenda);
        
        List<Venda> todasVendas = aplVenda.vendasCadastradas(compraFiado, vendaNormal);
        
        util.imprime(todasVendas);
        
        Assert.assertNotNull(todasVendas);
        
    }
   
    
}
