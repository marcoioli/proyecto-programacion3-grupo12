package Sala;

import java.util.*;
import Personas.Paciente;


/**
 * Representa la sala de espera privada de la clinica.
 * <p>
 * Solo puede haber un paciente dentro. Si otro intenta ingresar,
 * se aplica la comparacion de prioridades (Niño, Joven, Mayor)
 * mediante el patron <b>Double Dispatch</b>.
 * </p>
 * <p>
 * - Si el nuevo paciente gana: reemplaza al actual y el anterior pasa al {@link Patio}. <br>
 * - Si pierde o empata: el nuevo paciente es derivado al {@link Patio}.
 * </p>
 */

public class SalaDeEsperaPrivada {
    public Paciente ocupante;
    //referencia unica?? preguntar
    private final Patio patio = Patio.getInstance();

    /**
     * Intenta ingresar un paciente a la sala.
     *
     * @param nuevo paciente que desea ingresar
     */

    public void ingresar(Paciente nuevo) {
        boolean salaVacia = (ocupante == null);
        Resolucion resultado = null;

        if (!salaVacia) {
            resultado = nuevo.getRango().resolverContra(ocupante.getRango());
        }

        if (salaVacia) {
            ocupante = nuevo;
        } else {
            switch (resultado) {
                case GANA_ESTE:
                    patio.moverAlPatio(ocupante);
                    ocupante = nuevo;
                    break;
                case GANA_OTRO:
                case EMPATE:
                default:
                    patio.moverAlPatio(nuevo);
                    break;
            }
        }

    }

    /**
     * Libera la sala solo si el paciente indicado es el ocupante actual.
     *
     * @param paciente paciente que se desea liberar
     */
    public void liberarSiEs(Paciente paciente) {
        boolean mismoPaciente = (paciente != null && paciente.equals(ocupante));
        if (mismoPaciente) {
            ocupante = null;
        }
    }

    /**
     * Obtiene el paciente actual (puede ser null si la sala está vacia).
     *
     * @return paciente actual o null
     */
    public Paciente getOcupante() {
        return ocupante;
    }

    /**
     * Indica si la sala está vacia.
     *
     * @return true si no hay ocupante
     */
    public boolean estaVacia() {
        boolean vacia = false;
        if (ocupante == null) {
            vacia = true;
        }
        return vacia;
    }


}
