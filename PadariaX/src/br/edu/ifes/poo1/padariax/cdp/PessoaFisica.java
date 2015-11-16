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
public class PessoaFisica extends Cliente{
    private String cpf;

    
    public PessoaFisica(){
    }
    
    
    public PessoaFisica(String cpf, int codigo, String nome, String endereco, String telefone, Date dataCadastro) {
        super(codigo, nome, endereco, telefone, dataCadastro);
        this.cpf = cpf;
    }

 
    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}
