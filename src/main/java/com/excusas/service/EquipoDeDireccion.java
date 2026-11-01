package com.excusas.service;

import com.excusas.model.Prontuario;

public class EquipoDeDireccion implements DirectivoObserver {
    private final EmailSender emailSender;

    public EquipoDeDireccion(EmailSender emailSender) {
        this.emailSender = emailSender;
    }

    @Override
    public void actualizar(Prontuario prontuario) {
        emailSender.enviarEmail(Contactos.EMAIL_DIRECCION, Contactos.EMAIL_PRONTUARIOS,
                "Nuevo prontuario", "Se registró un prontuario para el legajo " + prontuario.getLegajo());
    }
}
