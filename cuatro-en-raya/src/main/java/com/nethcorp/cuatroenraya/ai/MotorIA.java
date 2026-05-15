package com.nethcorp.cuatroenraya.ai;

import com.nethcorp.cuatroenraya.model.EstadoCelda;
import com.nethcorp.cuatroenraya.model.Tablero;
import java.util.List;

public class MotorIA {
	private final int profundidadMaxima;
	private final int fichaIA;
	private final int fichaHumano;
	private final CalculadorDeVentaja evaluadorVentaja;
	
	public MotorIA(Dificultad dificultad, EstadoCelda jugadorIA) {
		profundidadMaxima = dificultad.obtenerProfundidad();
		fichaIA = (jugadorIA == EstadoCelda.JUGADOR_1) ? 1:2;
		fichaHumano = (fichaIA == 1) ? 2:1;
		evaluadorVentaja = new CalculadorDeVentaja();
	}
	
	public int calcularMejorMovimiento(Tablero tableroReal) {
        SimuladorTablero tableroSimulado = new SimuladorTablero(tableroReal);
        int mejorPuntaje = Integer.MIN_VALUE;
        int mejorColumna = -1;
        
        List<Integer> columnasValidas = tableroSimulado.obtenerColumnasValidas();
        int columnaCentral = tableroSimulado.obtenerColumnas() / 2;
        
        if(columnasValidas.contains(columnaCentral)) {
            mejorColumna = columnaCentral; 
        }else if(!columnasValidas.isEmpty()) {
            mejorColumna = columnasValidas.get(0);
        }

        for(int indice = 0; indice < columnasValidas.size(); indice++) {
        	int columna = columnasValidas.get(indice);
            int filaCaida = tableroSimulado.aplicarGravedad(columna, fichaIA);
            int puntaje = minimax(tableroSimulado, profundidadMaxima - 1, Integer.MIN_VALUE, Integer.MAX_VALUE, false);
            tableroSimulado.deshacerGravedad(columna, filaCaida);

            if(puntaje > mejorPuntaje) {
                mejorPuntaje = puntaje;
                mejorColumna = columna;
            }
        }
        
        return mejorColumna;
    }
  
    private int minimax(SimuladorTablero tableroSimulado, int profundidad, int alfa, int beta, boolean turnoIA) {
        boolean juegoTerminado = tableroSimulado.verificarVictoria(fichaIA) || tableroSimulado.verificarVictoria(fichaHumano) ||
        		tableroSimulado.obtenerColumnasValidas().isEmpty();
        
        if(profundidad == 0 || juegoTerminado) {
            if(juegoTerminado) {
                if(tableroSimulado.verificarVictoria(fichaIA)) {
                	return CalculadorDeVentaja.PUNTOS_VICTORIA;
                }
                
                if(tableroSimulado.verificarVictoria(fichaHumano)) {
                	return -CalculadorDeVentaja.PUNTOS_VICTORIA;
                }
                return 0;
                
            }
            return evaluadorVentaja.analizarVentaja(tableroSimulado, fichaIA, fichaHumano);
            
        }

        if(turnoIA) {
            int puntajeMaximoRuta = Integer.MIN_VALUE;
            for(int columna : tableroSimulado.obtenerColumnasValidas()) {
                int fila = tableroSimulado.aplicarGravedad(columna, fichaIA);
                int puntajeRamaActual = minimax(tableroSimulado, profundidad - 1, alfa, beta, false);
                tableroSimulado.deshacerGravedad(columna, fila);
                
                puntajeMaximoRuta = Math.max(puntajeMaximoRuta, puntajeRamaActual);
                alfa = Math.max(alfa, puntajeRamaActual);
                if(beta <= alfa) {
                	break;
                }
            }
            
            return puntajeMaximoRuta;

        }else {
        	
            int puntajeMinimoRuta = Integer.MAX_VALUE;
            
            for(int columna : tableroSimulado.obtenerColumnasValidas()) {
                int fila = tableroSimulado.aplicarGravedad(columna, fichaHumano);
                int puntajeRamaActual = minimax(tableroSimulado, profundidad - 1, alfa, beta, true);
                tableroSimulado.deshacerGravedad(columna, fila);
                
                puntajeMinimoRuta = Math.min(puntajeMinimoRuta, puntajeRamaActual);
                beta = Math.min(beta, puntajeRamaActual);
                
                if(beta <= alfa) {
                	break;
                }
            }
            
            return puntajeMinimoRuta;
        }
    }
    
}