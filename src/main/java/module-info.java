module com.example.galerija {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;

    opens com.example.galerija to javafx.fxml;
    exports com.example.galerija;
}