/**
 *
 */
package Models;

/**
 * DAO Data Access Object
 * <h2>Esta classe é responsável por pegar dados de um Jframe e Jogar noutro.
 * </h2>
 *
 * @Example Pesquisar Cliente Getters e Setters
 * @author
 */
public class ContaBancaria {

    private static int numeroConta;

    private static String nomeDoCliente;

    private static String agencia;

    private static String cidade;

    private static String tipoDaConta;

    private static String statusConta;

    private static String statusCliente;

    private String dataAbertura;
//    
//    private BigDecimal saldo;
//    
//    private BigDecimal limiteSaque;
//    
//    private Cliente cliente;
//    
//    private Agencia agen;

    public static String getStatusCliente() {
        return statusCliente;
    }

    public static void setStatusCliente(String statusCliente) {
        ContaBancaria.statusCliente = statusCliente;
    }

    public static int getNumeroConta() {
        return numeroConta;
    }

    public static String getStatusConta() {
        return statusConta;
    }

    public static void setStatusConta(String statusConta) {
        ContaBancaria.statusConta = statusConta;
    }

    public static void setNumeroConta(int numeroConta) {
        ContaBancaria.numeroConta = numeroConta;
    }

    public static String getNomeDoCliente() {
        return nomeDoCliente;
    }

    public static void setNomeDoCliente(String nomeDoCliente) {
        ContaBancaria.nomeDoCliente = nomeDoCliente;
    }

    public static String getAgencia() {
        return agencia;
    }

    public static void setAgencia(String agencia) {
        ContaBancaria.agencia = agencia;
    }

    public static String getCidade() {
        return cidade;
    }

    public static void setCidade(String cidade) {
        ContaBancaria.cidade = cidade;
    }

    public static String getTipoDaConta() {
        return tipoDaConta;
    }

    public static void setTipoDaConta(String tipoDaConta) {
        ContaBancaria.tipoDaConta = tipoDaConta;
    }
}
