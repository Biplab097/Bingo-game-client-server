import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BingoClientFinal {

public static void main(String args[]) throws IOException{


    InetAddress address=InetAddress.getByName(args[0]);
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
    boolean win=false;
    try{
        char mark;
        response = is.readLine();
        //System.out.println(response+"");
        if(response.compareTo("WELCOME f")==0){
            mark ='f';
            System.out.println("Welcome Player 1");
        }    
        else{
            mark = 's';
            System.out.println("welcome player 2");
        }    
        //System.out.println(mark);
        response = is.readLine();
        System.out.println(response);
        response = is.readLine();
        System.out.println(response);
        System.out.println();
        System.out.println("Fill in values 1 to 9 in random order");
        int[][] mat=new int[3][3];
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                mat[i][j] = br.nextInt();
            }
        }

        //checking if user have given some illegal duplicate input by converting 2d matrix to 1d array
        List<Integer> flatArray = new ArrayList<Integer>();
        for (int[] row : mat) {
            for (int col : row) {
               flatArray.add(col);
            }
        }
        //checking loop started
        boolean dupFound=false;
        dupFound=checkDuplicate(flatArray);
         
        while(dupFound){
            System.out.println("please re enter your table 1-9 unique values and should be less then 9:");
            for(int i=0;i<3;i++){
                for(int j=0;j<3;j++){
                    mat[i][j] = br.nextInt();
                }
            }
            flatArray=new ArrayList<Integer>();
            for (int[] row : mat) {
                for (int col : row) {
                   flatArray.add(col);
                }
            }
            dupFound=checkDuplicate(flatArray);

        } 

        System.out.println("Your input table is:");
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                System.out.print(mat[i][j]+" ");
            }
            System.out.println();
        }
        System.out.println();
        if(mark=='s')System.out.println("Wait for player1's move...");
        if(mark == 'f'){
            System.out.println("You are first player start the game by choosing any number 1-9.");
            line = sr.nextLine();
            boolean check0=findMatrix(Integer.parseInt(line),mat);

            if(check0){
                System.out.println("Output table after first round");
                for(int i=0;i<3;i++){
                    for(int j=0;j<3;j++){
                        System.out.print(mat[i][j]+" ");
                    }
                    System.out.println();
                }
                System.out.println("Wait for 2nd player's move....");

            }
            else{
                System.out.println("Please Enter valid number present in your table");
                line = sr.nextLine();
                boolean check=findMatrix(Integer.parseInt(line),mat);
                if(check){
                    System.out.println("Output table after first round");
                    for(int i=0;i<3;i++){
                        for(int j=0;j<3;j++){
                            System.out.print(mat[i][j]+" ");
                        }
                        System.out.println();
                    }
    
                }
            }
            os.println(line);os.flush();
        }
        while(true){
            response = is.readLine();
            System.out.println("Opponent move : "+response);
            if(response.compareTo("-1")==0){
                System.out.println("You lose");
                os.close();
                break;
            }
            boolean check=findMatrix(Integer.parseInt(response),mat);
            if(check){
                System.out.println(("Your updated table is:"));
                win=winOrlose(mat);
                if(win){
                    System.out.println("Congratulations You WON!!!");
                    line = "-1";
                    os.println(line);os.flush();
                    try{os.close();}catch(Exception e){
                        System.out.println("game over");
                    }
                    break;
                }
                for(int i=0;i<3;i++){
                    for(int j=0;j<3;j++){
                        System.out.print(mat[i][j]+" ");
                    }
                    System.out.println();
                }
                
            }
            else{System.out.println("Input table not matching: check your table input");}
            System.out.println("please enter your next choice:");
            line = sr.nextLine();
            //System.out.println(("coming here1"));
            boolean check1=findMatrix(Integer.parseInt(line),mat);
            //System.out.println(("coming here2"));
            if(check1){
                System.out.println("YOUR updated table is:");

                for(int i=0;i<3;i++){
                    for(int j=0;j<3;j++){
                        System.out.print(mat[i][j]+" ");
                    }
                    System.out.println();
                }
            }
            
            else{
                System.out.println("Please Enter valid number present in your table");
                line = sr.nextLine();
                boolean check2=findMatrix(Integer.parseInt(line),mat);
                if(check2){
                    System.out.println("YOUR updated table:");

                    for(int i=0;i<3;i++){
                        for(int j=0;j<3;j++){
                            System.out.print(mat[i][j]+" ");
                        }
                        System.out.println();
                    }
                    System.out.println("Please wait for opponent's move...");

                }
            }
            if(winOrlose(mat)){
                System.out.println("Congratulations BINGO You WON!!!");
                line = "-1";
                os.println(line);os.flush();
                try {
                    os.close();
                } catch (Exception e) {
                    //TODO: handle exception
                    System.out.println("game over bye bye");
                }
                break;
            }
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

    private static boolean winOrlose(int[][] mat) {
        int count=2,flag=0,val=0;
        for (int i = 0; i < mat.length; i++) {
            val=0;
            for (int j = 0; j < mat.length; j++) {
                val=val+mat[i][j];
                if(val==-3){
                    ++flag;
                }
            }
            
        }
        if(flag==count){
            return true;
        }

        return false;
        
    }

    private static boolean findMatrix(int target, int[][] matrix) {

         for (int i = 0; i < matrix.length; i++) {
              for (int j = 0; j < matrix.length; j++) {
                  if(matrix[i][j]==target){
                       matrix[i][j]=-1;
                       return true;
                  }
              }
         }
        
    return false;
    }
    private static  boolean checkDuplicate(List<Integer> flatArray){
        for (int k = 0; k < flatArray.size();k++) {
          
            for (int j = k + 1; j < flatArray.size(); j++) {
                if(flatArray.get(j)>9)return true;
                if (flatArray.get(k) == flatArray.get(j)) {
                    System.out.println("all fine");
                    
                    // Remove duplicate found in inner loop
                   
                    flatArray.remove(j);
                    j--;
                    return true;
                }
            }
        }   
        return false;
    }
}
