/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifes.poo1.padariax.cln.cgt;

import br.edu.ifes.poo1.padariax.cln.cdp.Arquivo;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Classe responsável por implementar as funções
 * de manipulação de arquivos.
 * @author aleao
 */

public class AplArquivo {
    
    /**
     * Este método e responsável por importar um arquivo
     * de um diretório e passar cada linha para uma lista
     * de String.
     * @param file - Arquivo com o nome e caminho
     * @return listaImportada - Lista com as linhas do arquivo    
     */    
    public List<String> importar(Arquivo file) {
        List<String> listaImportada = new ArrayList<>();

        Path path = Paths.get(file.toString());

        try (Scanner scanner = new Scanner(path, "Utf-8")) { 
            scanner.nextLine();
            while (scanner.hasNextLine()) {                
                listaImportada.add(scanner.nextLine());
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return listaImportada;
    }
}
