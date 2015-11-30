/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifes.poo1.padariax.cln.cgt;

import br.edu.ifes.poo1.padariax.cln.cdp.Cliente;
import br.edu.ifes.poo1.padariax.cln.cdp.Compra;
import br.edu.ifes.poo1.padariax.cln.cdp.MeioPagamento;
import br.edu.ifes.poo1.padariax.cln.cdp.Produto;
import br.edu.ifes.poo1.padariax.cln.cdp.Venda;
import br.edu.ifes.poo1.padariax.cln.cdp.relatorios.APagarFornecedor;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 *
 * @author aleao
 */
public class AplRelatorio {
    
    private AplVenda aplVenda;
    private AplCompra aplCompra;

    public AplRelatorio() {
        this.aplVenda = new AplVenda();
        this.aplCompra = new AplCompra();
    }

    /**
     *
     * @param mapaCompras
     * @return
     */
    public List<String> aPagarFornecedor(Map mapaCompras) {
        List<APagarFornecedor> listaAPagar = new ArrayList();
        
        List<Compra> listaCompras = new ArrayList(mapaCompras.values());

        for (Compra compra : listaCompras) {
            listaAPagar.add(new APagarFornecedor(compra.getFornecedor(),compra.valorPago()));
        }
        
        Collections.sort(listaAPagar);
        
        List<String> listaTotalPagar = new ArrayList(listaAPagar);
        
        return listaTotalPagar;
    }

    public List<String> aReceberPorCliente(List<Venda> listaVendas,List<Cliente> listaClientes) {
        List<String> listaTotalReceber = new ArrayList();

        for (Cliente cliente : listaClientes) {
            listaTotalReceber.add(cliente.toString()+";"+aplVenda.retornaValorAReceber(listaVendas, cliente));
        }

        return listaTotalReceber;
    }

    /**
     * Utilizar um for na lista de todos os produtos existentes e multiplicar
     * pela quantidade vendida do produto. O lucro e o valor total vendido menos
     * o valor total de custo do produto.
     */
    public List<String> vendasLucroPorProduto(List<Venda> listaVendas, List<Produto> listaProduto) {
        List<String> listaLucro = new ArrayList();

        for (Produto produto : listaProduto) {
            int quantidadeVendida = aplVenda.retornaQuantidadeProdutoVendida(listaVendas, produto);
            double receitaBruta = quantidadeVendida * produto.valorVenda();
            double lucro = receitaBruta - (quantidadeVendida * produto.getValorCusto());

            listaLucro.add(produto.toString() + "; R$" + new BigDecimal(receitaBruta).setScale(2, BigDecimal.ROUND_DOWN)
                    + "; R$" + new BigDecimal(lucro).setScale(2, BigDecimal.ROUND_DOWN));
        }

        return listaLucro;
    }

    public List<String> vendasLucroPorMeioPagamento(List<Venda> listaVendas) {
        List<String> listaLucro = new ArrayList();

        for (MeioPagamento meio : MeioPagamento.values()) {
            listaLucro.add(meio + "; R$" + new BigDecimal(aplVenda.receitaBrutaPorMeioPagamento(listaVendas, meio)).setScale(2, BigDecimal.ROUND_DOWN)
                    + "; R$" + new BigDecimal(aplVenda.lucroPorMeioPagamento(listaVendas, meio)).setScale(2, BigDecimal.ROUND_DOWN));
        }

        return listaLucro;
    }

    /**
     * Imprime balanco do estoque mensal
     */
    public List<String> balancoMensal(List<Venda> listaVendas, List<Compra> listaCompras, List<Produto> listaProduto) {
        List<String> listaBalanco = new ArrayList();

        for (Produto produto : listaProduto) {
            String observacoes = "";

            int quantidadeVendida = aplVenda.retornaQuantidadeProdutoVendida(listaVendas, produto);
            int quantidadeComprada = aplCompra.retornaQuantidadeProdutoComprada(listaCompras, produto);

            produto.setEstoqueAtual(produto.getEstoqueAtual() + quantidadeComprada - quantidadeVendida);

            if (produto.getEstoqueAtual() < produto.getEstoqueMinimo()) {
                observacoes = "COMPRAR MAIS";
            }

            listaBalanco.add(produto.getCodigo() + ";" + produto.getDescricao() + ";"
                    + produto.getEstoqueAtual() + ";" + observacoes);
        }

        return listaBalanco;
    }
}
