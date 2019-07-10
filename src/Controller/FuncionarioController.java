package Controller;

import Conexao.ConexaoPostgresql;
import Funcoes.Funcoes;
import Funcoes.FuncoesAdministrativas;
import com.mysql.jdbc.exceptions.MySQLDataException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import org.postgresql.util.PSQLException;
import tx.utilitarios.TXDatas;
import tx.utilitarios.TXPasswordCheck;

/**
 *
 * @author Taffarel Xavier <taffarel_deus@hotmail.com>
 */
public class FuncionarioController {

    /**
     * @param t
     * @return Se o valor retornado for maior que 0, então retorna 1, do contrário retorna zero.
     * @throws SQLException
     * @throws Exception
     */
    public int contador(String t) throws SQLException, Exception {
        try {
            String sql = "SELECT COUNT(*) AS total FROM funcionarios WHERE func_nome = ?;";

            PreparedStatement stm = ConexaoPostgresql.abrir().prepareStatement(sql);

            stm.setString(1, t);

            ResultSet result = stm.executeQuery();

            result.next();

            String foundType = result.getString(1);

            return Integer.valueOf(foundType) > 0 ? 1 : 0;

        } catch (NumberFormatException | SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    public List<Models.Funcionario> read(String nome) {
        try {
            String sql = "SELECT * FROM funcionarios WHERE func_nome_completo LIKE ?;";

            PreparedStatement stm = ConexaoPostgresql.abrir().prepareStatement(sql);

            stm.setString(1, "%" + nome + "%");

            ResultSet rs = stm.executeQuery();

            List<Models.Funcionario> fnc = new ArrayList<>();

            while (rs.next()) {

                Models.Funcionario func = new Models.Funcionario();

                func.setId(rs.getInt("func_id"));

                func.setNomeCompleto(rs.getString("func_nome_completo"));

                func.setNomeDeUsuario(rs.getString("func_nome"));

                func.setCPF(rs.getString("func_cpf"));

                fnc.add(func);

            }
            stm.close();
            return fnc;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     *
     * @param tabela
     * @param TextoFiltro
     */
    public static void pesquisarFuncionarios(JTable tabela, String TextoFiltro) {

        DefaultTableModel model = (DefaultTableModel) tabela.getModel();
        int rowCount = model.getRowCount();

            //Remove todas as linhas da tabela
        for (int i = rowCount - 1; i >= 0; i--) {
            model.removeRow(i);
        }

        FuncionarioController pdao = new FuncionarioController();

        for (Models.Funcionario c : pdao.read(TextoFiltro)) {
            model.addRow(new Object[]{
                c.getId(),
                c.getNomeCompleto(),
                c.getNomeDeUsuario(),
                c.getCPF().toUpperCase()
            });
        }

    }

    public String verificarCPF(String cpf) throws SQLException, Exception {
        try {

            String sql = "SELECT * FROM funcionarios WHERE func_cpf = ?;";

            PreparedStatement stm = ConexaoPostgresql.abrir().prepareStatement(sql);

            stm.setString(1, cpf);

            ResultSet result = stm.executeQuery();

            if (result.next()) {
                return result.getString("func_cpf");
            }

        } catch (PSQLException e) {
            System.out.println(e.getMessage());
        }
        return "";
    }

    /**
     * <h2>Aqui, Verifica se Tem Funcionário Cadastrado</h2>
     *
     * @return
     */
    public int countRows() {
        try {

            Statement stm = ConexaoPostgresql.abrir().createStatement();

            String sql = "SELECT COUNT(*) As total FROM funcionarios;";

            ResultSet result = stm.executeQuery(sql);

            if (result.next()) {
                String foundType = result.getString(1);

                return Integer.valueOf(foundType) > 0 ? 1 : 0;
            }

        } catch (NumberFormatException | SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Atenção[1]", JOptionPane.INFORMATION_MESSAGE);
        }
        return 0;
    }

    /**
     * <h2>Cadastra Novo Usuário</h2>
     *
     * @param nomeUusario
     * @param senha
     * @param nomeCompleto
     * @param cpf
     * @return
     * @throws SQLException
     */
    public int setCadastrarUsuario(String nomeUusario, String senha, String nomeCompleto, String cpf) throws SQLException {
        try {
            if (!FuncionarioController.usuarioExiste(nomeUusario.trim())) {
                String sql = "INSERT INTO funcionarios (func_nome, func_senha, func_nome_completo,"
                        + " func_cpf,func_login_tentativas) VALUES (?,?,?,?,?);";
                PreparedStatement preparedStatement = ConexaoPostgresql.abrir().prepareStatement(sql,
                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY);
                preparedStatement.setString(1, nomeUusario.trim());
                preparedStatement.setString(2, senha);
                preparedStatement.setString(3, nomeCompleto);
                preparedStatement.setString(4, cpf);
                preparedStatement.setInt(5, 0);
                return preparedStatement.executeUpdate();
            } else {
                JOptionPane.showMessageDialog(null, "O nome de usuário já existe.", "Atenção", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Atenção", JOptionPane.ERROR_MESSAGE);
        }
        return 0;
    }

    /**
     * <h2>Alterar a senha do Funcionário</h2>
     *
     * @param novaSenha
     * @param cpf
     * @return
     * @throws SQLException
     */
    public int alterarSenhaFuncionario(String novaSenha, String cpf) throws SQLException {
        try {
            String sql = "UPDATE funcionarios SET func_senha = ? WHERE funcionarios.func_cpf = ?;";
            PreparedStatement preparedStatement = ConexaoPostgresql.abrir().prepareStatement(sql,
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            preparedStatement.setString(1, TXPasswordCheck.gerarHashPassword(novaSenha));
            preparedStatement.setString(2, cpf);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Atenção", JOptionPane.ERROR_MESSAGE);
        }
        return 0;
    }

    /**
     *
     * @param nome
     * @param Id
     * @param cpf
     * @return
     * @throws SQLException
     */
    public static int setAlterarDados(String nome, int Id, String cpf) throws SQLException, Exception {
        try {
            String sql = "UPDATE funcionarios SET func_nome_completo = ? WHERE funcionarios.func_id = ? AND funcionarios.func_cpf = ?;";
            PreparedStatement preparedStatement = ConexaoPostgresql.abrir().prepareStatement(sql,
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            preparedStatement.setString(1, nome);
            preparedStatement.setInt(2, Id);
            preparedStatement.setString(3, cpf);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Atenção", JOptionPane.ERROR_MESSAGE);
        }
        return 0;
    }

    /**
     *
     * @param Id
     * @param cpf
     * @return
     * @throws SQLException
     * @throws Exception
     */
    public static int setDeleteFuncionario(int Id, String cpf) throws SQLException, Exception {
        try {
            String sql = "DELETE FROM funcionarios WHERE funcionarios.func_id = ? AND  funcionarios.func_cpf = ?;";
            PreparedStatement preparedStatement = ConexaoPostgresql.abrir().prepareStatement(sql,
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            preparedStatement.setInt(1, Id);
            preparedStatement.setString(2, cpf);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Atenção", JOptionPane.ERROR_MESSAGE);
        }
        return 0;
    }

    /**
     * <h2>Faz o Login</h2>
     *
     * @param userName
     * @param password
     * @return -1 = senha incorreta; 0 = se o usuário não existir; 1 = tudo certo; 2 = Se houver algum erro;
     */
    public static int fazerLogin(JTextField userName, JTextField password) {

        PreparedStatement stm;

        try {
            //Procura registro pelo nome de usuário
            try {
                stm = ConexaoPostgresql.abrir().prepareStatement("SELECT * FROM funcionarios WHERE func_nome = ? LIMIT 1;");

                stm.setString(1, userName.getText());

                ResultSet rs = stm.executeQuery();
                //Se existir 
                if (rs.next()) {

                    String user = rs.getString("func_nome");

                    String pass = rs.getString("func_senha");

                    try {
                        if (userName.getText().equals(user) && TXPasswordCheck.verificarPassword(password.getText(), pass)) {
                            FuncoesAdministrativas.setNomeDeUsuario(user);
                            FuncionarioController.loginSessaoFuncionario(userName.getText(), TXDatas.getDataFormatada());
                            return 1;
                        } else {
                            return -1;
                        }
                    } catch (java.lang.IllegalArgumentException e) {

                    }

                } else {
                    return 0;
                }
            } catch (MySQLDataException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Atenção COD.3", JOptionPane.WARNING_MESSAGE);
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Atenção COD.4", JOptionPane.WARNING_MESSAGE);
        }
        return 2;
    }

    /**
     *
     * @param nomeUsuario
     * @param data
     * @return
     * @throws Exception
     */
    public static int loginSessaoFuncionario(String nomeUsuario, String data) throws Exception {

        try {
            String sql = "INSERT INTO login_sessao_funcionarios (lgf_nome_usuario, login_data) VALUES (?,?);";
            PreparedStatement preparedStatement = ConexaoPostgresql.abrir().prepareStatement(sql,
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            preparedStatement.setString(1, nomeUsuario);
            preparedStatement.setString(2, data);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Atenção", JOptionPane.ERROR_MESSAGE);
        }
        return 0;
    }

    /**
     *
     * @return @throws Exception
     */
    public static String getUltimoLogin() throws Exception {
        try {
            PreparedStatement stm = ConexaoPostgresql.abrir().prepareStatement("SELECT * FROM login_sessao_funcionarios ORDER BY lgf_id DESC LIMIT 1;");
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getString("lgf_nome_usuario");
            }
        } catch (PSQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Atenção BC1", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Atenção BC2", JOptionPane.ERROR_MESSAGE);
        }
        return "";
    }

    /**
     *
     * @param nomeUsuario
     * @return
     * @throws Exception
     */
    public static boolean usuarioExiste(String nomeUsuario) throws Exception {
        try {
            PreparedStatement stm = ConexaoPostgresql.abrir().prepareStatement("SELECT COUNT(*) as total FROM funcionarios WHERE func_nome = ?;");
            stm.setString(1, nomeUsuario);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt("total") > 0;
            }
        } catch (PSQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Atenção", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Atenção", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    
    /**
     * <p>
     * Inclui um novo cliente no DB (Database)</p>
     *
     * @param Senha
     * @param nomeCompleto
     * @param Agencia
     * @param TipoDaConta
     * @param cpf
     * @param cli_status 0=bloquado; 1=liberado
     * @param statusCliente
     * @return Inteiro 1 se for executado com sucesso, falso do contrário.
     */
    public int cadastrarCliente(String Senha, String nomeCompleto,
            int Agencia, String TipoDaConta, String cpf, int cli_status, String statusCliente) {
        try {

            PreparedStatement stm = ConexaoPostgresql.abrir().prepareStatement("INSERT INTO clientes (cli_senha, "
                    + "cli_nome, cli_agencia, cli_tipo_conta,cli_cpf,cli_status,cli_bloqueio_motivo,cli_status_cliente) VALUES (?,?,?,?,?,?,0,?);");
            stm.setString(1, TXPasswordCheck.gerarHashPassword(Senha));
            stm.setString(2, nomeCompleto);
            stm.setInt(3, Agencia);
            stm.setString(4, TipoDaConta);
            stm.setString(5, cpf);
            stm.setInt(6, cli_status);
            stm.setString(7, statusCliente);
            return stm.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Atenção", JOptionPane.ERROR_MESSAGE);
        }
        return 0;
    }
    
    
    /**
     * Quantidade de códigos de seguranças gerados por cliente.
     */
    private static final int QUANTIDADE_CODIGO_SEGURANCA = 30;

    /**
     * <p>
     * Autoexplicativo</p>
     *
     * @param numeroDaConta O código do cliente, no caso, o número da conta.
     * @return inteiro
     * @throws SQLException
     */
    public int excluirCliente(String numeroDaConta) throws SQLException, Exception {
        try {
            String sql = "DELETE FROM clientes WHERE clientes.cli_numero_conta = ?;";
            PreparedStatement preparedStatement = ConexaoPostgresql.abrir().prepareStatement(sql,
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            preparedStatement.setString(1, numeroDaConta);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Atenção", JOptionPane.ERROR_MESSAGE);
        }
        return 0;
    }

    /**
     *
     * @param numeroContaUsuario
     * @return
     */
    public static int[] insertHash(int numeroContaUsuario) {
        
        String sql = "INSERT INTO tbl_hash (has_hash, has_usuario_id, has_ordem) VALUES";

        int i = 0;

        int numeros[] = Funcoes.gerarNumeros(QUANTIDADE_CODIGO_SEGURANCA);

        for (int x : numeros) {
            ++i;
            if (i < QUANTIDADE_CODIGO_SEGURANCA) {
                sql += " ('" + TXPasswordCheck.gerarHashPassword(String.valueOf(x)) + "'," + numeroContaUsuario + "," + i + "),";
            } else {
                sql += " ('" + TXPasswordCheck.gerarHashPassword(String.valueOf(x)) + "'," + numeroContaUsuario + "," + i + ");";
            }
        }

        Statement stmt;

        try {
            stmt = ConexaoPostgresql.abrir().createStatement();

            stmt.executeUpdate(sql);

            return numeros;
        } catch (SQLException ex) {
            Logger.getLogger(ClienteController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    /**
     * <h2>Altera a senha do ClienteController</h2>
     *
     * @param novaSenha
     * @param numeroDaConta
     * @return
     * @throws SQLException
     * @throws Exception
     */
    public int alterarSenhaCliente(String novaSenha, int numeroDaConta) throws SQLException, Exception {
        try {
            String sql = "UPDATE clientes SET cli_senha = ? WHERE clientes.cli_numero_conta = ?;";
            PreparedStatement preparedStatement = ConexaoPostgresql.abrir().prepareStatement(sql,
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            preparedStatement.setString(1, TXPasswordCheck.gerarHashPassword(novaSenha));
            preparedStatement.setInt(2, numeroDaConta);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Atenção", JOptionPane.ERROR_MESSAGE);
        }
        return 0;
    }

    /**
     * <p>
     * Autoexplicativo</p>
     *
     * @param novoNome
     * @param numAgencia
     * @param tipoConta
     * @param clienteCodigo
     * @param cliStatus
     * @param statusCliente
     * @param motivoBloqueio
     * @return
     * @throws SQLException
     * @throws Exception
     */
    public static int alterarDadosDoCliente(String novoNome, int numAgencia,
            String tipoConta, int clienteCodigo, int cliStatus,
            String statusCliente, int motivoBloqueio) throws SQLException, Exception {
        try {
            String sql = "UPDATE clientes SET cli_nome = ?, cli_agencia = ?,"
                    + " cli_tipo_conta = ?, cli_status = ?, cli_status_cliente = ?, cli_bloqueio_motivo=? WHERE clientes.cli_numero_conta = ?;";
            PreparedStatement stm = ConexaoPostgresql.abrir().prepareStatement(sql,
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            stm.setString(1, novoNome);
            stm.setInt(2, numAgencia);
            stm.setString(3, tipoConta);
            stm.setInt(4, cliStatus);
            stm.setString(5, statusCliente);
            stm.setInt(6, motivoBloqueio);
            stm.setInt(7, clienteCodigo);
            return stm.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Atenção", JOptionPane.ERROR_MESSAGE);
        }
        return 0;
    }

    /**
     * <h2>Pega o último registro da tabela clientes;</h2>
     *
     * @return String
     */
    public String getUltimoRegistroCliente() {
        try {
            String sql = "SELECT cli_numero_conta FROM clientes ORDER BY cli_numero_conta DESC LIMIT 1";

            PreparedStatement stm = ConexaoPostgresql.abrir().prepareStatement(sql);

            ResultSet result = stm.executeQuery();

            if (result.next()) {
                return result.getString("cli_numero_conta");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Atenção", JOptionPane.ERROR_MESSAGE);
        }
        return "";
    }

    /**
     * <p>
     * Pega o último registro da tabela clientes;</p>
     *
     * @param tipoConta 1=Corrente; 2=Poupança, de acordo com a tabela
     * 'tipo_de_contas'
     * @return
     */
    public String getUltimoRegistroClientePorTipo(String tipoConta) {
        try {
            String sql = "SELECT cli_numero_conta FROM clientes WHERE cli_tipo_conta = ? ORDER BY cli_numero_conta DESC LIMIT 1";

            PreparedStatement stm = ConexaoPostgresql.abrir().prepareStatement(sql);

            stm.setString(1, tipoConta);

            ResultSet result = stm.executeQuery();

            if (result.next()) {
                return result.getString("cli_numero_conta");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Atenção", JOptionPane.ERROR_MESSAGE);
        }
        return "";
    }

    /**
     * <h2 styke="margin:0px;">Pega os dados da tabela clientes e preeche<br>a
     * tabela jTablePesquisarCliente</h2>
     *
     * @param nome
     * @return
     */
    public static List<Models.Cliente> lerTabelaCliente(String nome) {

        PreparedStatement stmt;

        try {
            stmt = ConexaoPostgresql.abrir().prepareStatement("SELECT * FROM clientes AS t1 JOIN cidades AS t2 ON t1.cli_agencia = t2.cdd_id WHERE cli_nome LIKE ?;");
            //Para poder filtrar por qualquer nome na coluna cli_nome
            stmt.setString(1, "%" + nome + "%");

            ResultSet rs = stmt.executeQuery();

            List<Models.Cliente> contas = new ArrayList<>();
                        
            while (rs.next()) {
                
                Models.Cliente conta = new Models.Cliente();

                conta.setContaId(rs.getInt("cli_numero_conta"));

                conta.setNome(rs.getString("cli_nome"));

                conta.setAgencia(rs.getString("cdd_agencia"));

                conta.setTipoConta(rs.getString("cli_tipo_conta"));

                conta.setCidade(rs.getString("cdd_nome"));

                conta.setCpf(rs.getString("cli_cpf"));

                conta.setCidadeID(rs.getInt("cdd_id"));

                conta.setStatusConta(rs.getInt("cli_status"));
                
                conta.setStatusCliente(rs.getString("cli_status_cliente"));

                contas.add(conta);

            }
            stmt.close();
            return contas;
        } catch (SQLException ex) {
            Logger.getLogger(ClienteController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                ConexaoPostgresql.fecharConexao();
            } catch (SQLException ex) {
                Logger.getLogger(ClienteController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return null;
    }

    /**
     * <p>
     * Um aliás do método lerTabelaCliente nesta classe.
     * </p>
     *
     * @param tabela
     * @param TextoFiltro
     */
    public static void pesquisarClientes(JTable tabela, String TextoFiltro) {

        DefaultTableModel model = (DefaultTableModel) tabela.getModel();

        int rowCount = model.getRowCount();

        //Remova as linhas uma a uma do final da tabela
        for (int i = rowCount - 1; i >= 0; i--) {
            model.removeRow(i);
        }

        //Preenche a tabela jTablePesquisarCliente
        lerTabelaCliente(TextoFiltro).forEach((Models.Cliente c) -> {
            try {
                model.addRow(new Object[]{
                    c.getContaId(),
                    c.getNome(),
                    c.getAgencia(),
                    c.getCidadeID() + "-" + c.getCidade(),
                    ContaController.buscarNomeContaPorId(Integer.parseInt(c.getTipoConta()), "cnt_id") + "-"
                    + ContaController.buscarNomeContaPorId(Integer.parseInt(c.getTipoConta()), "cnt_tipo"),
                    c.getCpf(),
                    c.getStatusConta() == 0 ? "bloqueado".toUpperCase() : "desbloqueado".toUpperCase(),
                    c.getStatusCliente()
                });
            } catch (NumberFormatException ex) {
                Logger.getLogger(ClienteController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

    }
    
}
