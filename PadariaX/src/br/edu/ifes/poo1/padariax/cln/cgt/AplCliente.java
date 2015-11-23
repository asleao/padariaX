/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifes.poo1.padariax.cln.cgt;

import br.edu.ifes.poo1.padariax.cln.cdp.Arquivo;
import br.edu.ifes.poo1.padariax.cln.cdp.Cliente;
import br.edu.ifes.poo1.padariax.cln.cdp.PessoaFisica;
import br.edu.ifes.poo1.padariax.cln.cdp.PessoaJuridica;
import br.edu.ifes.poo1.padariax.cln.cdp.TipoCliente;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author aleao
 */
public class AplCliente extends AplArquivo {

    private AplArquivo aplArquivo;

    public AplCliente() {
        this.aplArquivo = new AplArquivo();
    }

    /**
     * Função responsável por transformar as linhas lidas
     * do arquivo em uma lista de Clientes. Esta lista possui
     * tanto clientes PessoaFisica quanto PessoaJurídica.
     * @param file - Arquivo
     * @return listaCliente - lista de clientes
     * @throws ParseException 
     */
    public List<Cliente> cadastroCliente(Arquivo file) throws ParseException {
        List<Cliente> listaCliente = new ArrayList<>();
        List<String> listaImportada = aplArquivo.importar(file);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        for (String linha : listaImportada) {
            Scanner sc = new Scanner(linha);
            sc.useDelimiter(";");            
            
            Pattern pattern = Pattern.compile("(\\;)F(\\;)");
            Matcher matcher = pattern.matcher(linha);

            if (matcher.find()) {
                PessoaFisica pessoaFisica = new PessoaFisica();

                pessoaFisica.setCodigo(Integer.parseInt(sc.next()));
                pessoaFisica.setNome(sc.next());
                pessoaFisica.setEndereco(sc.next());
                pessoaFisica.setTelefone(sc.next());
                pessoaFisica.setDataCadastro(sdf.parse(sc.next()));
                pessoaFisica.setTipo(TipoCliente.valueOf(sc.next()));
                pessoaFisica.setCpf(sc.next());

                listaCliente.add(pessoaFisica);
            } else{                
                PessoaJuridica pessoaJuridica = new PessoaJuridica();

                pessoaJuridica.setCodigo(Integer.parseInt(sc.next()));
                pessoaJuridica.setNome(sc.next());
                pessoaJuridica.setEndereco(sc.next());
                pessoaJuridica.setTelefone(sc.next());
                pessoaJuridica.setDataCadastro(sdf.parse(sc.next()));
                pessoaJuridica.setTipo(TipoCliente.valueOf(sc.next()));
                pessoaJuridica.setCnpj(sc.next());
                pessoaJuridica.setInscricaoEstadual(Integer.parseInt(sc.next()));

                listaCliente.add(pessoaJuridica);
            }
        }
        return listaCliente;
    }

}
