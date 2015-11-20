/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifes.poo1.padariax.cln.cdp;

import java.util.Date;
import java.util.List;

/**
 *
 * @author aleao
 */
public class Compra {
    private int notaFiscal;
    private Fornecedor fornecedor;
    private Date dataCompra;
    private int quantidade;    
    private List<CompraItens> listaItens;

    public Compra(){
    
    }
    
    public Compra(int notaFiscal, Fornecedor fornecedor, Date dataCompra, int quantidade, List<CompraItens> listaItens) {
        this.notaFiscal = notaFiscal;
        this.fornecedor = fornecedor;
        this.dataCompra = dataCompra;
        this.quantidade = quantidade;
        this.listaItens = listaItens;
    }

    public int getNotaFiscal() {
        return notaFiscal;
    }

    public void setNotaFiscal(int notaFiscal) {
        this.notaFiscal = notaFiscal;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    public Date getDataCompra() {
        return dataCompra;
    }

    public void setDataCompra(Date dataCompra) {
        this.dataCompra = dataCompra;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public List<CompraItens> getListaItens() {
        return listaItens;
    }

    public void setListaItens(List<CompraItens> listaItens) {
        this.listaItens = listaItens;
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
        
        for(CompraItens compra: this.listaItens){
            valorPago += compra.getQuantidade()*compra.getProduto().getValorCusto();
        }
        return valorPago;
    }    
    
}
