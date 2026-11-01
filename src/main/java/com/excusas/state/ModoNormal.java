package com.excusas.state;

import com.excusas.chain.Encargado;
import com.excusas.model.Excusa;

public class ModoNormal implements Estado {
    @Override
    public void manejarExcusa(Encargado encargado, Excusa excusa) {
        if (!encargado.intentarProcesar(excusa)) {
            encargado.derivar(excusa);
        }
    }
}
