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
	}
	
}
