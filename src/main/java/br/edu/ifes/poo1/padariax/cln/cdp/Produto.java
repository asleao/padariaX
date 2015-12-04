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
public class Produto {

    private int codigo;
    private String descricao;
    private int estoqueMinimo;
    private int estoqueAtual;
    private double valorCusto;
    private int percentualLucro;

    public Produto() {
    }

    public Produto(int codigo, String descricao, int estoqueMinimo, int estoqueAtual, double valorCusto, int percentualLucro) {
        this.codigo = codigo;
        this.descricao = descricao;
        this.estoqueMinimo = estoqueMinimo;
        this.estoqueAtual = estoqueAtual;
        this.valorCusto = valorCusto;
        this.percentualLucro = percentualLucro;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getEstoqueMinimo() {
        return estoqueMinimo;
    }

    public void setEstoqueMinimo(int estoqueMinimo) {
        this.estoqueMinimo = estoqueMinimo;
    }

    public int getEstoqueAtual() {
        return estoqueAtual;
    }

    public void setEstoqueAtual(int estoqueAtual) {
        this.estoqueAtual = estoqueAtual;
    }

    public double getValorCusto() {
        return valorCusto;
    }

    public void setValorCusto(double valorCusto) {
        this.valorCusto = valorCusto;
    }

    public int getPercentualLucro() {
        return percentualLucro;
    }

    public void setPercentualLucro(int percentualLucro) {
        this.percentualLucro = percentualLucro;
    }

    public double valorVenda() {        
        return this.valorCusto * (1.0 + ((double)this.percentualLucro/100));
    }

    @Override
    public String toString() {
        return this.codigo + ";" + this.descricao ;
    }
}
