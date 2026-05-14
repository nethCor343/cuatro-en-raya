package com.nethcorp.cuatroenraya.ai;

import com.nethcorp.cuatroenraya.model.EstadoCelda;
import com.nethcorp.cuatroenraya.model.Tablero;

import java.util.ArrayList;
import java.util.List;

public class SimuladorTablero {
	private final int[][] tablero;
	private final int filas;
	private final int columnas;
	
	public SimuladorTablero(Tablero tableroOriginal) {
		this.filas = tableroOriginal.obtenerFila();
		this.columnas = tableroOriginal.obtenerColumna();
		tablero = new int[filas][columnas];
		clonarEstado(tableroOriginal);
	}
	
	public void clonarEstado(Tablero tableroOriginal) {
		for(int indiceFila = 0; indiceFila < filas; indiceFila++) {
			for(int indiceColumna = 0; indiceColumna < columnas; indiceColumna++) {
				EstadoCelda estado = tableroOriginal.obtenerCelda(indiceFila, indiceColumna);
				if(estado == EstadoCelda.JUGADOR_1) {
					tablero[indiceFila][indiceColumna] = 1;
				}else if(estado == EstadoCelda.JUGADOR_2) {
					tablero[indiceFila][indiceColumna] = 2;
				}else {
					tablero[indiceFila][indiceColumna] = 0;
				}
			}
		}
	}
	
	public int aplicarGravedad(int indiceColumna, int fichaJugador) {
		for(int indiceFila = filas - 1; indiceFila >= 0; indiceFila--) {
			if(tablero[indiceFila][indiceColumna] == 0) {
				tablero[indiceFila][indiceColumna] = fichaJugador;
				return indiceFila;
			}
		}
		return -1;
	}
	
	public void deshacerGravedad(int indiceColumna, int indiceFila) {
		tablero[indiceFila][indiceColumna] = 0;
	}
	
	public List<Integer> obtenerColumnasValidas() {
		List<Integer> columnasValidas = new ArrayList<>();
		for(int indiceColumna = 0; indiceColumna < columnas; indiceColumna++) {
			if(tablero[0][indiceColumna] == 0) {
				columnasValidas.add(indiceColumna);
			}
		}
		
		return columnasValidas;
	}
	
	public boolean verificarVictoria(int jugador) {
		//horizontal
		for(int indiceColumna = 0; indiceColumna < columnas - 3; indiceColumna++) {
			for(int indiceFila = 0; indiceFila < filas; indiceFila++) {
				if(tablero[indiceFila][indiceColumna] == jugador && tablero[indiceFila][indiceColumna + 1] == jugador &&
				   tablero[indiceFila][indiceColumna + 2] == jugador && tablero[indiceFila][indiceColumna + 3] == jugador) {
					return true;
				}
			}
		}
		
		//vertical
		for(int indiceColumna = 0; indiceColumna < columnas; indiceColumna++) {
			for(int indiceFila = 0; indiceFila < filas - 3; indiceFila++) {
				if(tablero[indiceFila][indiceColumna] == jugador && tablero[indiceFila + 1][indiceColumna] == jugador &&
				   tablero[indiceFila + 2][indiceColumna] == jugador && tablero[indiceFila + 3][indiceColumna] == jugador) {
					return true;
				}
			}
		}
		
		//diagonal \
		for(int indiceColumna = 0; indiceColumna < columnas - 3; indiceColumna++) {
			for(int indiceFila = 0; indiceFila < filas - 3; indiceFila++) {
				if(tablero[indiceFila][indiceColumna] == jugador && tablero[indiceFila + 1][indiceColumna + 1] == jugador &&
				   tablero[indiceFila + 2][indiceColumna + 2] == jugador && tablero[indiceFila + 3][indiceColumna + 3] == jugador) {
					return true;
				}
			}
		}
		
		//diagonal /
		for(int indiceColumna = 0; indiceColumna < columnas - 3; indiceColumna++) {
			for(int indiceFila = 3; indiceFila < filas; indiceFila++) {
				if(tablero[indiceFila][indiceColumna] == jugador && tablero[indiceFila - 1][indiceColumna + 1] == jugador &&
				   tablero[indiceFila - 2][indiceColumna + 2] == jugador && tablero[indiceFila - 3][indiceColumna + 3] == jugador) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	public int[][] obtenerTablero() {
		return tablero;
	}
	
	public int obtenerFilas() {
		return filas;
	}
	
	public int obtenerColumnas() {
		return columnas;
	}
	
}
