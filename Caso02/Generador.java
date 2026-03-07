import java.util.List;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.io.IOException;

public class Generador {

    public static void main(String[] args) {

        if (args.length != 6) {
            System.out.println("Uso: java Generador NF1 NC1 NF2 NC2 TP archivoSalida");
            return;
        }

        int nf1 = Integer.parseInt(args[0]);
        int nc1 = Integer.parseInt(args[1]);
        int nf2 = Integer.parseInt(args[2]);
        int nc2 = Integer.parseInt(args[3]);
        int tp = Integer.parseInt(args[4]);
        String archivoSalida = args[5];

        if (nc1 != nf2) {
            System.out.println("Error: NC1 debe ser igual a NF2 para poder multiplicar las matrices.");
            return;
        }
        // cantidad de bytes necesarios para almacenar las matrices y el resultado
        int tamanioMatriz1 = nf1 * nc1 * 4;
        int tamanioMatriz2 = nf2 * nc2 * 4;
        int tamanioResultado = nf1 * nc2 * 4;
        
        // donde empieza en memoria virtual
        int direccionMatriz1 = 0;
        int direccionMatriz2 = direccionMatriz1 + tamanioMatriz1;
        int direccionResultado = direccionMatriz2 + tamanioMatriz2;

        List<String> referencias = new ArrayList<>();
        
        for (int i = 0; i < nf1; i++) {
            for (int j = 0; j < nc2; j++) {
                for (int k = 0; k < nc1; k++) {
                    int indiceM1 = i * nc1 + k;
                    int direccionVirtual = direccionMatriz1 + indiceM1 * 4;
                    int pagina = direccionVirtual / tp;
                    int offset = direccionVirtual % tp;

                    String ref = "[M1-" + i + "-" + k + "]," + pagina + "," + offset;

                    referencias.add(ref);

                    int indiceM2 = k * nc2 + j;
                    int direccionVirtualm2 = direccionMatriz2 + indiceM2 * 4;
                    int paginam2 = direccionVirtualm2 / tp;
                    int offsetm2 = direccionVirtualm2 % tp;

                    String refm2 = "[M2-" + k + "-" + j + "]," + paginam2 + "," + offsetm2;

                    referencias.add(refm2);
                    
                }
                int indicem3 = i * nc2 + j;
                int direccionVirtualm3 = direccionResultado + indicem3 * 4;
                int paginam3 = direccionVirtualm3 / tp;
                int offsetm3 = direccionVirtualm3 % tp;

                String refm3 = "[M3-" + i + "-" + j + "]," + paginam3 + "," + offsetm3;

                referencias.add(refm3);
            }
        }
        int nr = referencias.size();
        int tamanioTotal = tamanioMatriz1 + tamanioMatriz2 + tamanioResultado;
        int np = (tamanioTotal + tp - 1) / tp;

        try {
            PrintWriter writer = new PrintWriter(new FileWriter(archivoSalida));

            writer.println("TP=" + tp);
            writer.println("NF1=" + nf1);
            writer.println("NC1=" + nc1);
            writer.println("NF2=" + nf2);
            writer.println("NC2=" + nc2);
            writer.println("NR=" + nr);
            writer.println("NP=" + np);

            for (String r : referencias) {
                writer.println(r);
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error al escribir el archivo");
        }


    }
}