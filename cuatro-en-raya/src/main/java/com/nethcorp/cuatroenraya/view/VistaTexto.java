package com.nethcorp.cuatroenraya.view;

import com.nethcorp.cuatroenraya.model.*;
import com.nethcorp.cuatroenraya.ai.*;
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
    
    public Dificultad solicitarDificultadIA() {
        System.out.println("\n DIFICULTAD ");
        System.out.println("1. Facil");
        System.out.println("2. Medio");
        System.out.println("3. Dificil");
        
        int opcion = 0;
        boolean entradaValida = false;

        while(!entradaValida) {
            System.out.print("Elige la dificultad (1-3): ");
            
            if(scanner.hasNextInt()) {
                opcion = scanner.nextInt();
                
                if(opcion >= 1 && opcion <= 3) {
                    entradaValida = true;
                }else {
                    System.out.println("Error: El numero debe ser 1, 2 o 3.");
                }
                
            }else {
                System.out.println("Error: dato invalido");
                scanner.next();
                
            }
        }

        if(opcion == 1) {
        	return Dificultad.FACIL;
        }
        
        if(opcion == 2) {
        	return Dificultad.MEDIO;
        }
        
        return Dificultad.DIFICIL;
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
