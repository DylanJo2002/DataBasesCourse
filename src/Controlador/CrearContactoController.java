/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Contacto;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * FXML Controller class
 *
 * @author dilan
 */
public class CrearContactoController implements Initializable {

    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtApellido;
    @FXML
    private TextField txtEdad;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtTelefono;
    @FXML
    private ComboBox<String> cbxGenero;
    @FXML
    private Button btnContacto;
    private Principal_Controlador_FXML principalControlador;
    
    public void setPrincipalControlador(Principal_Controlador_FXML principalControlador){
        this.principalControlador = principalControlador;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<String> itemsCombo = FXCollections.observableArrayList();
        itemsCombo.addAll("Femenino","Masculino");
        cbxGenero.setItems(itemsCombo);
    }    

    @FXML
    private void btAction(ActionEvent event) {
        String nombres = txtNombre.getText().trim();
        String apellidos = txtApellido.getText().trim();
        String edad = txtEdad.getText().trim();
        String email = txtEmail.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String genero = cbxGenero.getSelectionModel().getSelectedItem();
        try {
            int enteroEdad = Integer.parseInt(edad);
            
            if(!(nombres.isEmpty() || apellidos.isEmpty() || edad.isEmpty()
                || email.isEmpty() || telefono.isEmpty() || 
                        genero.equals("SELECCIONAR"))){
            
            Contacto nuevoContacto = new Contacto();
            nuevoContacto.setNombre(nombres);
            nuevoContacto.setApellido(apellidos);
            nuevoContacto.setEdad(enteroEdad);
            nuevoContacto.setEmail(email);
            nuevoContacto.setGenero(genero.charAt(0));
            nuevoContacto.setTelefono(telefono);
            
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("AgendaPU");
            ContactoJpaController service = new ContactoJpaController (emf);
            
            service.create(nuevoContacto);
            
            principalControlador.cargarContactos();
            
            alert("Operación existosa", "Contacto agregado");
            
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
            return;
            }
            
            throw new Exception("Campos inválidos"); 
            
        } catch(Exception ex){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }

    }
    
    private void alert(String titulo, String cuerpo){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(cuerpo);
        alert.showAndWait();
    }
    
}
