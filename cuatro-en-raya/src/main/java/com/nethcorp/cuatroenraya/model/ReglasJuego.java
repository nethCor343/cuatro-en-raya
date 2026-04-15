package com.nethcorp.cuatroenraya.model;

public class ReglasJuego {
	private static final int FICHAS_PARA_GANAR = 4;
	private EstadoCelda ganador;
	
	public ReglasJuego() {
		ganador = null;
	}
	
	public boolean verificarVictoria(Tablero tablero, int fila, int columna) {
        EstadoCelda ficha = tablero.obtenerCelda(fila, columna);
        
        int[][] direcciones = {{0, 1}, {1, 0}, {1, 1}, {1, -1}};

        for (int[] dir : direcciones) {
            int pasoFila = dir[0];
            int pasoColumna = dir[1];
            
            int linea = 1 + 
                contarConsecutivos(tablero, fila, columna, pasoFila, pasoColumna, ficha) +
                contarConsecutivos(tablero, fila, columna, -pasoFila, -pasoColumna, ficha);

            if (linea >= FICHAS_PARA_GANAR) {
                ganador = ficha;
                return true;
            }
        }
        return false;
    }
	
	private int contarConsecutivos(Tablero tablero, int fila, int columna, int pasoFila, int pasoColumna, EstadoCelda ficha) {
        int conteo = 0;
        int filaActual = fila + pasoFila;
        int columnaActual = columna + pasoColumna;

        while (filaActual >= 0 && filaActual < tablero.obtenerFila() &&
               columnaActual >= 0 && columnaActual < tablero.obtenerColumna() &&
               tablero.obtenerCelda(filaActual, columnaActual) == ficha) {
        	
            conteo++;
            
            filaActual += pasoFila;
            columnaActual += pasoColumna;
        }
        return conteo;
    }
	
	public boolean verificarEmpate(Tablero tablero) {
        return tablero.estaLleno() && ganador == null;
    }

    public EstadoCelda obtenerGanador() {
        return ganador;
    }

}
