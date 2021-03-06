/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifes.poo1.padariax.cln.cgt;

import br.edu.ifes.poo1.padariax.cln.cdp.Cliente;
import br.edu.ifes.poo1.padariax.cln.cdp.Compra;
import br.edu.ifes.poo1.padariax.cln.cdp.Fornecedor;
import br.edu.ifes.poo1.padariax.cln.cdp.MeioPagamento;
import br.edu.ifes.poo1.padariax.cln.cdp.Produto;
import br.edu.ifes.poo1.padariax.cln.cdp.Venda;
import br.edu.ifes.poo1.padariax.cln.cdp.relatorios.APagarFornecedor;
import br.edu.ifes.poo1.padariax.cln.cdp.relatorios.AReceberCliente;
import br.edu.ifes.poo1.padariax.cln.cdp.relatorios.BalancoMensal;
import br.edu.ifes.poo1.padariax.cln.cdp.relatorios.VendasLucroMeioPagamento;
import br.edu.ifes.poo1.padariax.cln.cdp.relatorios.VendasLucroProduto;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

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
     * Função responsável por criar ler uma lista de compras e listar o total a
     * ser pago agrupando por fornecedor.
     *
     * @param listaCompras
     * @return listaTotalPagar
     */
    public List<String> aPagarFornecedor(List<Compra> listaCompras) {
        List<APagarFornecedor> listaAPagar = new ArrayList();
        HashMap<Fornecedor,BigDecimal> mapaFornecedor = new HashMap();
        
        for (Compra compra : listaCompras) {
            if (mapaFornecedor.containsKey(compra.getFornecedor())) {
                BigDecimal valorPago = mapaFornecedor.get(compra.getFornecedor());
                
                BigDecimal valorCompra = new BigDecimal(valorPago.doubleValue() +compra.valorPago().doubleValue());
                
                mapaFornecedor.put(compra.getFornecedor(), valorCompra);
            } else {
                mapaFornecedor.put(compra.getFornecedor(), compra.valorPago());
            }
        }

        for(Entry<Fornecedor,BigDecimal> entry:mapaFornecedor.entrySet()){
            listaAPagar.add(new APagarFornecedor(entry.getKey(),entry.getValue()));
        }
        
        Collections.sort(listaAPagar);

        List<String> listaTotalPagar = new ArrayList(listaAPagar);

        return listaTotalPagar;
    }

    /**
     * Função responsável por listar todos os clientes cadastrados, mostrando o
     * valor a ser recebido de cada um deles.
     *
     * @param listaVendas
     * @param listaClientes
     * @return listaTotalReceber
     */
    public List<String> aReceberPorCliente(List<Venda> listaVendas, List<Cliente> listaClientes) {

        List<AReceberCliente> listaReceber = new ArrayList();

        for (Cliente cliente : listaClientes) {
            listaReceber.add(new AReceberCliente(cliente, aplVenda.retornaValorAReceber(listaVendas, cliente)));
        }

        Collections.sort(listaReceber);

        List<String> listaTotalReceber = new ArrayList(listaReceber);
        return listaTotalReceber;
    }

    /**
     * Função responsável por mostrar para cada produto a relação da receita
     * bruta e lucro obtido em cada venda.
     *
     * @param listaVendas
     * @param listaProduto
     * @return listaLucro
     */
    public List<String> vendasLucroPorProduto(List<Venda> listaVendas, List<Produto> listaProduto) {

        List<VendasLucroProduto> listaVendaLucroProduto = new ArrayList();

        for (Produto produto : listaProduto) {
            int quantidadeVendida = aplVenda.retornaQuantidadeProdutoVendida(listaVendas, produto);
            double receitaBruta = quantidadeVendida * produto.valorVenda();
            double lucro = receitaBruta - (quantidadeVendida * produto.getValorCusto());

            listaVendaLucroProduto.add(new VendasLucroProduto(produto, receitaBruta, lucro));
        }

        Collections.sort(listaVendaLucroProduto);

        List<String> listaLucro = new ArrayList(listaVendaLucroProduto);

        return listaLucro;
    }

    /**
     * Função responsável por relacionar receita bruta e lucro obtido com as
     * vendas agrupando por meio de pagamento.
     *
     * @param listaVendas
     * @return
     */
    public List<String> vendasLucroPorMeioPagamento(List<Venda> listaVendas) {

        List<VendasLucroMeioPagamento> listaVendaLucroMeioPagamento = new ArrayList();

        for (MeioPagamento meio : MeioPagamento.values()) {
            listaVendaLucroMeioPagamento.add(new VendasLucroMeioPagamento(meio, aplVenda.receitaBrutaPorMeioPagamento(listaVendas, meio), aplVenda.lucroPorMeioPagamento(listaVendas, meio)));
        }

        Collections.sort(listaVendaLucroMeioPagamento);
        List<String> listaLucro = new ArrayList(listaVendaLucroMeioPagamento);
        return listaLucro;
    }

    /**
     * Função reponsável por realizar um balanço do estoque. Para cada produto,
     * ele mostra a quantidade restante no estoque, após as vendas dos mês, e
     * caso o produto fique abaixo da quantidade mínima estabelecida, o campo de
     * observações é preenchido com "COMPRAR MAIS".
     *
     * @param listaVendas
     * @param listaCompras
     * @param listaProduto
     * @return
     */
    public List<String> balancoMensal(List<Venda> listaVendas, List<Compra> listaCompras, List<Produto> listaProduto) {
        List<BalancoMensal> listaBalanco = new ArrayList();        

        for (Produto produto : listaProduto) {
            String observacoes = "";

            int quantidadeVendida = aplVenda.retornaQuantidadeProdutoVendida(listaVendas, produto);
            int quantidadeComprada = aplCompra.retornaQuantidadeProdutoComprada(listaCompras, produto);

            produto.setEstoqueAtual(produto.getEstoqueAtual() + quantidadeComprada - quantidadeVendida);

            if (produto.getEstoqueAtual() < produto.getEstoqueMinimo()) {
                observacoes = "COMPRAR MAIS";
            }

            listaBalanco.add(new BalancoMensal(produto, observacoes));
        }

        Collections.sort(listaBalanco);
        List<String> listaEstoque = new ArrayList(listaBalanco);

        return listaEstoque;
    }
}
