// echo server
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class BingoServer{


    private static ExecutorService pool = Executors.newFixedThreadPool(6);
    private static ArrayList<ClientHandler> clients=new ArrayList<>();
    public static void main(String args[])throws IOException{


    Socket s=null;
    ServerSocket ss2=null;
    
    int i=0;
    System.out.println("Server Listening......");
    Map<Integer,String> tmap = new HashMap<Integer,String>();
    
    try{
        ss2 = new ServerSocket(8555); // can also use static final PORT_NUM , when defined
        System.out.println("OK");
    }
    catch(IOException e){
    e.printStackTrace();
    System.out.println("Server error");

    }
    while(true){
        try{
            System.out.println("server waiting for clients to connect");
            s= ss2.accept();
            i++;
            System.out.println("connection Established "+i);
            ClientHandler clientThread=new ClientHandler(s,i,tmap,clients);
            clients.add(clientThread);
            pool.execute(clientThread);

        }

    catch(Exception e){
        e.printStackTrace();
        System.out.println("Connection Error");

    }
    
    }

}

}
