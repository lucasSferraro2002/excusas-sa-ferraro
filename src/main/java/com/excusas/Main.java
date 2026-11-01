package com.excusas;

import com.excusas.chain.*;
import com.excusas.model.*;
import com.excusas.service.*;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> crearInterfaz());
    }

    private static String nombreModo(Encargado encargado) {
        return encargado.getModo().getClass().getSimpleName();
    }

    private static void crearInterfaz() {
        JFrame frame = new JFrame("Sistema de Gestion de Excusas S.A.");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(750, 750);
        frame.setLayout(new BorderLayout());

        JPanel panelFormulario = new JPanel(new GridLayout(4, 2, 10, 10));
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panelFormulario.add(new JLabel("Nombre del empleado:"));
        JTextField txtNombre = new JTextField();
        panelFormulario.add(txtNombre);

        panelFormulario.add(new JLabel("Legajo (solo numeros):"));
        JTextField txtLegajo = new JTextField();
        panelFormulario.add(txtLegajo);

        panelFormulario.add(new JLabel("Motivo:"));
        JComboBox<Motivo> comboMotivo = new JComboBox<>(Motivo.values());
        panelFormulario.add(comboMotivo);

        JButton btnProcesar = new JButton("Procesar Excusa");
        panelFormulario.add(new JLabel());
        panelFormulario.add(btnProcesar);

        frame.add(panelFormulario, BorderLayout.NORTH);

        JTextArea txtConsola = new JTextArea();
        txtConsola.setEditable(false);
        frame.add(new JScrollPane(txtConsola), BorderLayout.CENTER);

        EmailSender emailSender = (destinatario, remitente, asunto, cuerpo) -> {
            txtConsola.append("\n[EMAIL] De: " + remitente + " | Para: " + destinatario + "\n");
            txtConsola.append("        Asunto: " + asunto + " | Msj: " + cuerpo + "\n");
        };

        AdministradorProntuarios administrador = AdministradorProntuarios.getInstance();
        administrador.agregarObservador(new EquipoDeDireccion(emailSender));

        Recepcionista recepcionista = new Recepcionista("Ana", "Recepcionista@excusas.com", 101, emailSender);
        SupervisoraArea supervisora = new SupervisoraArea("Laura", "Supervisora@excusas.com", 102, emailSender);
        GerenteHR gerente = new GerenteHR("Carlos", "Gerente@excusas.com", 103, emailSender);
        CEO ceo = new CEO("Roberto", "CEO@excusas.com", 1, emailSender, administrador);
        List<Encargado> encargados = List.of(recepcionista, supervisora, gerente, ceo);

        Responsable cadena = new CadenaBuilder()
                .agregarResponsable(recepcionista)
                .agregarResponsable(supervisora)
                .agregarResponsable(gerente)
                .agregarResponsable(ceo)
                .construir();

        JPanel panelEstado = new JPanel(new FlowLayout());
        JComboBox<Encargado> comboEncargados = new JComboBox<>(encargados.toArray(new Encargado[0]));
        JSpinner spnMinutos = new JSpinner(new SpinnerNumberModel(12, 0, 120, 1));

        JButton btnVerEstados = new JButton("Ver Estados");
        JButton btnTomarCafe = new JButton("Dar Café");
        JButton btnDescanso = new JButton("Dar Break (min)");
        JButton btnDiaSiguiente = new JButton("Pasar de Día");

        panelEstado.add(new JLabel("Seleccionar encargado:"));
        panelEstado.add(comboEncargados);
        panelEstado.add(btnTomarCafe);
        panelEstado.add(spnMinutos);
        panelEstado.add(btnDescanso);
        panelEstado.add(btnDiaSiguiente);
        panelEstado.add(btnVerEstados);
        frame.add(panelEstado, BorderLayout.SOUTH);

        btnProcesar.addActionListener(e -> {
            try {
                String nombre = txtNombre.getText();
                int legajo = Integer.parseInt(txtLegajo.getText());
                Motivo motivo = (Motivo) comboMotivo.getSelectedItem();
                Empleado empleado = new Empleado(nombre, nombre.toLowerCase() + "@excusas.com", legajo);
                Excusa excusa = motivo.nuevaExcusa(empleado);

                txtConsola.append("\n--- Procesando excusa de " + nombre + " (" + motivo + ") ---");
                cadena.revisarExcusa(excusa);

                txtConsola.append("\nResultado: " + (excusa.isAceptada() ? "ACEPTADA" : excusa.getRespuestaFinal()) + "\n");
                txtConsola.append("-------------------------------------------\n");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "El legajo debe ser un numero.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnVerEstados.addActionListener(e -> {
            txtConsola.append("\n--- ESTADOS ACTUALES DEL PERSONAL ---\n");
            encargados.forEach(enc -> txtConsola.append(enc + ": " + nombreModo(enc) + "\n"));
            txtConsola.append("-------------------------------------\n");
        });

        btnTomarCafe.addActionListener(e -> {
            Encargado seleccionado = (Encargado) comboEncargados.getSelectedItem();
            if (seleccionado != null) {
                seleccionado.tomarCafe();
                txtConsola.append("\n[*] " + seleccionado + " tomó un café. Ahora está " + nombreModo(seleccionado) + ".\n");
            }
        });

        btnDescanso.addActionListener(e -> {
            Encargado seleccionado = (Encargado) comboEncargados.getSelectedItem();
            if (seleccionado != null) {
                int minutos = (Integer) spnMinutos.getValue();
                Object antes = seleccionado.getModo();
                seleccionado.tomarDescanso(minutos);
                String efecto = seleccionado.getModo() == antes
                        ? "Sin efecto (fuera de 10-15 min)."
                        : "Ahora está " + nombreModo(seleccionado) + ".";
                txtConsola.append("\n[*] " + seleccionado + " tomó un break de " + minutos + " min. " + efecto + "\n");
            }
        });

        btnDiaSiguiente.addActionListener(e -> {
            txtConsola.append("\n[*] --- COMIENZA UN NUEVO DÍA LABORAL ---\n");
            encargados.forEach(Encargado::comenzarNuevoDia);
        });

        frame.setVisible(true);
    }
}
