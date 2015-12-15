/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifes.poo1.padariax.cln.cdp;

/**
 *
 * @author aleao
 */
public class Item {
    private Produto produto;
    private int quantidade;
    
    public Item(){
    
    }

    public Item(Produto produto, int quantidade) {
        this.produto = produto;
        this.quantidade = quantidade;
    }
    

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
    
    /**
     * Função responsável por retornar o valor pago pelo produto.
     * Ela multiplica a quantidade pelo seu valor de custo.
     *      * 
     * @return valorItem.
     */
    public double valorCompraItem(){               
        return  this.getQuantidade()*this.getProduto().getValorCusto();
    } 
    /**
     * Função responsável por retornar o valor de venda do produto.
     * Ela multiplica a quantidade pelo seu valor de venda.
     * @return 
     */
    public double valorVendaItem(){          
        return  this.getQuantidade()*this.produto.valorVenda();
    } 
}
