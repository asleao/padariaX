/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifes.poo1.padariax.cln.cdp.relatorios;

import br.edu.ifes.poo1.padariax.cln.cdp.MeioPagamento;
import java.math.BigDecimal;

/**
 *
 * @author aleao
 */
public class VendasLucroMeioPagamento implements Comparable<VendasLucroMeioPagamento> {

    private MeioPagamento meioPagamento;
    private Double receitaBruta;
    private Double lucro;

    public VendasLucroMeioPagamento() {

    }

    public VendasLucroMeioPagamento(MeioPagamento meioPagamento, Double receitaBruta, Double lucro) {
        this.meioPagamento = meioPagamento;
        this.receitaBruta = receitaBruta;
        this.lucro = lucro;
    }

    public MeioPagamento getMeioPagamento() {
        return meioPagamento;
    }

    public void setMeioPagamento(MeioPagamento meioPagamento) {
        this.meioPagamento = meioPagamento;
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
     * ele ordena pelo meio de pagamento de forma crescente.    
     *
     * @param venda
     * @return
     */
    @Override
    public int compareTo(VendasLucroMeioPagamento venda) {
        int resultado = Double.compare(venda.getLucro(), this.getLucro());

        if (resultado == 0) {
            resultado = venda.getMeioPagamento().compareTo(this.meioPagamento);
        } 

        return resultado;
    }

    @Override
    public String toString() {
        return this.meioPagamento + ";R$ " + new BigDecimal(this.receitaBruta).setScale(2, BigDecimal.ROUND_DOWN).toString().replace(".", ",")
                + ";R$ " + new BigDecimal(this.lucro).setScale(2, BigDecimal.ROUND_DOWN).toString().replace(".", ",");
    }
   
}
