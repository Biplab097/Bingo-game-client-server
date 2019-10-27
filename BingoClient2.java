// A simple Client Server Protocol .. Client for Echo Server

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class BingoClient2 {

public static void main(String args[]) throws IOException{

    InetAddress acceptorHost = InetAddress.getByName(args[0]);
    int acceptorPort = Integer.parseInt(args[1]);
    // instantiates a data socket
    //Socket mySocket = new Socket(acceptorHost, acceptorPort);
    Socket s1=null;
    String line=null;
    
    BufferedReader br=null;
    BufferedReader is=null;
    PrintWriter os=null;

    try {
        s1=new Socket(acceptorHost, acceptorPort); // You can use static final constant PORT_NUM
        br= new BufferedReader(new InputStreamReader(System.in));
        is=new BufferedReader(new InputStreamReader(s1.getInputStream()));
        os= new PrintWriter(s1.getOutputStream());
    }
    catch (IOException e){
        e.printStackTrace();
        System.err.print("IO Exception");
    }

    System.out.println("Client Address : "+acceptorHost);
    

    String response=null;
    int val=0;
    try{
        
        System.out.println("Enter value to echo Server ");
        int i=Integer.parseInt(br.readLine());
        System.out.println("Enter Data to echo Server ( Enter QUIT to end):");
        line=br.readLine(); 
        while(line.compareTo("QUIT")!=0){
                os.println(i);
                os.println(line);
                
                os.flush();
                response=is.readLine();
                val=Integer.parseInt(is.readLine());
                System.out.println("Server Response data: "+response+" and value "+val);
                System.out.println(Thread.currentThread().getName());
                System.out.println("Enter QUIT to close the connection anything else to keep the connection");
                line=br.readLine();
                if(line.compareTo("QUIT")==0)break;
                
                i=Integer.parseInt(is.readLine());
                line=is.readLine();
                
                continue;
            }



    }
    catch(IOException e){
        e.printStackTrace();
    System.out.println("Socket read Error");
    }
    finally{

        is.close();os.close();br.close();s1.close();
                System.out.println("Connection Closed");

    }

}
}
