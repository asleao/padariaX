/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifes.poo1.padariax.cdp;

import java.util.Date;
import java.util.List;

/**
 *
 * @author aleao
 */
public class Venda {
    private Date dataVenda;
    private int quantidade;
    private Cliente cliente;
    private List<Produto> listaProduto;
    private MeioPagamento meioPagamento;
    
    public Venda(){
    }

    public Venda(Date dataVenda, int quantidade, Cliente cliente, List<Produto> listaProduto, MeioPagamento meioPagamento) {
        this.dataVenda = dataVenda;
        this.quantidade = quantidade;
        this.cliente = cliente;
        this.listaProduto = listaProduto;
        this.meioPagamento = meioPagamento;
    }
    
    

    public Date getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(Date dataVenda) {
        this.dataVenda = dataVenda;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<Produto> getListaProduto() {
        return listaProduto;
    }

    public void setListaProduto(List<Produto> listaProduto) {
        this.listaProduto = listaProduto;
    }

    public MeioPagamento getMeioPagamento() {
        return meioPagamento;
    }

    public void setMeioPagamento(MeioPagamento meioPagamento) {
        this.meioPagamento = meioPagamento;
    }
    
    
}
