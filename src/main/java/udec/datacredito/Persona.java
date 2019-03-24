
package udec.datacredito;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jonathan
 */
public class Persona implements Serializable{
    
    private int id;
    
    private String nombre;
    
    private String apellido;
    
    List<Record> recor = new ArrayList();

    public Persona(int id, String nombre, String apellido) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public List<Record> getRecor() {
        return recor;
    }

    public void setRecor(List<Record> recor) {
        this.recor = recor;
    }
    
    
    
}//Persona
