package view;

import controllers.ControllerAnalisadorLexico;
import controllers.ControllerAnalisadorSintatico;
import models.ErrosSemanticos;
import models.TabelaSimbolo;
import models.Token;
import persistencia.ManipulaArquivo;

public class Main {
    
    public static void main(String args[]) {
                
        System.out.println("Compilador Maroto Iniciado ... XD \n");
        
        ManipulaArquivo ma = new ManipulaArquivo();
        
        ControllerAnalisadorLexico lexico = new ControllerAnalisadorLexico();
        ControllerAnalisadorSintatico sintatico = new ControllerAnalisadorSintatico();
       
        // Obtem o nome do(s) arquivo(s).
        String[] arquivos = ma.getArquivos();
        
        // Obtem os arquivos da pasta Arquivo e compila um por vez.
        for(int i=0; i<arquivos.length; i++) {
            
            // Obtem o conteudo do arquivo.
            String texto = ma.lerArquivo(arquivos[i]);
            // Divide o nome do diretorio pela \ (Arquivos\nomeDoArquivo.txt)
            String[] arrayS = arquivos[i].split("\\\\");
            
            //System.out.println("Compilando arquivo \""+arrayS[1]+"\" ...");
            
            // Se texto for diferente de vazio, inicia a compilacao do arquivo.
            if(!texto.isEmpty()) {
                
                Token tokens = new Token();
                TabelaSimbolo simbolos = new TabelaSimbolo();
                
                // Percorre os caracteres do texto e retorna os erros encontrados.
                String errosLexicos = lexico.analisar(texto, tokens, simbolos);
                
                // Armazena os Erros Semanticos.
                ErrosSemanticos errosSemanticos = new ErrosSemanticos();
                
                // Percorre os tokens e retorna os erros encontrados.
                String errosSintaticos = sintatico.analisar(tokens, errosSemanticos);

                // Salva o resultado da compilacao em um Arquivo de mesmo nome e prefixo compilado_ na pasta Arquivos/Compilados.
                ma.salvaArquivo(errosLexicos, errosSintaticos, errosSemanticos.getErrosSemanticos(), "Arquivos/Compilados/compilado_"+arrayS[1]);
                //System.out.println("Arquivo compilado com sucesso!\n");
            }            
        }     
    }
}
