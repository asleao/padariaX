/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifes.poo1.padariax.cln.cdp.relatorios;

import br.edu.ifes.poo1.padariax.cln.cdp.Fornecedor;
import java.math.BigDecimal;

/**
 *
 * @author aleao
 */
public class APagarFornecedor implements Comparable<APagarFornecedor> {

    private String cabecalho;
    private Fornecedor fornecedor;
    private BigDecimal totalPagar;

    public APagarFornecedor(){
        this.cabecalho = "Fornecedor;CNPJ;Pessoa de contato ;Telefone;Total a pagar";
    }
    public APagarFornecedor(Fornecedor fornecedor, BigDecimal totalPagar) {
        this.fornecedor = fornecedor;
        this.totalPagar = totalPagar;
    }

    public String getCabecalho() {
        return cabecalho;
    }

    public void setCabecalho(String cabecalho) {
        this.cabecalho = cabecalho;
    }

    
    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    public BigDecimal getTotalPagar() {
        return totalPagar;
    }

    public void setTotalPagar(BigDecimal totalPagar) {
        this.totalPagar = totalPagar;
    }

    @Override
    public int compareTo(APagarFornecedor obj) {
        return this.fornecedor.getNome().compareTo(((APagarFornecedor) obj).getFornecedor().getNome());
    }

    @Override
    public String toString() {
        return this.fornecedor.toString() + ";R$ " + this.totalPagar.setScale(2, BigDecimal.ROUND_DOWN).toString().replace(".", ",");
    }
}
