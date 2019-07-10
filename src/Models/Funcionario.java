package Models;

public class Funcionario {
    
    private static int id;
    
    private static String nomeCompleto;
    
    private static String nomeDeUsuario;
    
    private static String CPF;

    public static int getId() {
        return id;
    }

    public static void setId(int aId) {
        id = aId;
    }

    public static String getNomeCompleto() {
        return nomeCompleto;
    }

    public static void setNomeCompleto(String aNomeCompleto) {
        nomeCompleto = aNomeCompleto;
    }

    public static String getNomeDeUsuario() {
        return nomeDeUsuario;
    }

    public static void setNomeDeUsuario(String aNomeDeUsuario) {
        nomeDeUsuario = aNomeDeUsuario;
    }

    public static String getCPF() {
        return CPF;
    }

    public static void setCPF(String aCPF) {
        CPF = aCPF;
    }
    
    private int numeroMatricula;
    
    private Endereco endereco;
    
   
     public static void Funcionario(int id, String nomeCompleto, String nomeDeUsuario, String CPF) {
        Funcionario.id = id;
        Funcionario.nomeCompleto = nomeCompleto;
        Funcionario.nomeDeUsuario = nomeDeUsuario;
        Funcionario.CPF = CPF;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public int getNumeroMatricula() {
        return numeroMatricula;
    }

    public void setNumeroMatricula(int numeroMatricula) {
        this.numeroMatricula = numeroMatricula;
    }

    
}
