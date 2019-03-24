
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
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

/**
 *
 * @author Jonathan
 */
public class Datos implements Serializable{

    Scanner dato = new Scanner(System.in);

    public Datos() {
        menu();

    }//Constructor
    
    private void escritura(Persona persona){
        try  {
            File archivo=new File("Archivos\\documento.txt");
            ArrayList<Persona> lista = new ArrayList();
            if(archivo.exists()){
                ObjectInputStream  objectInput = new ObjectInputStream(new FileInputStream(archivo));
                lista = (ArrayList<Persona>) objectInput.readObject();
            }
            eliminarRepetidos(lista, persona);
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
    
    private ArrayList lectura(){
         try(FileInputStream fis=new FileInputStream("Archivos\\documento.txt")){
             ObjectInputStream lectura = new ObjectInputStream(fis);
             //Persona person = (Persona) lectura.readObject();
             ArrayList<Persona> persona = (ArrayList<Persona>) lectura.readObject();
             lectura.close();
             return persona;
        }catch(IOException e){
            return null;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Datos.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }//catch
    }//lectura
    
    private void datosPersona(){
        try {
            System.out.println("Ingrese identificacion de la persona: ");
            int identificacion = dato.nextInt();
            System.out.println("Ingrese el nombre de la persona: ");
            String nombre  = dato.next();
            boolean alfa = Pattern.matches("^[a-zA-Z ]*$", nombre);
            if(!alfa){
                System.out.println("El nombre solamente puede contener letras");
                datosPersona();
            }//if
            System.out.println("Ingrese el apellido de la persona: ");
            String apellido = dato.next();
            boolean alfa2 = Pattern.matches("^[a-zA-Z]*$", apellido);
            if(!alfa2){
                System.out.println("El apellido solamente puede contener letras");
                datosPersona();
            }//if
            Persona persona = new Persona(identificacion, nombre, apellido);
            escritura(persona);
            menu();
        } catch (InputMismatchException e) {
            System.out.println("Datos incorrectos intente de nuevo");
            dato = new Scanner(System.in);
            datosPersona();
        }
        
    }//datos
    
    private Persona eliminarRepetidos(ArrayList<Persona> lista,Persona persona){
        ArrayList<Persona> prueba = new ArrayList<>(lista);
        try {
            for (Persona lista1 : prueba) {
            if(lista1.getId() == persona.getId()){
                lista.remove(lista1);
            }
        }
        } catch (Exception e) {
        }
        
        return persona;
    }//eliminarRepetidos
    
    private void datosRecord(Persona persona){
        try {
            System.out.println("\nIngrese codigo del record: ");
            int codigo = dato.nextInt();
            System.out.println("Ingrese el nombre de la empresa: ");
            String empresa = dato.next();
            System.out.println("Ingrese el valor del record: ");
            float valor = dato.nextFloat();
            Record record = new Record(codigo, empresa, true, valor);
            persona.getRecor().add(record);
            escritura(persona);
            menu();
        } catch (InputMismatchException e) {
            System.out.println("Datos incorrectos intente de nuevo");
            dato = new Scanner(System.in);
            datosRecord(persona);
        }//catch
    }//datosRecord
    
    public void menu(){
        System.out.println("BIENVENIDO");
        System.out.println("A continuacion seleccione la opcion deseada:");
        try {
            System.out.println("1. Agregar Persona");
            System.out.println("2. Añadir Record a persona");
            System.out.println("3. Ver Persona");
            System.out.println("4. Modificar estado de Record");
            System.out.println("5. Eliminar Record");
            System.out.println("6. Salir");
            byte opcion = dato.nextByte();
            seleccion(opcion);
        } catch (InputMismatchException e) {
            System.out.println("Por favor digite un numero valido");
            dato=new Scanner(System.in);
            menu();
        }//catch
    }//menu
    /**
     * metodo que redirige segun la seleccion del usuario
     * @param opcion parametro que recibe la opcion deigitada por el usuario sobre la accion que desea realizar
     */
    private void seleccion(byte opcion){
        switch(opcion){
            case 1:{
                datosPersona();
                break;
            }case 2:{
                int id = capturaIdentificacion();
                Persona persona = tomarPersona(id);
                datosRecord(persona);
                break;
            }case 3:{
                int id = capturaIdentificacion();
                ArrayList<Persona> persona =buscaPersona(id);
                impresion(persona, id);
                break;
            }case 4:{
                cambiarEstado();
                menu();
                break;
            }case 5:{
                eliminarRecord();
                menu();
                break;
            }case 6:{
                System.exit(0);
            }default:{
                System.out.println("Seleccion invalida intente de nuevo");
                menu();
                break;
            }//default
        }//switch
    }//seleccion
    
    private int capturaIdentificacion(){
        System.out.println("Ingrese identificacion de la persona: ");
        int identificacion = dato.nextInt();
        return identificacion;
    }
    /**
     * metodo que realiza la busqueda de una persona por la identificacion
     */
    private ArrayList buscaPersona(int identificacion){
        ArrayList<Persona> lista = lectura();
        ArrayList<Persona> datos = new ArrayList();
        for (Persona persona : lista) {
            if(persona.getId() == identificacion){
                datos.add(persona);
            }
        }return datos;
    }//buscaPersona
    
    private Persona tomarPersona(int identificacion){
        ArrayList<Persona> lista = lectura();
        Persona datos=null;
        for (Persona persona : lista) {
            if(persona.getId() == identificacion){
               datos = persona;
            }
        }return datos;
    }
    /**
     * metodo que realiza la impresion de la persona con sus records respectivos
     * @param persona parametro que recibe el objeto con los datos especificos de la person a mostrar
     */
    private void impresion(ArrayList<Persona> persona, int identificacion){
        Object actual = null;
        boolean bandera=true;
        ArrayList<Persona> lista = null;
        //Persona persona1 = tomarPersona(identificacion);
        //Persona personaFinal = eliminarRepetidos(lista, persona1);
        int contador =0;
      while(contador < persona.size()) {
            actual = persona;
            lista = (ArrayList<Persona>) actual;
            for (int u = 0; u < lista.size(); ++u) {
                if(lista.get(u).getId() == identificacion){
                    if(bandera){
                        System.out.println("\nIdentificacion: " + lista.get(u).getId());
                        System.out.println("Nombre: "+lista.get(u).getNombre());
                        System.out.println("Apellido: "+lista.get(u).getApellido());
                        bandera = false;
                    }//if 
                    for (Persona lista1 : lista) {
                        if(lista1.recor.isEmpty()){
                            System.out.println("La persona no tiene records");
                            contador++;
                        }else{
                        System.out.println("\nRECORD "+(contador+1));
                        System.out.println("Identificacion: "+lista1.recor.get(contador).getCodigo());
                        System.out.println("Empresa: "+lista1.recor.get(contador).getEmpresa());
                        System.out.println("Estado: "+estado(lista1.recor.get(contador).isEstado()));
                        System.out.println("Valor: "+lista1.recor.get(contador).getValor());
                        contador++;
                        }//else
                    }//for
                }//if
            }//for
        }//while
        menu();
    }//impresion
    
    private String estado(boolean estado){
        if(estado){
            return "Positivo";
        }else{
            return "Negativo";
        }
    }//estado
    
    private void cambiarEstado(){
        try {
             System.out.println("Digite la identificacion de la persona: ");
            int identificacion = dato.nextInt();
            Persona persona = tomarPersona(identificacion);
            System.out.println("Digite el codigo del record a modificar: ");
            int codigo = dato.nextInt();
            int contador =0;
            for (Record record : persona.recor) {
                if(persona.recor.get(contador).getCodigo() ==codigo){
                    persona.recor.get(contador).setEstado(false);
                }//if
            }//for
            escritura(persona);
        } catch (InputMismatchException e) {
            System.out.println("Dato incorrecto por favor intente nuevamente");
            dato = new Scanner(System.in);
            cambiarEstado();
        }//catch
    }//cambiarEstado
    
    private void eliminarRecord(){
        Persona persona=null;
        try {
            System.out.println("Digite la identificacion de la persona: ");
            int identificacion = dato.nextInt();
            persona = tomarPersona(identificacion);
            System.out.println("Digite el codigo del record a eliminar: ");
            int codigo = dato.nextInt();
            int contador =0;
            ArrayList<Record> recor = new ArrayList<Record>(persona.recor) ;
            for (Record record : recor) {
                if(persona.recor.get(contador).getCodigo() ==codigo && !persona.recor.get(contador).isEstado()){
                    persona.recor.remove(persona.recor.get(contador));
                }else{
                    System.out.println("El estado es positivo, no se puede eliminar el record");
                }//else
            }//for
            
        } catch (InputMismatchException e) {
            System.out.println("Por favor ingrese datos validos");
            dato = new Scanner(System.in);
            eliminarRecord();
        }//catch
        escritura(persona);
    }//eliminarRecord
}//Menu
