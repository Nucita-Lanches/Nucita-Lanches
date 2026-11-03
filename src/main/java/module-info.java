module com.senai.nucitalanches.nucitalanches {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;


    opens com.senai.nucitalanches.nucitalanches to javafx.fxml;
    opens com.senai.nucitalanches.nucitalanches.ui to javafx.fxml;
    exports com.senai.nucitalanches.nucitalanches;
}
