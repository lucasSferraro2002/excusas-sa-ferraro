package com.excusas.chain;

import com.excusas.model.Empleado;
import com.excusas.model.Excusa;
import com.excusas.model.NivelExcusa;
import com.excusas.service.EmailSender;
import com.excusas.state.Estado;
import com.excusas.state.PoliticaDeModo;
import com.excusas.state.PoliticaEstandar;

public abstract class Encargado extends Empleado implements EslabonDeCadena {
    private final EmailSender emailSender;
    private PoliticaDeModo politica = new PoliticaEstandar();
    private int cantidadExcusas = 0;
    private Estado modo = politica.modoPara(cantidadExcusas);
    private Responsable siguiente;

    protected Encargado(String nombre, String email, int legajo, EmailSender emailSender) {
        super(nombre, email, legajo);
        this.emailSender = emailSender;
    }

    @Override
    public void revisarExcusa(Excusa excusa) {
        modo.manejarExcusa(this, excusa);
    }

    @Override
    public void setSiguiente(Responsable siguiente) { this.siguiente = siguiente; }

    public boolean tieneSiguiente() { return siguiente != null; }

    public void derivar(Excusa excusa) {
        if (siguiente != null) {
            siguiente.revisarExcusa(excusa);
        } else {
            excusa.rechazarPorFaltaDePruebas();
        }
    }

    public boolean intentarProcesar(Excusa excusa) {
        if (!puedeProcesar(excusa)) {
            return false;
        }
        procesarExcusaEspecifica(excusa);
        aplicarCantidad(cantidadExcusas + 1);
        return true;
    }

    public void enviarEmail(String destino, String asunto, String cuerpo) {
        emailSender.enviarEmail(destino, getEmail(), asunto, cuerpo);
    }

    public void tomarCafe() { aplicarCantidad(politica.cantidadTrasCafe()); }

    public void tomarDescanso(int minutos) {
        politica.cantidadTrasDescanso(minutos).ifPresent(this::aplicarCantidad);
    }

    public void comenzarNuevoDia() { modo.alNuevoDia(this); }

    public void descansarUnaNoche() { aplicarCantidad(politica.cantidadTrasUnaNoche(cantidadExcusas)); }

    public Estado getModo() { return modo; }
    public void setModo(Estado modo) { this.modo = modo; }
    public void setPolitica(PoliticaDeModo politica) { this.politica = politica; }

    private void aplicarCantidad(int cantidad) {
        this.cantidadExcusas = cantidad;
        this.modo = politica.modoPara(cantidad);
    }

    protected boolean esDeNivelHasta(Excusa excusa, NivelExcusa maximo) {
        return excusa.getNivel().compareTo(maximo) <= 0;
    }

    protected abstract boolean puedeProcesar(Excusa excusa);
    protected abstract void procesarExcusaEspecifica(Excusa excusa);

    @Override
    public String toString() { return getClass().getSimpleName(); }
}
