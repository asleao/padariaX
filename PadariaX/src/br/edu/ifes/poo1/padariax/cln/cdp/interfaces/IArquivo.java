/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifes.poo1.padariax.cln.cdp.interfaces;

import br.edu.ifes.poo1.padariax.cln.cdp.Arquivo;
import java.util.List;

/**
 *
 * @author aleao
 */
public interface IArquivo {
    public List<String> ler(Arquivo file);
    public void salvar(List<String> arquivo);
}
