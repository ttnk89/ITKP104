package itkp104;

import java.io.*;
import java.net.*;
import java.util.*;

//ITKP104 ohjelmointityö harjoitus 3.2 udp palvelin
//kaiuttaa asiakkaan lähettämän tekstin kaikille asiakkaille
//jotka ovat palvelimelle paketin lähettäneet ennen ko. asiakasta.
//Viestin muoto on Lähettäjän nimi;teksti

public class UDPpalvelin {
	public static void main(String[] args) throws IOException {
		int portNumber = 9999;
		List<Integer> asiakkaat = new ArrayList<>();
		boolean running = true;
		
		String viestivirhe = "Anna teksti muodossa: nimi;teksti";
		
		DatagramSocket socket = null;
		
		socket = new DatagramSocket(portNumber);
        System.out.println("Server started: " + socket);

	    //try {
	    while (running) {
	    	byte[] buf = new byte[256];
	    	DatagramPacket packet = new DatagramPacket(buf, buf.length);
	        //System.out.println("Waiting for a packet ...");
	        socket.receive(packet);
	        
	        //get packet address & port
	        InetAddress address = packet.getAddress();
	        int port = packet.getPort();
	        if (!asiakkaat.contains(port)) {
	        	asiakkaat.add(port);
	        	System.out.println("added: " + port);
	        }
	        
	        String received = new String(packet.getData(), 0, packet.getLength());
	        System.out.println(port + " : " + received);
            if (received.equals("end")) {
                running = false;
                continue;
            }
            
	        //jos viesti ei muotoa "teksti;teksti" lähetä virhe takaisin vain lähettäjälle
	        if (!received.contains(";")) {
	        	buf = viestivirhe.getBytes();
	        	packet = new DatagramPacket(buf, buf.length, address, port);
		        received = new String(packet.getData(), 0, packet.getLength());
		        //System.out.println("Virhevastaus: " + received);
	        	socket.send(packet);
	        }
	        //loop address/port with clients in list
	        else {
	        	//
	        	buf = received.getBytes();
	        	for (Integer i : asiakkaat) {
	        		packet = new DatagramPacket(buf, buf.length, address, i.intValue());
	        		socket.send(packet);
	        		System.out.println("sent to " + i.intValue() + " : " + new String(packet.getData(), 0, packet.getLength()));
	        	}
	        }
            
        }
        socket.close();
	}
}
