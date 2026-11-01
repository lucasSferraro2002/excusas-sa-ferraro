package com.excusas.chain;

import com.excusas.model.Excusa;
import com.excusas.model.NivelExcusa;
import com.excusas.service.EmailSender;
import com.excusas.service.RegistroDeProntuarios;

/** Solo escucha excusas inverosímiles (no toma las de niveles inferiores). */
public class CEO extends Encargado {
    private static final String ASUNTO = "Resolución final";
    private static final String CUERPO = "Aprobado por creatividad";

    private final RegistroDeProntuarios registro;

    public CEO(String nombre, String email, int legajo, EmailSender emailSender, RegistroDeProntuarios registro) {
        super(nombre, email, legajo, emailSender);
        this.registro = registro;
    }

    @Override
    protected boolean puedeProcesar(Excusa excusa) { return excusa.getNivel() == NivelExcusa.INVEROSIMIL; }

    @Override
    protected void procesarExcusaEspecifica(Excusa excusa) {
        excusa.aceptar();
        enviarEmail(excusa.getEmpleado().getEmail(), ASUNTO, CUERPO);
        registro.registrarProntuario(excusa);
    }
}
