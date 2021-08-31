/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Contacto;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.JOptionPane;

/**
 *
 * @author dilan
 */
public class fachada {

    public static List<Contacto> obtenerContactos() throws Exception {

        List<Contacto> contactos = new ArrayList<Contacto>();

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("AgendaPU");
        ContactoJpaController service = new ContactoJpaController(emf);
        contactos = service.findContactoEntities();

        return contactos;
    }

    public static void editarContacto(Contacto contacto) throws Exception {
        ContactoJpaController service = crearService();

        service.edit(contacto);
    }

    public static void crear(Contacto contacto) throws Exception {
        ContactoJpaController service = crearService();

        service.create(contacto);
    }

    public static void eliminarContacto(int id) throws Exception{
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AgendaPU");
        ContactoJpaController service = crearService();
        
        service.destroy(id);
    }

    private static ContactoJpaController crearService(){
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AgendaPU");
        ContactoJpaController service = new ContactoJpaController(emf);

        return service;
    }
    public static void alertar(String mensaje, int tipo) {
        JOptionPane.showMessageDialog(null, mensaje,
                "ERROR", tipo);

    }

}
