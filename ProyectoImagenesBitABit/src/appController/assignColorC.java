package appController;

import javafx.fxml.FXML;

import javafx.scene.control.ColorPicker;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color; // Añadida importación de javafx.scene.paint.Color
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;

import javafx.scene.control.Label;

public class assignColorC {
	@FXML
	private String link;
	@FXML
	private colorQuantityC cq;
	@FXML
	private Label labelNumColor;
	@FXML
	private TextField textFieldBits;
	@FXML
	private ColorPicker pickerColor;
	@FXML
	private Button buttonNext;
	@FXML
	private Button buttonFinish;
	@FXML
	private String[] bit;
	@FXML
	private String[] colors;
	@FXML
	private int cont = 1;
	@FXML
	private int width = 0;
	@FXML
	private int height = 0;
	@FXML
	private int limit = 1;
	@FXML
	private Stage primaryStage;
	/* Para abrir ventanas debo tener un stage */

	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	public void setCq(colorQuantityC cq) {
		this.cq = cq;
		this.bit = new String[cq.getCantColor()];
		this.colors = new String[cq.getCantColor()];
	}

	// Setter para link
	public void setLink(String link) {
		this.link = link;
	}

	// Getter para link
	public String getLink() {
		return link;
	}

	/*Método del siguiente botón*/
	@FXML
	public void btnNextColor(ActionEvent event) {

		if (cont < cq.getCantColor()) {
			labelNumColor.setText("Color  " + (cont + 1));
			cont++;
			bit[cont - 2] = textFieldBits.getText();
			colors[cont - 2] = pickerColor.getValue() + "";
		} else {
			cont++;
			//////////////////////////////
			bit[cont - 2] = textFieldBits.getText();
			colors[cont - 2] = pickerColor.getValue() + "";

			System.out.println(getLink());
			System.out.println("Arrays ");
			for (int i = 0; i < bit.length; i++) {
				System.out.println("Limite: " + bit[i]);
				System.out.println("color: " + colors[i]);
			}
			buttonNext.setDisable(true);
			buttonFinish.setDisable(false);
			calculateBitLimit();
		}

	}

	// Event Listener on Button[#buttonFinish].onAction
	@FXML
	public void btnFinish(ActionEvent event) {
		ReadDoc(event);

	}

	/* Función que calcula de cuanto en cuanto bits recorrerá */
	@FXML
	public void calculateBitLimit() {
		int exp = 0;
		limit = 1;
		while (exp < cq.getCantColor()) {
			exp = (int) Math.pow(2, limit);

			limit++;
		}
		limit--;
		System.out.println(limit);
	}

	public void ReadDoc(ActionEvent event) {
		try (BufferedReader br = new BufferedReader(new FileReader(getLink()))) {
			// Lee la línea del archivo
			int i = 0;
			String line = br.readLine();
			// Separa los elementos por comas
			String[] elements = line.split(",");
			String[] elementsBi = new String[elements.length];
			// Imprime los elementos en consola
			for (i = 0; i < elementsBi.length; i++) {
				elementsBi[i] = convertirHexABin(elements[i]);
				System.out.println("Bina: " + elementsBi[i]);

				width = (elementsBi[i].length()) / limit;
			}

			height = i;
			System.out.println("Ancho: " + width);
			System.out.println("Altura: " + height);
			Image ima = createImage(bit, colors, elementsBi, width, height);
			saveImageToFile(ima);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/* Función para pasar un valor hexadecimal y convertirlo a binario */
	public String convertirHexABin(String hexOriginal) {
		String binario = "";
		String digitosHex = "0123456789ABCDEF";
		String hex = hexOriginal;
		int tipoBinario = hexOriginal.indexOf("x");
		if (tipoBinario != -1) {
			hex = hexOriginal.substring(tipoBinario + 1);
		}

		for (int i = 0; i < hex.length(); i++) {
			char c = hex.charAt(i);
			int d = digitosHex.indexOf(c);
			String binarioCuatroDigitos = Integer.toBinaryString(d);

			while (binarioCuatroDigitos.length() < 4) {
				binarioCuatroDigitos = "0" + binarioCuatroDigitos;
			}
			binario += binarioCuatroDigitos;
		}
		return binario;
	}

	public Image createImage(String[] bits, String[] colors, String bina[], int width, int height) {
		WritableImage writableImage = new WritableImage(width, height);
		PixelWriter pixelWriter = writableImage.getPixelWriter();

		for (int y = 0; y < height; y++) {
			/*
			 * Este index tiene la función de recorrer cada fila de mis bina sin que se
			 * pierda ya que tomo valores dependiendo la cantidad de colores y no quiero
			 * recorrer siempre toda el array de binarios, mejor se guarda en el index y ya
			 * solo solo retomo en cada vuelta
			 */
			int index = 0;

			for (int x = 0; x < width; x++) {
				if (index + limit <= bina[y].length()) {
					String substring = bina[y].substring(index, index + limit);

					for (int k = 0; k < bits.length; k++) {
						if (substring.equals(bits[k])) {
							Color color = Color.web(colors[k]);
							pixelWriter.setColor(x, y, color);
						}
					}

					// Actualizo el índice para la siguiente para el siguiente valor del array de
					// binarios
					index += limit;
				}
			}
		}
		return writableImage;
	}

	private void saveImageToFile(Image image) {
		// Abre una ventana para guardar la ruta y ahí poner la imagen
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Guardar Imagen");
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Archivos PNG", "*.png"),
				new FileChooser.ExtensionFilter("Archivos JPEG", "*.jpg", "*.jpeg"));

		File selectedFile = fileChooser.showSaveDialog(primaryStage);

		if (selectedFile != null) {
			// Guarda la imagen en el archivo seleccionado
			try {
				ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", selectedFile);
				System.out.println("Imagen guardada en: " + selectedFile.getAbsolutePath());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}