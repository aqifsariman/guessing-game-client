package ibf.sdf;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Console;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public final class App {
    private App() {
    }

    public static void main(String[] args) throws NumberFormatException, UnknownHostException, IOException {
        Socket socket = new Socket("localhost", Integer.parseInt(args[0]));

        try (OutputStream os = socket.getOutputStream()) {

            BufferedOutputStream bos = new BufferedOutputStream(os);
            DataOutputStream dos = new DataOutputStream(bos);

            InputStream is = socket.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            DataInputStream dis = new DataInputStream(bis);

            try (Scanner scan = new Scanner(System.in)) {
                String inputString = "";
                String response = "";
                while (!inputString.equalsIgnoreCase("quit")) {
                    System.out.print("Guess the number: ");
                    inputString = scan.nextLine();
                    dos.writeUTF(inputString);
                    dos.flush();
                    response = dis.readUTF();
                    System.out.println(response);
                    if (response.equalsIgnoreCase("Wow das right.")) {
                        System.out.println("Thanks for playing the guessing game. Goodbye!");
                        System.exit(0);
                    }
                }
            }
            dis.close();
            bis.close();
            is.close();
            dos.close();
            bos.close();
            os.close();
        }
    }

}
