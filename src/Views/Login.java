package Views;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Login extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle("Login");
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        final Text scenetitle = new Text("Autenticação");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        Label userName = new Label("Agência:");
        grid.add(userName, 0, 1);

        final TextField userTextField = new TextField();
        grid.add(userTextField, 1, 1);

        Label pw = new Label("Conta:");
        grid.add(pw, 0, 2);

        final PasswordField agenciaField = new PasswordField();
        grid.add(agenciaField, 1, 2);

        Label labelSenha = new Label("Senha:");
        grid.add(labelSenha, 0, 3);

        final PasswordField password = new PasswordField();
        grid.add(password, 1, 3);

        Button btnCancelar = new Button("Cancelar");
        HBox hbBtn2 = new HBox(12);
        hbBtn2.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn2.getChildren().add(btnCancelar);
        grid.add(hbBtn2, 0, 4);

        Button btn = new Button("Entrar");
        HBox hbBtn = new HBox(12);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, 4);

        final Text actiontarget = new Text();
        grid.add(actiontarget, 1, 6);
        
        /**
         * Cancelar
         */
        btnCancelar.setOnAction((ActionEvent e) -> {
            primaryStage.close();
        });
        /**
         * Fazer Login
         */
        btn.setOnAction((ActionEvent e) -> {
            actiontarget.setFill(Color.FIREBRICK);
            new TelaPrincipal().setVisible(true);
        });

        Scene scene = new Scene(grid, 320, 220);
        primaryStage.setFullScreen(true);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
