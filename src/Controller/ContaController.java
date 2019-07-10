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


/**
 *
 * @author Taffrel Xavier <taffarel_deus@hotmail.com>
 */
public class ContaController {
     /**
     * <p>
     * Busca todos os valores da tabela tipo_de_contas. É chamado na
     * TelaAlterarCliente e TelaPrincipal</p>
     * @return Array String com o tipo das contas
     * @throws Exception
     */
    public static String[] getValues() throws Exception {

        PreparedStatement stm = ConexaoPostgresql.abrir().prepareStatement("SELECT * FROM tipo_de_contas;",
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_UPDATABLE);

        ResultSet rs = stm.executeQuery();
        //Pega o total de registro da tabela tipo_de_contas.
        String[] r = new String[getTotalDeRegistros(rs)];
        int i = -1;
        while (rs.next()) {
            ++i;
            r[i] = rs.getString("cnt_id") + "-" + rs.getString("cnt_tipo");
        }
        return r;
    }
    /**
     * <p>
     * Busca o nome da conta pelo o ID <br>
     * É chamado na classe Cliente <br>
     * ContaController.buscarNomeContaPorId(c.getTipoConta(), "cnt_id") + "-" <br>
     * ContaController.buscarNomeContaPorId(c.getTipoConta(), "cnt_tipo")
     * </p>
     * @param id
     * @param coluna
     * @return
     */
    public static String buscarNomeContaPorId(int id, String coluna) {

        PreparedStatement stm;
        try {
            
            stm = ConexaoPostgresql.abrir().prepareStatement("SELECT * FROM tipo_de_contas WHERE cnt_id = ?;");

            stm.setInt(1, id);

            ResultSet rs = stm.executeQuery();

            if (rs.next()) {
                return rs.getString(coluna);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ContaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

    /**
     * <p>Retorna o total de registros de uma busca.</p>
     * @param rs ResultSet
     * @return Inteiro
     * @throws SQLException
     */
    public static int getTotalDeRegistros(ResultSet rs) throws SQLException {
        int total;
        rs.last();
        total = rs.getRow();
        rs.beforeFirst();
        return total;
    }
}
