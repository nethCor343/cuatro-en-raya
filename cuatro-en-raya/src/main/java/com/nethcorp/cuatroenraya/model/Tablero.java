package com.nethcorp.cuatroenraya.model;

public class Tablero {
	private final int fila;
	private final int columna;
	private final EstadoCelda[][] celdas;
	private int fichasColocadas;
	
	public Tablero(int fila, int columna) {
		this.fila = fila;
		this.columna = columna;
		this.celdas = new EstadoCelda[fila][columna];
		fichasColocadas = 0;
		limpiarTablero();
	}
	
	private void limpiarTablero() {
		for(int indiceFila = 0; indiceFila < fila; indiceFila++) {
			for(int indiceColumna = 0; indiceColumna < columna; indiceColumna++) {
				celdas[indiceFila][indiceColumna] = EstadoCelda.VACIO;
			}
		}
	}
	
	public int dejarCaerFicha(int columnaFicha, EstadoCelda jugador) {
		if(columnaFicha < 0 || columnaFicha >= columna) {
			return -1;
		}
		
		for(int indiceFila = fila - 1; indiceFila >= 0; indiceFila--) {
			if(celdas[indiceFila][columna] == EstadoCelda.VACIO) {
				celdas[indiceFila][columna] = jugador;
				fichasColocadas++;
				return indiceFila;
			}
		}
		
		return -1;
	}
	
	public boolean estaLleno() {
		return fichasColocadas == (fila * columna);
	}
	
	public EstadoCelda obtenerCelda(int indiceFila, int indiceColumna) {
		if(indiceFila < 0 || indiceFila >= fila || indiceColumna < 0 || indiceColumna >= columna) {
			return null;
		}
		
		return celdas[indiceFila][indiceColumna];
	}
	
	public int obtenerFila() {
		return fila;
	}
	
	public int obtenerColumna() {
		return columna;
	}
	
}
