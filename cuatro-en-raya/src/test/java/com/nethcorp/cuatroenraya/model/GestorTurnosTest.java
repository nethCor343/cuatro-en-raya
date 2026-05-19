package com.nethcorp.cuatroenraya.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GestorTurnosTest {

    private GestorTurnos gestor;

    @BeforeEach
    public void setUp() {
        gestor = new GestorTurnos();
    }

    @Test
    public void testInicializacionTurno() {
        assertEquals(EstadoCelda.JUGADOR_1, gestor.obtenerTurnoActual(),
        		"el primer turno deberia ser del JUGADOR_1");
    }

    @Test
    public void testSiguienteTurnoCambiaAJugador2() {
        gestor.siguienteTurno();
        
        assertEquals(EstadoCelda.JUGADOR_2, gestor.obtenerTurnoActual(),
        		"despues del Jugador 1, le toca al JUGADOR_2");
    }

    @Test
    public void testSiguienteTurnoCambiaAJugador1() {
        gestor.siguienteTurno(); 
        gestor.siguienteTurno();
        
        assertEquals(EstadoCelda.JUGADOR_1, gestor.obtenerTurnoActual(),
        		"despues del Jugador 2, el turno debe volver al JUGADOR_1");
    }
    
}