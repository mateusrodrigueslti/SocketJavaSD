import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Created by Mateus Rodrigues on 30/04/2016.
 */

public class Servidor implements Runnable {
    public Socket cliente;

    public Servidor (Socket socket){
        this.cliente = socket;
    }
    public static void main(String[] args) {

        try {
            ServerSocket serverSocket = new ServerSocket(8100);

            while (true) {
                Socket cliente = serverSocket.accept();

                Servidor s = new Servidor(cliente);
                Thread t = new Thread(s);
                t.start();
            }
        }
        catch (IOException e) {
            System.out.println("Ocorreu algo erro no servidor !");
            System.out.println("Causa: " + e.getMessage());
        }
    }

    @Override
    public void run() {
        HashMap<String, String> dicionario = new HashMap<>();
        dicionario.put("blue" , "Azul");
        dicionario.put("pink" , "Rosa");
        try{
            Scanner entrada = new Scanner(this.cliente.getInputStream());
            PrintStream saida = new PrintStream(this.cliente.getOutputStream());
            while (entrada.hasNextLine()){
                String texto = entrada.nextLine();
                String resposta = dicionario.get(texto);
                if(resposta == null){
                    saida.println("Significado nao encontrado para a palavra pesquisada");
                }
                else{
                    saida.println(resposta);
                }
            }
        } catch (IOException e) {
            System.out.println("Problema interno no servidor");
            System.out.println("Causa: " + e.getMessage());
        }
    }
}
