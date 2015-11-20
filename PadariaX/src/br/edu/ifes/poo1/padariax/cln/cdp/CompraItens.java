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
class CompraItens {
    private Produto produto;
    private int quantidade;
    
    public CompraItens(){
    
    }

    public CompraItens(Produto produto, int quantidade) {
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
    
    
}
