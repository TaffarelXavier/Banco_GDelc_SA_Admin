/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Conexao.ConexaoPostgresql;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

/**
 *
 * @author Taffrel Xavier <taffarel_deus@hotmail.com>
 */
public class EnderecoController {
     /**
     * 
     * @return 
     */
    public static String[] getEstados() {
        try {
            PreparedStatement stm = ConexaoPostgresql.abrir().prepareStatement("select * from estados;");

            ResultSet result = stm.executeQuery();

            String[] est = new String[27];

            int i = -1;

            while (result.next()) {
                i++;
                est[i] = result.getString("sigla");
            }

            return est;

        } catch (NumberFormatException | SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    
     /**
     *
     * @return @throws Exception
     */
    public List<Models.Endereco> read() throws Exception {

        try {

            PreparedStatement stm = ConexaoPostgresql.abrir().prepareStatement("SELECT * FROM cidades ORDER BY cdd_nome ASC;",
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            ResultSet result = stm.executeQuery();

            List<Models.Endereco> endereco = new ArrayList<>();

            while (result.next()) {
                Models.Endereco cid = new Models.Endereco();
                cid.setNomeCidade(result.getString("cdd_nome"));
                cid.setNomeCidade(result.getString("cdd_uf"));
                cid.setNomeCidade(result.getString("cdd_agencia"));
                endereco.add(cid);
            }

            return endereco;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Atenção COD.6", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    /**
     * <p style="font-size:18px;">Preenche o JComboBox (Cidade) na <br>
     * TelaPrincipal com os dados da tabela cidades por ordem asc.</p>
     *
     * @param jc o JComboBox
     * @return
     * @throws Exception
     */
    public int buscarCidades(JComboBox jc) throws Exception {

        try {

            PreparedStatement stm = ConexaoPostgresql.abrir().prepareStatement("SELECT * FROM cidades ORDER BY cdd_nome ASC;");

            ResultSet result = stm.executeQuery();

            while (result.next()) {
                jc.addItem(result.getString("cdd_nome") + "-" + result.getString("cdd_uf") + "-" + result.getString("cdd_agencia") + "-" + result.getString("cdd_id"));
            }
            return 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Atenção COD.7", JOptionPane.ERROR_MESSAGE);
        }
        return 0;
    }

    /**
     * <p style="font-size:18px;">Busca todas as cidades da tabela cidades no
     * DB.</p>
     *
     * @param coluna A coluna que vai retornar os dados
     * @return String os dados da coluna
     * @throws Exception
     */
    public static String[] getValuesCidades(String coluna) throws Exception {

        try {

            PreparedStatement stm = ConexaoPostgresql.abrir().prepareStatement("SELECT * FROM cidades ORDER BY cdd_nome ASC;",
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            ResultSet result = stm.executeQuery();

            String[] dados = new String[ContaController.getTotalDeRegistros(result)];

            int i = -1;

            while (result.next()) {
                ++i;
                dados[i] = result.getString(coluna);
            }
            return dados;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Atenção COD.8", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    /**
     *
     * @param filtro 
     * @return int Algum valor da coluna cdd_id;
     */
    public static int getCidadeId(int filtro) {
        try {
            PreparedStatement stm = ConexaoPostgresql.abrir().prepareStatement("SELECT * FROM cidades WHERE cdd_id = ?;",
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            stm.setInt(1, filtro);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt("cdd_id");
            }
        } catch (SQLException e) {

        }
        return 0;
    }

    /**
     * <p style="font:16px arial">Busca dados da tabela cidades.</p>
     * @param filtro O filtro
     * @param getColuna A coluna que será retornada.
     * @return String A coluna que será retornada.
     */
    public static String getCidadePorColumnStr(int filtro, String getColuna) {
        try {
            PreparedStatement stm = ConexaoPostgresql.abrir().prepareStatement("SELECT * FROM cidades WHERE cdd_id = ?;",
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            stm.setInt(1, filtro);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getString(getColuna);
            }
        } catch (SQLException e) {

        }
        return null;
    }

    /**
     *
     */
    public static enum Tipos {
        INT(1),
        STR(2);
        private final int value;

        private Tipos(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
}
