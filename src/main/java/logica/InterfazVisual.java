package logica;

import java.util.List;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
/**
 *
 * @author sebastian.eduardo.va
 */
public class InterfazVisual {

    //Atributos contenedores
    private JFrame ventana;
    private JPanel panelEtiqueta;
    private JPanel panelTabla;
    private JPanel panelDatos;
    private JPanel panelBotones;

    //Atributos botones
    private JButton botonRegistrar;
    private JButton botonActualizar;
    private JButton botonBorrar;
    private JButton botonLimpiar;

    //Atributos de registros
    private JTextField campoNombre;
    private JTextField campoCantidad;
    private JTextField campoPrecio;
    private JTextField campoDescripcion;
    private JTextField campoIdProducto; //Este atributo no se ve y solo lo puedo editar yo

    //Atributos tablas
    private JTable tablaResultados;
    private DefaultTableModel modeloTabla;

    //Atributo para consultas
    private ProductoDAO consulta;

    //Contructor
    public InterfazVisual() {
        this.ventana = new JFrame("Gestion de inventario");
        this.panelEtiqueta = new JPanel();
        this.panelTabla = new JPanel();
        this.panelDatos = new JPanel();
        this.panelBotones = new JPanel();

        this.botonRegistrar = new JButton("Registrar");
        this.botonActualizar = new JButton("Actualizar");
        this.botonBorrar = new JButton("Borrar");
        this.botonLimpiar = new JButton("Limpiar registros");

        this.campoNombre = new JTextField();
        this.campoCantidad = new JTextField();
        this.campoPrecio = new JTextField();
        this.campoDescripcion = new JTextField();
        this.campoIdProducto = new JTextField();

        this.tablaResultados = new JTable();

        //Hay que sobreeescribir el método isCellEditable para que devuelva false
        this.modeloTabla = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; //de esta manera no se pueden editar las celdas
            }
        };

        this.consulta = new ProductoDAO();

    }

    public void crearEntorno() {
        //Creo la ventana
        ventana.setSize(1000, 800);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setLocationRelativeTo(null);
        ventana.setLayout(new BorderLayout());

        // --- ELEMENTOS DEL NORTH ---
        JLabel etiquetaPrincipal = new JLabel("Registro de inventario", JLabel.CENTER);
        etiquetaPrincipal.setFont(new Font("Arial", Font.BOLD, 24));

        panelEtiqueta.add(etiquetaPrincipal);
        // -------------------------------

        // --- ELEMENTOS DEL CENTER ---
        this.panelDatos.setLayout(new GridLayout(4, 2));

        JLabel etiquetaNombre = new JLabel("Nombre del producto");
        this.panelDatos.add(etiquetaNombre);
        this.panelDatos.add(this.campoNombre);

        JLabel etiquetaCantidad = new JLabel("Cantidad");
        this.panelDatos.add(etiquetaCantidad);
        this.panelDatos.add(this.campoCantidad);

        JLabel etiquetaPrecio = new JLabel("Precio");
        this.panelDatos.add(etiquetaPrecio);
        this.panelDatos.add(this.campoPrecio);

        JLabel etiquetaDescripcion = new JLabel("Descripción");
        this.panelDatos.add(etiquetaDescripcion);
        this.panelDatos.add(this.campoDescripcion);
        // -------------------------------

        // --- ELEMENTOS DEL SOUTH ---
        this.panelTabla.setLayout(new BorderLayout());

        this.panelBotones.setLayout(new FlowLayout());
        this.panelBotones.add(this.botonRegistrar);
        this.panelBotones.add(this.botonActualizar);
        this.panelBotones.add(this.botonBorrar);
        this.panelBotones.add(this.botonLimpiar);

        //Creo la tabla con sus columnas
        this.modeloTabla.addColumn("ID");
        this.modeloTabla.addColumn("Nombre");
        this.modeloTabla.addColumn("Cantidad");
        this.modeloTabla.addColumn("Precio");
        this.modeloTabla.addColumn("Descripción");

        this.tablaResultados.setModel(this.modeloTabla);

        this.panelTabla.add(this.panelBotones, BorderLayout.NORTH);
        this.panelTabla.add(new JScrollPane(this.tablaResultados), BorderLayout.CENTER);

        // -------------------------------
        // --- EVENTOS ---
        //para mostrar los datos de la tabla al crear la interfaz
        mostrarDatos();

        //Los campos de texto
        agregarValidador(this.campoCantidad);
        agregarValidador(this.campoPrecio);

        //Botones
        //Para agregar registrar los productos
        agregarInsertarDatos(this.botonRegistrar);
        //Para vaciar los campos
        agregarVaciadoCampos(this.botonLimpiar);
        //Para actualizar producto
        agregarActualizarDatos(this.botonActualizar);
        //Para borrar el producto
        agregarBorrarDatos(this.botonBorrar);

        //Tabla
        //Para seleccionar cada fila de la tabla
        agregarEventoTabla(this.tablaResultados);

        //-----------------
        // agrego elementos y hago visible la ventana
        this.ventana.add(this.panelEtiqueta, BorderLayout.NORTH);
        this.ventana.add(this.panelDatos, BorderLayout.CENTER);
        this.ventana.add(this.panelTabla, BorderLayout.SOUTH);
        this.ventana.setVisible(true);

    }

    //Metodo para validar que los campos estén llenos
    private boolean validacionCampos() {

        //Si el Nombre está vacío
        if (this.campoNombre.getText().isEmpty()) {
            JOptionPane.showMessageDialog(ventana, "Debes agregar un nombre");
            return false;
        }

        //Si la cantidad está vacía
        if (this.campoCantidad.getText().isEmpty()) {
            JOptionPane.showMessageDialog(ventana, "Debes agregar una cantidad");
            return false;
        }

        //Si el precio está vacío
        if (this.campoPrecio.getText().isEmpty()) {
            JOptionPane.showMessageDialog(ventana, "Debes agregar un precio");
            return false;
        }

        //Si el precio está vacío
        if (this.campoDescripcion.getText().isEmpty()) {
            JOptionPane.showMessageDialog(ventana, "Debes agregar una descripción");
            return false;
        }

        return true;
    }

    //Metodo para agregar la limpieza de campos al boton
    private void agregarVaciadoCampos(JButton botonLimpiar) {
        botonLimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vaciarCampos();
            }
        });
    }

    //Metodo para vaciar los campos
    private void vaciarCampos() {
        this.campoNombre.setText("");
        this.campoCantidad.setBackground(Color.WHITE);
        this.campoCantidad.setText("");
        this.campoPrecio.setBackground(Color.WHITE);
        this.campoPrecio.setText("");
        this.campoDescripcion.setText("");
        this.campoIdProducto.setText("");
    }

    //Metodo para agregar la insercion de datos al boton
    private void agregarInsertarDatos(JButton botonRegistrar) {
        botonRegistrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                insertarDatos();
            }
        });
    }

    //Metodo para hacer una insercion de datos (INSERT)
    private void insertarDatos() {

        //validamos que los campos no estén vacíos
        if (!validacionCampos()) {
            return;
        }
        
        //Validamos que no se trate de agregar un producto que ya existe
        if(!this.campoIdProducto.getText().isEmpty()){
            //Para verificar que el usuario seleccionó un producto a cambiar
            JOptionPane.showMessageDialog(ventana, "No puedes agregar un producto existente");
            return;
        }

        String nombre = this.campoNombre.getText();
        int cantidad = Integer.parseInt(this.campoCantidad.getText());
        double precio = Double.parseDouble(this.campoPrecio.getText());
        String descripcion = this.campoDescripcion.getText();

        if (this.consulta.registrarProducto(nombre, cantidad, precio, descripcion)) {
            JOptionPane.showMessageDialog(ventana, "Producto agregado con éxito");
            mostrarDatos();
            vaciarCampos();
        } else {
            JOptionPane.showMessageDialog(ventana, "Error al agregar el producto");
        }

    }

    //Metodo para agregar el evento a la tabla
    private void agregarEventoTabla(JTable tablaResultado) {
        tablaResultado.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                enviarDatosTablaAcampo(tablaResultado); //Agrego la tabla que quiero cambiar por si hay más en el futuro
            }
        });

    }

    //Metodo para enviar los datos de la fila a los campos
    private void enviarDatosTablaAcampo(JTable tablaResultado) {

        //Al hacer click se obtiene el índice de la fila
        int filaSeleccionada = tablaResultado.getSelectedRow();

        //Si la fila es una fila real (no es -1)
        if (filaSeleccionada != -1) {

            //Vamos a extraer el contenido de cada fila con el número de fila y columna para ponerlo en los campos
            campoIdProducto.setText(modeloTabla.getValueAt(filaSeleccionada, 0).toString());
            campoNombre.setText(modeloTabla.getValueAt(filaSeleccionada, 1).toString());
            campoCantidad.setText(modeloTabla.getValueAt(filaSeleccionada, 2).toString());
            campoPrecio.setText(modeloTabla.getValueAt(filaSeleccionada, 3).toString());
            campoDescripcion.setText(modeloTabla.getValueAt(filaSeleccionada, 4).toString());
        }
    }

    //Metodo para agregar actualizacion de datos
    private void agregarActualizarDatos(JButton botonActualizar) {
        botonActualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarDatos();
            }

        });

    }

    //Metodo para hacer una actualizacion de datos (UPDATE)
    private void actualizarDatos() {
        //Validamos que los campos no estén en blanco una vez seleccionados
        if (!validacionCampos()) {
            return;
        }
        
        if(this.campoIdProducto.getText().isEmpty()){
            //Para verificar que el usuario seleccionó un producto a cambiar
            JOptionPane.showMessageDialog(ventana, "Debes elegir un producto");
            return;
        }

        //Parseamos los datos como en la inserción, incluyendo el campo ID
        int id = Integer.parseInt(this.campoIdProducto.getText());
        String nombre = this.campoNombre.getText();
        int cantidad = Integer.parseInt(this.campoCantidad.getText());
        double precio = Double.parseDouble(this.campoPrecio.getText());
        String descripcion = this.campoDescripcion.getText();

        //Enviamos los datos a la clase DAO
        if (this.consulta.actualizarProducto(nombre, cantidad, precio, descripcion, id)) {
            JOptionPane.showMessageDialog(ventana, "Producto actualizado con éxito.");
            mostrarDatos(); // Refrescamos la tabla
            vaciarCampos(); // Limpiamos los campos (y el ID)        
        } else {
            JOptionPane.showMessageDialog(ventana, "Error al actualizar el producto.");
        }

    }
    
    //Metodo para agregarBorrarDatos al boton
    private void agregarBorrarDatos(JButton botonEliminar){
        botonEliminar.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
               borrarDatos();
            }
        });
    }

    //Metodo para borrar datos (DELETE)
    private void borrarDatos() {
        if(this.campoIdProducto.getText().isEmpty()){
        //Para verificar que el usuario seleccionó un producto a cambiar
        JOptionPane.showMessageDialog(ventana, "Debes elegir un producto");
        return;
        }
        
        //Se debe pedir confirmación al usuario antes de borrar
        int confirmacion = JOptionPane.showConfirmDialog(ventana,
                "¿Estás seguro de que deseas eliminar el producto?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION);
        
        //Si el usuario confirma (YES_OPTION)
        if(confirmacion == JOptionPane.YES_OPTION){
            //Parseamos el ID para enviarlo a la BBDD
            int id = Integer.parseInt(this.campoIdProducto.getText());
            
            //Se envía la orden para borrar al DAO
            if(this.consulta.borrarProducto(id)){
                JOptionPane.showMessageDialog(ventana, "Producto eliminado correctamente");
                mostrarDatos(); //Refrescamos la tabla para que desaparezca
                vaciarCampos(); //limpiamos los campos
            } else{
                JOptionPane.showMessageDialog(ventana, "Error al borrar el producto.");
            }
        }
        
    }

    //Metodo para agregar el validador de teclado al JButton
    private void agregarValidador(JTextField campoTexto) {
        campoTexto.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                validarNumero(campoTexto, e);
            }
        });
    }

    //Metodo para validar que el caracter introducido sea un numero
    private void validarNumero(JTextField idNumerico, KeyEvent e) {
        //Se toma el caracter ingresado por teclado
        char caracter = e.getKeyChar();

        //Se evalua que no sea un digito o la tecla de borrado
        if (!Character.isDigit(caracter) && caracter != KeyEvent.VK_BACK_SPACE && caracter != '.' && caracter != ',') {
            e.consume();//se borra si no es un digito
            idNumerico.setBackground(Color.RED); //Se coloca el fondo rojo
        } else {
            idNumerico.setBackground(Color.WHITE);//Se mantiene normal cuando se ingresa un digito
        }
    }

    //Metodo para mostrar los productos en el JTable
    private void mostrarDatos() {

        //Lo metemos en una lista
        List<Producto> listaProductos = this.consulta.consultarProductos();

        //Esto es para que se vacíe la tabla antes de cada consulta
        this.modeloTabla.setRowCount(0);

        //un foreach para recorrer la lista e insertar los datos
        for (Producto p : listaProductos) {

            //Creamos un array genérico object para que el DefaultModelTable pueda insertar sus datos
            Object[] fila = new Object[5];

            //Asignamos cada elemento que devulva la consulta a ese array
            fila[0] = p.getId();
            fila[1] = p.getNombre();
            fila[2] = p.getCantidad();
            fila[3] = p.getPrecio();
            fila[4] = p.getDescripcion();

            // añadimos la fila ya preparada al modelo de la tabla
            this.modeloTabla.addRow(fila);
        }
    }
}
