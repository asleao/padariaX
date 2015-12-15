/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifes.poo1.padariax.cln.cgt;

import br.edu.ifes.poo1.padariax.cln.cdp.Arquivo;
import br.edu.ifes.poo1.padariax.cln.cdp.Fornecedor;
import br.edu.ifes.poo1.padariax.cln.util.Utilitario;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author aleao
 */
public class AplFornecedor {

    private Utilitario aplArquivo;
    private Map mapaFornecedor;

    public AplFornecedor() {
        this.aplArquivo = new Utilitario();
        mapaFornecedor = new HashMap();
    }

    /**
     * Função responsável por transformar as linhas lidas do arquivo em Map de
     * Fornecedores.
     *
     * @param file - caminnho do arquivo
     * @return listaFornecedor - Lista de Fornecedores
     * @throws java.io.IOException
     */
    public Map cadastroFornecedor(Arquivo file) throws IOException {
        List<String> listaImportada = aplArquivo.importar(file);

        for (String linha : listaImportada) {
            try (Scanner sc = new Scanner(linha)) {
                sc.useDelimiter(";");
                Fornecedor fornecedor = criaFornecedor(sc);
                mapaFornecedor.put(fornecedor.getCodigo(), fornecedor);
            }
        }

        return mapaFornecedor;
    }

    /**
     * Função responsável por criar um objeto fornecedor.
     *
     * @param sc
     * @return
     */
    private Fornecedor criaFornecedor(Scanner sc) {
        Fornecedor fornecedor = new Fornecedor();

        fornecedor.setCodigo(Integer.parseInt(sc.next()));
        fornecedor.setNome(sc.next());
        fornecedor.setEndereco(sc.next());
        fornecedor.setTelefone(sc.next());
        fornecedor.setCnpj(sc.next());
        fornecedor.setPessoaContato(sc.next());

        return fornecedor;
    }

}
