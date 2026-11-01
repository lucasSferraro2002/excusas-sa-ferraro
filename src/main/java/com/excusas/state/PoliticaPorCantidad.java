package com.excusas.state;

/**
 * Menos de 5 excusas: Productivo. De 5 a 10: Normal. 11 o más: Vago.
 * (El enunciado no define el 5 exacto; se asigna a Normal.)
 */
public class PoliticaPorCantidad implements PoliticaDeModo {
    @Override
    public Estado modoPara(int excusasProcesadas) {
        if (excusasProcesadas < 5) return new ModoProductivo();
        if (excusasProcesadas <= 10) return new ModoNormal();
        return new ModoVago();
    }
}
