/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifes.poo1.padariax.cln.cgt;

import br.edu.ifes.poo1.padariax.cln.cdp.Arquivo;
import br.edu.ifes.poo1.padariax.cln.cdp.Produto;
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
public class AplProduto {

    private Utilitario aplArquivo;
    private Map mapaProduto;
    private Produto produto;

    public AplProduto() {
        this.aplArquivo = new Utilitario();
        this.mapaProduto = new HashMap();
    }

    /**
     * Função responsável por transformar as linhas lidas do arquivo em um Map
     * de Produto.
     *
     * @param file - caminnho do arquivo
     * @return listaFornecedor - Map de Fornecedores
     * @throws java.io.IOException
     */
    public Map cadastroProduto(Arquivo file) throws IOException {
        List<String> listaImportada = aplArquivo.importar(file);

        for (String linha : listaImportada) {
            try (Scanner sc = new Scanner(linha)) {
                sc.useDelimiter(";");
                produto = criaProduto(sc);
                mapaProduto.put(produto.getCodigo(), produto);
            }
        }
        return mapaProduto;
    }

    /**
     * Função responsável por criar um objeto Produto.
     *
     * @param linha
     * @return
     */
    private Produto criaProduto(Scanner sc) {
        Produto produtoLocal = new Produto();

        produtoLocal.setCodigo(Integer.parseInt(sc.next()));
        produtoLocal.setDescricao(sc.next());
        produtoLocal.setEstoqueMinimo(Integer.parseInt(sc.next()));
        produtoLocal.setEstoqueAtual(Integer.parseInt(sc.next()));
        produtoLocal.setValorCusto(Double.parseDouble(sc.next().replace(",", ".")));
        produtoLocal.setPercentualLucro(Integer.parseInt(sc.next()));

        return produtoLocal;
    }
}
