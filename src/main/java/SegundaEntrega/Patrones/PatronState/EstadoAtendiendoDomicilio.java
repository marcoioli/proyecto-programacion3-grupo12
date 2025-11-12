package SegundaEntrega.Patrones.PatronState;

import SegundaEntrega.Modelo.Negocio.Ambulancia;
import java.util.Random; //para simular tiempo de uso

public class EstadoAtendiendoDomicilio implements IEstadoAmbulancia {

    private final Ambulancia ambulancia;
    private final Random random = new Random();

    /**
     * Constructor.
     * Al entrar en este estado, se inicia automáticamente la simulación
     * del tiempo que tarda la atención.
     * @param ambulancia El contexto.
     */
    public EstadoAtendiendoDomicilio(Ambulancia ambulancia) {
        this.ambulancia = ambulancia;
        simularTiempoDeAtencion();
    }

    /**
     * Inicia un nuevo hilo (timer) para simular la duración de la
     * atención a domicilio. Cuando termina, llama a retornarAClinica()
     * para transicionar al siguiente estado.
     */
    private void simularTiempoDeAtencion() {
        System.out.println("   ... Ambulancia ocupada en domicilio (simulando tiempo)...");
        new Thread(() -> {
            try {
                Thread.sleep(random.nextInt(3000) + 2000);
                System.out.println("   ... Atención a domicilio finalizada.");
                ambulancia.retornarAClinica();

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("WARN: Hilo de simulación de atención interrumpido.");
            }
        }).start();
    }

    @Override
    public void solicitarAtencionDomicilio() {
        System.out.println("   Ambulancia Atendiendo Domicilio: Ya está en una atención.");
        // permanece en 3)
    }

    @Override
    public void solicitarTraslado() {
        System.out.println("   Ambulancia Atendiendo Domicilio: No puede iniciar un traslado ahora.");
        // informa que no puede
    }

    @Override
    public void solicitarMantenimiento() {
        System.out.println("   Ambulancia Atendiendo Domicilio: No puede ir a taller ahora.");
        // informa que no puede
    }

    @Override
    public void retornarAClinica() {
        // Asumimos que retornarAClinica() se llama cuando termina la atención y no requiere traslado
        System.out.println("   Ambulancia Atendiendo Domicilio -> Regresando Sin Paciente");
        ambulancia.setEstado(new EstadoRegresandoSinPaciente(ambulancia));
    }

    // --- Verificaciones ---
    @Override
    public boolean puedeAtenderDomicilio() { return false; } // Ya está atendiendo
    @Override
    public boolean puedeTrasladar() { return false; }
    @Override
    public boolean puedeIrATaller() { return false; }
}