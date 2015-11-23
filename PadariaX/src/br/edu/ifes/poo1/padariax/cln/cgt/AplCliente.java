/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifes.poo1.padariax.cln.cgt;

import br.edu.ifes.poo1.padariax.cln.cdp.Arquivo;
import br.edu.ifes.poo1.padariax.cln.cdp.PessoaFisica;
import br.edu.ifes.poo1.padariax.cln.cdp.TipoCliente;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author aleao
 */
public class AplCliente extends AplArquivo{

    private AplArquivo aplArquivo;
    
    public AplCliente(){
        this.aplArquivo = new AplArquivo();
    }

    public List<PessoaFisica> cadastroPessoaFisica(Arquivo file) throws ParseException {
        List<PessoaFisica> listaPessoaFisica = new ArrayList<>();    
        List<String> listaImportada = aplArquivo.importar(file);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        for (String linha : listaImportada) {
            Scanner sc = new Scanner(linha);
            PessoaFisica pessoa = new PessoaFisica();
            sc.useDelimiter(";");
                        
            pessoa.setCodigo(Integer.parseInt(sc.next()));
            pessoa.setNome(sc.next());
            pessoa.setEndereco(sc.next());
            pessoa.setTelefone(sc.next());
            pessoa.setDataCadastro(sdf.parse(sc.next()));            
            pessoa.setTipo(TipoCliente.valueOf(sc.next()));
            pessoa.setCpf(sc.next());
            
            listaPessoaFisica.add(pessoa);
        }

        return listaPessoaFisica;
    }
}
