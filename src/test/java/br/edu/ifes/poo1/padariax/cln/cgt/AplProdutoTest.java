/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifes.poo1.padariax.cln.cgt;

import br.edu.ifes.poo1.padariax.cln.cdp.Arquivo;
import br.edu.ifes.poo1.padariax.cln.cdp.Produto;
import br.edu.ifes.poo1.padariax.cln.util.Utilitario;
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
public class AplProdutoTest {

    private AplProduto aplProduto;
    private Utilitario util;
    private Arquivo arquivo;

    @Before
    public void setUp() {
        this.arquivo = new Arquivo("./src/test/java/br/edu/ifes/poo1/padariax/arquivos/teste_1/", "produtos.csv");
        util = new Utilitario();
        aplProduto = new AplProduto();
    }

    @Test
    public void testCadastroProduto() {
        List<String> listaArquivo = util.importar(arquivo);
        Map mapaProduto = aplProduto.cadastroProduto(arquivo);
        
        List<Produto> listaProduto = ordenaMap(mapaProduto);
        
        util.imprime(listaProduto);

        assertEquals(mapaProduto.size(), listaArquivo.size());
    }

    private List<Produto> ordenaMap(Map mapaProduto) {
        List<Produto> listaProduto = new ArrayList(mapaProduto.values());
//        Collections.sort(listaProduto,new Produto());
        return listaProduto;
    }

}
