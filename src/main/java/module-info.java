module kth.se.ramkalo.labb4 {
    requires javafx.controls;
    requires javafx.fxml;


    opens kth.se.ramkalo.labb4 to javafx.fxml;
    exports kth.se.ramkalo.labb4;
}