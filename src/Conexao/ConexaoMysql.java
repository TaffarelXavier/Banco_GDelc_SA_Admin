package Conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 * Faz as conexões com o banco de dados.
 *
 * @author Grupo 5 LC - IFTO
 */
public class ConexaoMysql {

    /**
     * <p style="font:16px arial">Strings de conexões</p>
     */
    private static final String[][] DSNs = {
        { /*Índice:0*/
            "com.mysql.jdbc.Driver",
            "jdbc:mysql://187.84.237.46:41890/cemvsdb_2018",
            "cemvs_user_2018",
            "YBce{h47P]v"
        },
        { /*Índice:1*/
            "org.postgresql.Driver",
            "jdbc:postgresql://localhost:5432/banco_ifto",
            "postgres",
            "chkdsk"
        }
    };

    public ConexaoMysql() {
    } //Possibilita instancias

    /**
     * <h2>Abre a conexão com o banco.</h2>
     *
     * @return
     * @throws Exception
     */
    public static Connection abrir() throws Exception {
        try {
            int index = 1; //Com 0, conecta ao banco Mysql; com 1, conecta ao banco postgre.
            // Registrar o driver
            Class.forName(DSNs[index][0]).newInstance();
            // Capturar a conexão
            Connection conn = DriverManager.getConnection(DSNs[index][1], DSNs[index][2], DSNs[index][3]);
            // Retorna a conexao aberta
            return conn;
        } catch (SQLException e) {
            switch (e.getSQLState()) {
                case "28000":
                    JOptionPane.showMessageDialog(null, "A senha do banco está incorreta.\n" + e.getMessage(), "Atenção", JOptionPane.ERROR_MESSAGE);
                    System.exit(0);
                case "08S01":
                    JOptionPane.showMessageDialog(null, "O servidor de banco de dados SQL não está ligado.\n" + e.getMessage().replaceAll("\\.", "\n"),
                            "Atenção", JOptionPane.ERROR_MESSAGE);
                    System.exit(0);
                default:
                    JOptionPane.showMessageDialog(null, "Houve um erro.\n" + e.getMessage(), "Atenção", JOptionPane.ERROR_MESSAGE);
                    break;
            }
        }
        return null;
    }

    /**
     * Fecha a Conexão
     *
     * @return
     * @throws Exception
     */
    public static boolean fecharConexao() throws Exception {

        try {

            abrir().close();
            return true;

        } catch (SQLException e) {

            return false;

        }

    }
}
