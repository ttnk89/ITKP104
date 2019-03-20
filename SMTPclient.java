package itkp104;

import java.io.*;
import java.net.*;
import java.util.*;

// ITKP104 ohjelmointityö harjoitus 2.1 ja 2.2 SMTP asiakas
//kommunikoi smtp palvelimen kanssa HELO, QUIT, MAIL FROM, RCPT TO sekä DATA komennoilla

public class SMTPclient {
	
	public static void main(String[] args) throws IOException {
		String hostName = "localhost";
		int portNumber = 25000;
		boolean quit = false;
		String komento;
		String message;
		
	    Socket cSocket = new Socket(hostName, portNumber);
	    PrintWriter out = new PrintWriter(cSocket.getOutputStream(), true);
	    BufferedReader in = new BufferedReader(new InputStreamReader(cSocket.getInputStream()));
		
	    Scanner reader = new Scanner(System.in);
	    
		//connect
		try {
		    message = in.readLine();
		    System.out.println("Palvelin: " + message);
		    
		}catch (IOException e) {
		    System.out.println("Exception caught when connecting "
				    + portNumber );
		}
		
		//write msg & get reply loop
		//HELO,QUIT,MAIL FROM,RCPT TO,DATA
		while (!quit) {
			System.out.print("> ");
			komento=reader.nextLine();
		    out.println(komento);
		    out.flush();
		    message = in.readLine();
		    System.out.println("Palvelin: " + message);
		    if (komento.equals("DATA")) {
		    	while (!komento.equals(".")) {
		    		komento=reader.nextLine();
		    		out.println(komento);
		    	}
		    	out.flush();
			    message = in.readLine();
			    System.out.println("Palvelin: " + message);
		    }
		    if (komento.equals("QUIT")) {
	    		quit = true;
		    }
		}
		
        out.close();
        in.close();
        reader.close();
        cSocket.close();
        System.out.println("end");
		
	}
	
}
