/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifes.poo1.padariax.cln.cdp;

import java.util.Date;

/**
 *
 * @author aleao
 */
public abstract class Cliente extends Pessoa{
    
    private Date dataCadastro;
    private TipoCliente tipo;
    
    
    public Cliente(){        
    }

    public Cliente( int codigo, String nome, String endereco, String telefone, Date dataCadastro, TipoCliente tipo) {
        super(codigo, nome, endereco, telefone);
        this.dataCadastro = dataCadastro;
        this.tipo = tipo;
    }

    
    

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }
    
}
