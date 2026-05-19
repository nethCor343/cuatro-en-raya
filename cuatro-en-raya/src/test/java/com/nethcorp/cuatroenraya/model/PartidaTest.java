package com.nethcorp.cuatroenraya.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PartidaTest {

    private Partida partida;

    @BeforeEach
    public void setUp() {
        partida = new Partida(6, 7);
    }

    // tests de excepciones

    @Test
    public void testInicializacionInvalidaLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> new Partida(-1, 7), "Fila negativa debe fallar");
        assertThrows(IllegalArgumentException.class, () -> new Partida(6, 0), "Columna cero debe fallar");
    }

    @Test
    public void testJugarMovimientoInvalidoFueraDeLimites() {
        ResultadoTurno resultado = partida.jugar(-1);
        
        assertEquals(ResultadoTurno.MOVIMIENTO_INVALIDO, resultado);
        assertEquals(EstadoCelda.JUGADOR_1, partida.obtenerTurnoActual(),
        		"el turno no debio cambiar porque el movimiento fue invalido");
    }

    @Test
    public void testJugarMovimientoInvalidoColumnaLlena() {
    	partida.jugar(0); // J1
        partida.jugar(0); // J2
        partida.jugar(0); // J1
        partida.jugar(0); // J2
        partida.jugar(0); // J1
        partida.jugar(0); // J2

        ResultadoTurno resultado = partida.jugar(0);

        assertEquals(ResultadoTurno.MOVIMIENTO_INVALIDO, resultado);
    }

    // tests de comportamiento normal

    @Test
    public void testJugarMovimientoValidoRetornaEnCurso() {
        ResultadoTurno resultado = partida.jugar(0);
        
        assertEquals(ResultadoTurno.EN_CURSO, resultado);
        assertEquals(EstadoCelda.JUGADOR_2, partida.obtenerTurnoActual(),"El turno debio pasar al JUGADOR_2");
        assertFalse(partida.verificarFinalJuego(), "El juego no deberia haber terminado");
    }

    @Test
    public void testJugarVictoriaFinalizaJuego() {
    	partida.jugar(0);
        partida.jugar(1);
        partida.jugar(0);
        partida.jugar(1);
        partida.jugar(0);
        partida.jugar(1);
        
        ResultadoTurno resultadoVictoria = partida.jugar(0);

        assertEquals(ResultadoTurno.VICTORIA, resultadoVictoria);
        assertTrue(partida.verificarFinalJuego(), "el estado de la partida debe marcar finalizado");
        assertEquals(EstadoCelda.JUGADOR_1, partida.obtenerGanador(), "JUGADOR_1 deberia ser el ganador");
    }

    @Test
    public void testJugarDespuesDeTerminadoRetornaJuegoTerminado() {

    	partida.jugar(0);
        partida.jugar(1);
        partida.jugar(0);
        partida.jugar(1);
        partida.jugar(0);
        partida.jugar(1);
        
        
        partida.jugar(0);
        
        ResultadoTurno resultadoFueradeTiempo = partida.jugar(1);

        assertEquals(ResultadoTurno.JUEGO_TERMINADO, resultadoFueradeTiempo);
    }

}