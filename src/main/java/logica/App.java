package logica;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

/**
 *
 * @author sebastian.eduardo.va
 */
public class App {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
      /*  InterfazVisual gestorInventario = new InterfazVisual();
        gestorInventario.crearEntorno();*/
      
      ProductoDAO operaciones = new ProductoDAO();
      operaciones.obtenerConexion();
      operaciones.registrarProducto("Laptop Canaima", 12, 20, "Laptop China del gobierno de Venezuela");
    }
    
}
