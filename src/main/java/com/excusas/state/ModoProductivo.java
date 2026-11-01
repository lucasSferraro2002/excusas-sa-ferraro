package com.excusas.state;

import com.excusas.model.Empleado;
import com.excusas.model.Excusa;

public class ModoProductivo implements Estado {

    @Override
    public void manejarExcusa(Empleado empleado, Excusa excusa) {
        if (!empleado.intentarProcesar(excusa)) {
            if (empleado.getSiguiente() != null) {
                empleado.getEmailSender().enviarEmail(
                        "CTO@excusas.com",
                        empleado.getEmail(),
                        "Derivacion de excusa",
                        "Se deriva la excusa al siguiente sector."
                );
                empleado.getSiguiente().revisarExcusa(excusa);
            }
        }
    }
}