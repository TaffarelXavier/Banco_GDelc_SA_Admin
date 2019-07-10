/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import static Controller.EmpresaController.contarLinhasTblEmpresa;
/**
 *
 * @author Taffarel <taffarel_deus@hotmail.com>
 */
public class Iniciar extends javax.swing.JFrame{
    
    public static void main(String[] args) {
        if (contarLinhasTblEmpresa() == 0) {
            new TelaConfiguracoes().setVisible(true);
        } else {
            new TelaLogin().setVisible(true);
        }
    }
}
