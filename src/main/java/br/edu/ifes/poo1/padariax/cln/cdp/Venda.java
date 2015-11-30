/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifes.poo1.padariax.cln.cdp;

import br.edu.ifes.poo1.padariax.cln.cdp.interfaces.IConta;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 *
 * @author aleao
 */
public class Venda implements IConta, Serializable{

    private Date dataVenda;
    private Cliente cliente;
    private List<Item> listaItens;
    private MeioPagamento meioPagamento;

    public Venda() {
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
     * Função responsável por retornar o valor pago na venda. Ela busca todos os
     * itens vendidos e retorna a soma da quantidade multiplicada pelo valor de
     * custo do produto.
     *
     * @return valorPago.
     */
    @Override
    public BigDecimal valorPago() {
        Double valorPago = new Double(new Long(0));

        for (Item item : this.listaItens) {
            valorPago += item.valorVendaItem();
        }

        return new BigDecimal(valorPago).setScale(2, BigDecimal.ROUND_DOWN);
    }

    /**
     * Função responsável por exportar as vendas feitas no formato definido. O
     * codigo do cliente estara preenchido somente quando o pagamento for do
     * tipo 'F' (Fiado), nos demais casos este campo estara vazio.
     *
     */
    public void exportaVendas() {
        Locale ptBR = new Locale("pt", "BR");
        DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM, ptBR);

        if (this.meioPagamento.equals(MeioPagamento.F)) {
            for (Item item : this.listaItens) {
                System.out.println(this.cliente.getCodigo() + ";" + df.format(this.dataVenda)
                        + ";" + item.getProduto().getCodigo()
                        + ";" + item.getQuantidade());
            }
        } else {
            for (Item item : this.listaItens) {
                System.out.println(";" + df.format(this.dataVenda)
                        + ";" + item.getProduto().getCodigo()
                        + ";" + item.getQuantidade());
            }
        }

    }      

    @Override
    public String toString() {
        Locale ptBR = new Locale("pt", "BR");
        DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM, ptBR);
        String toString = "";

        if (this.meioPagamento.equals(MeioPagamento.F)) {
            toString = this.cliente.getNome() + ";" + this.cliente.getTipo() + ";"
                    + this.cliente.getTipo() + ";" + this.cliente.getTelefone() + ";"
                    + df.format(this.cliente.getDataCadastro()) + ";"
                    + valorPago();
        }
        return toString;
    }    
   

}
