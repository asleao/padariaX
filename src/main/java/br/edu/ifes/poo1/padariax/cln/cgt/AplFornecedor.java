/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifes.poo1.padariax.cln.cgt;

import br.edu.ifes.poo1.padariax.cln.cdp.Arquivo;
import br.edu.ifes.poo1.padariax.cln.cdp.Fornecedor;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author aleao
 */
public class AplFornecedor {

    private AplArquivo aplArquivo;
    private List<Fornecedor> listaFornecedor;
    
    public AplFornecedor() {
        this.aplArquivo = new AplArquivo();
        listaFornecedor = new ArrayList();
    }

    /**
     * Função responsável por transformar as linhas lidas do arquivo em uma
     * lista de Fornecedores.
     * @param file - caminnho do arquivo
     * @return listaFornecedor - Lista de Fornecedores
     */
    public List<Fornecedor> cadastroFornecedor(Arquivo file) {        
        List<String> listaImportada = aplArquivo.importar(file);

        for (String linha : listaImportada) {
            listaFornecedor.add(criaFornecedor(linha));
        }

        return listaFornecedor;
    }

    private Fornecedor criaFornecedor(String linha) {
        Fornecedor fornecedor = new Fornecedor();

        try {
            Scanner sc = new Scanner(linha);
            sc.useDelimiter(";");

            fornecedor.setCodigo(Integer.parseInt(sc.next()));
            fornecedor.setNome(sc.next());
            fornecedor.setEndereco(sc.next());
            fornecedor.setTelefone(sc.next());
            fornecedor.setCnpj(sc.next());
            fornecedor.setPessoaContato(sc.next());
        } catch (Exception e) {
            e.printStackTrace();
        }        
        return fornecedor;
    }
    
    /**
     * Função responsável por buscar um fornecedor
     * a partir do seu código.
     * @param codigo 
     * @return Objeto Fornecedor
     */
    public Fornecedor buscaFornecedor(int codigo){
        if(listaFornecedor.contains(codigo)){
            System.out.println("existe");
        }
        return listaFornecedor.get(0);
    }
}
