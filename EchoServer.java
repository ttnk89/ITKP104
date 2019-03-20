package itkp104;
import java.net.*;
import java.io.*;

//ITKP104 ohjelmointityö harjoitus 1.2 kaikupalvelin
//ottaa asiakkaalta vastaan viestin, lisää siihen oman nimensä ; merkillä erotettuna ja lähettää tämän takaisin asiakkaalle

public class EchoServer {
	public static void main(String[] args) throws IOException {
		
		int port = 80;
		ServerSocket serverSocket = new ServerSocket(port);
		
		try {
			Socket clientSocket = serverSocket.accept();

			PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			System.out.println("Client connected on port " + port +". Servicing requests.");
            String s;
            while ((s = in.readLine()) != null) {
            	System.out.println("Received message: " + s + " from " + clientSocket.toString());
                out.println("testipalvelin;" + s);
                System.out.println("sent message");
                out.flush();
            }
            
            out.close();
            in.close();
            clientSocket.close();
            serverSocket.close();
		} catch (IOException e) {
		    System.out.println("Exception caught when trying to listen on port "
				    + port + " or listening for a connection");
		}

	}
}
