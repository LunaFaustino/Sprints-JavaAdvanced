package br.com.fiap.sprint4.actuator;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

@Component
public class OdontoprevMetrics {

    private final Counter clinicasCounter;
    private final Counter dentistasCounter;
    private final Counter pacientesCounter;

    public OdontoprevMetrics(MeterRegistry registry) {
        // Contadores para operações de cadastro
        this.clinicasCounter = Counter.builder("odontoprev.clinicas.cadastros")
                .description("Número de clínicas cadastradas")
                .register(registry);

        this.dentistasCounter = Counter.builder("odontoprev.dentistas.cadastros")
                .description("Número de dentistas cadastrados")
                .register(registry);

        this.pacientesCounter = Counter.builder("odontoprev.pacientes.cadastros")
                .description("Número de pacientes cadastrados")
                .register(registry);
    }

    public void incrementClinicaCadastro() {
        clinicasCounter.increment();
    }

    public void incrementDentistaCadastro() {
        dentistasCounter.increment();
    }

    public void incrementPacienteCadastro() {
        pacientesCounter.increment();
    }
}
