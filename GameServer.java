/** The FryingPan class is the main class creates the server for the clients to connect to.
    This is a template for a Java file.
    @author Mikaela C. Paderna (234696)
    @version May 14, 2024
**/
/*
    I have not discussed the Java language code in my program 
    with anyone other than my instructor or the teaching assistants 
    assigned to this course.
    I have not used Java language code obtained from another student, 
    or any other unauthorized source, either modified or unmodified.
    If any Java language code or documentation used in my program 
    was obtained from another source, such as a textbook or website, 
    that has been clearly noted with a proper citation in the comments 
    of my program.
*/
import java.io.*;
import java.net.*;

public class GameServer {
    private ServerSocket ss;
    private int numPlayers;
    private int maxPlayers;

    private Socket p1Socket;
    private Socket p2Socket;
    private ReadFromClient p1ReadRunnable;
    private ReadFromClient p2ReadRunnable;
    private WriteToClient p1WriteRunnable;
    private WriteToClient p2WriteRunnable;

    private int[] orderTypes;
    private int p1gs, p2gs, servergs;
    private double p1x, p1y, p2x, p2y;
    private String p1d, p2d;
    private int p1tp, p2tp;
    private int p1interact, p2interact;

    public GameServer() {
        System.out.println("==== GAME SERVER ====");
        numPlayers = 0;
        maxPlayers = 2;

        p1gs = 0;
        p2gs = 0;
        servergs = 0;
        p1x = 350;
        p1y = 250;
        p1d = "down";
        p2x = 750;
        p2y = 250;
        p2d = "down";
        p1tp = -1;
        p2tp = -1;
        p1interact = 0;
        p2interact = 0;

        orderTypes = new int[6];

        try {
            ss = new ServerSocket(45371);
        } catch (IOException ex) {
            System.out.println("IOException from GameServer constructor");
        }
    }

    public void acceptConnections() {
        try {
            System.out.println("Waiting for connections...");

            while (numPlayers < maxPlayers) {
                Socket s = ss.accept();
                DataInputStream in = new DataInputStream(s.getInputStream());
                DataOutputStream out = new DataOutputStream(s.getOutputStream());

                numPlayers++;
                out.writeInt(numPlayers);
                System.out.println("Player #" + numPlayers + " has connected.");

                ReadFromClient rfc = new ReadFromClient(numPlayers, in);
                WriteToClient wtc = new WriteToClient(numPlayers, out);

                if (numPlayers == 1) {
                    p1Socket = s;
                    p1ReadRunnable = rfc;
                    p1WriteRunnable = wtc;
                } else {
                    p2Socket = s;
                    p2ReadRunnable = rfc;
                    p2WriteRunnable = wtc;
                    p1WriteRunnable.sendStartMsg();
                    p2WriteRunnable.sendStartMsg();
                    
                    Thread readThread1 = new Thread(p1ReadRunnable);
                    Thread readThread2 = new Thread(p2ReadRunnable);
                    readThread1.start();
                    readThread2.start();
                    Thread writeThread1 = new Thread(p1WriteRunnable);
                    Thread writeThread2 = new Thread(p2WriteRunnable);
                    writeThread1.start();
                    writeThread2.start();
                }
            }
            System.out.println("No longer accepting connections");


        } catch (IOException ex) {
            System.out.println("IOException from acceptConnections()");
        }
    }

    private class ReadFromClient implements Runnable {
        private int playerID;
        private DataInputStream dataIn;

        public ReadFromClient(int pID,DataInputStream in) {
            playerID = pID;
            dataIn = in;
            System.out.println("RFC" + playerID + " Runnable created");
        }

        public void run() {
            try {
                while (true) {
                    
                    if (playerID == 1) {
                        p1gs = dataIn.readInt();
                        p1x = dataIn.readDouble();
                        p1y = dataIn.readDouble();
                        p1d = dataIn.readUTF();
                        p1interact = dataIn.readInt();
                        p1tp = dataIn.readInt();
                    } else {
                        p2gs = dataIn.readInt();
                        p2x = dataIn.readDouble();
                        p2y = dataIn.readDouble();
                        p2d = dataIn.readUTF();
                        p2interact = dataIn.readInt();
                        p2tp = dataIn.readInt();
                    }
                    if (servergs == 0 && p1gs == 1 && p2gs == 1){
                        servergs = 2;
                    }
                
                }
            } catch (IOException ex) {
                System.out.println("IOException from RFC run()");
            }
        }
    }

    private class WriteToClient implements Runnable {
        private int playerID;
        private DataOutputStream dataOut;

        public WriteToClient(int pID, DataOutputStream out) {
            playerID = pID;
            dataOut = out;
            System.out.println("WTC" + playerID + " Runnable created");
        }

        public void run() {
            try {
                while (true) {
                    dataOut.writeInt(servergs);
                    if (playerID == 1) {
                        dataOut.writeDouble(p2x);
                        dataOut.writeDouble(p2y);
                        dataOut.writeUTF(p2d);
                        dataOut.writeInt(p2interact);
                        dataOut.writeInt(p2tp);
                        dataOut.flush();
                    } else {
                        dataOut.writeDouble(p1x);
                        dataOut.writeDouble(p1y);
                        dataOut.writeUTF(p1d);
                        dataOut.writeInt(p1interact);
                        dataOut.writeInt(p1tp);
                        dataOut.flush();
                    }

                    try {
                        Thread.sleep(25);
                    } catch (InterruptedException ex) {
                        System.out.println("InterruptedException from WTC run()");
                    }
                }
            } catch (IOException ex) {
                System.out.println("IOException from WTC run()");
            }
        }

        public void sendStartMsg() {
            try {
                dataOut.writeUTF("We now have 2 players. Go!");
                /* randomizeOrders();
                for (int i = 0; i < orderTypes.length; i++)
                dataOut.writeInt(orderTypes[i]); */
                dataOut.flush();
            } catch (IOException ex) {
                System.out.println("IOException from sendStartMsg()");
            }
        }

    }

    public void randomizeOrders() {
        for (int i = 0; i < 6; i++) {
            int newOrder = (int) Math.ceil(Math.random()*3);
            orderTypes[i] = newOrder;
            System.out.print("hi");
        }
    }


    public static void main(String[] args) {
        GameServer gs = new GameServer();
        gs.acceptConnections();
    }
}
