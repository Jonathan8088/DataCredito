
package udec.datacredito;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

/**
 *
 * @author Jonathan
 */
public class Datos implements Serializable{

    

    public Datos() {
        datos();
         lectura();
    }//Constructor
    
    private void escritura(Persona persona){
        try  {
            File archivo=new File("Archivos\\documento.txt");
            ArrayList<Persona> lista = new ArrayList();
            if(archivo.exists()){
                ObjectInputStream  objectInput = new ObjectInputStream(new FileInputStream(archivo));
                lista = (ArrayList<Persona>) objectInput.readObject();
            }
            lista.add(persona);
            ObjectOutputStream escritura = new ObjectOutputStream( new FileOutputStream(archivo));
            
            escritura.writeObject(lista);
 
            escritura.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Datos.class.getName()).log(Level.SEVERE, null, ex);
        }//catch
    }//escitura
    
    private void lectura(){
         try(FileInputStream fis=new FileInputStream("Archivos\\documento.txt")){
             ObjectInputStream lectura = new ObjectInputStream(fis);
             //Persona person = (Persona) lectura.readObject();

            Object actual = null;
        ArrayList<Persona> lista = null;
        while((actual = lectura.readObject()) != null) {
            lista = (ArrayList<Persona>) actual;
            for (int u = 0; u < lista.size(); ++u) {
                System.out.println(" Identificacion: " + lista.get(u).getId());
                System.out.println("Nombre: "+lista.get(u).getNombre());
                System.out.println("Apellido: "+lista.get(u).getApellido());
            }
        }
             lectura.close();
        }catch(IOException e){
 
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Datos.class.getName()).log(Level.SEVERE, null, ex);
        }//catch
    }//lectura
    
    private void datos(){
        Scanner dato = new Scanner(System.in);
        try {
            System.out.println("Ingrese identificacion de la persona: ");
            int identificacion = dato.nextInt();
            System.out.println("Ingrese el nombre de la persona: ");
            String nombre  = dato.next();
            boolean alfa = Pattern.matches("^[a-zA-Z]*$", nombre);
            if(!alfa){
                System.out.println("El nombre solamente puede contener letras");
                datos();
            }//if
            System.out.println("Ingrese el apellido de la persona: ");
            String apellido = dato.next();
            boolean alfa2 = Pattern.matches("^[a-zA-Z]*$", apellido);
            if(!alfa2){
                System.out.println("El apellido solamente puede contener letras");
                datos();
            }//if
            Persona persona = new Persona(identificacion, nombre, apellido);
            escritura(persona);
        } catch (InputMismatchException e) {
            System.out.println("Datos incorrectos intente de nuevo");
            datos();
        }
        
    }//datos
}//Menu
