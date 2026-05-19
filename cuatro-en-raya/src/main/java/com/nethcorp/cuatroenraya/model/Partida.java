package com.nethcorp.cuatroenraya.model;

public class Partida {
    private final Tablero tablero;
    private final GestorTurnos turnos;
    private final ReglasJuego reglas;
    private boolean finalizado;

    public Partida(int filas, int columnas) {
    	if (filas <= 0 || columnas <= 0) {
            throw new IllegalArgumentException("las dimensiones de la partida deben ser mayores a 0");
        }
    	
        this.tablero = new Tablero(filas, columnas);
        this.turnos = new GestorTurnos();
        this.reglas = new ReglasJuego();
        finalizado = false;
    }

    public ResultadoTurno jugar(int indiceColumna) {
        if(finalizado) {
        	return ResultadoTurno.JUEGO_TERMINADO;
        }

        EstadoCelda jugadorActual = turnos.obtenerTurnoActual();
        int filaColocada;
        
        try {
            filaColocada = tablero.dejarCaerFicha(indiceColumna, jugadorActual);
            
        }catch (IllegalArgumentException | IllegalStateException e) {
            return ResultadoTurno.MOVIMIENTO_INVALIDO;
        }

        if(reglas.verificarVictoria(tablero, filaColocada, indiceColumna)) {
            finalizado = true;
            return ResultadoTurno.VICTORIA;
        }

        if(reglas.verificarEmpate(tablero)) {
            finalizado = true;
            return ResultadoTurno.EMPATE;
        }

        turnos.siguienteTurno();
        return ResultadoTurno.EN_CURSO;
    }

    public Tablero obtenerTablero() {
    	return tablero;
    }
    
    public EstadoCelda obtenerTurnoActual() {
    	return turnos.obtenerTurnoActual();
    }
    
    public EstadoCelda obtenerGanador() {
    	return reglas.obtenerGanador();
    	
    }
    
    public boolean verificarFinalJuego() {
    	return finalizado;
    	
    }
    
}
