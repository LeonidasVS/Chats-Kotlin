/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package semana4;

/**
 *
 * @author ALUMNO Anderson Cisneros... 
 */
public class Gatito {
    
    private String Nombre;
    private String Raza;
    private int Edad;
    private String Sonido;
    
    public Gatito(String Nombre, String Raza, int Edad, String Sonido) {
        this.Nombre = Nombre;
        this.Raza = Raza;
        this.Edad = Edad;
        this.Sonido = Sonido;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getRaza() {
        return Raza;
    }

    public void setRaza(String Raza) {
        this.Raza = Raza;
    }

    public int getEdad() {
        return Edad;
    }

    public void setEdad(int Edad) {
        this.Edad = Edad;
    }

    public String getSonido() {
        return Sonido;
    }

    public void setSonido(String Sonido) {
        this.Sonido = Sonido;
    }

    @Override
    public String toString() {
        return "Gatito{" + "Nombre = " + Nombre + ", Raza = " + Raza + ", Edad = " + Edad + ", Sonido = " + Sonido + '}';
    }
    
}
