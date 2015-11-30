/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifes.poo1.padariax.cln.cdp;

import br.edu.ifes.poo1.padariax.cln.cdp.interfaces.IPessoaFisica;
import java.util.Date;

/**
 *
 * @author aleao
 */
public class PessoaFisica extends Cliente implements IPessoaFisica{
    private String cpf;

    
    public PessoaFisica(){
    }

    public PessoaFisica(int codigo, String nome, String endereco, String telefone, Date dataCadastro, String cpf) {
        super(codigo, nome, endereco, telefone, dataCadastro, TipoCliente.F);
        this.cpf = cpf;
    }
    @Override
    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    @Override
    public String toString() {
        return this.cpf;
    }

    @Override
    public int compareTo(Object o) {
       return cpf.compareTo(((PessoaFisica)o).cpf);     
    }
    
    
}
