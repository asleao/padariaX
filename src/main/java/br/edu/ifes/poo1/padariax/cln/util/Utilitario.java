/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifes.poo1.padariax.cln.util;

import br.edu.ifes.poo1.padariax.cln.cdp.Arquivo;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author aleao
 */
public class Utilitario {

    /**
     * Este método e responsável por importar um arquivo de um diretório e
     * passar cada linha para uma lista de String.
     *
     * @param file - Arquivo com o nome e caminho
     * @return listaImportada - Lista com as linhas do arquivo
     * @throws java.io.IOException
     */
    public List<String> importar(Arquivo file) throws IOException {
        List<String> listaImportada = new ArrayList<>();

        Path path = Paths.get(file.toString());

        try (Scanner scanner = new Scanner(path, "Utf-8")) {
            scanner.nextLine();
            while (scanner.hasNextLine()) {
                listaImportada.add(scanner.nextLine());
            }
        } 

        return listaImportada;
    }

    /**
     * Método responsável por ler uma lista generica de objetos e imprimir no
     * console.
     *
     * @param listaObjeto
     */
    public void imprime(Collection<?> listaObjeto) {

        for (Object obj : listaObjeto) {
            System.out.println(obj.toString());
        }
    }

    /**
     * Funcao responsavel por escrever uma lista de String em um arquivo em
     * determinado local.
     *
     * @param lista
     * @param destino
     * @param cabecalho
     * @throws java.io.FileNotFoundException
     */
    public void exportar(List<?> lista, Arquivo destino, String cabecalho) throws FileNotFoundException {
        File file = new File(destino.toString());
        try (PrintWriter printWriter = new PrintWriter(file)) {
            printWriter.println(cabecalho);

            for (Object linha : lista) {
                printWriter.println(linha);
            }
        }
    }
}
