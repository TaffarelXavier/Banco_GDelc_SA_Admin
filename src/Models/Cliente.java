package Models;

public class Cliente {

    private int contaId;
    
    private String nome;
    
    private String agencia;
    
    private String cidade;
    
    private String tipoConta;
    
    private String cpf; 
    
    private int statusConta; 
    
    private String statusCliente; 
    
    private Endereco endereco;
    
    public String getStatusCliente() {
        return statusCliente;
    }

    public void setStatusCliente(String statusCliente) {
        this.statusCliente = statusCliente;
    }

    public int getStatusConta() {
        return statusConta;
    }
    
    /**
     * <p style="font:16px arial">Bloqueado ou Desbloqueado</p>
     * @param statusConta 
     */
    public void setStatusConta(int statusConta) {
        this.statusConta = statusConta;
    }

    private int cidadeID;

    public int getCidadeID() {
        return cidadeID;
    }

    public void setCidadeID(int cidadeID) {
        this.cidadeID = cidadeID;
    }
    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    public int getContaId() {
        return contaId;
    }

    public void setContaId(int contaId) {
        this.contaId = contaId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getAgencia() {
        return agencia;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getTipoConta() {
        return tipoConta;
    }

    public void setTipoConta(String tipoConta) {
        this.tipoConta = tipoConta;
    }
}
