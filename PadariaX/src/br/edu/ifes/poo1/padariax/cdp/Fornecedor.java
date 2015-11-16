/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifes.poo1.padariax.cdp;

import java.util.Date;

/**
 *
 * @author aleao
 */
public class Fornecedor extends PessoaJuridica{
    private String pessoaContato;

    public Fornecedor(){
    }
    
    public Fornecedor(String pessoaContato, String cnpj, int inscricaoEstadual, int codigo, String nome, String endereco, String telefone, Date dataCadastro) {
        super(cnpj, inscricaoEstadual, codigo, nome, endereco, telefone, dataCadastro);
        this.pessoaContato = pessoaContato;
    }

    public String getPessoaContato() {
        return pessoaContato;
    }

    public void setPessoaContato(String pessoaContato) {
        this.pessoaContato = pessoaContato;
    }
    
}
