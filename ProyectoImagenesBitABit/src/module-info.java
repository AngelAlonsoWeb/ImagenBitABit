module ProyectoImagenesBitABit {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	requires java.desktop;
	requires javafx.swing;
	
	opens application to javafx.graphics, javafx.fxml;
	opens appController to javafx.graphics, javafx.fxml;
	/*
	opens appModel to javafx.graphics, javafx.fxml;
	
	*/
}
