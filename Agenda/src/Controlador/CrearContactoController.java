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

    public void setPrincipalControlador(Principal_Controlador_FXML principalControlador) {
        this.principalControlador = principalControlador;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<String> itemsCombo = FXCollections.observableArrayList();
        itemsCombo.addAll("M", "F");
        cbxGenero.setItems(itemsCombo);
    }

    @FXML
    private void btAction(ActionEvent event) {
        String[] entradas = new String[5];
        entradas[0] = txtNombre.getText().trim();
        entradas[1] = txtApellido.getText().trim();
        entradas[2] = txtEdad.getText().trim();
        entradas[3] = txtEmail.getText().trim();
        entradas[4] = txtTelefono.getText().trim();
        String genero = cbxGenero.getSelectionModel().getSelectedItem();
        try {
            int enteroEdad = Integer.parseInt(entradas[2]);

            if (validarInputs(entradas, genero, enteroEdad)) {

                Contacto nuevoContacto = new Contacto();
                nuevoContacto.setNombre(entradas[0]);
                nuevoContacto.setApellido(entradas[1]);
                nuevoContacto.setEdad(enteroEdad);
                nuevoContacto.setEmail(entradas[3]);
                nuevoContacto.setGenero(genero.charAt(0));
                nuevoContacto.setTelefono(entradas[4]);

                try {
                    EntityManagerFactory emf
                            = Persistence.createEntityManagerFactory("AgendaPU");
                    ContactoJpaController service = new ContactoJpaController(emf);

                    service.create(nuevoContacto);

                    principalControlador.cargarContactos();

                    alert("Operación existosa", "Contacto agregado");
                } catch (Exception ex) {
                    principalControlador.alertarConexion(ex);
                }
                Node source = (Node) event.getSource();
                Stage stage = (Stage) source.getScene().getWindow();
                stage.close();
                return;
            }

            throw new Exception("Campos inválidos");

        } catch (Exception ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Las entradas no son válidas");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }

    }

    private boolean validarInputs(String[] textInputs, String comboInput,
            int enteroEdad) {

        for (String textInput : textInputs) {
            if (textInput.isEmpty()) {
                return false;
            }
            if (textInputs[textInputs.length - 1] == textInput) {
                if (textInput.length() > 10) {
                    return false;
                }
            } else {
                if (textInput.length() > 100) {
                    return false;
                }
            }
        }
        if (comboInput.equals("SELECCIONAR") || enteroEdad < 0
                || enteroEdad > 120) {
            return false;
        }

        return true;

    }

    private void alert(String titulo, String cuerpo) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(cuerpo);
        alert.showAndWait();
    }

}
