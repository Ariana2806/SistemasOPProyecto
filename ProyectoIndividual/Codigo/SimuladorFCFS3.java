import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.Queue;

class Proceso {
    private String nombre;
    private int tiempoEjecucionInicial;
    private int tiempoEjecucionRestante;

    public Proceso(String nombre, int tiempoEjecucion) {
        this.nombre = nombre;
        this.tiempoEjecucionInicial = tiempoEjecucion;
        this.tiempoEjecucionRestante = tiempoEjecucion;
    }

    public String getNombre() {
        return nombre;
    }

    public int getTiempoEjecucionInicial() {
        return tiempoEjecucionInicial;
    }

    public int getTiempoEjecucionRestante() {
        return tiempoEjecucionRestante;
    }

    public void reducirTiempoEjecucion() {
        tiempoEjecucionRestante -= 1000;
    }
}

class SimuladorFCFS3 extends JFrame {
    private static final int NUM_PROCESOS = 5;
    private Queue<Proceso> colaProcesos;
    private JLabel[] labels;
    private Timer timer;
    private int procesoActual = 0;

    public SimuladorFCFS3() {
        setTitle("Simulador FCFS");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(NUM_PROCESOS, 1));

        colaProcesos = new LinkedList<>();
        labels = new JLabel[NUM_PROCESOS];

        // Crear procesos y agregar a la cola en orden del 1 al 5
        for (int i = 1; i <= NUM_PROCESOS; i++) {
            int tiempo = (int) (Math.random() * 5000) + 1000; // Tiempo aleatorio entre 1000 y 6000 milisegundos
            Proceso proceso = new Proceso("Proceso" + i, tiempo);
            colaProcesos.add(proceso);

            labels[i - 1] = new JLabel(proceso.getNombre() + " - Tiempo restante: " + proceso.getTiempoEjecucionRestante());
            labels[i - 1].setOpaque(true);
            labels[i - 1].setBackground(getRandomColor());
            add(labels[i - 1]);
        }

        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ejecutarFCFS();
            }
        });

        setSize(400, 300);
        setLocationRelativeTo(null);
        setVisible(true);

        // Iniciar el temporizador después de configurar la interfaz
        timer.start();
    }

    private void ejecutarFCFS() {
        if (procesoActual < NUM_PROCESOS) {
            Proceso procesoActual = colaProcesos.peek();
            ejecutarProceso(procesoActual);
        } else {
            // Detener el temporizador si no hay más procesos
            timer.stop();
        }
    }

    private void ejecutarProceso(Proceso proceso) {
        // Reducir el tiempo de ejecución y actualizar la interfaz
        proceso.reducirTiempoEjecucion();
        labels[procesoActual].setText(proceso.getNombre() + " - Tiempo restante: " + proceso.getTiempoEjecucionRestante());

        if (proceso.getTiempoEjecucionRestante() <= 0) {
            labels[procesoActual].setText(labels[procesoActual].getText() + " - Completado");
            labels[procesoActual].setBackground(Color.GRAY);
            colaProcesos.poll();  // Quitar el proceso de la cola
            procesoActual++;  // Pasar al siguiente proceso
        }
    }

    private Color getRandomColor() {
        return new Color((int) (Math.random() * 256), (int) (Math.random() * 256), (int) (Math.random() * 256));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SimuladorFCFS3::new);
    }
}
