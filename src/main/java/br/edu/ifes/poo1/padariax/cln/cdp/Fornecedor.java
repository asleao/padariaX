/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifes.poo1.padariax.cln.cdp;

import br.edu.ifes.poo1.padariax.cln.cdp.interfaces.IPessoaJuridica;
import java.io.Serializable;

/**
 *
 * @author aleao
 */
public class Fornecedor extends Pessoa implements IPessoaJuridica,Serializable,Comparable{
    private String pessoaContato;
    private String cnpj;

    public Fornecedor(){
    }

    public Fornecedor(String pessoaContato, String cnpj, int codigo, String nome, String endereco, String telefone) {
        super(codigo, nome, endereco, telefone);
        this.pessoaContato = pessoaContato;
        this.cnpj = cnpj;
    }

    @Override
    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }       

    public String getPessoaContato() {
        return pessoaContato;
    }

    public void setPessoaContato(String pessoaContato) {
        this.pessoaContato = pessoaContato;
    }

    @Override
    public String toString() {
        return super.toString()+ this.cnpj+ ";" + this.pessoaContato;
    }
    
    @Override
    public int compareTo(Object o) {
       return super.getNome().compareTo(((Fornecedor)o).getNome());     
    }
    
}
