package com.nethcorp.cuatroenraya.ai;

public class CalculadorDeVentaja {
    public static final int PUNTOS_VICTORIA = 1000000;
    private static final int PUNTOS_TRES_FICHAS = 100;
    private static final int PUNTOS_DOS_FICHAS = 10;
    private static final int PENALIZACION_TRES_OPONENTE = -80;

    public int analizarVentaja(SimuladorTablero tableroSimulado, int fichaIA, int fichaHumano) {
        int ventajaNeta = 0;
        int[][] tablero = tableroSimulado.obtenerTablero();
        int filas = tableroSimulado.obtenerFilas();
        int columnas = tableroSimulado.obtenerColumnas();

        int columnaCentro = columnas / 2;
        int fichaCentro = 0;
        
        for(int indiceFila = 0; indiceFila < filas; indiceFila++) {
            if(tablero[indiceFila][columnaCentro] == fichaIA) {
            	fichaCentro++;
            }
        }
        
        ventajaNeta += fichaCentro * 3;

        for(int indiceFila = 0; indiceFila < filas; indiceFila++) {
            for(int indiceColumna = 0; indiceColumna < columnas - 3; indiceColumna++) {
                ventajaNeta += evaluarVector(new int[]{tablero[indiceFila][indiceColumna],
                		tablero[indiceFila][indiceColumna + 1], tablero[indiceFila][indiceColumna + 2],
                		tablero[indiceFila][indiceColumna + 3]}, fichaIA, fichaHumano);
            }
        }
        
        for(int indiceColumna = 0; indiceColumna < columnas; indiceColumna++) {
            for(int indiceFila = 0; indiceFila < filas - 3; indiceFila++) {
                ventajaNeta += evaluarVector(new int[]{tablero[indiceFila][indiceColumna],
                		tablero[indiceFila + 1][indiceColumna], tablero[indiceFila + 2][indiceColumna],
                		tablero[indiceFila + 3][indiceColumna]}, fichaIA, fichaHumano);
            }
        }
        
        for(int indiceFila = 0; indiceFila < filas - 3; indiceFila++) {
            for(int indiceColumna = 0; indiceColumna < columnas - 3; indiceColumna++) {
                ventajaNeta += evaluarVector(new int[]{tablero[indiceFila][indiceColumna],
                		tablero[indiceFila + 1][indiceColumna + 1], tablero[indiceFila + 2][indiceColumna + 2],
                		tablero[indiceFila + 3][indiceColumna + 3]}, fichaIA, fichaHumano);
            }
        }
        
        for(int indiceFila = 3; indiceFila < filas; indiceFila++) {
            for (int indiceColumna = 0; indiceColumna < columnas - 3; indiceColumna++) {
                ventajaNeta += evaluarVector(new int[]{tablero[indiceFila][indiceColumna],
                		tablero[indiceFila - 1][indiceColumna + 1], tablero[indiceFila - 2][indiceColumna + 2],
                		tablero[indiceFila - 3][indiceColumna + 3]}, fichaIA, fichaHumano);
            }
        }

        return ventajaNeta;
    }

    private int evaluarVector(int[] vector, int fichaIA, int fichaHumano) {
        int contadorIA, contadorHumano, contadorVacio;
        contadorIA = contadorHumano = contadorVacio = 0;

        for(int indice = 0; indice < vector.length; indice++) {
        	if(vector[indice] == fichaIA) {
        		contadorIA++;
        	}else if(vector[indice] == fichaHumano) {
        		contadorHumano++;
        	}else {
        		contadorVacio++;
        	}
        }
        
        if(contadorIA == 4) {
        	return PUNTOS_VICTORIA;
        }
        
        if(contadorIA == 3 && contadorVacio == 1) {
        	return PUNTOS_TRES_FICHAS;
        }
        
        if(contadorIA == 2 && contadorVacio == 2) {
        	return PUNTOS_DOS_FICHAS;
        }
        
        if(contadorHumano == 3 && contadorVacio == 1) {
        	return PENALIZACION_TRES_OPONENTE;
        }

        return 0;
    }
    
}