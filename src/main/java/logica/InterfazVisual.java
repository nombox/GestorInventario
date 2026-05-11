package logica;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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
    
    //Contructor
    public InterfazVisual(){
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
    this.campoDescripcion  = new JTextField();
    this.campoIdProducto  = new JTextField();
    
    this.tablaResultados = new JTable();
    this.modeloTabla = new DefaultTableModel();
    
    }
    
    public void crearEntorno(){
        //Creo la ventana
        ventana.setSize(1000,800);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setLocationRelativeTo(null);
        ventana.setLayout(new BorderLayout());
        
        // --- ELEMENTOS DEL NORTH ---
        JLabel etiquetaPrincipal = new JLabel("Registro de inventario", JLabel.CENTER);
        etiquetaPrincipal.setFont(new Font("Arial",Font.BOLD, 24));

        panelEtiqueta.add(etiquetaPrincipal);
        // -------------------------------
        
        // --- ELEMENTOS DEL CENTER ---
        this.panelDatos.setLayout(new GridLayout(4,2));
        
        JLabel etiquetaNombre = new JLabel("Nombre del producto");
        this.panelDatos.add(etiquetaNombre);
        this.panelDatos.add(this.campoNombre);
        
        JLabel etiquetaCantidad = new JLabel("Cantidad");
        this.panelDatos.add(etiquetaCantidad);
        this.panelDatos.add(this.campoCantidad);
        agregarValidador(this.campoCantidad);
        
        JLabel etiquetaPrecio = new JLabel("Precio");
        this.panelDatos.add(etiquetaPrecio);
        this.panelDatos.add(this.campoPrecio);
        agregarValidador(this.campoPrecio);
        
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
        
        // agrego elementos y hago visible la ventana
        this.ventana.add(this.panelEtiqueta, BorderLayout.NORTH);
        this.ventana.add(this.panelDatos, BorderLayout.CENTER);
        this.ventana.add(this.panelTabla, BorderLayout.SOUTH);
        this.ventana.setVisible(true);
        
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
        if (!Character.isDigit(caracter) && caracter != KeyEvent.VK_BACK_SPACE && caracter != '.' && caracter !=',') {
            e.consume();//se borra si no es un digito
            idNumerico.setBackground(Color.RED); //Se coloca el fondo rojo
        } else {
            idNumerico.setBackground(Color.WHITE);//Se mantiene normal cuando se ingresa un digito
        }
    }
}
