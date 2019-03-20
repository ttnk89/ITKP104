package itkp104;

import java.io.*;
import java.net.*;
import java.util.*;

//ITKP104 ohjelmointityö harjoitus 3.1 udp asiakas
//lähettää viestin palvelimelle ja sitten vastaanottaa palvelimelta useampia viestejä,
//joista erottaa nimen ja tekstin toisistaan ja tulostaa ne erillisenä näyttöön. Esim: Ari: Moikka

public class UDPasiakas {
	public static void main(String[] args) throws IOException {
		String hostName = "localhost";
		boolean read = true;
		boolean display = false;

		int portNumber = 9999;
		byte[] sbuf = new byte[256];
		byte[] rbuf = new byte[256];
		String message;
		DatagramSocket cSocket = null;
		InetAddress address = null;
		
		//connect
		try {
			cSocket = new DatagramSocket();
			cSocket.setSoTimeout(1000);
			address = InetAddress.getByName(hostName);
		}
		catch(UnknownHostException uhe) {
			System.out.println("Host unknown: " + uhe.getMessage());
		}
	    catch(IOException ioe) {
	    	System.out.println("Unexpected exception: " + ioe.getMessage());
	    }
		
		Scanner reader = new Scanner(System.in);
		BufferedReader userIn = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Press ENTER to type a message.");
		while (read) {
			//read for user input and send
			while (System.in.available()>0) {
				userIn.readLine();
				message="";
				System.out.print("> ");
				message=userIn.readLine();	
				if (!message.equals("")) {
					sbuf = message.getBytes();
					DatagramPacket packet = new DatagramPacket(sbuf, sbuf.length, address, portNumber);
					cSocket.send(packet);
				}	
			}

			//check for messages from server
			DatagramPacket packet = new DatagramPacket(rbuf, rbuf.length);
			packet.setLength(rbuf.length);
			//System.out.println("waiting for packet..");
			display = true;
			try {
				cSocket.receive(packet);
			} catch(SocketTimeoutException e) {
				display = false;
			}
			//display
			if (display) {
				String received = new String(packet.getData(), 0, packet.getLength());
				
				if (received.equals("Anna teksti muodossa: nimi;teksti")) {
					System.out.println(received);
				}
				else {
					String[] tulostus = received.split(";");
				    System.out.println(tulostus[0] + " : " + tulostus[1]);
				}
			    
			}
		}
		cSocket.close();
		reader.close();
		userIn.close();
		System.out.println("end");
	}
}
