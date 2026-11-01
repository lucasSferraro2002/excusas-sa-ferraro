package com.excusas.state;

import com.excusas.chain.Encargado;
import com.excusas.model.Excusa;
import com.excusas.service.Contactos;

/** Evalúa si puede; si no, avisa al CTO y deriva (el aviso va solo cuando hay a quién derivar). */
public class ModoProductivo implements Estado {
    private static final String ASUNTO = "Derivacion de excusa";
    private static final String CUERPO = "Se deriva la excusa al siguiente sector.";

    @Override
    public void manejarExcusa(Encargado encargado, Excusa excusa) {
        if (!encargado.intentarProcesar(excusa)) {
            if (encargado.tieneSiguiente()) {
                encargado.enviarEmail(Contactos.EMAIL_CTO, ASUNTO, CUERPO);
            }
            encargado.derivar(excusa);
        }
    }
}
