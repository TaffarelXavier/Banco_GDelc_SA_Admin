/**
 * É uma classe de utilitários
 */
package Funcoes;

import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.JFormattedTextField;
import javax.swing.JTextField;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

/**
 *
 * @author Grupo 5
 */
public class Funcoes {

    private static int tid;

    /**
     * <h2>Pega o ID do Adminitrador logado</h2>
     * Serve também para enviar um dado inteiro de um jFrame para outro jFrame
     *
     * @return
     */
    public static int getId() {
        return tid;
    }

    /**
     * <h2>Define o ID do Adminitrador logado</h2>
     * Esse método é chamado na TelaPrincipal e na Tela de Login
     *
     * @param id
     */
    public static void setId(int id) {
        tid = id;
    }

    /**
     * <h2>Centraliza um painel no meio de um outro elemento, por exemp, de um
     * jFrame</h2>
     *
     * @param pai
     * @param filho
     */
    public static void centralizarContainer(Component pai, Component filho) {
        // obter dimensões do pai
        int larguraPai = pai.getWidth();
        int alturaPai = pai.getHeight();
        // obter dimensões do filho
        int larguraFilho = filho.getWidth();
        int alturaFilho = filho.getWidth();
        // calcular novas coordenadas do filho  
        int novoX = (larguraPai - larguraFilho);
        int novoY = (alturaPai - alturaFilho);
        // centralizar filho
        filho.getParent().setLayout(new GridBagLayout());
        filho.setSize(new Dimension(novoX, novoY));
        filho.repaint();
    }

    /**
     * <p style="font-size:14px;">Divide uma string. se uma string for String
     * str = "minha string e tal"; eu posso dividir da seguinte forma:
     * Funcoes.dividirString(str," "); Dessa forma, a string será dividida pelos
     * espaços (O espaço aqui é o delimitador).
     * </p>
     *
     * @param novaString A string para dividir
     * @param delimitador O delimitador
     * @return
     */
    public static String[] dividirString(String novaString, String delimitador) {
        String n[] = novaString.split(delimitador);
        return n;
    }

    /**
     * <p>
     * Abre um arquivo no PC</p>
     *
     * @param fileName O caminho do arquivo.
     * @return
     */
    public static String abrirArquivo(String fileName) {
        try {

            if (Desktop.isDesktopSupported()) {
                java.awt.Desktop desktop = java.awt.Desktop.getDesktop();
                desktop.open(new File(fileName));
            }
        } catch (IOException e) {
        }

        return null;
    }

    /**
     * <p>
     * Verifica se uma String é nula</p>
     *
     * @param str
     * @return
     */
    public static boolean isNull(String str) {
        return str.isEmpty();
    }

    /**
     * <p>
     * Verifica se uma String é nula ou está em branco.</p>
     *
     * @param string
     * @return
     */
    public static boolean isNullOrBlank(String string) {
        if (isNull(string) || string.trim().length() == 0) {
            return true;
        }
        return false;
    }

    /**
     * <p style="font:16px arial">Gerar número aleatórios e únicos</p>
     *
     * @param total A quantidade de números gerados
     * @return Array
     */
    public static int[] gerarNumeros(int total) {

        int[] numeros = new int[total];

        for (int x = 0; x < total; x++) {
            Random random = new Random(System.nanoTime() % 100000);
            int randomInt = random.nextInt(1000000000);
            numeros[x] = Integer.parseInt(String.valueOf(randomInt).substring(0, 3));
        }

        return numeros;
    }
    
    /**
     * <p style="font-weight:900;">Aplica padding aos Objetos. [...=um array de objetos]</p>
     * @param field 
     */
    public static void setPadding(JTextField... field) {
        for (JTextField t : field) {
            t.setBorder(BorderFactory.createCompoundBorder(t.getBorder(), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        }
    }
      /**
     * <p style="font:16px arial">Formata um número.</p>
     *
     * @param numero o número para ser formatado
     * @return
     */
    public static String formatarNumeroAsCurrency(Object numero) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        return formatter.format(numero);
    }

    /**
     *
     * @param jFormatTextFied
     */
    public static void permitirNumeroAsCurrencyLocal(JFormattedTextField jFormatTextFied) {
        DecimalFormat dFormat = new DecimalFormat("#,###,###.00");
        NumberFormatter formatar = new NumberFormatter(dFormat);
        formatar.setFormat(dFormat);
        formatar.setAllowsInvalid(false);
        formatar.setOverwriteMode(false);
        jFormatTextFied.setFormatterFactory(new DefaultFormatterFactory(formatar));
    }

    public static String decimalFormat(BigDecimal Preis) {
        String res = "0.00";
        if (Preis != null) {
            NumberFormat nf = NumberFormat.getInstance();
            if (nf instanceof DecimalFormat) {
                ((DecimalFormat) nf).applyPattern("###0.00");

            }
            res = nf.format(Preis);
        }

        return res;
    }

}
