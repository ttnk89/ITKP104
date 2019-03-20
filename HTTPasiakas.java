package itkp104;
import java.io.*;
import java.net.*;

//ITKP104 ohjelmointityö harjoitus 1.1 http asiakas
//hakee example.com palvelimelta html sivun ja tulostaa sen näyttöön

public class HTTPasiakas {
	
	
	
	public static void main(String[] args) throws IOException {
		
		String server = "www.example.com";
		String resurssi = "/";
		Socket s = null;
		try {
			s = new Socket(server, 80);
			OutputStream theOutput = s.getOutputStream();
			PrintWriter out = new PrintWriter(theOutput, true);

			out.print("GET " + resurssi + " HTTP/1.1\r\n");
			out.print("Host:www.example.com\r\n");
			out.print("Connection:Close\r\n\r\n");
			
			out.flush();
			
	        InputStream in = s.getInputStream();
	        InputStreamReader isr = new InputStreamReader(in);
	        BufferedReader br = new BufferedReader(isr);
	        int c;
	        while ((c = br.read()) != -1) {
	          System.out.print((char) c);
	        }
	         
			out.close();
		}catch(IOException e) {
			System.out.println("IO: " + e.getMessage());
		}catch(Exception e) {
			System.out.println("Exception: " + e.getMessage());
		}
		s.close();
		System.out.println("end");
	}
}
