import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.Executors;
public class BingoSrver {

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
                pool.execute(game.new Player(listener.accept(), 'f',sharemem, i));
                pool.execute(game.new Player(listener.accept(), 's',sharemem, i));
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
                System.out.println(sharemem.get(i));
                processCommands();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (opponent != null && opponent.output != null) {
                    opponent.output.println("OTHER_PLAYER_LEFT");
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
                output.println("MESSAGE Waiting for opponent to connect");
                output.println("Fill the table for first player ");
                sharemem.add(i, -1);
                System.out.println(sharemem.get(i+1));
                try{
                    wait();
                }catch(Exception e){
                    System.out.println(e);
                }
                System.out.println("Player f : " + sharemem.get(i));
                
                
                
            } else {
                opponent = currentPlayer;
                opponent.opponent = this;
                sharemem.add(i+1, -1);
                System.out.println("player s : "+ sharemem.get(i+1));
                output.println("Fill the table for sencond player ");
                notify();
            }
        }
        private void processCommands()throws IOException{
            System.out.println("here-2");
            input = new Scanner(socket.getInputStream());
            String line = null;
            int val = 0;
            System.out.println(mark);
            if(mark == 'f' ){
                System.out.println("here-3");
                output.println("Waiting for your 1st input");
                line = input.nextLine();
                System.out.println("here-4");
                val = Integer.parseInt(line);
                sharemem.add(i+1,val);
                System.out.println("player f : "+ sharemem.get(i+1));

            }
            while(true){
                
                if(val == -32){
                    checkVal(val, mark);

                }
                else if(val == -42){
                    checkVal(val, mark);
                }
                else if(mark == 'f' && sharemem.get(i)!= -1){
                    System.out.println("coming player s : "+ sharemem.get(i));
                    //send
                    checkVal(sharemem.get(i), mark);
                    output.println(sharemem.get(i));
                    //read 
                    line = input.nextLine();
                    System.out.println("here-6");
                    val = Integer.parseInt(line);
                    System.out.println(val);
                    //checkVal(val,mark);
                    //put data in f
                    sharemem.add(i,-1);
                    sharemem.add(i+1,val);


                }
                else if(mark == 's' && sharemem.get(i+1)!= -1){
                    System.out.println("coming player f : "+ sharemem.get(i));
                    //send

                    output.println(sharemem.get(i+1));
                    //read 
                    line = input.nextLine();
                    System.out.println("here-6");
                    val = Integer.parseInt(line);
                    //sharemem.add(i+1,val);
                    //put data in f
                    sharemem.add(i+1,-1);
                    sharemem.add(i,val);
                    System.out.println(sharemem.get(i));
                    

                }
                else {
                    if(mark == 'f'){
                        output.print('.');output.flush();
                        try {
                      // thread to sleep for 1000 milliseconds
                     Thread.sleep(10000);
                     } catch (Exception e) {
                       System.out.println(e);
                      }
                    }
                    else {
                        output.print(".");output.flush();
                    }
                }
            }
        }
        public void checkVal(int val,char mark){

            if(val==-32){
                if(mark=='f'){
                output.println("closing socket...");
                sharemem.add(i+1,val);
                }
                else{
                output.println("closing socket...");
                sharemem.add(i+1,val);
                }
            }
            if(val==-42){
                if(mark=='f'){
                output.println("closing socket...");
                sharemem.add(i+1,val);
                }
                else{
                output.println("closing socket...");
                sharemem.add(i+1,val);
                }
            }

        }
    }
}
