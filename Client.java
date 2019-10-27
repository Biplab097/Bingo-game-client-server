import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
    public static BufferedReader br=null;
    public static PrintWriter out=null;
    public static void main(String[] args) throws IOException {
        InetAddress acceptorHost = InetAddress.getByName(args[0]);
        int acceptorPort = Integer.parseInt(args[1]);
        Socket socket = new Socket(acceptorHost, acceptorPort);
        try {
            ServerConnection serverconn = new ServerConnection(socket);
            br = new BufferedReader(new InputStreamReader(System.in));
            out= new PrintWriter(socket.getOutputStream());
            new Thread(serverconn).start();
            while(true){
                System.out.println("> ");
                String command = br.readLine();
                if(command.equals("quit"))break;
                out.println(command);

            }
            
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        finally{
            socket.close();
            System.exit(0);
 
        }

        
    }
}    

   
