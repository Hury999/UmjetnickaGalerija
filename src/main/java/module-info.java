module com.example.galerija {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires MaterialFX;

    opens com.example.galerija;
    exports com.example.galerija;
}