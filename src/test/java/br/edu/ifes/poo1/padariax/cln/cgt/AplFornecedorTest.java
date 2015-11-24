/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifes.poo1.padariax.cln.cgt;

import br.edu.ifes.poo1.padariax.cln.cdp.Arquivo;
import br.edu.ifes.poo1.padariax.cln.cdp.Fornecedor;
import br.edu.ifes.poo1.padariax.cln.util.Utilitario;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
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
//        this.arquivo = new Arquivo("/home/aleao/Dropbox/IFES/Materias/POO1/2015-2/Trabalho Pratico/POO1_Trab1_Testes/01/in/", "fornecedores.csv");
        this.arquivo = new Arquivo("/media/aleao/Dados/Dropbox/IFES/Materias/POO1/2015-2/Trabalho Pratico/POO1_Trab1_Testes/01/in/", "fornecedores.csv");
        util = new Utilitario();
        aplFornecedor = new AplFornecedor();
    }

    @Test
    public void testCadastroFornecedor() {
        List<String> listaArquivo = util.importar(arquivo);
        List<Fornecedor> listaFornecedor = aplFornecedor.cadastroFornecedor(arquivo);

        aplFornecedor.imprimeFornecedor(listaFornecedor);

        aplFornecedor.buscaFornecedor(1);

        assertEquals(listaFornecedor.size(), listaArquivo.size());
    }

}
