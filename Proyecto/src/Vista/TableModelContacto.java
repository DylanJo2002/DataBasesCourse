/**
 * El propósito de esta clase es servir de modelo a la tabla que se muestra
 * en la ventana principal del servidor: nos sirve para manejar de mejor forma
 * los datos de los empleados y a estos como una lista
 *
 * La clase implementa la interfaz TableModel e implementa sus métodos
 */
package Vista;

import Modelo.Contacto;
import java.util.ArrayList;
import java.util.List;
import javax.swing.event.EventListenerList;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

public class TableModelContacto implements TableModel {

    private final int columnas = 7;
    protected EventListenerList listenerList;
    private List<Contacto> contactos;
    

    public TableModelContacto() {
        
        listenerList = new EventListenerList();
        contactos = new ArrayList<>();
    }

    @Override
    public int getRowCount() {
        return contactos.size();
    }

    @Override
    public int getColumnCount() {
        return columnas;
    }

    @Override
    public String getColumnName(int columnIndex) {

        String columnName = "";

        switch (columnIndex) {

            case 0: {
                columnName = "ID";
            }
            break;

            case 1: {
                columnName = "Nombre";
            }
            break;

            case 2: {
                columnName = "Apellido";
            }
            break;

            case 3: {
                columnName = "Edad";
            }
            break;

            case 4: {
                columnName = "Genero";
            }
            break;

            case 5: {
                columnName = "Telefono";
            }
            break;
            case 6: {
                columnName = "Email";
            }
            break;
        }

        return columnName;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return String.class;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        Contacto contacto = contactos.get(rowIndex);
        String valor = "";

        switch (columnIndex) {

            case 0: {
                valor = contacto.getId().toString();
            }
            break;

            case 1: {
                valor = contacto.getNombre();
            }
            break;

            case 2: {
                valor = contacto.getApellido();
            }
            break;

            case 3: {
                valor = contacto.getEdad().toString();
            }
            break;

            case 4: {
                valor = contacto.getGenero().toString();
            }
            break;

            case 5: {
                valor = contacto.getTelefono();
            }
            break;
            
            case 6: {
                valor = contacto.getEmail();
            }
            break;

        }

        return valor;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addTableModelListener(TableModelListener l) {
        listenerList.add(TableModelListener.class, l);

    }

    @Override
    public void removeTableModelListener(TableModelListener l) {
        listenerList.remove(TableModelListener.class, l);
    }
    /**
     * Cambia la lista de empleados que la tabla muestra, recibiendo una lista
     * nueva
     * @param empleados 
     */
    public void setContactos(List<Contacto> contactosNuevos) {
        this.contactos.clear();
        this.contactos = contactosNuevos;

    }
    /**
     * Obtiene el Empleado de una fila seleccionada en la tabla que existe
     * en la misma posición en el arreglo o lista.
    */
    public Contacto getEmpleadoAt(int fila) {

        return contactos.get(fila);
    }

}
