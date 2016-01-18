package jeuSocket;

import java.util.ArrayList;
import IA.IA2;

/*
 * Copyright (c) 1995, 2013, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */ 

import java.io.*;
import java.net.*;

public class WumpusClientAI2 {
    public static void main(String[] args) throws IOException {
        
        if (args.length != 2) {
            System.err.println(
                "Usage: java KnockNnockClient <host name> <port number>");
            System.exit(1);
        }

        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);
        
        IA2 artificialIntelligence = new IA2(5, 5, 1);

        try (
            Socket kkSocket = new Socket(hostName, portNumber);
            PrintWriter out = new PrintWriter(kkSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                new InputStreamReader(kkSocket.getInputStream()));
        ) {
            BufferedReader stdIn =
                new BufferedReader(new InputStreamReader(System.in));
            String fromServer;
            String fromAI;

            while ((fromServer = in.readLine()) != null) {
                System.out.println("Server: " + fromServer);
                ArrayList<String> info = new ArrayList<String>();
                
                String[] temp = fromServer.split("\\s+");
                for (int i = 0; i<temp.length; i++) {
                    info.add(temp[i]);
                }
                if (fromServer.equals("Vous avez perdu"))
                    break;
                
                if((info.get(0).equals("0") || info.get(0).equals("1") || info.get(0).equals("2") || info.get(0).equals("3") || info.get(0).equals("4")) &&
                   (info.get(1).equals("0") || info.get(1).equals("1") || info.get(1).equals("2") || info.get(1).equals("3") || info.get(1).equals("4")))
                    fromAI = artificialIntelligence.jouer(Integer.parseInt(info.get(0)), 
                                                          Integer.parseInt(info.get(1)), 
                                                          info);//stdIn.readLine();
                                                      
                else
                    fromAI = "\n";
                    
                if (fromAI != null) {
                    System.out.println("Client: " + fromAI);
                    out.println(fromAI);
                }
            }
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                hostName);
            System.exit(1);
        }
    }
}

