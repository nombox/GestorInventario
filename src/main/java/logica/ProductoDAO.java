/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logica;

import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

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

        } catch (ClassNotFoundException ex) {

            // Este nuevo catch atrapa el error si realmente no se puso la dependencia en Maven
            System.out.println("Error: No se encontró el driver de MySQL en las dependencias.");
        } catch (SQLException e) {
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

    //Metodo para realizar una query SELECT en una tabla
    public List<Producto> consultarProductos() {

        //Creo una lista para guardar los productos de la consulta
        List<Producto> lista = new ArrayList<>();

        //Se hace la sentencia sql de SELECT
        String sql = "SELECT * FROM productos";

        //Metemos todo en un try with resources, abrimos la conexión y preparamos el statement.
        try (Connection con = obtenerConexion(); PreparedStatement ps = con.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();

            //Recorro cada una de las filas de la tabla y obtengo sus datos
            while (rs.next()) {
                //Los guardo en un nuevo objeto tipo Producto
                Producto p = new Producto(
                        rs.getInt("Id_producto"),
                        rs.getString("Nombre"),
                        rs.getInt("Cantidad"),
                        rs.getDouble("Precio"),
                        rs.getString("Descripcion")
                );

                //lo añadimos a la lista
                lista.add(p);
            }
        } catch (SQLException e) {
            System.out.println("Error de conexión: " + e.getMessage());
        }

        //Devolvemos la lista para insertar en la JTabla
        return lista;
    }

    //Metodo para actualizar un producto en la tabla
    public boolean actualizarProducto(String nombre, int cantidad, double precio, String descripcion, int id_producto) {

        //Se hace la sentencia sql de ACTUALIZACION
        String sql = "UPDATE productos set Nombre=?, Cantidad=?, Precio=?, Descripcion=? where Id_producto=?";

        //Metemos todo en un try with resources, abrimos la conexión y preparamos el statement.
        try (Connection con = obtenerConexion(); PreparedStatement ps = con.prepareStatement(sql)) {

            // se reemplazan los interrogantes con los valores restantes
            ps.setString(1, nombre);
            ps.setInt(2, cantidad);
            ps.setDouble(3, precio);
            ps.setString(4, descripcion);
            ps.setInt(5, id_producto);

            //con este método devolvemos el total de filas afectadas
            int filasAfectadas = ps.executeUpdate();

            //Si afectó a más de 0 filas significa que se actualizó correctamente
            if (filasAfectadas > 0) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Error de conexión: " + e.getMessage());
        }

        return false;
    }

    //Metodo para borrar un producto en la tabla
    public boolean borrarProducto(int id_producto) {

        //Se hace la sentencia sql de ACTUALIZACION
        String sql = "DELETE FROM productos where Id_producto=?";

        //Metemos todo en un try with resources, abrimos la conexión y preparamos el statement.
        try (Connection con = obtenerConexion(); PreparedStatement ps = con.prepareStatement(sql)) {

            // se reemplazan los interrogantes con los valores restantes
            ps.setInt(1, id_producto);

            //con este método devolvemos el total de filas afectadas
            int filasAfectadas = ps.executeUpdate();

            //Si afectó a más de 0 filas significa que se eliminó correctamente
            if (filasAfectadas > 0) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Error de conexión: " + e.getMessage());
        }

        return false;
    }

}
