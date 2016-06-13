import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by Mateus Rodrigues on 30/04/2016.
 */
public class Cliente implements Runnable {
    private Socket cliente;

    public Cliente(Socket cliente){
        this.cliente = cliente;
    }

    public static void main(String[] args){
        try {
            Socket socket = new Socket("localhost", 8100);
            Cliente c = new Cliente(socket);
            Thread t = new Thread(c);
            t.start();
        }
        catch (IOException e) {
            System.out.println("Ocorrou algum problema na comunicação com o servidor");
            System.out.println("Causa: " + e.getMessage());
        }
    }

    @Override
    public void run() {
        try{
            PrintStream saida = new PrintStream(this.cliente.getOutputStream());
            Scanner teclado = new Scanner(System.in);
            Scanner entrada = new Scanner(this.cliente.getInputStream());
            while (teclado.hasNextLine()){
                saida.println(teclado.nextLine());
                try{
                    System.out.println(entrada.nextLine());
                }
                catch (Exception ex){
                    System.out.println("Ocorrou algum problema na comunicação com o servidor");
                    System.out.println("Causa: " + ex.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("Ops, ocorreu algum problema !");
            System.out.println("Causa: " + e.getMessage());
        }
    }
}
