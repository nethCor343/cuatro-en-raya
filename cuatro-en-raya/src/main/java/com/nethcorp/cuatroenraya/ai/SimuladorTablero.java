package com.nethcorp.cuatroenraya.ai;

import com.nethcorp.cuatroenraya.model.EstadoCelda;
import com.nethcorp.cuatroenraya.model.Tablero;

import java.util.ArrayList;
import java.util.List;


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
	
	public int aplicarGravedad(int c, int idJug) {
		for(int i = fila-1; i>=0; i--) {
			if(matriz[i][c] == 0) {
				matriz[i][c] = idJug;
				return i;
			}
		}
		return -1;
	}
	
	public void deshacerGravedad(int c, int f) {
		matriz[f][c]=0;
	}
	
	public List<Integer> obtenerColumnaValidas() {
		List<Integer> valido = new ArrayList<>();
		for(int i=0; i<columna; i++) {
			if(matriz[0][i]==0) {
				valido.add(i);
			}
		}
		return valido;
	}
	
	public boolean verificarVictoria(int jug) {
		//horizontal
		for(int i=0; i<columna-3; i++) {
			for(int j=0; j<fila;j++) {
				if(matriz[j][i]==jug && matriz[j][i+1]==jug &&
				   matriz[j][i+2]==jug && matriz[j][i+3]==jug) {
					return true;
				}
			}
		}
		
		//vertical
		for(int i=0; i<columna; i++) {
			for(int j=0; j<fila-3; j++) {
				if(matriz[j][i]==jug && matriz[j+1][i]==jug &&
				   matriz[j+2][i]==jug && matriz[j+3][i]==jug) {
					return true;
				}
			}
		}
		
		//diagonal \
		for(int i=0; i<columna-3; i++) {
			for(int j=0; j<fila-3; j++) {
				if(matriz[j][i]==jug && matriz[j+1][i+1]==jug &&
				   matriz[j+2][i+2]==jug && matriz[j+3][i+3]==jug) {
					return true;
				}
			}
		}
		
		//diagonal /
		for(int i=0; i<columna-3; i++) {
			for(int j=3; j<fila; j++) {
				if(matriz[j][i]==jug && matriz[j-1][i+1]==jug &&
				   matriz[j-2][i+2]==jug && matriz[j-3][i+3]==jug) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	public int[][] obtenerMatriz() {
		return matriz;
	}
	
	public int obtenerFilas() {
		return fila;
	}
	
	public int obtenerColumna() {
		return columna;
	}
	
}
