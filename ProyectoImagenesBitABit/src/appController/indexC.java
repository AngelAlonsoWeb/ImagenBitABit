package appController;

import java.io.File;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class indexC {
	@FXML
	private Label labelTitle;
	@FXML
	private Label labelMessage;
	@FXML
	private Button buttonUpload;
	@FXML
	private String link;
	// getter para link
	public String getLink() {
        return link;
    }
    // Setter para link
    public void setLink(String link) {
        this.link = link;
    }
    /*Función que carga la ruta del archivo txt*/
	@FXML
	public void uploadFile(ActionEvent event) throws IOException {
		
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecciona un archivo");
        
        // Filtrro que solo permite archivos de texto txt
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Archivos de texto (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

        File selectedFile = fileChooser.showOpenDialog(stage);
        String file = selectedFile.getAbsolutePath();
        setLink(file);

        if (selectedFile != null) {
            System.out.println("Archivo seleccionado: " + getLink());
            nextWindow(event);
        }
        
	}
	@FXML
	public void nextWindow(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/appView/colorQuantity.fxml"));
	    Parent root = loader.load();

	    //Iniciar el controllador para pasarle el vslor del link al siguiente controlador por medio de su setter
	    colorQuantityC colorQuantityController = loader.getController();
	    colorQuantityController.setLink(this);

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
