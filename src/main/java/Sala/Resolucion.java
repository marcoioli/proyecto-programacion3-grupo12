package Sala;

    /** Resultado de la comparacion de prioridades en la sala de espera. */
    public enum Resolucion {
        /** Gana la instancia que inicia la comparacion ("este"). */
        GANA_ESTE,
        /** Gana la otra instancia (el que ya estaba ocupando). */
        GANA_OTRO,
        /** Ambos tienen la misma prioridad. */
        EMPATE
    }

