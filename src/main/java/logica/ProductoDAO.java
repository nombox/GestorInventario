/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logica;

import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;

/**
 *
 * @author sebas
 */
public class ProductoDAO {

    //Atributos privados y constantes para la BBDD
    private final String URL = "jdbc:mysql://localhost:3306/inventario?useSSL=false&serverTimezone=UTC";
    private final String USUARIO = "root";
    private final String CONTRASENA = "castelao";

    //Constructor
    public ProductoDAO() {

    }

    //Metodos
    //para conectarse a la base de datos
    public Connection obtenerConexion() {
        Connection conexion = null;

        //Metemos todo en un trycatch por posibles errores de conexion de red
        try {
            
            // forzamos a java a cargar el traductor de mysql para evitar problemas
            Class.forName("com.mysql.cj.jdbc.Driver");            
            
            conexion = DriverManager.getConnection(URL, USUARIO, CONTRASENA);
            System.out.println("Se ha iniciado sesión correctamente");
            
        }catch(ClassNotFoundException ex){
            
            // Este nuevo catch atrapa el error si realmente no se puso la dependencia en Maven
            System.out.println("Error: No se encontró el driver de MySQL en las dependencias.");
        } 
        catch (SQLException e) {
            System.out.println("Error de conexión: " + e.getMessage());
        }

        return conexion;
    }

    //Debe ser booleano para verificar si se guardó o no correctamente
    public boolean registrarProducto(String nombre, int cantidad, double precio, String descripcion) {
        //Se hace la sentencia sql de inserción y se colocan "?" por motivos de seguridad.
        String sql = "INSERT INTO productos (Nombre, Cantidad, Precio, Descripcion) VALUES (?, ?, ?, ?)";

        //Metemos todo en un try with resources, abrimos la conexión y preparamos el statement.
        try (Connection con = obtenerConexion(); PreparedStatement ps = con.prepareStatement(sql)) {

            // se reemplazan los interrogantes con los valores restantes
            ps.setString(1, nombre);
            ps.setInt(2, cantidad);
            ps.setDouble(3, precio);
            ps.setString(4, descripcion);

            //con este método devolvemos el total de filas afectadas
            int filasAfectadas = ps.executeUpdate();

            //Si afectó a más de 0 filas significa que se insertó correctamente
            if (filasAfectadas > 0) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Error de conexión: " + e.getMessage());
        }

        return false;
    }

}
