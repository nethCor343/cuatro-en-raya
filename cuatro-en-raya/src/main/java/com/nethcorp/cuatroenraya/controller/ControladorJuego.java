package com.nethcorp.cuatroenraya.controller;

import com.nethcorp.cuatroenraya.view.*;
import com.nethcorp.cuatroenraya.model.*;

public class ControladorJuego {
	private final Partida partida;
    private final VistaTexto vistaIU;

    public ControladorJuego(Partida partida, VistaTexto vista) {
        this.partida = partida;
        this.vistaIU = vista;
    }

    public void iniciarPartida() {

        while (!partida.verificarFinalJuego()) {
            
            vistaIU.dibujarTablero(partida.obtenerTablero());

            int columnaElegida = vistaIU.solicitarColumna(partida.obtenerTurnoActual());

            ResultadoTurno resultado = partida.jugar(columnaElegida);

            switch (resultado) {
                case VICTORIA:
                    vistaIU.dibujarTablero(partida.obtenerTablero());
                    vistaIU.mostrarMensajeVictoria(partida.obtenerGanador());
                    break;
                    
                case EMPATE:
                    vistaIU.dibujarTablero(partida.obtenerTablero());
                    System.out.println("!!Empate!!");
                    break;
                    
                case MOVIMIENTO_INVALIDO:
                	System.out.println("Movimiento invalido");
                    break;
                    
                case EN_CURSO:
                    break;
            }
        }
    }
    
}
