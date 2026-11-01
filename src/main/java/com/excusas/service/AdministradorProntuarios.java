package com.excusas.service;

import com.excusas.model.Excusa;
import com.excusas.model.Prontuario;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class AdministradorProntuarios implements RegistroDeProntuarios {
    private static final AdministradorProntuarios INSTANCIA = new AdministradorProntuarios();

    private final List<DirectivoObserver> observadores = new CopyOnWriteArrayList<>();
    private final List<Prontuario> prontuarios = new CopyOnWriteArrayList<>();

    private AdministradorProntuarios() { }

    public static AdministradorProntuarios getInstance() {
        return INSTANCIA;
    }

    public void agregarObservador(DirectivoObserver observador) {
        observadores.add(observador);
    }

    @Override
    public void registrarProntuario(Excusa excusa) {
        Prontuario prontuario = new Prontuario(excusa);
        prontuarios.add(prontuario);
        observadores.forEach(obs -> obs.actualizar(prontuario));
    }

    public List<Prontuario> getProntuarios() {
        return Collections.unmodifiableList(prontuarios);
    }
}
