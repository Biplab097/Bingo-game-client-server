import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.Executors;
public class BingoServerFinal{

    private static ArrayList<Integer> sharemem = new ArrayList<Integer>();
    public static void main(String[] args) throws Exception {
        
        int n = 200, i=0;
        

        try (var listener = new ServerSocket(58901)) {
            System.out.println("Bingo Server is Running...");
            var pool = Executors.newFixedThreadPool(n);
            while (true) {
                Game game = new Game();
                sharemem.add(i);
                sharemem.add(i+1);
                try{
                pool.execute(game.new Player(listener.accept(), 'f',sharemem, i));
                pool.execute(game.new Player(listener.accept(), 's',sharemem, i));
                }catch (Exception e){System.out.println("pool closed....");}
                i = (i+2)%n;
            }
        }
    }
}
class Game {
    Player currentPlayer;
    class Player implements Runnable {
        char mark;
        Player opponent;
        Socket socket;
        Scanner input;
        PrintWriter output;
        ArrayList<Integer> sharemem;
        int i;

        public Player(Socket socket, char mark, ArrayList<Integer> sharemem, int i) {
            this.socket = socket;
            this.mark = mark;
            this.sharemem = sharemem;
            this.i  = i;
        }

        @Override
        public void run() {
            try {
                setup();
                processCommands();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (opponent != null && opponent.output != null) {
                    opponent.output.println("-1");
                }
                try {socket.close();} catch (IOException e) {}
            }
        }

        private void setup() throws IOException {
            input = new Scanner(socket.getInputStream());
            output = new PrintWriter(socket.getOutputStream(), true);
            output.println("WELCOME " + mark);
            if (mark == 'f') {
                currentPlayer = this;
                output.println("MESSAGE: Waiting for opponent to connect");
                sharemem.add(i, -1);
            } else {
                opponent = currentPlayer;
                opponent.opponent = this;
                sharemem.add(i+1, -1);
                output.println("You are second player fill the table");
                output.println("waiting for the move");
                opponent.output.println("Fill the table for first player and type in your move");
            }
        }
        private void processCommands(){
            try {
                System.out.println("here"+mark);
                while (true) {
                    try{
                        var command = input.nextLine();
                        if (command.startsWith("-")) {
                            return;
                        } //else if (command.startsWith("MOVE")) {
                        else{
                            prmoco(Integer.parseInt(command));
                        }
                    }catch (Exception e){
                        System.out.println("game closed...");
                        break;
                    }
                    
                }
            } catch (Exception e) {
                //TODO: handle exception
                e.printStackTrace();
                System.out.println("e1");
            }
        }
        private void prmoco(int i)  {
            try {
                opponent.output.println(i);
            } catch (Exception e) {
                System.out.println("e2");
            }
        }
    }
}
