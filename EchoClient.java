package itkp104;

import java.net.*;
import java.io.*;

// ITKP104 ohjelmointityö harjoitus 1.3 kaikuasiakas
//lähettää teksti stringin palvelimelle, vastaanottaa palvelimen kaiuttaman viestin,
//erottaa palvelimen nimen ja tekstin toisistaan ja tulostaa ne erillisinä näyttöön

public class EchoClient {
	public static void main(String[] args) throws IOException {
		String hostName = "localhost";
		int portNumber = 80;

		try {
		    Socket cSocket = new Socket(hostName, portNumber);
		    PrintWriter out = new PrintWriter(cSocket.getOutputStream(), true);
		    BufferedReader in = new BufferedReader(new InputStreamReader(cSocket.getInputStream()));
		    
		    out.println("moi");
		    out.flush();
		    
		    String message = in.readLine();
		    String[] tulostus = message.split(";");
		    System.out.println("Palvelin: " + tulostus[0]);
		    System.out.println("Teksti: " + tulostus[1]);
		    
            out.close();
            in.close();
            cSocket.close();
            System.out.println("end");
		}catch (IOException e) {
		    System.out.println("Exception caught when connecting "
				    + portNumber );
		}
	}
		
}
