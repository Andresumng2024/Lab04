// IMPORTANTE!!!! TENER EN CUENTA QUE TOCA ESPERAR MINIMO UN MINUTO PARA QUE EMPIEZE A COBRAR
package P1;

import java.time.format.DateTimeFormatter;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalTime;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Stack;
import javax.swing.Timer; // Importación para Timer

public class ParqueaderoApp extends JFrame {
    private ArrayList<RegistroVehiculo> listaVehiculos;
    private Stack<RegistroVehiculo> vehiculos2Ruedas;
    private Stack<RegistroVehiculo> vehiculos4Ruedas;
    private int contadorVehiculos = 0;

    private JTextField txtPlaca;
    private JComboBox<String> comboTipo;
    private JTable tablaVehiculos;
    private DefaultTableModel modeloTabla;

    public ParqueaderoApp() {
        listaVehiculos = new ArrayList<>();
        vehiculos2Ruedas = new Stack<>();
        vehiculos4Ruedas = new Stack<>();
        configurarVentana();
        inicializarComponentes();

        // Configuramos un Timer que actualiza la tabla cada 10 segundos
        Timer timerActualizacion = new Timer(10000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarTabla();
            }
        });
        timerActualizacion.start();
    }
     private void configurarVentana() {
        setTitle("Administración de Parqueadero");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
    }

    private void inicializarComponentes() {
        JPanel panelIngreso = new JPanel();
        panelIngreso.setLayout(new GridLayout(3, 2, 10, 10));

        panelIngreso.add(new JLabel("Placa:"));
        txtPlaca = new JTextField();
        panelIngreso.add(txtPlaca);

        panelIngreso.add(new JLabel("Tipo de Vehículo:"));
        comboTipo = new JComboBox<>(new String[]{"Bicicleta", "Ciclomotor", "Motocicleta", "Carro"});
        panelIngreso.add(comboTipo);

        JButton btnIngresar = new JButton("Ingresar Vehículo");
        btnIngresar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ingresarVehiculo();
            }
        });
          panelIngreso.add(btnIngresar);

        JPanel panelBotones = new JPanel(new GridLayout(3, 2, 10, 10));
        JButton btnVisualizarTabla = new JButton("Visualizar Tabla");
        JButton btnVisualizar2Ruedas = new JButton("Visualizar 2 Ruedas");
        JButton btnVisualizar4Ruedas = new JButton("Visualizar 4 Ruedas");
        JButton btnCantidadTotal = new JButton("Cantidad y Total a Pagar");
        JButton btnEliminarVehiculo = new JButton("Eliminar Vehículo");
        JButton btnSalir = new JButton("Salir");

        btnVisualizarTabla.addActionListener(e -> visualizarTablaCompleta());
        btnVisualizar2Ruedas.addActionListener(e -> visualizarVehiculos2Ruedas());
        btnVisualizar4Ruedas.addActionListener(e -> visualizarVehiculos4Ruedas());
        btnCantidadTotal.addActionListener(e -> mostrarCantidadYTotal());
        btnEliminarVehiculo.addActionListener(e -> eliminarVehiculo());
        btnSalir.addActionListener(e -> System.exit(0));

        panelBotones.add(btnVisualizarTabla);
        panelBotones.add(btnVisualizar2Ruedas);
        panelBotones.add(btnVisualizar4Ruedas);
        panelBotones.add(btnCantidadTotal);
        panelBotones.add(btnEliminarVehiculo);
        panelBotones.add(btnSalir);

        modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("Número");
        modeloTabla.addColumn("Placa");
        modeloTabla.addColumn("Tipo");
        modeloTabla.addColumn("Hora Ingreso");
        modeloTabla.addColumn("Valor a Pagar");

        tablaVehiculos = new JTable(modeloTabla);
        JScrollPane scrollTabla = new JScrollPane(tablaVehiculos);

        add(panelIngreso, BorderLayout.NORTH);
        add(scrollTabla, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
    }    

    private void ingresarVehiculo() {
        String placa = txtPlaca.getText();
        String tipo = (String) comboTipo.getSelectedItem();
        LocalTime horaIngreso = LocalTime.now();

        contadorVehiculos++;
        RegistroVehiculo nuevoVehiculo = new RegistroVehiculo(contadorVehiculos, placa, tipo, horaIngreso);
        listaVehiculos.add(nuevoVehiculo);

        if (tipo.equals("Bicicleta") || tipo.equals("Ciclomotor") || tipo.equals("Motocicleta")) {
            vehiculos2Ruedas.push(nuevoVehiculo);
        } else if (tipo.equals("Carro")) {
            vehiculos4Ruedas.push(nuevoVehiculo);
        }

        actualizarTabla();
    }
