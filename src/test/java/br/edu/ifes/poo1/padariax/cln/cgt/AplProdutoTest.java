/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifes.poo1.padariax.cln.cgt;

import br.edu.ifes.poo1.padariax.cln.cdp.Arquivo;
import br.edu.ifes.poo1.padariax.cln.cdp.Produto;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author aleao
 */
public class AplProdutoTest {

    private AplProduto aplProduto;
    private AplArquivo aplArquivo;
    private Arquivo arquivo;

    @Before
    public void setUp() {
        this.arquivo = new Arquivo("/home/aleao/Dropbox/IFES/Materias/POO1/2015-2/Trabalho Pratico/POO1_Trab1_Testes/01/in/", "produtos.csv");
        aplArquivo = new AplArquivo();
        aplProduto = new AplProduto();
    }

    @Test
    public void testCadastroProduto() {
        List<String> listaArquivo = aplArquivo.importar(arquivo);
        List<Produto> listaProduto = aplProduto.cadastroProduto(arquivo);
        
        aplProduto.imprimeProduto(listaProduto);

        assertEquals(listaProduto.size(), listaArquivo.size());
    }
    
}
