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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
/**
 *
 * @author Taffarel <taffarel_deus@hotmail.com>
 */
public class EmpresaController {
    /**
     * 
     * @return 
     */
    public static String getNomeFantasia() {
        return getNomeDaEmpresa(getUltimoRegisto(), "empr_nome_fantasia");
    }


    public enum MSG {
        /**
         *
         */
        NOME_EMPRESA_BRANCO("O nome da empresa está em branco."),
        CNPJ_EM_BRANCO("O CNPJ está em branco.");

        private final String valor;

        MSG(String valorOpcao) {
            valor = valorOpcao;
        }

        public String getValor() {
            return valor;
        }
    }

   /**
    * 
    * @param cnpj
    * @param nomeDaEmpres
    * @param inscricaoEstadual
    * @param site
    * @param email
    * @param cep
    * @param endereco
    * @param numero
    * @param bairro
    * @param cidade
    * @param estado
    * @param telefone
    * @param celular
    * @param nomeFantasia
    * @return
    * @throws SQLException 
    */
    public static int cadastrarEmpresa(String cnpj, String nomeDaEmpres, String inscricaoEstadual,
            String site, String email, String cep, String endereco, String numero, String bairro,
            String cidade, String estado, String telefone, String celular,String nomeFantasia) throws SQLException {
        try {
            PreparedStatement stm = ConexaoPostgresql.abrir().prepareStatement("insert into empresa(empr_cnpj,empr_razao_social, empr_inscricao_estadual, empr_data_abertura, empr_site,empr_email, empr_cep, empr_endereco, empr_numero, empr_bairro, empr_cidade, empr_estado, empr_telefone, empr_celular,empr_nome_fantasia) values(?,?,?,now(),?,?,?,?,?,?,?,?,?,?,?)", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            stm.setString(1, cnpj);
            stm.setString(2, nomeDaEmpres);
            stm.setString(3, inscricaoEstadual);
            stm.setString(4, site);
            stm.setString(5, email);
            stm.setString(6, cep);
            stm.setString(7, endereco);
            stm.setString(8, numero);
            stm.setString(9, bairro);
            stm.setString(10, cidade);
            stm.setString(11, estado);
            stm.setString(12, telefone);
            stm.setString(13, celular);
            stm.setString(14, nomeFantasia);
            return stm.executeUpdate();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Atenção", JOptionPane.ERROR_MESSAGE);
        }
        return 0;
    }

    /**
     *
     * @param cnpj
     * @param nomeDaEmpres
     * @param inscricaoEstadual
     * @param site
     * @param email
     * @param cep
     * @param endereco
     * @param numero
     * @param bairro
     * @param cidade
     * @param estado
     * @param telefone
     * @param celular
     * @param nomeFantasia
     * @param empresaId
     * @return
     * @throws SQLException
     */
    public static int alterarDadosEmpresa(String cnpj, String nomeDaEmpres, String inscricaoEstadual,
            String site, String email, String cep, String endereco, String numero, String bairro,
            String cidade, String estado, String telefone, String celular,String nomeFantasia, int empresaId) throws SQLException {
        try {
            PreparedStatement stm = ConexaoPostgresql.abrir().prepareStatement("UPDATE empresa SET empr_cnpj = ?, empr_razao_social=? ,empr_inscricao_estadual=?,empr_site=?, empr_email=?, empr_cep=?, empr_endereco=?, empr_numero=?, empr_bairro=?,"
                    + " empr_cidade=?, empr_estado=?, empr_telefone=?, empr_celular=?, empr_nome_fantasia=? "
                    + " WHERE empr_id = ?;", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            stm.setString(1, cnpj);
            stm.setString(2, nomeDaEmpres);
            stm.setString(3, inscricaoEstadual);
            stm.setString(4, site);
            stm.setString(5, email);
            stm.setString(6, cep);
            stm.setString(7, endereco);
            stm.setString(8, numero);
            stm.setString(9, bairro);
            stm.setString(10, cidade);
            stm.setString(11, estado);
            stm.setString(12, telefone);
            stm.setString(13, celular);
            stm.setString(14, nomeFantasia);
            stm.setInt(15, empresaId);
            return stm.executeUpdate();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Atenção", JOptionPane.ERROR_MESSAGE);
        }
        return 0;
    }

    public static int contarLinhasTblEmpresa() {
        try {
            PreparedStatement stm = ConexaoPostgresql.abrir().prepareStatement("SELECT COUNT(*) as total FROM empresa;");

            ResultSet rs = stm.executeQuery();

            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmpresaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    /**
     * <p style="font:16px arial">Busca todos os dados da empresa da linha
     * 1;</p>
     *
     * @param emprId
     * @return [int] os dados da tabela.
     */
    public static String[] getDadosEmpresa(int emprId) {
        try {
            PreparedStatement stm = ConexaoPostgresql.abrir().prepareStatement("SELECT * FROM empresa WHERE empr_id = ?;");

            stm.setInt(1, emprId);

            ResultSet rs = stm.executeQuery();

            String[] dados = new String[16];

            if (rs.next()) {
                dados[0] = String.valueOf(rs.getInt("empr_id"));
                dados[1] = rs.getString("empr_cnpj");
                dados[2] = rs.getString("empr_razao_social");
                dados[3] = rs.getString("empr_inscricao_estadual");
                dados[4] = rs.getString("empr_data_abertura");
                dados[5] = rs.getString("empr_site");
                dados[6] = rs.getString("empr_email");
                dados[7] = rs.getString("empr_cep");
                dados[8] = rs.getString("empr_endereco");
                dados[9] = rs.getString("empr_numero");
                dados[10] = rs.getString("empr_bairro");
                dados[11] = rs.getString("empr_cidade");
                dados[12] = rs.getString("empr_estado");
                dados[13] = rs.getString("empr_telefone");
                dados[14] = rs.getString("empr_celular");
                dados[15] = rs.getString("empr_nome_fantasia");
            }

            return dados;
        } catch (SQLException ex) {

        }
        return null;
    }
    
    /**
     * 
     * @param emprId
     * @param coluna
     * @return um string contendo o valor da coluna do segunda parâmetro.
     */
    public static String getNomeDaEmpresa(int emprId, String coluna) {
        try {
            PreparedStatement stm = ConexaoPostgresql.abrir().prepareStatement("SELECT * FROM empresa WHERE empr_id = ?;");

            stm.setInt(1, emprId);

            ResultSet rs = stm.executeQuery();

            String colun = new String();

            if (rs.next()) {
                colun = rs.getString(coluna);
            }
            return colun;
        } catch (SQLException ex) {

        }
        return null;
    }

    /**
     *
     * @return
     */
    public static int getUltimoRegisto() {
        try {
            PreparedStatement stm = ConexaoPostgresql.abrir().prepareStatement("select empr_id from empresa order by empr_id desc;");

            ResultSet rs = stm.executeQuery();

            if (rs.next()) {
                return rs.getInt("empr_id");
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmpresaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    
    
}
