
package it.polito.tdp.borders;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.borders.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

	private Model model;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtAnno"
    private TextField txtAnno; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCalcolaConfini(ActionEvent event) {
    	txtResult.clear();
    	String anno = txtAnno.getText();
    	try {
    		int a = Integer.parseInt(anno);
    		if(a>=1816&&a<=2006) {
    			model.creaGrafo();
    			model.caricaGrafo(a);
//    			txtResult.appendText("il numero di vertici : "+model.getNVertici()+"\n");
//    			txtResult.appendText("il numero di archi : "+model.getNArchi()+"\n");
    			txtResult.appendText(model.getComponentiConnesse());
    			txtResult.appendText(model.getVertici()+"\n");
    		}
    		else {
    			txtResult.setText("Devi inserire un anno compreso tra 1816 e 2006");
    		}
    	}
    	catch(NumberFormatException e ) {
    		txtResult.setText("Hai inserito l'anno in un formato non corretto");

    }

   
	}

	@FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtAnno != null : "fx:id=\"txtAnno\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    }
}
