/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifes.poo1.padariax.cln.cdp.relatorios;

import br.edu.ifes.poo1.padariax.cln.cdp.Produto;

/**
 *
 * @author aleao
 */
public class BalancoMensal implements Comparable<BalancoMensal> {

    private String cabecalho;
    private Produto produto;
    private String observacao;

    public BalancoMensal() {
        this.cabecalho = "Código;Produto;Quantidade em estoque;Observações";
    }

    public BalancoMensal(Produto produto, String observacao) {
        this.produto = produto;
        this.observacao = observacao;
    }

    public String getCabecalho() {
        return cabecalho;
    }

    public void setCabecalho(String cabecalho) {
        this.cabecalho = cabecalho;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    @Override
    public int compareTo(BalancoMensal balanco) {
        return this.produto.getDescricao().compareTo(balanco.getProduto().getDescricao());
    }

    @Override
    public String toString() {
        return produto.getCodigo() + ";" + produto.getDescricao() + ";"
                + produto.getEstoqueAtual() + ";" + observacao;
    }
}
