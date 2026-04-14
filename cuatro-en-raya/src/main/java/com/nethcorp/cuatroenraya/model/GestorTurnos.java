package com.nethcorp.cuatroenraya.model;

public class GestorTurnos {
	private EstadoCelda turnoActual;
	
	public GestorTurnos() {
		turnoActual = EstadoCelda.JUGADOR_1; 
	}
	
	public void siguienteTurno() {
		turnoActual = (turnoActual == EstadoCelda.JUGADOR_1)
						? EstadoCelda.JUGADOR_2
						: EstadoCelda.JUGADOR_1;
	}
	
	public EstadoCelda obtenerTurnoActual() {
		return turnoActual;
	}

}
