package com.nethcorp.cuatroenraya.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TableroTest {

    private Tablero tablero;
    private final int FILAS = 6;
    private final int COLUMNAS = 7;

    @BeforeEach
    public void setUp() {
        tablero = new Tablero(FILAS, COLUMNAS);
    }

    // tests de comportamiento normal

    @Test
    public void testInicializacionTablero() {
        assertEquals(FILAS, tablero.obtenerFila(), "las filas no coinciden con la inicializacion");
        assertEquals(COLUMNAS, tablero.obtenerColumna(), "las columnas no coinciden con la inicializacion");
        assertFalse(tablero.estaLleno(), "el tablero no deberia estar lleno al inicio");
        
        verificarQueElTableroEstaCompletamenteVacio(tablero);
    }

    @Test
    public void testDejarCaerFichaFondo() {
        int columnaObjetivo = 0;
        int filaFondoEsperada = FILAS - 1; //5

        int filaResultante = tablero.dejarCaerFicha(columnaObjetivo, EstadoCelda.JUGADOR_1);
        
        assertEquals(filaFondoEsperada, filaResultante, "la ficha deberia haber caido hasta el fondo");
        assertEquals(EstadoCelda.JUGADOR_1, tablero.obtenerCelda(filaFondoEsperada, columnaObjetivo));
    }

    @Test
    public void testDejarCaerFichaApilada() {
        int columnaObjetivo = 3;
        int filaApiladaEsperada = FILAS - 2; //4
        
        tablero.dejarCaerFicha(columnaObjetivo, EstadoCelda.JUGADOR_1); 
        int filaSegundaFicha = tablero.dejarCaerFicha(columnaObjetivo, EstadoCelda.JUGADOR_2); 
        
        assertEquals(filaApiladaEsperada, filaSegundaFicha, "la ficha deberia estar justo encima de la anterior");
        assertEquals(EstadoCelda.JUGADOR_2, tablero.obtenerCelda(filaApiladaEsperada, columnaObjetivo));
    }

    @Test
    public void testEstaLlenoVerdadero() {
        llenarTableroPorCompleto(EstadoCelda.JUGADOR_1);
        
        boolean resultado = tablero.estaLleno();
        
        assertTrue(resultado, "el tablero deberia detectar que esta completamente lleno");
    }

    // tests de excepciones

    @Test
    public void testInicializacionTableroInvalida() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Tablero(-1, 7);
        }, "deberia lanzar excepcion al crear tablero con filas negativas");

        assertThrows(IllegalArgumentException.class, () -> {
            new Tablero(6, 0);
        }, "deberia lanzar excepcion al crear tablero con 0 columnas");
    }

    @Test
    public void testDejarCaerFichaColumnaLlena() {
        int columnaObjetivo = 2;
        llenarColumna(columnaObjetivo, EstadoCelda.JUGADOR_1);

        assertThrows(IllegalStateException.class, () -> {
            tablero.dejarCaerFicha(columnaObjetivo, EstadoCelda.JUGADOR_2);
        }, "deberia lanzar IllegalStateException al tirar en una columna llena");
    }

    @Test
    public void testDejarCaerFichaColumnaInvalida() {
        assertThrows(IllegalArgumentException.class, () -> {
            tablero.dejarCaerFicha(-1, EstadoCelda.JUGADOR_1);
        }, "deberia lanzar excepcion por columna negativa");

        assertThrows(IllegalArgumentException.class, () -> {
            tablero.dejarCaerFicha(COLUMNAS, EstadoCelda.JUGADOR_1);
        }, "deberia lanzar excepcion por columna fuera del limite derecho");
    }

    @Test
    public void testObtenerCeldaIndicesInvalidos() {
        assertThrows(IndexOutOfBoundsException.class, () -> {
            tablero.obtenerCelda(-1, 0);
        }, "deberia lanzar excepcion por fila negativa");

        assertThrows(IndexOutOfBoundsException.class, () -> {
            tablero.obtenerCelda(0, -1);
        }, "deberia lanzar excepcion por columna negativa");

        assertThrows(IndexOutOfBoundsException.class, () -> {
            tablero.obtenerCelda(FILAS, 0);
        }, "deberia lanzar excepcion por fila excedida");

        assertThrows(IndexOutOfBoundsException.class, () -> {
            tablero.obtenerCelda(0, COLUMNAS);
        }, "deberia lanzar excepcion por columna excedida");
    }

    // metodos auxiliares
    
    private void verificarQueElTableroEstaCompletamenteVacio(Tablero tab) {
        for(int f=0; f<tab.obtenerFila(); f++) {
            for(int c=0; c<tab.obtenerColumna(); c++) {
                assertEquals(EstadoCelda.VACIO, tab.obtenerCelda(f, c),
                		"se encontro una celda no vacia en la inicializacion");
            }
        }
    }

    private void llenarColumna(int columna, EstadoCelda jugador) {
        for(int i=0; i<tablero.obtenerFila(); i++) {
            tablero.dejarCaerFicha(columna, jugador);
        }
    }

    private void llenarTableroPorCompleto(EstadoCelda jugador) {
        for(int c=0; c<tablero.obtenerColumna(); c++) {
            llenarColumna(c, jugador);
        }
    }
}