package com.excusas.state;

import java.util.OptionalInt;
import java.util.Random;
import java.util.function.BooleanSupplier;

public class PoliticaEstandar implements PoliticaDeModo {
    private static final int PRIMER_CANTIDAD_NORMAL = 5;
    private static final int PRIMER_CANTIDAD_VAGO = 11;
    private static final int DESCANSO_MINIMO = 10;
    private static final int DESCANSO_MAXIMO = 15;

    private final BooleanSupplier moneda;

    public PoliticaEstandar() {
        this(new Random()::nextBoolean);
    }

    public PoliticaEstandar(BooleanSupplier moneda) {
        this.moneda = moneda;
    }

    @Override
    public Estado modoPara(int excusasProcesadas) {
        if (excusasProcesadas < PRIMER_CANTIDAD_NORMAL) return new ModoProductivo();
        if (excusasProcesadas < PRIMER_CANTIDAD_VAGO) return new ModoNormal();
        return new ModoVago();
    }

    @Override
    public int cantidadTrasCafe() { return 0; }

    @Override
    public OptionalInt cantidadTrasDescanso(int minutos) {
        if (minutos >= DESCANSO_MINIMO && minutos <= DESCANSO_MAXIMO) {
            return OptionalInt.of(PRIMER_CANTIDAD_NORMAL);
        }
        return OptionalInt.empty();
    }

    @Override
    public int cantidadTrasUnaNoche(int cantidadActual) {
        return moneda.getAsBoolean() ? 0 : PRIMER_CANTIDAD_NORMAL;
    }
}
