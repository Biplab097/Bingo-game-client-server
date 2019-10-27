import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Map;

class ClientHandler extends Thread {

    String line=null;
    BufferedReader  is = null;
    PrintWriter os=null;
    Socket s=null;
    int i=0;
    Map<Integer,String> tmap = null;
    ArrayList<ClientHandler> Clients;
    
    public ClientHandler(Socket s,int i,Map<Integer,String>  tmap,ArrayList<ClientHandler> Clients){
        this.s=s;
        this.i=i;
        this.tmap=tmap;
        this.Clients=Clients;
    }

    public void run() {
    try{
        is= new BufferedReader(new InputStreamReader(s.getInputStream()));
        os=new PrintWriter(s.getOutputStream());

    }catch(IOException e){
        System.out.println("IO error in server thread");
    }

    try {
        while(true){
            line=is.readLine();
            if(line.contains("name")){
                os.println("BingoServer.getRandomName()");
            }
            else if(line.startsWith("say")){
                int firstspace=line.indexOf(" ");
                if(firstspace==-1){
                    outToAll(line.substring(firstspace+1));
                }
            }
            //System.out.println(line);
            

            
            tmap.put(i,line);
	            
            System.out.println("Response to Client"+i+"  :  "+line);
            System.out.println("collection of values :"+tmap);
            line=is.readLine();
            os.println(line);
            os.flush();
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

    private void outToAll(String msg) {
    
        for(ClientHandler aClient: Clients) {
            aClient.os.println(msg);
        }
    
    
    }
    
}
