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
public class PessoaJuridica extends Cliente{
    private String cnpj;
    private int inscricaoEstadual;


    public PessoaJuridica(){
    }
    
    public PessoaJuridica(String cnpj, int inscricaoEstadual, int codigo, String nome, String endereco, String telefone, Date dataCadastro) {
        super(codigo, nome, endereco, telefone, dataCadastro);
        this.cnpj = cnpj;
        this.inscricaoEstadual = inscricaoEstadual;
    }
     
    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public int getInscricaoEstadual() {
        return inscricaoEstadual;
    }

    public void setInscricaoEstadual(int inscricaoEstadual) {
        this.inscricaoEstadual = inscricaoEstadual;
    }
    
}
