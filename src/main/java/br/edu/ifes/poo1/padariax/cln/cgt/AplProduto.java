/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifes.poo1.padariax.cln.cgt;

import br.edu.ifes.poo1.padariax.cln.cdp.Arquivo;
import br.edu.ifes.poo1.padariax.cln.cdp.Produto;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author aleao
 */
public class AplProduto {

    private AplArquivo aplArquivo;

    public AplProduto() {
        this.aplArquivo = new AplArquivo();
    }

    /**
     * Função responsável por transformar as linhas lidas do arquivo em uma
     * lista de Produto.
     *
     * @param file - caminnho do arquivo
     * @return listaFornecedor - Lista de Fornecedores
     */
    public List<Produto> cadastroProduto(Arquivo file) {
        List<Produto> listaProduto = new ArrayList();
        List<String> listaImportada = aplArquivo.importar(file);

        for (String linha : listaImportada) {
            listaProduto.add(criaFornecedor(linha));
        }
        return listaProduto;
    }

    private Produto criaFornecedor(String linha) {
        Produto produto = new Produto();

        try {
            Scanner sc = new Scanner(linha);
            sc.useDelimiter(";");
            
            produto.setCodigo(Integer.parseInt(sc.next()));
            produto.setDescricao(sc.next());
            produto.setEstoqueMinimo(Integer.parseInt(sc.next()));
            produto.setEstoqueAtual(Integer.parseInt(sc.next()));            
            produto.setValorCusto(Double.parseDouble(sc.next().replace(",",".")));
            produto.setPercentualLucro(Integer.parseInt(sc.next()));
            
            
        } catch (Exception e) {
        }

        return produto;
    }
    
    public void imprimeProduto(List<Produto> listaProduto) {
        for(Produto produto:listaProduto){
            System.out.println(produto.toString());
        }
    }
}
