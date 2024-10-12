
package P2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.Queue;

public class TurnoEPS extends JFrame {
    private Queue<Paciente> colaPacientes = new LinkedList<>();
    private JTextArea areaTurnos;
    private JTextArea areaColaPacientes;
    private JLabel etiquetaTurnoActual;
    private JButton botonExtenderTiempo;
    private Timer temporizador;
    private int tiempoRestante = 5; // 5 segundos para la simulación
    private Paciente pacienteActual;
    private int numeroTurnoActual = 0;
    private int contadorTurnos = 1; // Contador global para asignar turnos

    public TurnoEPS() {
        setTitle("Asignación de Turnos - EPS");
        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JTextField campoNombre = new JTextField(15);
        JTextField campoEdad = new JTextField(3);
        JComboBox<String> comboAfiliacion = new JComboBox<>(new String[]{"Sin afiliación", "POS", "PC"});
        JComboBox<String> comboCondicion = new JComboBox<>(new String[]{"Ninguna", "Embarazo", "Limitación motriz"});

        JButton botonAgregar = new JButton("Agregar Paciente");
        areaTurnos = new JTextArea(12, 30);
        areaTurnos.setBorder(BorderFactory.createTitledBorder("Historial de Turnos"));
        areaColaPacientes = new JTextArea(12, 30);
        areaColaPacientes.setBorder(BorderFactory.createTitledBorder("Cola de Pacientes"));
        etiquetaTurnoActual = new JLabel("Turno actual: Ninguno");
        botonExtenderTiempo = new JButton("Extender Tiempo");

        add(new JLabel("Nombre:"));
        add(campoNombre);
        add(new JLabel("Edad:"));
        add(campoEdad);
        add(new JLabel("Afiliación:"));
        add(comboAfiliacion);
        add(new JLabel("Condición:"));
        add(comboCondicion);
        add(botonAgregar);
        add(etiquetaTurnoActual);
        add(botonExtenderTiempo);
        add(new JScrollPane(areaTurnos));
        add(new JScrollPane(areaColaPacientes));

        botonAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = campoNombre.getText();
                int edad = Integer.parseInt(campoEdad.getText());
                String afiliacion = (String) comboAfiliacion.getSelectedItem();
                String condicion = (String) comboCondicion.getSelectedItem();

                if ("Sin afiliación".equals(afiliacion)) {
                    afiliacion = null;
                }

                Paciente nuevoPaciente = new Paciente(nombre, edad, afiliacion, condicion, contadorTurnos++);
                agregarPaciente(nuevoPaciente);
                campoNombre.setText("");
                campoEdad.setText("");
            }
        });