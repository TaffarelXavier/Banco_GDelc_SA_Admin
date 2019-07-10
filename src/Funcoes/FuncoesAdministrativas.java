package Funcoes;


public class FuncoesAdministrativas {

    private static String titulojFrame;

    private static boolean PrimeiroAcesso = false;

    private static String nomeDeUsuario;

    public static String getTitulojFrame() {
        return titulojFrame;
    }

    public static void setTitulojFrame(String titulojFrame) {
        FuncoesAdministrativas.titulojFrame = titulojFrame;
    }

    public static boolean isPrimeiroAcesso() {
        return PrimeiroAcesso;
    }

    public static void setPrimeiroAcesso(boolean PrimeiroAcesso) {
        FuncoesAdministrativas.PrimeiroAcesso = PrimeiroAcesso;
    }

    public static String getNomeDeUsuario() {
        return nomeDeUsuario;
    }

    public static void setNomeDeUsuario(String nomeDeUsuario) {
        FuncoesAdministrativas.nomeDeUsuario = nomeDeUsuario;
    }

}
