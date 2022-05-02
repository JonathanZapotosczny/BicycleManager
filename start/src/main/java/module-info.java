module br.manager.bicycle.projetointegrador {
    requires javafx.controls;
    requires javafx.fxml;

    opens br.manager.bicycle.projetointegrador to javafx.fxml;
    exports br.manager.bicycle.projetointegrador;
}
