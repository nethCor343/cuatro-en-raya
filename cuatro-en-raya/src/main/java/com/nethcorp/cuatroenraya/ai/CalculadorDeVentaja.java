package com.nethcorp.cuatroenraya.ai;

public class CalculadorDeVentaja {
    public static final int PUNTOS_VICTORIA = 1000000;
    private static final int PUNTOS_TRES_FICHAS = 100;
    private static final int PUNTOS_DOS_FICHAS = 10;
    private static final int PENALIZACION_TRES_OPONENTE = -80;

    public int analizarVentaja(SimuladorTablero simulador, int ai, int humano) {
        int puntaje = 0;
        int[][] matriz = simulador.obtenerTablero();
        int fila = simulador.obtenerFilas();
        int columna = simulador.obtenerColumnas();

        int columCentro = columna/2;
        int fichasCentro = 0;
        for(int f = 0; f < fila; f++) {
            if(matriz[f][columCentro] == ai) {
            	fichasCentro++;
            }
        }
        
        puntaje += fichasCentro*3;

        for(int f=0; f<fila; f++) {
            for(int c=0; c<columna-3; c++) {
                puntaje += evaluarVector(new int[]{matriz[f][c],
                		matriz[f][c+1], matriz[f][c+2], matriz[f][c+3]}, ai, humano);
            }
        }
        
        for(int c=0; c<columna; c++) {
            for(int f=0; f<fila-3; f++) {
                puntaje += evaluarVector(new int[]{matriz[f][c],
                		matriz[f+1][c], matriz[f+2][c], matriz[f+3][c]}, ai, humano);
            }
        }
        
        for(int f=0; f<fila-3; f++) {
            for(int c=0; c<columna-3; c++) {
                puntaje += evaluarVector(new int[]{matriz[f][c],
                		matriz[f+1][c+1], matriz[f+2][c+2], matriz[f+3][c+3]}, ai, humano);
            }
        }
        
        for(int f=3; f<fila; f++) {
            for (int c=0; c<columna-3; c++) {
                puntaje += evaluarVector(new int[]{matriz[f][c],
                		matriz[f-1][c+1], matriz[f-2][c+2], matriz[f-3][c+3]}, ai, humano);
            }
        }

        return puntaje;
    }

    private int evaluarVector(int[] vector, int ai, int humano) {
        int fichasAI = 0, fichasHumano = 0, vacios = 0;

        for(int i=0; i<vector.length; i++) {
        	if(vector[i]==ai) {
        		fichasAI++;
        	}else if(vector[i]==humano) {
        		fichasHumano++;
        	}else {
        		vacios++;
        	}
        }
        
        if(fichasAI == 4) {
        	return PUNTOS_VICTORIA;
        }
        
        if(fichasAI == 3 && vacios == 1) {
        	return PUNTOS_TRES_FICHAS;
        }
        
        if(fichasAI == 2 && vacios == 2) {
        	return PUNTOS_DOS_FICHAS;
        }
        
        if(fichasHumano == 3 && vacios == 1) {
        	return PENALIZACION_TRES_OPONENTE;
        }

        return 0;
    }
    
}