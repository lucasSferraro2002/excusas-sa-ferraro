package com.excusas;

import com.excusas.chain.*;
import com.excusas.model.*;
import com.excusas.service.*;
import com.excusas.state.*;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> crearInterfaz());
    }

    private static void crearInterfaz() {
        JFrame frame = new JFrame("Sistema de Gestion de Excusas S.A.");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(750, 750);
        frame.setLayout(new BorderLayout());

        JPanel panelFormulario = new JPanel(new GridLayout(6, 2, 10, 10));
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panelFormulario.add(new JLabel("Nombre del empleado:"));
        JTextField txtNombre = new JTextField();
        panelFormulario.add(txtNombre);

        panelFormulario.add(new JLabel("Legajo (solo numeros):"));
        JTextField txtLegajo = new JTextField();
        panelFormulario.add(txtLegajo);

        panelFormulario.add(new JLabel("Nivel de complejidad:"));
        JComboBox<NivelComplejidad> comboNivel = new JComboBox<>(NivelComplejidad.values());
        panelFormulario.add(comboNivel);

        panelFormulario.add(new JLabel("Seleccionar Excusa:"));
        JComboBox<String> comboTextoExcusa = new JComboBox<>();
        panelFormulario.add(comboTextoExcusa);

        panelFormulario.add(new JLabel("Escribir otra excusa:"));
        JTextField txtDescripcion = new JTextField();
        txtDescripcion.setEnabled(false);
        panelFormulario.add(txtDescripcion);

        comboNivel.addActionListener(e -> {
            NivelComplejidad nivel = (NivelComplejidad) comboNivel.getSelectedItem();
            comboTextoExcusa.removeAllItems();

            if (nivel.ordinal() == 0) {
                comboTextoExcusa.addItem("Me quede dormido");
                comboTextoExcusa.addItem("Perdi el colectivo");
            } else if (nivel.ordinal() == 1) {
                comboTextoExcusa.addItem("Se corto la luz en todo el barrio");
                comboTextoExcusa.addItem("Tuve que cuidar a un familiar");
            }
            comboTextoExcusa.addItem("Otro");
        });

        comboTextoExcusa.addActionListener(e -> {
            boolean esOtro = "Otro".equals(comboTextoExcusa.getSelectedItem());
            txtDescripcion.setEnabled(esOtro);
            if (!esOtro) {
                txtDescripcion.setText("");
            }
        });

        comboNivel.setSelectedIndex(0);

        JButton btnProcesar = new JButton("Procesar Excusa");
        panelFormulario.add(new JLabel());
        panelFormulario.add(btnProcesar);

        frame.add(panelFormulario, BorderLayout.NORTH);

        JTextArea txtConsola = new JTextArea();
        txtConsola.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(txtConsola);
        frame.add(scrollPane, BorderLayout.CENTER);

        EmailSender emailSender = (destinatario, remitente, asunto, cuerpo) -> {
            txtConsola.append("\n[EMAIL] De: " + remitente + " | Para: " + destinatario + "\n");
            txtConsola.append("        Asunto: " + asunto + " | Msj: " + cuerpo + "\n");
        };

        Recepcionista recepcionista = new Recepcionista("Ana", "Recepcionista@excusas.com", 101, emailSender);
        SupervisoraArea supervisora = new SupervisoraArea("Laura", "Supervisora@excusas.com", 102, emailSender);
        GerenteHR gerente = new GerenteHR("Carlos", "Gerente@excusas.com", 103, emailSender);
        CEO ceo = new CEO("Roberto", "CEO@excusas.com", 1, emailSender);

        Empleado cadenaDeResponsabilidad = new CadenaBuilder()
                .agregarResponsable(recepcionista)
                .agregarResponsable(supervisora)
                .agregarResponsable(gerente)
                .agregarResponsable(ceo)
                .construir();

        AdministradorProntuarios.getInstance().agregarObservador(excusa -> {
            txtConsola.append("\n[ALERTA A DIRECTORIO] Prontuario registrado. Legajo: " + excusa.getEmpleado().getLegajo() + "\n");
        });

        JPanel panelEstado = new JPanel(new FlowLayout());
        JComboBox<Empleado> comboEmpleados = new JComboBox<>(new Empleado[]{recepcionista, supervisora, gerente, ceo});

        JButton btnVerEstados = new JButton("Ver Estados");
        JButton btnTomarCafe = new JButton("Dar Café");
        JButton btnDescanso = new JButton("Dar Break (12m)");
        JButton btnDiaSiguiente = new JButton("Pasar de Día");

        panelEstado.add(new JLabel("Seleccionar encargado:"));
        panelEstado.add(comboEmpleados);
        panelEstado.add(btnTomarCafe);
        panelEstado.add(btnDescanso);
        panelEstado.add(btnDiaSiguiente);
        panelEstado.add(btnVerEstados);
        frame.add(panelEstado, BorderLayout.SOUTH);

        btnProcesar.addActionListener(e -> {
            try {
                String nombre = txtNombre.getText();
                int legajo = Integer.parseInt(txtLegajo.getText());
                String descripcion = txtDescripcion.isEnabled() ? txtDescripcion.getText() : (String) comboTextoExcusa.getSelectedItem();
                Empleado empleado = new EmpleadoComun(nombre, nombre.toLowerCase() + "@excusas.com", legajo);
                NivelComplejidad nivel = (NivelComplejidad) comboNivel.getSelectedItem();

                Excusa excusa = new Excusa(empleado, nivel, descripcion);

                txtConsola.append("\n--- Procesando excusa de " + nombre + " (" + nivel + ") ---");
                cadenaDeResponsabilidad.revisarExcusa(excusa);

                txtConsola.append("\nResultado: " + (excusa.isAceptada() ? "ACEPTADA" : "RECHAZADA") + "\n");
                txtConsola.append("-------------------------------------------\n");

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "El legajo debe ser un numero.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnVerEstados.addActionListener(e -> {
            txtConsola.append("\n--- ESTADOS ACTUALES DEL PERSONAL ---\n");
            txtConsola.append("Recepcionista: " + recepcionista.getModo().getClass().getSimpleName() + "\n");
            txtConsola.append("Supervisora: " + supervisora.getModo().getClass().getSimpleName() + "\n");
            txtConsola.append("Gerente HR: " + gerente.getModo().getClass().getSimpleName() + "\n");
            txtConsola.append("CEO: " + ceo.getModo().getClass().getSimpleName() + "\n");
            txtConsola.append("-------------------------------------\n");
        });

        btnTomarCafe.addActionListener(e -> {
            Empleado seleccionado = (Empleado) comboEmpleados.getSelectedItem();
            if (seleccionado != null) {
                seleccionado.getModo().tomarCafe(seleccionado);
                txtConsola.append("\n[*] " + seleccionado.getClass().getSimpleName() + " tomó un café. Ahora está Productivo.\n");
            }
        });

        btnDescanso.addActionListener(e -> {
            Empleado seleccionado = (Empleado) comboEmpleados.getSelectedItem();
            if (seleccionado != null) {
                seleccionado.getModo().tomarDescanso(seleccionado, 12);
                txtConsola.append("\n[*] " + seleccionado.getClass().getSimpleName() + " tomó un break. Ahora está Normal.\n");
            }
        });

        btnDiaSiguiente.addActionListener(e -> {
            txtConsola.append("\n[*] --- COMIENZA UN NUEVO DÍA LABORAL ---\n");
            Empleado actual = (Empleado) cadenaDeResponsabilidad;

            while (actual != null) {
                if (actual.getModo() instanceof ModoVago) {
                    ((ModoVago) actual.getModo()).alDiaSiguiente(actual);
                }
                actual = (Empleado) actual.getSiguiente();
            }
        });

        frame.setVisible(true);
    }
}