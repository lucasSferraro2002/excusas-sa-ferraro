package com.excusas.model;

import com.excusas.chain.Responsable;
import com.excusas.service.EmailSender;
import com.excusas.state.Estado;
import com.excusas.state.ModoProductivo;
import com.excusas.state.ModoNormal;
import com.excusas.state.ModoVago;

public abstract class Empleado implements Responsable {
    private String nombre;
    private String email;
    private int legajo;
    private Estado modo;
    public int cantidadExcusas;
    private Responsable siguiente;
    private EmailSender emailSender;

    public Empleado(String nombre, String email, int legajo, EmailSender emailSender) {
        this.nombre = nombre;
        this.email = email;
        this.legajo = legajo;
        this.emailSender = emailSender;
        this.modo = new ModoProductivo();
        this.cantidadExcusas = 0;
    }

    public void setSiguiente(Responsable siguiente) { this.siguiente = siguiente; }
    public Responsable getSiguiente() { return siguiente; }
    public EmailSender getEmailSender() { return emailSender; }
    public void setEmailSender(EmailSender emailSender) { this.emailSender = emailSender;}
    public String getEmail() { return email; }
    public String getNombre() { return nombre; }
    public int getLegajo() { return legajo; }
    public Estado getModo() { return modo; }
    public void setModo(Estado modo) { this.modo = modo; }

    @Override
    public void revisarExcusa(Excusa excusa) {
        this.modo.manejarExcusa(this, excusa);
    }

    public boolean intentarProcesar(Excusa excusa) {
        if (getNivelAsignado() != null && excusa.getNivel().ordinal() <= getNivelAsignado().ordinal()) {
            procesarExcusaEspecifica(excusa);

            cantidadExcusas++;
            if (cantidadExcusas < 5) setModo(new ModoProductivo());
            else if (cantidadExcusas <= 10) setModo(new ModoNormal());
            else setModo(new ModoVago());

            return true;
        }
        return false;
    }

    protected abstract NivelComplejidad getNivelAsignado();
    protected abstract void procesarExcusaEspecifica(Excusa excusa);
    public void reiniciarContador() {
        this.cantidadExcusas = 0;
    }
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
    public void setCantidadExcusas(int cantidad) {
        this.cantidadExcusas = cantidad;
    }
}