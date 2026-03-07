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

    }
}