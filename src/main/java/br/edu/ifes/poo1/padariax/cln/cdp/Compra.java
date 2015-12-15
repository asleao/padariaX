/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifes.poo1.padariax.cln.cdp;

import br.edu.ifes.poo1.padariax.cln.cdp.interfaces.IConta;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *
 * @author aleao
 */
public class Compra implements IConta {

    private int notaFiscal;
    private Fornecedor fornecedor;
    private Date dataCompra;
    private List<Item> listaItens;

    public Compra() {

    }

    public Compra(int notaFiscal, Fornecedor fornecedor, Date dataCompra, List<Item> listaItens) {
        this.notaFiscal = notaFiscal;
        this.fornecedor = fornecedor;
        this.dataCompra = dataCompra;
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

    public List<Item> getListaItens() {
        return listaItens;
    }

    public void setListaItens(List<Item> listaItens) {
        this.listaItens = listaItens;
    }

    /**
     * Função responsável por retornar o valor pago na nota fiscal. Ela busca
     * todos os itens adquiridos e retorna a soma da quantidade multiplicada
     * pelo valor de custo do produto.
     *
     * @return valorPago.
     */
    @Override
    public BigDecimal valorPago() {
        Double valorPago = new Double(new Long(0));       
        
        for (Item item : this.listaItens) {
            valorPago += item.valorCompraItem();
        }
              
        return new BigDecimal(valorPago).setScale(2, BigDecimal.ROUND_DOWN);
    }

    /**
     * Função responsável por exportar as compras feitas no formato definido.
     * 
     */
    public void exportaCompras() {
        for(Item item: this.listaItens){
            System.out.println(this.notaFiscal + ";" + this.fornecedor.getCodigo() 
                    + ";" + this.dataCompra+";"+item.getProduto().getCodigo()+";"
                    +item.getQuantidade());
        }    
    }
    

    @Override
    public String toString() {
        return  this.fornecedor.getNome() + ";" + this.fornecedor.getCnpj()+ ";"+
                this.fornecedor.getPessoaContato() + ";" + 
                this.fornecedor.getTelefone()+ ";" +"R$ "+valorPago();
    }
    
    
}
