package com.nethcorp.cuatroenraya.view;

import com.nethcorp.cuatroenraya.model.*;
import java.util.Scanner;

public class VistaTexto {
	private final Scanner scanner;

    public VistaTexto() {
        scanner = new Scanner(System.in);
    }

    public void dibujarTablero(Tablero tablero) {
        System.out.println();
        
        for(int columna = 0; columna < tablero.obtenerColumna(); columna++) {
            System.out.print(" " + (columna + 1) + " ");
        }
        System.out.println();

        for(int indiceFila = 0; indiceFila < tablero.obtenerFila(); indiceFila++) {
            for(int indiceColumna = 0; indiceColumna < tablero.obtenerColumna(); indiceColumna++) {
                String simbolo = ".";
                EstadoCelda celda = tablero.obtenerCelda(indiceFila, indiceColumna);
                if(celda == EstadoCelda.JUGADOR_1) {
                	simbolo = "O";
                }
                
                if(celda == EstadoCelda.JUGADOR_2) {
                	simbolo = "X";
                }
                
                System.out.print("[" + simbolo + "]");
            }
            System.out.println();
        }
    }

    public int solicitarColumna(EstadoCelda jugadorActual) {
        String simbolo = (jugadorActual == EstadoCelda.JUGADOR_1) ? "O" : "X";
        System.out.print("Turno de [" + simbolo + "]. Introduce columna: ");
        
        while(!scanner.hasNextInt()) {
            System.out.print("Numero invalido. Introduce un numero: ");
            scanner.next();
        }
        
        int columnaJugador = scanner.nextInt();
        return columnaJugador - 1;
    }

    public void mostrarMensajeVictoria(EstadoCelda ganador) {
        String simbolo = (ganador == EstadoCelda.JUGADOR_1) ? "O" : "X";
        System.out.println("El Jugador [" + simbolo + "] ha ganado.");
    }

}
