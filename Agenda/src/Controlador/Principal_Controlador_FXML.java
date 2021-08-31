/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Agenda.Agenda;
import Controlador.exceptions.NonexistentEntityException;
import Modelo.Contacto;
import javafx.event.EventHandler;
import javafx.scene.control.TableColumn.CellEditEvent;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.converter.DefaultStringConverter;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author dilan
 */
public class Principal_Controlador_FXML implements Initializable {

    @FXML
    private Button btnBorrar;
    @FXML
    private ImageView imagenConexion;
    @FXML
    private Label lblConexion;
    @FXML
    private Button btnCrear;
    @FXML
    private AnchorPane AnchorPane1;
    @FXML
    private TableView<Contacto> tabla;
    @FXML
    private TableColumn<Contacto, String> columnaID;
    @FXML
    private TableColumn<Contacto, String> columnaNombres;
    @FXML
    private TableColumn<Contacto, String> columnaApellidos;
    @FXML
    private TableColumn<Contacto, String> columnaEdad;
    @FXML
    private TableColumn<Contacto, String> columnaGenero;
    @FXML
    private TableColumn<Contacto, String> columnaTelefono;
    @FXML
    private TableColumn<Contacto, String> columnaEmail;

    public void cargarContactos() throws Exception {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("AgendaPU");
        ContactoJpaController service = new ContactoJpaController(emf);
        List<Contacto> contactos = service.findContactoEntities();
        tabla.getItems().setAll(contactos);

        for (TableColumn<Contacto, ?> columna : tabla.getColumns()) {
            addTooltipToColumnCells(columna);
        }

        tabla.getSelectionModel().focus(-1);

    }

    private static <T> void addTooltipToColumnCells(TableColumn<Contacto, T> column) {

        Callback<TableColumn<Contacto, T>, TableCell<Contacto, T>> existingCellFactory
                = column.getCellFactory();

        column.setCellFactory(c -> {
            TableCell<Contacto, T> cell = existingCellFactory.call(c);

            Tooltip tooltip = new Tooltip();
            tooltip.textProperty().bind(cell.itemProperty().asString());

            cell.setTooltip(tooltip);
            return cell;
        });
    }

    public void onActionBtnBorrar() {

        int indiceSeleccionado = tabla.getSelectionModel().getFocusedIndex();

        if (indiceSeleccionado != -1) {
            Contacto contacto = tabla.getItems().get(indiceSeleccionado);
            String alerta = "¿Estás seguro de eliminar a ";
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("ADVERTENCIA");
            alert.setContentText(alerta.concat(contacto.getNombre()).concat("?"));

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                EntityManagerFactory emf = Persistence.createEntityManagerFactory("AgendaPU");
                ContactoJpaController service = new ContactoJpaController(emf);

                try {
                    service.destroy(contacto.getId());
                    cargarContactos();
                } catch (Exception ex) {
                    alertarConexion(ex);
                }
            }

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("Debe seleccionar un contacto para borrarla");
            alert.showAndWait();
        }

    }

    public void onActionBtnCrear(ActionEvent event) {
        System.out.println("Controlador.Principal_Controlador_FXML.onActionBtnCrear()");
        Map<String, URL> URLs = Agenda.generarURLs();

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(URLs.get("crearContacto"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Nuevo contacto");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL.WINDOW_MODAL);
            stage.initOwner(
                    ((Node) event.getSource()).getScene().getWindow());
            CrearContactoController controlador = (CrearContactoController) fxmlLoader.getController();
            controlador.setPrincipalControlador(this);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void setColumnsCellFactory() {
        columnaNombres.setCellFactory(TextFieldTableCell.forTableColumn());
        columnaApellidos.setCellFactory(TextFieldTableCell.forTableColumn());
        columnaEdad.setCellFactory(TextFieldTableCell.forTableColumn());
        columnaGenero.setCellFactory(ComboBoxTableCell.forTableColumn(
                new DefaultStringConverter(), "M", "F"));
        columnaTelefono.setCellFactory(TextFieldTableCell.forTableColumn());
        columnaEmail.setCellFactory(TextFieldTableCell.forTableColumn());
    }

    public void alertarConexion(Exception ex) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERROR DE CONEXIÓN");
        alert.setHeaderText("Ocurrió un error en la conexión el SMBD. "
                .concat("Por favor, verifique que el servicio esté activo y ")
                .concat("que exista la base de datos <agenda> junto a la tabla ")
                .concat("<contacto>"));
        alert.setContentText(ex.getMessage());
        alert.showAndWait();
    }

    public void setCellsCommit() {

        columnaNombres.setOnEditCommit(new EventHandler<CellEditEvent<Contacto, String>>() {
            @Override
            public void handle(CellEditEvent<Contacto, String> event) {
                Contacto person = event.getRowValue();
                String nuevoValor = event.getNewValue().trim();

                if (!nuevoValor.isEmpty() && nuevoValor.length() <= 100) {
                    Contacto contactoABD = new Contacto(person);
                    contactoABD.setNombre(nuevoValor);
                    try {
                        editContactDatabase(contactoABD);
                        person.setNombre(nuevoValor);
                    } catch (Exception ex) {
                        alertarConexion(ex);
                    }

                }

                tabla.refresh();
            }
        });

        columnaApellidos.setOnEditCommit(new EventHandler<CellEditEvent<Contacto, String>>() {
            @Override
            public void handle(CellEditEvent<Contacto, String> event) {
                Contacto person = event.getRowValue();
                String nuevoValor = event.getNewValue().trim();
                if (!nuevoValor.isEmpty() && nuevoValor.length() <= 100) {
                    Contacto contactoABD = new Contacto(person);
                    contactoABD.setApellido(nuevoValor);
                    try {
                        editContactDatabase(contactoABD);
                        person.setApellido(nuevoValor);

                    } catch (Exception ex) {
                        alertarConexion(ex);
                    }

                }
                tabla.refresh();
            }
        });

        columnaEdad.setOnEditCommit(new EventHandler<CellEditEvent<Contacto, String>>() {
            @Override
            public void handle(CellEditEvent<Contacto, String> event) {
                Contacto person = event.getRowValue();
                try {
                    int nuevaEdad = Integer.parseInt(event.getNewValue());
                    if (nuevaEdad >= 0 && nuevaEdad <= 120) {
                        Contacto contactoABD = new Contacto(person);
                        contactoABD.setEdad(nuevaEdad);
                        try {
                            editContactDatabase(contactoABD);
                            person.setEdad(nuevaEdad);
                        } catch (Exception ex) {
                            alertarConexion(ex);
                        }

                        return;
                    }
                    throw new NumberFormatException();
                } catch (NumberFormatException ne) {

                }
                tabla.refresh();
            }
        });

        columnaGenero.setOnEditCommit(new EventHandler<CellEditEvent<Contacto, String>>() {
            @Override
            public void handle(CellEditEvent<Contacto, String> event) {
                Contacto person = event.getRowValue();
                String genero = event.getNewValue();
                Contacto contactoABD = new Contacto(person);
                contactoABD.setGenero(genero.charAt(0));
                try {
                    editContactDatabase(contactoABD);
                    person.setGenero(genero.charAt(0));
                } catch (Exception ex) {
                    alertarConexion(ex);
                }
                tabla.refresh();
            }
        });

        columnaTelefono.setOnEditCommit(new EventHandler<CellEditEvent<Contacto, String>>() {
            @Override
            public void handle(CellEditEvent<Contacto, String> event) {
                Contacto person = event.getRowValue();
                String nuevoValor = event.getNewValue().trim();
                if (!nuevoValor.isEmpty() && nuevoValor.length() <= 10) {
                    Contacto contactoABD = new Contacto(person);
                    contactoABD.setTelefono(nuevoValor);
                    try {
                        editContactDatabase(contactoABD);
                        person.setTelefono(nuevoValor);
                    } catch (Exception ex) {
                        alertarConexion(ex);
                    }

                }
                tabla.refresh();
            }
        });

        columnaEmail.setOnEditCommit(new EventHandler<CellEditEvent<Contacto, String>>() {
            @Override
            public void handle(CellEditEvent<Contacto, String> event) {
                Contacto person = event.getRowValue();
                String nuevoValor = event.getNewValue().trim();
                if (!nuevoValor.isEmpty() && nuevoValor.length() <= 100) {
                    Contacto contactoABD = new Contacto(person);
                    contactoABD.setEmail(nuevoValor);
                    try {
                        editContactDatabase(contactoABD);
                        person.setEmail(nuevoValor);
                    } catch (Exception ex) {
                        alertarConexion(ex);
                    }
                }
                tabla.refresh();
            }
        });
    }

    public void editContactDatabase(Contacto contacto) throws Exception {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("AgendaPU");
        ContactoJpaController service = new ContactoJpaController(emf);

        service.edit(contacto);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        columnaID.setCellValueFactory(new PropertyValueFactory<Contacto, String>("id"));
        columnaNombres.setCellValueFactory(new PropertyValueFactory<Contacto, String>("nombre"));
        columnaApellidos.setCellValueFactory(new PropertyValueFactory<Contacto, String>("apellido"));
        columnaEdad.setCellValueFactory(new PropertyValueFactory<Contacto, String>("edad"));
        columnaGenero.setCellValueFactory(new PropertyValueFactory<Contacto, String>("genero"));
        columnaTelefono.setCellValueFactory(new PropertyValueFactory<Contacto, String>("telefono"));
        columnaEmail.setCellValueFactory(new PropertyValueFactory<Contacto, String>("email"));

        setColumnsCellFactory();
        setCellsCommit();
        for (TableColumn<Contacto, ?> columna : tabla.getColumns()) {
            columna.setResizable(false);
        }
        try {
            cargarContactos();
        } catch(Exception e){
            alertarConexion(e);
            System.exit(0);
        }
    }
}
