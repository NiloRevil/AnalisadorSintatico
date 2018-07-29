package models;

import java.util.ArrayList;

public class AnalisadorSemantico {
    
    private ArrayList<NoSemantico> tabelaSemantica;
    private ArrayList<NoSemantico> funcoesPendentes;
    private ArrayList<NoSemantico> proceduresPendentes;
    private String erros; // 12
    private Escopo escopo;      
    private boolean temStart;
    private boolean ativarAtribuicao;
    private boolean ativarAtribuicao2;     
         
    private static int count;    
    
    public AnalisadorSemantico() {
        
        AnalisadorSemantico.count = 0;
        this.tabelaSemantica = new ArrayList();
        this.funcoesPendentes = new ArrayList();
        this.proceduresPendentes = new ArrayList();
        this.erros = "";
        this.escopo = new Escopo();
        this.escopo.addEscopo("global", "global");
    }
    
    public void ativarAtribuicao(boolean ativar) {
        this.ativarAtribuicao = ativar;
    }
    
    public void atribuicao3(String nome) {
        
        NoSemantico no = this.tabelaSemantica.get(this.getLastIndex());
        if(no.getDeclaracao().equals("struct")) {
            
            for(NoSemantico s : this.tabelaSemantica) {
                if(s.getNomeEscopo().equals(no.getDeclaracao()) && s.getValorEscopo().equals(no.getNome())) {

                    if(s.getDeclaracao().equals("varStruct") && s.getNome().equals(nome)) {
                        this.reEmpilhaNo(s.getId());
                        return;
                    }
                }  
            }
        }
    }
    
    public void addEscopo(String nome, String valor) {
                      
        this.escopo.addEscopo(nome, valor);
    }
    
     public void addEscopo() {
                      
        NoSemantico no = this.tabelaSemantica.get(this.getLastIndex());
        if(no.getDeclaracao().equals("function")) {
            
            this.escopo.addEscopo("function", no.getNome());
        }        
    }
    
    public void removerEscopo() {
     
        this.escopo.removeLast();
    }
        
    public void declararStart(String linha) {
        
        if(this.temStart) {
           this.erros += "Erro 01 - Mais de um metodo principal 'Start' foi declarado na linha "+linha+".\n";
        } else {
           this.temStart = true;
        }
    }
    
    public void declararVar(String linha) {
        
        NoSemantico no = new NoSemantico();
        no.setDeclaracao("var");
        no.setLinhaDeclaracao(linha);
        no.setNomeEscopo(this.escopo.getLastNomeEscopo());
        no.setValorEscopo(this.escopo.getLastValorEscopo());
        this.tabelaSemantica.add(no);
    }
    
    public void declararVar(String tipo, String linha) {
        
        NoSemantico no = new NoSemantico();
        no.setDeclaracao("var");
        no.setLinhaDeclaracao(linha);
        no.setTipo(tipo);
        no.setNomeEscopo(this.escopo.getLastNomeEscopo());
        no.setValorEscopo(this.escopo.getLastValorEscopo());
        this.tabelaSemantica.add(no);
    }
    
    public void declararConst(String linha) {    

        NoSemantico no = new NoSemantico();
        no.setDeclaracao("const");
        no.setLinhaDeclaracao(linha);
        no.setNomeEscopo(this.escopo.getLastNomeEscopo());
        no.setValorEscopo(this.escopo.getLastValorEscopo());
        this.tabelaSemantica.add(no);
    }
    
    public void declararConst(String tipo, String linha) {
        
        NoSemantico no = new NoSemantico();
        no.setDeclaracao("const");
        no.setLinhaDeclaracao(linha);
        no.setTipo(tipo);
        no.setNomeEscopo(this.escopo.getLastNomeEscopo());
        no.setValorEscopo(this.escopo.getLastValorEscopo());
        this.tabelaSemantica.add(no);
    }
    
    public void declararStruct(String linha) {
        
        NoSemantico no = new NoSemantico();
        no.setDeclaracao("struct");
        no.setLinhaDeclaracao(linha);
        no.setNomeEscopo(this.escopo.getLastNomeEscopo());
        no.setValorEscopo(this.escopo.getLastValorEscopo());
        this.tabelaSemantica.add(no);
    }
    
    public void declararFuncao(String linha) {
        
        NoSemantico no = new NoSemantico();
        no.setDeclaracao("function");
        no.setLinhaDeclaracao(linha);
        no.setNomeEscopo(this.escopo.getLastNomeEscopo());
        no.setValorEscopo(this.escopo.getLastValorEscopo());
        this.tabelaSemantica.add(no);
    }
    
    public void declararProcedure(String linha) {
        
        NoSemantico no = new NoSemantico();
        no.setDeclaracao("procedure");
        no.setLinhaDeclaracao(linha);
        no.setNomeEscopo(this.escopo.getLastNomeEscopo());
        no.setValorEscopo(this.escopo.getLastValorEscopo());
        this.tabelaSemantica.add(no);
    }
    
    public void declararTypedef(String linha) {
        
        NoSemantico no = new NoSemantico();
        no.setDeclaracao("typedef");
        no.setLinhaDeclaracao(linha);
        no.setNomeEscopo(this.escopo.getLastNomeEscopo());
        no.setValorEscopo(this.escopo.getLastValorEscopo());
        this.tabelaSemantica.add(no);
    }
    
    public void declararVarStruct(String tipo, String linha) {
        
        NoSemantico no = new NoSemantico();
        no.setDeclaracao("varStruct");
        no.setLinhaDeclaracao(linha);
        no.setTipo(tipo);
        no.setNomeEscopo(this.escopo.getLastNomeEscopo());
        no.setValorEscopo(this.escopo.getLastValorEscopo());
        this.tabelaSemantica.add(no);
    }
        
    public void addTipo(String tipo, String linha) {
        
        NoSemantico no = this.tabelaSemantica.get(this.getLastIndex());
        String escopoAtual = this.escopo.getLastNomeEscopo();
        if(no.getDeclaracao().equals("function")) {
            
            if(no.getTipo().isEmpty()) {
                no.setTipo(tipo);
            } else if(no.getValor().isEmpty()) {
                no.setValor(tipo);
            } else {
                no.setValor2(tipo);
            }
        } else if(no.getDeclaracao().equals("procedure")) {
            
            if(no.getValor().isEmpty()) {
                no.setValor(tipo);
            } else {
                no.setValor2(tipo);
            }
        } else if(escopoAtual.equals("struct")) {                      
            
            this.declararVarStruct(tipo, linha);
        } else {
            
            if(this.tabelaSemantica.get(this.getLastIndex()).getTipo().isEmpty()) {
                this.tabelaSemantica.get(this.getLastIndex()).setTipo(tipo);
            } else {
                this.erros += "ERRO AO ADICIONAR TIPO\n";
            }   
        }        
    }
    
    public void addNome(String nome, String linha) {
        
        NoSemantico no = this.tabelaSemantica.get(this.getLastIndex());
        if(no.getDeclaracao().equals("var")) {
            
            if(no.getNome().isEmpty()) {
                
                this.verificarNome(no, nome, linha);
                this.tabelaSemantica.get(this.getLastIndex()).setNome(nome);                                                   
            } else {
                 
                // Se a variavel atual da tabela já tem um nome, adiciona uma nova.
                this.declararVar(this.tabelaSemantica.get(this.getLastIndex()).getTipo());
                this.verificarNome(no, nome, linha);             
                this.tabelaSemantica.get(this.getLastIndex()).setNome(nome);
            }            
        } else if(no.getDeclaracao().equals("const")) {
            
            if(no.getNome().isEmpty()) {
                
                this.verificarNome(no, nome, linha);           
                this.tabelaSemantica.get(this.getLastIndex()).setNome(nome);
            } else {
                 
                // Se a constante atual da tabela já tem um nome, adiciona uma nova.
                this.declararConst(this.tabelaSemantica.get(this.getLastIndex()).getTipo());
                this.verificarNome(no, nome, linha);                
                this.tabelaSemantica.get(this.getLastIndex()).setNome(nome);
            }  
        } else if(no.getDeclaracao().equals("varStruct")) {
            
            if(no.getNome().isEmpty()) {
                
                this.verificarNome(no, nome, linha);            
                this.tabelaSemantica.get(this.getLastIndex()).setNome(nome);
            } else {
                 
                // Se a variavel atual da struct já tem um nome, adiciona uma nova.                 
                this.declararVarStruct(this.tabelaSemantica.get(this.getLastIndex()).getTipo(), linha);
                this.verificarNome(no, nome, linha);               
                this.tabelaSemantica.get(this.getLastIndex()).setNome(nome);
            }  
        } else if(no.getDeclaracao().equals("function")) {
            
            if(no.getNome().isEmpty()) {
                
                int id = this.verificarNomeFuncao(nome);
                if(id != 0) {
                    
                    no.setIdSobrecarga(id);
                }                
                this.tabelaSemantica.get(this.getLastIndex()).setNome(nome);                
            } else {
                
                // Se a funcao atual da tabela ja tem nome, adiciona os parametros.
                if(no.getValor().isEmpty()) {
                    
                    no.setValor(nome);
                } else {
                    
                    no.setValor2(nome);
                }                
            }
        } else if(no.getDeclaracao().equals("procedure")) {
            
            if(no.getNome().isEmpty()) {
                
                int id = this.verificarNomeProcedure(nome);
                if(id != 0) {
                    
                    no.setIdSobrecarga(id);
                }                
                this.tabelaSemantica.get(this.getLastIndex()).setNome(nome);                
            } else {
                
                // Se a procedure atual da tabela ja tem nome, adiciona os parametros.
                if(no.getValor().isEmpty()) {
                    
                    no.setValor(nome);
                } else {
                    
                    no.setValor2(nome);
                }                
            }
        } else {
            
            if(no.getNome().isEmpty()) {
                
                this.verificarNome(no, nome, linha);                
                this.tabelaSemantica.get(this.getLastIndex()).setNome(nome);
            } else {
                this.erros += "ERRO AO ATUALIZAR NOME - Linha "+linha+"\n";
            }
        }        
    }
    
    public void addValor(String valor, String linha) {
        
        if(this.ativarAtribuicao) {
        
            NoSemantico no = this.tabelaSemantica.get(this.getLastIndex());            
            if(no.getValor().isEmpty()) {

                no.setValor(valor);
            } else {

                no.setValor3(valor);
            }
        } else if(this.ativarAtribuicao2) {
            
            this.tabelaSemantica.get(this.getLastIndex()).setValor(valor);
            
            this.ativarAtribuicao2 = false;
        } else {
            
            this.verificarValor(valor, linha);             
        }        
    }
    
    public void verificarConst(String linha) {
        
        NoSemantico no = this.tabelaSemantica.get(this.getLastIndex());
        if(no.getValor().isEmpty()) {
            
            this.erros += "Erro 02 - Constante "+no.getNome()+" não inicializada na linha "+linha+"\n";
        }
    }
    
    public boolean verificarNome(NoSemantico no, String nome, String linha) {
        
        if(no.getDeclaracao().equals("var")) {
            
            if(this.verificarNomeVar(nome, no.getNomeEscopo(), no.getValorEscopo())) {
                this.erros += "Erro 03 - Erro na linha "+linha+", já existe uma variável declarada como '"+nome+"'.\n";
                return false;
            }            
        } else if(no.getDeclaracao().equals("const")){
            
            if(this.verificarNomeConst(nome)) {
                this.erros += "Erro 04 - Erro na linha "+linha+", já existe uma constante declarada como '"+nome+"'.\n";
                return false;
            } 
        } else if(no.getDeclaracao().equals("struct")){
            
            if(this.verificarNomeStruct(nome)) {
                this.erros += "Erro 05 - Erro na linha "+linha+", já existe uma struct declarada como '"+nome+"'.\n";
                return false;
            } 
        } else if(no.getDeclaracao().equals("typedef")){
            
            if(this.verificarNomeTypedef(nome)) {
                this.erros += "Erro 07 - Erro na linha "+linha+", já existe um tipo declarado como '"+nome+"'.\n";
                return false;
            } 
        } else if(no.getDeclaracao().equals("varStruct")){
            
            if(this.verificarNomeVarStruct(nome, no.getNomeEscopo(), no.getValorEscopo())) {
                this.erros += "Erro 08 - Erro na linha "+linha+", já existe uma variável declarada como '"+nome+"'.\n";
                return false;
            } 
        } else {
            this.erros += "ERRO AO VERIFICAR NOME\n";
            return false;
        }
        
        return true;
    }
    
    public boolean verificarNomeVar(String nome, String nomeEscopo, String valorEscopo) {
        
        for (NoSemantico no : this.tabelaSemantica) {
            
            if(no.getDeclaracao().equals("var")) {
                
                if(no.getNomeEscopo().equals("Global") && no.getNome().equals(nome)) {
                
                    return true;
                } else if(no.getNomeEscopo().equals(nomeEscopo) && no.getValorEscopo().equals(valorEscopo) && no.getNome().equals(nome)) {

                    boolean trava = true;
                    if(nomeEscopo.equals("function")) {
                                                
                        for (NoSemantico f : this.funcoesPendentes) {
                            
                            if(f.getNome().equals(valorEscopo) && f.getIdSobrecarga() == no.getId()) {
                                   
                                trava = false;
                            }                             
                        }
                    } else if(nomeEscopo.equals("procedure")) {
                        
                        for (NoSemantico p : this.proceduresPendentes) {
                            
                            if(p.getNome().equals(valorEscopo)) {
                                   
                                trava = false;
                            }                             
                        }
                    } 
                    
                    if(trava)
                        return true;                    
                }                
            }
        }
        
        return false;
    }
    
    public boolean verificarNomeConst(String nome) {
    
        for (NoSemantico no : this.tabelaSemantica) {
            
            if(no.getDeclaracao().equals("const") && no.getNome().equals(nome)) {
                
                return true;
            }
        }
        
        return false;
    }
    
    public boolean verificarNomeStruct(String nome) {
    
        for (NoSemantico no : this.tabelaSemantica) {
            
            if(no.getDeclaracao().equals("struct") && no.getNome().equals(nome)) {
                
                return true;
            }
        }
        
        return false;
    }
    
    public int verificarNomeFuncao(String nome) {
                
        for (NoSemantico no : this.tabelaSemantica) {
            
            if(no.getDeclaracao().equals("function") && no.getNome().equals(nome)) {
                
                this.funcoesPendentes.add(this.tabelaSemantica.get(this.getLastIndex()));
                return no.getId();
            }
        }
        
        return 0;
    }
    
    public int verificarNomeProcedure(String nome) {
    
        for (NoSemantico no : this.tabelaSemantica) {
            
            if(no.getDeclaracao().equals("procedure") && no.getNome().equals(nome)) {
                
                this.proceduresPendentes.add(this.tabelaSemantica.get(this.getLastIndex()));
                return no.getId();
            }
        }
        
        return 0;
    }
    
    public boolean verificarNomeTypedef(String nome) {
    
        for (NoSemantico no : this.tabelaSemantica) {
            
            if(no.getDeclaracao().equals("typedef") && no.getNome().equals(nome)) {
                
                return true;
            }
        }
        
        return false;
    }
    
    public boolean verificarNomeVarStruct(String nome, String nomeEscopo, String valorEscopo) {
        
        for (NoSemantico no : this.tabelaSemantica) {
            
            if(no.getDeclaracao().equals("varStruct") && no.getNomeEscopo().equals(nomeEscopo) && no.getValorEscopo().equals(valorEscopo) && no.getNome().contains(nome)) {
                
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Foi decidido que a sobrecarga so sera aceita mediante a mudanca dos parametros, 
     * devendo manter o mesmo tipo de retorno e conteudo. 
     */
    public void verificarSobre_carga_scrita() {
            
        this.funcoesPendentes.forEach(
            (no) -> {
                NoSemantico noAtual = this.getForID(no.getIdSobrecarga());
                if(noAtual.getValor().equals(no.getValor())) {

                    this.erros += "Erro 09 - Erro na linha "+no.getLinhaDeclaracao()+", já existe uma funcao declarada como '"+no.getNome()+"' e esta não é uma sobrecarga.\n";
                } else if(!noAtual.getTipo().equals(no.getTipo())) {

                    this.erros += "Erro 10 - Erro na linha "+no.getLinhaDeclaracao()+", os tipos de retorno das funções não correspondem para uma sobrecarga.\n";
                }
            }
        );

        this.proceduresPendentes.forEach(
            (no) -> {
                NoSemantico noAtual = this.getForID(no.getIdSobrecarga());
                 if(noAtual.getValor().equals(no.getValor())) {

                    this.erros += "Erro 11 - Erro na linha "+no.getLinhaDeclaracao()+", já existe uma procedure declarada como '"+no.getNome()+"' e esta não é uma sobrecarga.\n";
                }
            }
        ); 
    }
         
    public void verificarValor(String valor, String linha) {
        
         for(int i=0; i<this.tabelaSemantica.size(); i++) {
            
            NoSemantico no = this.tabelaSemantica.get(i);
            if(no.getNome().equals(valor)) {
                if(no.getDeclaracao().equals("const")) {
                    
                    this.erros += "Erro 12 - Valor da constante '"+valor+"' não pode ser alterado na linha "+linha+".\n";
                } else {
                    
                    this.tabelaSemantica.add(this.tabelaSemantica.remove(i));
                    this.ativarAtribuicao2 = true;
                }                
                return;
            }
        }        
    }
    
    public void reEmpilhaNo(int id) {
        
        for(int i=0; i<this.tabelaSemantica.size(); i++) {
            
            NoSemantico no = this.tabelaSemantica.get(i);
            if(no.getId() == id) {
                                  
                this.tabelaSemantica.add(this.tabelaSemantica.remove(i));
                this.ativarAtribuicao2 = true;
                                
                return;
            }
        }
    }
    
    public NoSemantico getForID(int id) {
                    
        for(NoSemantico no : this.tabelaSemantica) {
            
            if(no.getId() == id){
                
                return no;
            }
        }
        
        return null;
    }
            
    public String getErros() {
        return this.erros;
    }
    
    public ArrayList<NoSemantico> getTabelaSemantica() {
        return tabelaSemantica;
    }
    
    public int getLastIndex() {
        return (this.tabelaSemantica.size()-1);
    }
           
    public static int getId() {
        return (AnalisadorSemantico.count++);
    }
    
    public void printSemanticTable() {
        
        this.tabelaSemantica.forEach(
            (no) -> {
                System.out.println("Id: "+no.getId());
                System.out.println("Declaração: "+no.getDeclaracao());
                System.out.println("Linha da Declaração: "+no.getLinhaDeclaracao());
                System.out.println("Tipo: "+no.getTipo());
                System.out.println("Nome: "+no.getNome());
                System.out.println("Valor: "+no.getValor());
                System.out.println("Nome Escopo: "+no.getNomeEscopo());
                System.out.println("Valor Escopo: "+no.getValorEscopo());                
                System.out.println();
            }
        );
    }
    
    public void printFunctionTable() {
        
        System.out.println("***** Funções Pendentes *****"); 
        this.funcoesPendentes.forEach(
            (no) -> {
                System.out.println("Função: "+no.getNome());  
            }
        );        
        
        System.out.println("\n***** Procedures Pendentes *****"); 
        this.proceduresPendentes.forEach(
            (no) -> {
                System.out.println("Procedure: "+no.getNome()); 
            }
        );
    }   
    
}
