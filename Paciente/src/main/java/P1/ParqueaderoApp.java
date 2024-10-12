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