
package it.polito.tdp.borders;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import org.jgrapht.graph.DefaultEdge;

import it.polito.tdp.borders.model.Country;
import it.polito.tdp.borders.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

	private Model model;
	
	  @FXML
	    private ResourceBundle resources;

	    @FXML
	    private URL location;

	    @FXML
	    private TextField txtAnno;

	    @FXML
	    private ComboBox<Country> comboStato;

	    @FXML
	    private Button Cerca;

	    @FXML
	    private TextArea txtResult;

	   

	    @FXML
	    void doCerca(ActionEvent event) {
	    	txtResult.clear();
	    	Country c = comboStato.getValue();
	    	if(c!=null) {
//	    		txtResult.setText(this.model.cercaPercorsoBFI(c));
//	    		txtResult.setText(this.model.cercaPercorsoricorsivo(c));
	    		txtResult.setText(this.model.cercaPercorsoIt(c));
	    	}
	    	else {
	    		txtResult.setText("Devi prima selezionare uno stato di partenza ");
	    	}

	    }

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
    			comboStato.getItems().addAll(model.getVertexSet());
    			Cerca.setDisable(false);
    			
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
