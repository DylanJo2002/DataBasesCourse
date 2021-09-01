/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import Vista.Principal;

/**
 *
 * @author dilan
 */
public class Agenda {

    public Agenda() {
        Thread hiloVista = new Thread(new Principal());
        hiloVista.run();
    }
    
    public static void main(String[] args) {
        Agenda agenda = new Agenda();
    }
    
}
