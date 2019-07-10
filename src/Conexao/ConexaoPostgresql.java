/**
 * Esta classe foi criada em 28/02/2018
 * pelo Grupo 5 - LC IFTO
 */
package Conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import javax.swing.JOptionPane;

/**
 *
 * @author Taffarel Xavier <taffarel_deus@hotmail.com>
 */
public class ConexaoPostgresql {

    private static Connection conn;
    
    /**
     * 
     * @return
     * @throws SQLException 
     */
    public static Connection abrir() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://localhost:5432/Banco_Analise_2";
            Properties props = new Properties();
            props.setProperty("user", "postgres");
            props.setProperty("password", "chkdsk");
            conn = DriverManager.getConnection(url, props);
            return conn;
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return null;
        }
    }
    
    /**
     * 
     * @throws SQLException 
     */
    public static void fecharConexao() throws SQLException {
        ConexaoPostgresql.conn.close();
    }
}
