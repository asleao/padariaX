/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifes.poo1.padariax.cln.cgt;

import br.edu.ifes.poo1.padariax.cln.cdp.Arquivo;
import br.edu.ifes.poo1.padariax.cln.cdp.PessoaFisica;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.text.ParseException;
import java.util.List;

/**
 *
 * @author aleao
 */
public class Main {

    private static Reader fr;

    /**
     * @param args the command line arguments
     * @throws java.io.FileNotFoundException
     * @throws java.text.ParseException
     */
    public static void main(String[] args) throws FileNotFoundException, IOException, ParseException {
        Arquivo arquivo = new Arquivo("/home/aleao/Dropbox/IFES/Materias/POO1/2015-2/Trabalho Pratico/POO1_Trab1_Testes/01/in/", "clientes.csv");
        AplCliente aplCliente = new AplCliente();


        List<PessoaFisica> lista1 = aplCliente.cadastroPessoaFisica(arquivo);

        for (PessoaFisica pessoa : lista1) {
            System.out.println(pessoa.toString());
        }
    }

}
