/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifes.poo1.padariax.cln.cdp;

import br.edu.ifes.poo1.padariax.cln.cdp.interfaces.IPessoaJuridica;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

/**
 *
 * @author aleao
 */
public class PessoaJuridica extends Cliente implements IPessoaJuridica{
    private String cnpj;
    private int inscricaoEstadual;


    public PessoaJuridica(){
    }

    public PessoaJuridica(int codigo, String nome, String endereco, String telefone, Date dataCadastro, String cnpj, int inscricaoEstadual) {
        super(codigo, nome, endereco, telefone, dataCadastro, TipoCliente.J);
        this.cnpj = cnpj;
        this.inscricaoEstadual = inscricaoEstadual;
    }
        
     
    @Override
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

    @Override
    public String toString() {
        Locale ptBR = new Locale("pt", "BR");
        DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM, ptBR);
        return super.toString() + ";" + this.cnpj+ ";" + super.getTelefone() + ";" +df.format(super.getDataCadastro());
    }
    
    @Override
    public int compareTo(Object o) {
       return super.getNome().compareTo(((PessoaJuridica)o).getNome());     
    }
    
}
