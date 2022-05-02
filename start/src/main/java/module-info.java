module br.manager.bicycle.projetointegrador {
    requires javafx.controls;
    requires java.sql;
    requires javafx.fxml;

    opens br.manager.bicycle.projetointegrador.screens to javafx.fxml;
    exports br.manager.bicycle.projetointegrador;
}
