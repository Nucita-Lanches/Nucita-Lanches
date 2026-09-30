module com.senai.nucitalanches.nucitalanches {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.senai.nucitalanches.nucitalanches to javafx.fxml;
    exports com.senai.nucitalanches.nucitalanches;
}