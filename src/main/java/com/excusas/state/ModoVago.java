package com.excusas.state;

import com.excusas.chain.Encargado;
import com.excusas.model.Excusa;

public class ModoVago implements Estado {
    @Override
    public void manejarExcusa(Encargado encargado, Excusa excusa) {
        encargado.derivar(excusa);
    }

    @Override
    public void alNuevoDia(Encargado encargado) {
        encargado.descansarUnaNoche();
    }
}
