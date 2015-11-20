/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifes.poo1.padariax.cln.cdp;

import br.edu.ifes.poo1.padariax.cln.cdp.interfaces.IConta;
import java.util.Date;
import java.util.List;

/**
 *
 * @author aleao
 */
public class Venda implements IConta{
    private Date dataVenda;    
    private Cliente cliente;
    private List<Item> listaItens;
    private MeioPagamento meioPagamento;
    
    public Venda(){
    }

    public Venda(Date dataVenda, Cliente cliente, MeioPagamento meioPagamento) {
        this.dataVenda = dataVenda;        
        this.cliente = cliente;
        this.meioPagamento = meioPagamento;
    }

    public List<Item> getListaItens() {
        return listaItens;
    }

    public void setListaItens(List<Item> listaItens) {
        this.listaItens = listaItens;
    }
   
    public Date getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(Date dataVenda) {
        this.dataVenda = dataVenda;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    
    public MeioPagamento getMeioPagamento() {
        return meioPagamento;
    }

    public void setMeioPagamento(MeioPagamento meioPagamento) {
        this.meioPagamento = meioPagamento;
    }
    
    /**
     * Função responsável por retornar o valor pago na nota fiscal.
     * Ela busca todos os itens adquiridos e retorna a soma da quantidade
     * multiplicada pelo valor de custo do produto.
     * 
     * @return valorPago.
     */
    public double valorPago(){
        double valorPago = 0;
        
        for(Item item: this.listaItens){
            valorPago += item.valorItem();
        }
        return valorPago;
    }  
    
}
