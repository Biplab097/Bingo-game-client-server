import java.net.*;
import java.io.*;
/**
 * This example illustrates the basic syntax for stream-mode
 * socket.
 * @author M. L. Liu
 */
public class Example4ConnectionAcceptor {

// An application that accepts a connection and receives a message 
//  using stream-mode socket.
// Two command line arguments are expected, in order: 
//    <port number for the the Server socket used in this process>
//    <message, a string, to send>

   public static void main(String[] args) {
      if (args.length != 2)
         System.out.println
            ("This program requires two command line arguments");
      else {
         try {
  		      int portNo = Integer.parseInt(args[0]);
            String message = args[1];
            // instantiates a socket for accepting connection  	
   	      ServerSocket connectionSocket = new ServerSocket(portNo);
/**/        System.out.println("\nReady for accepting a TCP socket connection ....\n");  
            // wait to accept a connecion request, at which
            //  time a data socket is created                  
            Socket dataSocket = connectionSocket.accept();
/**/        System.out.println("Connection accepted\n"); 
Thread.currentThread().sleep(2000);

/**/        System.out.println("Preparing a data stream .... \n"); 

Thread.currentThread().sleep(2000);

            // get a output stream for writing to the data socket
            OutputStream outStream = dataSocket.getOutputStream();
            // create a PrinterWriter object for character-mode output
            PrintWriter socketOutput = 
               new PrintWriter(new OutputStreamWriter(outStream));

            // write a message into the data stream
            socketOutput.println(message);

            //The ensuing flush method call is necessary for the data to
            // be written to the socket data stream before the
            // socket is closed.
/**/        System.out.println("Sending out msg into a data stream .... \n"); 
Thread.currentThread().sleep(2000);

            socketOutput.flush();
/**/        System.out.println("message sent\n"); 
//Thread.currentThread().sleep(5000);

/* for  second  requester

		/**/        System.out.println("\nReady for accepting a TCP socket connection ....\n");  
            // wait to accept a connecion request, at which
            //  time a data socket is created                  
            Socket dataSocket = connectionSocket.accept();
/**/        System.out.println("Connection accepted\n"); 
Thread.currentThread().sleep(2000);

/**/        System.out.println("Preparing a data stream .... \n"); 

Thread.currentThread().sleep(2000);

            // get a output stream for writing to the data socket
            OutputStream outStream = dataSocket.getOutputStream();
            // create a PrinterWriter object for character-mode output
            PrintWriter socketOutput = 
               new PrintWriter(new OutputStreamWriter(outStream));

            // write a message into the data stream
            socketOutput.println(message);

            //The ensuing flush method call is necessary for the data to
            // be written to the socket data stream before the
            // socket is closed.
/**/        System.out.println("Sending out msg into a data stream .... \n"); 
Thread.currentThread().sleep(2000);

            socketOutput.flush();
/**/        System.out.println("message sent\n"); 
Thread.currentThread().sleep(5000);

/*end editing


            dataSocket.close( );
/**/        System.out.println("data socket closed\n");
            connectionSocket.close( );
/**/        System.out.println("connection socket closed\n"); 
         } // end try
	      catch (Exception ex) {
            ex.printStackTrace( );
	      } //end catch
      } // end else
   } // end main
} // end class
