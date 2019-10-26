import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.LongBuffer;
import java.util.Scanner;

public class Bingoclient1 {

public static void main(String args[]) throws IOException{


    InetAddress address=InetAddress.getLocalHost();
    Socket s1=null;
    String line=null;
    Scanner br = null,sr=null;
    br=null;
    //LongBuffer br = LongBuffer.allocate(64);
    BufferedReader is=null;
    PrintWriter os=null;

    try {
        s1=new Socket(address, 58901); // You can use static final constant PORT_NUM
        br= new Scanner(new InputStreamReader(System.in));
        sr= new Scanner(new InputStreamReader(System.in));
        is=new BufferedReader(new InputStreamReader(s1.getInputStream()));
        os= new PrintWriter(s1.getOutputStream());
    }
    catch (IOException e){
        e.printStackTrace();
        System.err.print("IO Exception");
    }

    String response = null;
    try{
        char mark;
        response = is.readLine();
        System.out.println(response);
        if(response.compareTo("WELCOME f")==0)mark ='f';
        else mark = 's';
        System.out.println(mark);
        response = is.readLine();
        System.out.println(response);
        response = is.readLine();
            System.out.println(response);
        System.out.println("Fill in values 1 to 25");
        int[][] mat=new int[3][3];
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                mat[i][j] = br.nextInt();
            }
        }
        System.out.println("here :"+mat[1][1]);
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                System.out.print(mat[i][j]+" ");
            }
            System.out.println();
        }
        if(mark == 'f'){
            line = sr.nextLine();
            os.println(line);os.flush();
        }
        while(true){
            response = is.readLine();
            System.out.println(response);
            line = sr.nextLine();
            os.println(line);os.flush();
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
