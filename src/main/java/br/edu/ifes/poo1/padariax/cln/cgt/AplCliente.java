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
import br.edu.ifes.poo1.padariax.cln.util.Utilitario;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author aleao
 */
public class AplCliente {

    private Utilitario util;
    private Map mapaCliente;
    private Cliente cliente;
    private DateFormat dateFormat;

    public AplCliente() {
        this.util = new Utilitario();        
        this.mapaCliente = new HashMap();   
        this.dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);
    }

    /**
     * Função responsável por transformar as linhas lidas do arquivo em uma
     * Map de Clientes. Este map possui tanto clientes PessoaFisica quanto
     * PessoaJurídica.
     *
     * @param file - Caminho do arquivo
     * @return listaCliente - mapa de clientes
     * @throws ParseException
     */
    public Map cadastroCliente(Arquivo file) {        
        List<String> listaImportada = util.importar(file);
//        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");


        for (String linha : listaImportada) {
            Scanner sc = new Scanner(linha);
            sc.useDelimiter(";");

            try {
                if (ehPessoaFisica(linha)) {
                    cliente = criaPessoaFisica(sc);
                    mapaCliente.put(cliente.getCodigo(),cliente);
                } else {
                    cliente = criaPessoaJuridica(sc);
                    mapaCliente.put(cliente.getCodigo(),cliente);
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return mapaCliente;
    }

    /**
     * Função responsável por setar os atributos de um objeto
     * PessoaJuridica que foram lidos da linha do arquivo.
     * @param sc
     * @param sdf
     * @return 
     */
    private PessoaJuridica criaPessoaJuridica(Scanner sc) {
        PessoaJuridica pessoaJuridica = new PessoaJuridica();
        try {
            pessoaJuridica.setCodigo(Integer.parseInt(sc.next()));
            pessoaJuridica.setNome(sc.next());
            pessoaJuridica.setEndereco(sc.next());
            pessoaJuridica.setTelefone(sc.next());
            pessoaJuridica.setDataCadastro(dateFormat.parse(sc.next()));
            pessoaJuridica.setTipo(TipoCliente.valueOf(sc.next()));
            pessoaJuridica.setCnpj(sc.next());
            pessoaJuridica.setInscricaoEstadual(Integer.parseInt(sc.next()));
        } catch (NumberFormatException | ParseException e) {
        }

        return pessoaJuridica;
    }
    

    /**
     * TODO: Função responsável por setar os atributos de um objeto
     * PessoaFisica que foram lidos da linha do arquivo.
     * @param sc
     * @param sdf
     * @return 
     */

    private PessoaFisica criaPessoaFisica(Scanner sc) {
        PessoaFisica pessoaFisica = new PessoaFisica();
        try {
            pessoaFisica.setCodigo(Integer.parseInt(sc.next()));
            pessoaFisica.setNome(sc.next());
            pessoaFisica.setEndereco(sc.next());
            pessoaFisica.setTelefone(sc.next());
            pessoaFisica.setDataCadastro(dateFormat.parse(sc.next()));
            pessoaFisica.setTipo(TipoCliente.valueOf(sc.next()));
            pessoaFisica.setCpf(sc.next());
        } catch (NumberFormatException | ParseException e) {
            e.printStackTrace();
        }

        return pessoaFisica;
    }

    /**
     * Função responsável por verificar se a linha do arquivo é de uma
     * PessoaFisica ou PessoaJuridica.
     *
     * @param linha - linha do arquivo.
     * @return boolean contendo true ou false
     */
    private boolean ehPessoaFisica(String linha) {
        Pattern pattern = Pattern.compile("(\\;)F(\\;)");
        Matcher matcher = pattern.matcher(linha);
        return matcher.find();
    }
}
