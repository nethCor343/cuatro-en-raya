package com.nethcorp.cuatroenraya.ai;

import com.nethcorp.cuatroenraya.model.EstadoCelda;
import com.nethcorp.cuatroenraya.model.Tablero;

public class SimuladorTablero {
	private final int[][] matriz;
	private final int fila;
	private final int columna;
	
	public SimuladorTablero(Tablero tabOrig) {
		this.fila = tabOrig.obtenerFila();
		this.columna = tabOrig.obtenerColumna();
		matriz = new int[fila][columna];
		clonarEstado(tabOrig);
	}
	
	public void clonarEstado(Tablero tab) {
		for(int i=0; i<fila; i++) {
			for(int j=0; j<columna; j++) {
				EstadoCelda estd = tab.obtenerCelda(i, j);
				if(estd == EstadoCelda.JUGADOR_1) {
					matriz[i][j] = 1;
				}else if(estd == EstadoCelda.JUGADOR_2) {
					matriz[i][j] = 2;
				}else {
					matriz[i][j] = 0;
				}
			}
		}
	}	
}
