// echo server
import java.util.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.Serializable;


public class BingoServer{
public static void main(String args[])throws IOException{


    Socket s=null;
    ServerSocket ss2=null;
    BingoServer o = new BingoServer();
    int i=0;
    System.out.println("Server Listening......");
    @SuppressWarnings("unchecked") Map<Integer,String> tmap = new HashMap<Integer,String>();
    //tmap.put(1,"ok");
    //tmap.put(2,"ok2");
    //System.out.println("see "+tmap); 
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
            s= ss2.accept();
            i++;
            System.out.println("connection Established "+i);
            ServerThread st=new ServerThread(s,i,tmap);
            st.start();

        }

    catch(Exception e){
        e.printStackTrace();
        System.out.println("Connection Error");

    }
    
    }

}

}

class ServerThread extends Thread{  

    String line=null;
    BufferedReader  is = null;
    PrintWriter os=null;
    Socket s=null;
    int i=0;
    @SuppressWarnings("unchecked") Map<Integer,String> tmap = null;
    
    public ServerThread(Socket s,int i,Map<Integer,String>  tmap){
        this.s=s;
        this.i=i;
        this.tmap=tmap;
    }

    public void run() {
    try{
        is= new BufferedReader(new InputStreamReader(s.getInputStream()));
        os=new PrintWriter(s.getOutputStream());

    }catch(IOException e){
        System.out.println("IO error in server thread");
    }

    try {
        line=is.readLine();
        while(line.compareTo("QUIT")!=0){

            os.println(line);
            tmap.put(i,line);
	    
            os.flush();
            System.out.println("Response to Client"+i+"  :  "+line);
            System.out.println("collection of values :"+tmap.values());
            line=is.readLine();
        }   
    } catch (IOException e) {

        line=this.getName(); //reused String line for getting thread name
        System.out.println("IO Error/ Client "+line+" terminated abruptly");
    }
    catch(NullPointerException e){
        line=this.getName(); //reused String line for getting thread name
        System.out.println("Client "+line+" Closed");
    }

    finally{    
    try{
        System.out.println("Connection Closing..");
        if (is!=null){
            is.close(); 
            System.out.println(" Socket Input Stream Closed");
        }

        if(os!=null){
            os.close();
            System.out.println("Socket Out Closed");
        }
        if (s!=null){
        s.close();
        System.out.println("Socket Closed");
        }

        }
    catch(IOException ie){
        System.out.println("Socket Close Error");
    }
    }//end finally
    }
}
