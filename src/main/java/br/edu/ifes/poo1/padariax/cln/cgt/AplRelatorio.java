/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifes.poo1.padariax.cln.cgt;

import br.edu.ifes.poo1.padariax.cln.cdp.Compra;
import br.edu.ifes.poo1.padariax.cln.cdp.MeioPagamento;
import br.edu.ifes.poo1.padariax.cln.cdp.Produto;
import br.edu.ifes.poo1.padariax.cln.cdp.Venda;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author aleao
 */
public class AplRelatorio {

    private AplFornecedor aplFornecedor;
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
    public List<String> totalPagarFornecedor(Map mapaCompras) {
        List<String> listaTotalPagar = new ArrayList();
        List<Compra> listaCompras = new ArrayList(mapaCompras.values());

        for (Compra compra : listaCompras) {
            listaTotalPagar.add(compra.toString());
        }

        return listaTotalPagar;
    }

    public List<String> totalReceberPorCliente(List<Venda> listaVendas) {
        List<String> listaTotalReceber = new ArrayList();

        for (Venda venda : listaVendas) {
            listaTotalReceber.add(venda.toString());
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
            int quantidadeVendida = 0;
            int quantidadeComprada = 0;
            quantidadeVendida = aplVenda.retornaQuantidadeProdutoVendida(listaVendas, produto);
            quantidadeComprada = aplCompra.retornaQuantidadeProdutoComprada(listaCompras, produto);

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
