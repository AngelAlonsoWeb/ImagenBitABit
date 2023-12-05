package appController;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import java.io.IOException;

import javafx.event.ActionEvent;

import javafx.scene.control.Label;

import javafx.scene.control.Spinner;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class colorQuantityC {
	@FXML
	private indexC link;
	@FXML
	private Label labelTitle;
	@FXML
	private Spinner<Integer> spinnerCantColor;
	@FXML
	private Button buttonNext;
	/*getter and setter*/
	private int cantColor;
	// Getter para cantColor
    public int getCantColor() {
        return cantColor;
    }
    // Setter para cantColor
    public void setCantColor(int cantColor) {
        this.cantColor = cantColor;
    }
    // Setter para link
    public void setLink(indexC indexC) {
		this.link = indexC;
	}

	/*Evento del botón para recibir la cantidad de colores*/
	@FXML
	public void generarColor(ActionEvent event) throws IOException {
		setCantColor(spinnerCantColor.getValue());
		System.out.println("Clase colorQuantity: "+link.getLink());
		nextWindow(event);
	} 
	/*Método para ir a la siguiente ventana*/
	@FXML
	public void nextWindow(ActionEvent event) throws IOException {
	    FXMLLoader loader = new FXMLLoader(getClass().getResource("/appView/assignColor.fxml"));
	    Parent root = loader.load();

	    //Iniciar mi controlador de assignCOlorC y pasarle sus parámetros de setters
	    assignColorC assignColorController = loader.getController();
	    assignColorController.setCq(this);
	    assignColorController.setLink(link.getLink());

	    Node source = (Node) event.getSource();
	    Stage stage = (Stage) source.getScene().getWindow();
	    stage.close();

	    stage = new Stage();
	    Scene scene = new Scene(root);
	    stage = new Stage(StageStyle.DECORATED);
	    stage.setScene(scene);
	    stage.show();
	}

}
