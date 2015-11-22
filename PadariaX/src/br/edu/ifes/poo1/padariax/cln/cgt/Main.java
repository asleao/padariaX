/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifes.poo1.padariax.cln.cgt;

import br.edu.ifes.poo1.padariax.cln.cdp.Arquivo;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Reader;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author aleao
 */
public class Main {

    private static Reader fr;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        Arquivo arquivo = new Arquivo("/home/aleao/Dropbox/IFES/Materias/POO1/2015-2/Trabalho Pratico/POO1_Trab1_Testes/01/in/", "clientes.csv");
        AplArquivo apl =  new AplArquivo();

        List<String> lista = apl.importar(arquivo);         
        for(String s: lista){
            System.out.println(s);
        }
    }

}
