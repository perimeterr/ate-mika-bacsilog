/** The GameFrame class is where the window for the game is created. Necessary animations and keylisteners are also created here. This is also the client that allows for the connection to the server.
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

import java.awt.Font;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.Scanner;

import javax.swing.*;

public class GameFrame {
    private JFrame frame;
    private GameCanvas gc;
    
    private Player player1, player2;
    private Timer animationTimer;
    private boolean up, down, left, right;
    private Socket socket;
    private int playerID;
    private int tilePos;
    private int interactionType;
    private ReadFromServer rfsRunnable;
    private WriteToServer wtsRunnable;
    private CounterTile nearestTile;


    // Constructor creates a JFrame
    public GameFrame() {
        frame = new JFrame();
    }

    // Sets up the required operations to make the program visibile. Also creates the animation timer and keylisteners
    public void setUpGUI() {
        frame.setTitle("Ate Mika's Bacsilog - Player #" + playerID);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initializePlayers();
        gc = new GameCanvas(player1, player2);
        frame.add(gc);
        gc.setFocusable(true);
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);

        tilePos = -1;
        interactionType = 0;


        setUpAnimationTimer();
        setUpKeyListener();
    
    }
    
    // Method for instantiating the Player objects, according to the player id of the client
    private void initializePlayers() {
        if (playerID == 1) {
            player1 = new Player(350, 250,1);
            player2 = new Player(750, 250, 2);
        } else {
            player1 = new Player(750, 250, 2);
            player2 = new Player(350, 250, 1);
        }
    }

    // Creates the animation timer for contionuous animation
    private void setUpAnimationTimer() {
        int interval = 10;
        ActionListener al = new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                double speed = 7;
                if (up) {
                    player1.moveUp(speed);
                } else if (down) {
                    player1.moveDown(speed);
                } else if (left) {
                    player1.moveLeft(speed);
                } else if (right) {
                    player1.moveRight(speed);
                }
                for (int i = 0; i < 23; i++) {
                    gc.getCounterTile(i).checkInPlayerRange(player1);
                }
                if (playerID == 1) {
                    gc.getCounterTile(23).checkInPlayerRange(player2);
                    gc.getCounterTile(24).checkInPlayerRange(player2);
                } else {
                    gc.getCounterTile(23).checkInPlayerRange(player1);
                    gc.getCounterTile(24).checkInPlayerRange(player1);
                }
                if (gc.getCountdownTimer().hasTimerEnded())
                gc.setGameState(3);
                gc.updateOrderMenu(((ServingTile)gc.getCounterTile(22)).getOrderMenu());
                gc.repaint();
            }
        };
        animationTimer = new Timer(interval,al);
        animationTimer.start();
    }

    // Sets up the keylisteners for the player to interact with the game
    private void setUpKeyListener() {
        KeyListener kl = new KeyListener() {
            public void keyTyped(KeyEvent ke) {

            }

            public void keyPressed(KeyEvent ke) {
                int keyCode = ke.getKeyCode();
                switch (keyCode) {
                    case KeyEvent.VK_W:
                        up = true;
                        break;
                    case KeyEvent.VK_S:
                        down = true;
                        break;
                    case KeyEvent.VK_D:
                        right = true;
                        break;
                    case KeyEvent.VK_A:
                        left = true;
                        break;
                    case KeyEvent.VK_SPACE:
                        if (gc.getGameState() == 2) {
                            if (playerID == 1) {
                                for (int i = 0; i < 23; i++ ) {
                                    if (gc.getCounterTile(i).inPlayerRange)
                                    nearestTile = gc.getCounterTile(i);
                                }
                            } else {
                                for (CounterTile c : gc.getCounterTiles())
                                if (c.inPlayerRange) nearestTile = c;
                            }
                            if (nearestTile != null) {
                                sendInteraction();
                                player1.doAction(nearestTile);
                            }
                            break;
                        } else if (gc.getGameState() == 0) {
                            gc.setGameState(1);
                            System.out.println(gc.getGameState());
                        }

                }
            }

            public void keyReleased(KeyEvent ke) {
                int keyCode = ke.getKeyCode();
                switch (keyCode) {
                    case KeyEvent.VK_W:
                        up = false;
                        break;
                    case KeyEvent.VK_S:
                        down = false;
                        break;
                    case KeyEvent.VK_D:
                        right = false;
                        break;
                    case KeyEvent.VK_A:
                        left = false;
                        break;
                }
                nearestTile = null;
                tilePos = -1;
                interactionType = 0;
            }
        };
        frame.addKeyListener(kl);
        frame.setFocusable(true);
    };

    // Gets the player's action and gets the data to be sent to the server
    public void sendInteraction() {
        CounterTile[] tiles = gc.getCounterTiles();
        for (int i = 0; i < 24; i++) {
            if (tiles[i].equals(nearestTile))
            tilePos = i;
        }
        if (!player1.isHoldingItem())
        interactionType = 0;
        else if (player1.isHoldingItem())
        interactionType = 1;
    }

    // Connects to the server
    public void connectToServer() {
        try {
            Scanner input = new Scanner(System.in);
            System.out.print("IP Address: ");
            String ip = input.nextLine();
            System.out.print("Port: ");
            int port = Integer.parseInt(input.nextLine());
            socket = new Socket(ip, port);
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            playerID = in.readInt();
            System.out.println("You are Player #" + playerID);
            if (playerID == 1) {
                System.out.println("Waiting for Player #2 to connect...");
            }

            rfsRunnable = new ReadFromServer(in);
            wtsRunnable = new WriteToServer(out);
            rfsRunnable.waitForStartMsg();
            //addOrders();

        } catch (IOException ex) {
            System.out.println("IOException from connectToServer()");
        }
    }

    // Runnable to continuously read data from the server
    private class ReadFromServer implements Runnable {
        private DataInputStream dataIn;

        public ReadFromServer(DataInputStream in) {
            dataIn = in;
            System.out.println("RFS Runnable created");

        }

        public void run() {
            try {
                while (true) {
                    int sgs = dataIn.readInt();
                    double x = dataIn.readDouble();
                    double y = dataIn.readDouble();
                    String dir = dataIn.readUTF();
                    int iType = dataIn.readInt();
                    int tPos = dataIn.readInt();
                    if (gc != null) {
                        if (sgs == 2 && gc.getGameState() == 1)
                        gc.setGameState(2);
                        if (gc.getGameState() == 2) {
                            if (player2 != null) {
                                if (dir.equals("down")) {
                                    player2.faceDown();
                                } else if (dir.equals("up")) {
                                    player2.faceUp();
                                } else if (dir.equals("left")) {
                                    player2.faceLeft();
                                } else if (dir.equals("right")) {
                                    player2.faceRight();
                                }
                                player2.setX(x);
                                player2.setY(y);
                                if (tPos != -1) {
                                    if (iType == 0 && !player2.isHoldingItem()) {
                                        player2.doAction(gc.getCounterTile(tPos));
                                    } else if (iType == 1 && player2.isHoldingItem()) {
                                        player2.doAction(gc.getCounterTile(tPos));
                                    }
                                }
                            }
                        }
                    }
                    
                }
            } catch (IOException ex) {
                System.out.println("IOException from RFS run()");
            } 
        }

        public void waitForStartMsg() {
            try {
                String startMsg = dataIn.readUTF();
                /* for (int i = 0; i < 6; i++) {
                    if (orderTypes[i] != 0) {
                        orderTypes[i] = dataIn.readInt();
                    }
                } */
                System.out.println("Message from server: " + startMsg);
                Thread readThread = new Thread(rfsRunnable);
                Thread writeThread = new Thread(wtsRunnable);
                readThread.start();
                writeThread.start();
            } catch (IOException ex) {
                System.out.println("IOException from waitForStartMsg()");
            }
        }

    }

    // Runnable to continuously write player information to the server
    private class WriteToServer implements Runnable {
        private DataOutputStream dataOut;

        public WriteToServer(DataOutputStream out) {
            dataOut = out;
            System.out.println("WTS Runnable created");

        }

        public void run() {
            try {
                while (true) {
                    int gs = 0;
                    double x = 0;
                    double y = 0;
                    String dir = "";
                    int iType = 0;
                    int tPos = 0;
                    if (player1 != null && gc != null) {
                        gs = gc.getGameState();
                        x = player1.getX();
                        y = player1.getY();
                        dir = player1.getDirection();
                        iType = interactionType;
                        tPos = tilePos;
                    }
                    dataOut.writeInt(gs);
                    dataOut.writeDouble(x);
                    dataOut.writeDouble(y);
                    dataOut.writeUTF(dir);
                    dataOut.writeInt(iType);
                    dataOut.writeInt(tPos);
                    dataOut.flush();
                    try {
                        Thread.sleep(25);
                    } catch (InterruptedException ex) {
                        System.out.println("InterruptedException from WTS run()");
                    }
                }
            } catch (IOException ex) {
                System.out.println("IOException from WTS run()");
            }
        }

    }

  


}
