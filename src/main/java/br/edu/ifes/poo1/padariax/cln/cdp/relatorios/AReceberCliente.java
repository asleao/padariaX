/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifes.poo1.padariax.cln.cdp.relatorios;

import br.edu.ifes.poo1.padariax.cln.cdp.Cliente;
import br.edu.ifes.poo1.padariax.cln.cdp.Fornecedor;
import java.math.BigDecimal;

/**
 *
 * @author aleao
 */
public class AReceberCliente implements Comparable<AReceberCliente> {

    private Cliente cliente;
    private BigDecimal totalPagar;

    public AReceberCliente(Cliente cliente, BigDecimal totalPagar) {
        this.cliente = cliente;
        this.totalPagar = totalPagar;
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
        return this.cliente.getNome().compareTo(((AReceberCliente) obj).getCliente().getNome());
    }

    @Override
    public String toString() {
        return this.cliente.toString() + ";R$ " + this.totalPagar.toString().replace(".", ",");
    }
}
