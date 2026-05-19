package com.nethcorp.cuatroenraya.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReglasJuegoTest {

    private ReglasJuego reglas;
    private Tablero tablero;

    @BeforeEach
    public void setUp() {
        reglas = new ReglasJuego();
        tablero = new Tablero(6, 7);
    }

 // tests de comportamiento normal

    @Test
    public void testVictoriaHorizontal() {
        tablero.dejarCaerFicha(0, EstadoCelda.JUGADOR_1);
        tablero.dejarCaerFicha(1, EstadoCelda.JUGADOR_1);
        tablero.dejarCaerFicha(2, EstadoCelda.JUGADOR_1);
        tablero.dejarCaerFicha(3, EstadoCelda.JUGADOR_1);
        
        boolean hayVictoria = reglas.verificarVictoria(tablero, 5, 3);
        
        assertTrue(hayVictoria, "deberia detectar victoria horizontal");
        assertEquals(EstadoCelda.JUGADOR_1, reglas.obtenerGanador(), "el ganador debe ser JUGADOR_1");
    }

    @Test
    public void testVictoriaVertical() {
    	tablero.dejarCaerFicha(0, EstadoCelda.JUGADOR_1);
        tablero.dejarCaerFicha(0, EstadoCelda.JUGADOR_1);
        tablero.dejarCaerFicha(0, EstadoCelda.JUGADOR_1);
        tablero.dejarCaerFicha(0, EstadoCelda.JUGADOR_1);
        
        boolean hayVictoria = reglas.verificarVictoria(tablero, 2, 0);
        
        assertTrue(hayVictoria, "deberia detectar victoria vertical");
        assertEquals(EstadoCelda.JUGADOR_1, reglas.obtenerGanador());
    }

    @Test
    public void testVictoriaDiagonalPositiva() {
    	// (/)
        // columna 0: [J1]
        tablero.dejarCaerFicha(0, EstadoCelda.JUGADOR_1);
        
        // columna 1: [J2] -> [J1]
        tablero.dejarCaerFicha(1, EstadoCelda.JUGADOR_2);
        tablero.dejarCaerFicha(1, EstadoCelda.JUGADOR_1);
        
        // columna 2: [J2] -> [J2] -> [J1]
        tablero.dejarCaerFicha(2, EstadoCelda.JUGADOR_2);
        tablero.dejarCaerFicha(2, EstadoCelda.JUGADOR_2);
        tablero.dejarCaerFicha(2, EstadoCelda.JUGADOR_1);
        
        // columna 3: [J2] -> [J2] -> [J2] -> [J1]
        tablero.dejarCaerFicha(3, EstadoCelda.JUGADOR_2);
        tablero.dejarCaerFicha(3, EstadoCelda.JUGADOR_2);
        tablero.dejarCaerFicha(3, EstadoCelda.JUGADOR_2);
        tablero.dejarCaerFicha(3, EstadoCelda.JUGADOR_1);
        
        boolean hayVictoria = reglas.verificarVictoria(tablero, 2, 3);
        
        assertTrue(hayVictoria, "deberia detectar victoria diagonal positiva (/)");
    }
    
    @Test
    public void testVictoriaDiagonalNegativa() {
    	// (\)
        // columna 0: [J2] -> [J2] -> [J2] -> [J1]
        tablero.dejarCaerFicha(0, EstadoCelda.JUGADOR_2);
        tablero.dejarCaerFicha(0, EstadoCelda.JUGADOR_2);
        tablero.dejarCaerFicha(0, EstadoCelda.JUGADOR_2);
        tablero.dejarCaerFicha(0, EstadoCelda.JUGADOR_1);
        
        // columna 1: [J2] -> [J2] -> [J1]
        tablero.dejarCaerFicha(1, EstadoCelda.JUGADOR_2);
        tablero.dejarCaerFicha(1, EstadoCelda.JUGADOR_2);
        tablero.dejarCaerFicha(1, EstadoCelda.JUGADOR_1);
        
        // columna 2: [J2] -> [J1]
        tablero.dejarCaerFicha(2, EstadoCelda.JUGADOR_2);
        tablero.dejarCaerFicha(2, EstadoCelda.JUGADOR_1);
        
        // columna 3: [J1]
        tablero.dejarCaerFicha(3, EstadoCelda.JUGADOR_1);
 
        boolean hayVictoria = reglas.verificarVictoria(tablero, 2, 0);
        
        assertTrue(hayVictoria, "deberia detectar victoria diagonal negativa (\\)");
    }
    
    @Test
    public void testVictoriaRellenandoHuecoCentral() {
        // [J1] [J1] [VACIO] [J1]
        tablero.dejarCaerFicha(0, EstadoCelda.JUGADOR_1);
        tablero.dejarCaerFicha(1, EstadoCelda.JUGADOR_1);
        tablero.dejarCaerFicha(3, EstadoCelda.JUGADOR_1);

        int fila = tablero.dejarCaerFicha(2, EstadoCelda.JUGADOR_1);
        boolean hayVictoria = reglas.verificarVictoria(tablero, fila, 2);

        assertTrue(hayVictoria, "deberia detectar victoria al rellenar un hueco entre fichas");
    }

    @Test
    public void testNoHayVictoriaAun() {

        tablero.dejarCaerFicha(0, EstadoCelda.JUGADOR_1);
        int ultimaFila = tablero.dejarCaerFicha(0, EstadoCelda.JUGADOR_1);

        boolean hayVictoria = reglas.verificarVictoria(tablero, ultimaFila, 0);

        assertFalse(hayVictoria, "no deberia haber victoria con solo 2 fichas");
        assertNull(reglas.obtenerGanador(), "el ganador debe seguir siendo nulo");
    }
    
    @Test
    public void testVerificarEmpateFalsoTableroIncompleto() {

        boolean esEmpate = reglas.verificarEmpate(tablero);

        assertFalse(esEmpate, "no puede haber empate si el tablero no esta lleno");
    }
    
    // tests de excepciones

    @Test
    public void testVerificarVictoriaTableroNuloLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> {
            reglas.verificarVictoria(null, 0, 0);
        }, "deberia fallar si le pasamos un tablero nulo");
    }

    @Test
    public void testVerificarVictoriaCoordenadasInvalidasLanzaExcepcion() {
        // fila negativa
        assertThrows(IndexOutOfBoundsException.class, () -> reglas.verificarVictoria(tablero, -1, 0));
        // columna negativa
        assertThrows(IndexOutOfBoundsException.class, () -> reglas.verificarVictoria(tablero, 0, -1));
        // fila excedida
        assertThrows(IndexOutOfBoundsException.class, () -> reglas.verificarVictoria(tablero, 6, 0));
        // columna excedida
        assertThrows(IndexOutOfBoundsException.class, () -> reglas.verificarVictoria(tablero, 0, 7));
    }

    @Test
    public void testVerificarVictoriaCeldaVaciaLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> {
            reglas.verificarVictoria(tablero, 5, 0); // 5 es el fondo, esta vacio
        }, "deberia fallar al intentar verificar victoria sobre una celda vacia");
    }
    
    @Test
    public void testVerificarEmpateTableroNuloLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> {
            reglas.verificarEmpate(null);
        }, "deberia fallar si le pasamos un tablero nulo al verificar empate");
    }
   
}