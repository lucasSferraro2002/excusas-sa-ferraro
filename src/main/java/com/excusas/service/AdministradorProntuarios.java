package com.excusas.service;

import com.excusas.model.Excusa;
import java.util.ArrayList;
import java.util.List;

public class AdministradorProntuarios {
    private static AdministradorProntuarios instancia;

    private List<DirectivoObserver> observadores;
    private List<Excusa> prontuarios;

    private AdministradorProntuarios() {
        observadores = new ArrayList<>();
        prontuarios = new ArrayList<>();
    }

    public static AdministradorProntuarios getInstance() {
        if (instancia == null) {
            instancia = new AdministradorProntuarios();
        }
        return instancia;
    }

    public void agregarObservador(DirectivoObserver observador) {
        observadores.add(observador);
    }

    public void registrarProntuario(Excusa excusa) {
        prontuarios.add(excusa);
        notificarObservadores(excusa);
    }

    private void notificarObservadores(Excusa excusa) {
        for (DirectivoObserver obs : observadores) {
            obs.actualizar(excusa);
        }
    }
}