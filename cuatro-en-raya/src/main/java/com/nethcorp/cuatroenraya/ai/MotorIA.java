package com.nethcorp.cuatroenraya.ai;

import com.nethcorp.cuatroenraya.model.EstadoCelda;
import com.nethcorp.cuatroenraya.model.Tablero;
import java.util.List;

public class MotorIA {
	private final int profMaxima;
	private final int ia;
	private final int humano;
	private final CalculadorDeVentaja calculador;
	
	public MotorIA(Dificultad dificultad, EstadoCelda newIa) {
		profMaxima = dificultad.obtenerProfundidad();
		ia = (newIa == EstadoCelda.JUGADOR_1) ? 1:2;
		humano = (ia == 1) ? 2:1;
		calculador = new CalculadorDeVentaja();
	}
	
	public int calcularMejorMovimiento(Tablero tabReal) {
        SimuladorTablero sim = new SimuladorTablero(tabReal);
        int mejPuntaje = Integer.MIN_VALUE;
        int mejColumna = -1;
        
        List<Integer> cValidas = sim.obtenerColumnasValidas();
        
        int cCentral = sim.obtenerColumnas()/2;
        if(cValidas.contains(cCentral)) {
            mejColumna = cCentral; 
        }else if(!cValidas.isEmpty()) {
            mejColumna = cValidas.get(0);
        }

        for(int col : cValidas) {
            int filaCaida = sim.aplicarGravedad(col, ia);
            int puntaje = minimax(sim, profMaxima - 1, Integer.MIN_VALUE, Integer.MAX_VALUE, false);
            sim.deshacerGravedad(col, filaCaida);

            if(puntaje > mejPuntaje) {
                mejPuntaje = puntaje;
                mejColumna = col;
            }
        }
        
        return mejColumna;
    }

    private int minimax(SimuladorTablero simTab, int profundidad, int alfa, int beta, boolean maximizando) {
        boolean esTerminal = simTab.verificarVictoria(ia) || simTab.verificarVictoria(humano) || simTab.obtenerColumnasValidas().isEmpty();
        
        if(profundidad == 0 || esTerminal) {
            if(esTerminal) {
                if(simTab.verificarVictoria(ia)) {
                	return CalculadorDeVentaja.PUNTOS_VICTORIA;
                }
                
                if(simTab.verificarVictoria(humano)) {
                	return - CalculadorDeVentaja.PUNTOS_VICTORIA;
                }
                return 0;
                
            }
            return calculador.analizarVentaja(simTab, ia, humano);
            
        }

        if(maximizando) {
            int maxEval = Integer.MIN_VALUE;
            for(int col : simTab.obtenerColumnasValidas()) {
                int fila = simTab.aplicarGravedad(col, ia);
                int eval = minimax(simTab, profundidad - 1, alfa, beta, false);
                simTab.deshacerGravedad(col, fila);
                
                maxEval = Math.max(maxEval, eval);
                alfa = Math.max(alfa, eval);
                if(beta <= alfa) {
                	break;
                }
            }
            return maxEval;
            
        }else {
        	
            int minEval = Integer.MAX_VALUE;
            
            for(int col : simTab.obtenerColumnasValidas()) {
                int fila = simTab.aplicarGravedad(col, humano);
                int eval = minimax(simTab, profundidad - 1, alfa, beta, true);
                simTab.deshacerGravedad(col, fila);
                
                minEval = Math.min(minEval, eval);
                beta = Math.min(beta, eval);
                
                if(beta <= alfa) {
                	break;
                }
            }
            return minEval;
        }
    }
}