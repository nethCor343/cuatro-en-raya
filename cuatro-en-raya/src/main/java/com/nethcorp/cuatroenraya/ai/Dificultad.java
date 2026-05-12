package com.nethcorp.cuatroenraya.ai;

public enum Dificultad {
	FACIL(2),
    MEDIO(4),
    DIFICIL(6); 

    private final int profundidad;

    Dificultad(int profundidad) {
        this.profundidad = profundidad;
    }

    public int obtenerProfundidad() {
        return profundidad;
    }
}
