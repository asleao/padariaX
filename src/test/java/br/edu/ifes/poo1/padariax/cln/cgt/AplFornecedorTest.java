/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifes.poo1.padariax.cln.cgt;

import br.edu.ifes.poo1.padariax.cln.cdp.Arquivo;
import br.edu.ifes.poo1.padariax.cln.cdp.Fornecedor;
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
public class AplFornecedorTest {

    private Utilitario util;
    private Arquivo arquivo;
    private AplFornecedor aplFornecedor;

    @Before
    public void setUp() {
        this.arquivo = new Arquivo("./src/test/java/br/edu/ifes/poo1/padariax/arquivos/teste_1/", "fornecedores.csv");
        util = new Utilitario();
        aplFornecedor = new AplFornecedor();
    }

    @Test
    public void testCadastroFornecedor() {
        List<String> listaArquivo = util.importar(arquivo);
        Map mapaFornecedor = aplFornecedor.cadastroFornecedor(arquivo);

        List<Fornecedor> listaFornecedor = ordenaMap(mapaFornecedor);
        util.imprime(mapaFornecedor.values());

//        aplFornecedor.buscaFornecedor(1);
        assertEquals(mapaFornecedor.size(), listaArquivo.size());
    }

    private List<Fornecedor> ordenaMap(Map mapaFornecedor) {
        List<Fornecedor> listaFornecedor = new ArrayList(mapaFornecedor.values());
        Collections.sort(listaFornecedor);
        return listaFornecedor;
    }
}
