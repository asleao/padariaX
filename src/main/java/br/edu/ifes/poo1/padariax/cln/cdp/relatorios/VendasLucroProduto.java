/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifes.poo1.padariax.cln.cdp.relatorios;

import br.edu.ifes.poo1.padariax.cln.cdp.Produto;
import java.math.BigDecimal;

/**
 *
 * @author aleao
 */
public class VendasLucroProduto implements Comparable<VendasLucroProduto> {

    private Produto produto;
    private Double receitaBruta;
    private Double lucro;

    public VendasLucroProduto() {

    }

    public VendasLucroProduto(Produto produto, Double receitaBruta, Double lucro) {
        this.produto = produto;
        this.receitaBruta = receitaBruta;
        this.lucro = lucro;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Double getReceitaBruta() {
        return receitaBruta;
    }

    public void setReceitaBruta(Double receitaBruta) {
        this.receitaBruta = receitaBruta;
    }

    public Double getLucro() {
        return lucro;
    }

    public void setLucro(Double lucro) {
        this.lucro = lucro;
    }

    /**
     * Este método compara o lucro em ordem decrescente. Caso eles sejam iguais,
     * ele ordena pelo código do produto de forma crescente.    
     *
     * @param venda
     * @return
     */
    @Override
    public int compareTo(VendasLucroProduto venda) {
        int resultado = Double.compare(venda.getLucro(), this.getLucro());

        if (resultado == 0) {
            resultado = Integer.compare(this.getProduto().getCodigo(), venda.produto.getCodigo());
        } 

        return resultado;
    }

    @Override
    public String toString() {
        return this.produto.toString() + ";R$ " + new BigDecimal(this.receitaBruta).setScale(2, BigDecimal.ROUND_DOWN).toString().replace(".", ",")
                + ";R$ " + new BigDecimal(this.lucro).setScale(2, BigDecimal.ROUND_DOWN).toString().replace(".", ",");
    }
   
}
