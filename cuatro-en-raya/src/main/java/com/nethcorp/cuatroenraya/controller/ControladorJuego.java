package com.nethcorp.cuatroenraya.controller;

import com.nethcorp.cuatroenraya.view.*;
import com.nethcorp.cuatroenraya.model.*;
import com.nethcorp.cuatroenraya.ai.*;

public class ControladorJuego {
	private final Partida partida;
    private final VistaTexto vistaIU;

    public ControladorJuego(Partida partida, VistaTexto vista) {
        this.partida = partida;
        this.vistaIU = vista;
    }

    public void iniciarPartida() {
    	Dificultad  difElegida = vistaIU.solicitarDificultadIA();
    	
    	MotorIA ia = new MotorIA(difElegida, EstadoCelda.JUGADOR_2);

        while(!partida.verificarFinalJuego()) {
            vistaIU.dibujarTablero(partida.obtenerTablero());
            
            EstadoCelda jugadorActual = partida.obtenerTurnoActual();

            int columnaElegida;
            
            if(jugadorActual == EstadoCelda.JUGADOR_1) {
            	columnaElegida = vistaIU.solicitarColumna(partida.obtenerTurnoActual());
            }else {
            	columnaElegida = ia.calcularMejorMovimiento(partida.obtenerTablero());
            }

            ResultadoTurno resultado = partida.jugar(columnaElegida);

            switch(resultado) {
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
