package Agenda;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author dilan
 */
public class Agenda extends Application {
    
    static Map<String, URL> URLs;
    
    public static Map<String, URL> generarURLs(){
        
       URLs = new HashMap<String, URL>();
        
        try {
            Path principalFXML = Paths.get("src/Vista/Principal.fxml").
                    toAbsolutePath();
            URLs.put("principal", principalFXML.toUri().toURL());
            
            Path crearContactoFXML = Paths.get("src/Vista/CrearContacto.fxml").
                    toAbsolutePath();
            URLs.put("crearContacto", crearContactoFXML.toUri().toURL());           
            
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        }
        return URLs;
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        Map<String, URL> FileURLs = generarURLs();
        Parent root = FXMLLoader.load(FileURLs.get("principal"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.setTitle("Agenda de contactos");
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
