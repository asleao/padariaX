/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifes.poo1.padariax.cln.cdp.relatorios;

import br.edu.ifes.poo1.padariax.cln.cdp.Cliente;
import java.math.BigDecimal;

/**
 *
 * @author aleao
 */
public class AReceberCliente implements Comparable<AReceberCliente> {

    private String cabecalho;
    private Cliente cliente;
    private BigDecimal totalPagar;

    public AReceberCliente() {
        this.cabecalho = "Cliente;Tipo;CPF/CNPJ;Telefone;Data de cadastro;Total a pagar";
    }

    public AReceberCliente(Cliente cliente, BigDecimal totalPagar) {
        this.cliente = cliente;
        this.totalPagar = totalPagar;
    }

    public String getCabecalho() {
        return cabecalho;
    }

    public void setCabecalho(String cabecalho) {
        this.cabecalho = cabecalho;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public BigDecimal getTotalPagar() {
        return totalPagar;
    }

    public void setTotalPagar(BigDecimal totalPagar) {
        this.totalPagar = totalPagar;
    }

    @Override
    public int compareTo(AReceberCliente obj) {
        return this.cliente.getNome().compareTo(obj.getCliente().getNome());
    }

    @Override
    public String toString() {
        return this.cliente.toString() + ";R$ " + this.totalPagar.setScale(2, BigDecimal.ROUND_UP).toString().replace(".", ",");
    }
}
