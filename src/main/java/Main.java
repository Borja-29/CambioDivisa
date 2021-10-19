
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;


public class Main extends Application {
	
	private TextField txt1;
	private TextField txt2;
	
	private Button cambiar;
	
	private HBox caja1;
	private HBox caja2;
	private VBox root;
	
	ComboBox<Divisa> dOrigen;
	ComboBox<Divisa> dDestino;
	
	private ObjectProperty<Divisa> selecOrigen = new SimpleObjectProperty<>();
	private ObjectProperty<Divisa> selecDestino = new SimpleObjectProperty<>();
	
	private DoubleProperty cantDinero = new SimpleDoubleProperty(0);
	private DoubleProperty aux = new SimpleDoubleProperty(1);
	private DoubleProperty resultado = new SimpleDoubleProperty(0);
	private Double tasa1=0.0;
	private Double tasa2=0.0;
	
	private StringProperty strDinero = new SimpleStringProperty();
	
	Divisa euro = new Divisa("Euro", 1.0);
	Divisa libra = new Divisa("Libra", 0.8873);
	Divisa dolar = new Divisa("Dolar", 1.2007);
	Divisa yen = new Divisa("Yen", 133.59);

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		txt1 = new TextField();
		txt1.setMaxWidth(50);
		
		txt2 = new TextField();
		txt2.setMaxWidth(50);
		txt2.setEditable(false);
		
		cambiar = new Button("Cambiar");
		cambiar.setOnAction(e -> onCambiarAction(e));
		
		dOrigen = new ComboBox<>();
		dOrigen.getItems().addAll(
				euro, libra, dolar, yen
			);
		dOrigen.getSelectionModel().selectFirst();
		
		dDestino = new ComboBox<>();
		dDestino.getItems().addAll(
				euro, libra, dolar, yen
			);
		dDestino.getSelectionModel().selectFirst();
		
		caja1 = new HBox(5);
		caja1.setAlignment(Pos.CENTER);
		caja1.getChildren().addAll(txt1, dOrigen);
		
		caja2 = new HBox(5);
		caja2.setAlignment(Pos.CENTER);
		caja2.getChildren().addAll(txt2, dDestino);
		
		root = new VBox(5);
		root.setAlignment(Pos.CENTER);
		root.getChildren().addAll(caja1, caja2, cambiar);

		Scene scene = new Scene(root, 320, 200);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Cambio de divisa");
		primaryStage.show();
		
		selecOrigen.bind(dOrigen.getSelectionModel().selectedItemProperty());
		selecDestino.bind(dDestino.getSelectionModel().selectedItemProperty());
		
		txt1.textProperty().bindBidirectional(strDinero);
		strDinero.bindBidirectional(cantDinero, new NumberStringConverter());
		
	}
	
	private void onCambiarAction(ActionEvent e) {
		try {
			if(selecOrigen.getValue().toString().equals(euro.getNombre())) {
				tasa1 = euro.getTasa();
			}else if (selecOrigen.getValue().toString().equals(libra.getNombre())) {
				tasa1 = libra.getTasa();
			}else if (selecOrigen.getValue().toString().equals(dolar.getNombre())) {
				tasa1 = dolar.getTasa();
			}else if (selecOrigen.getValue().toString().equals(yen.getNombre())) {
				tasa1 = yen.getTasa();
			}
			
			if(selecDestino.getValue().toString().equals(euro.getNombre())) {
				tasa2 = euro.getTasa();
			}else if (selecDestino.getValue().toString().equals(libra.getNombre())) {
				tasa2 = libra.getTasa();
			}else if (selecDestino.getValue().toString().equals(dolar.getNombre())) {
				tasa2 = dolar.getTasa();
			}else if (selecDestino.getValue().toString().equals(yen.getNombre())) {
				tasa2 = yen.getTasa();
			}
			; 
			resultado.bind((aux.multiply(tasa2)).multiply(cantDinero).divide(tasa1));
			txt2.textProperty().bindBidirectional(resultado, new NumberStringConverter());
	
			
		}catch(RuntimeException f){
			Alert errorAlerta = new Alert(AlertType.ERROR);
			errorAlerta.setTitle("AdivinApp");
			errorAlerta.setHeaderText("Error");
			errorAlerta.setContentText("El número introducido no es valido.");
			errorAlerta.showAndWait();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

}