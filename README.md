# Bingo-game-client-server
This is a client-server implementation of Bingo game. Where two clients have to create table by giving 9 inputs in a row 
after getting instructions from server.
After filling the table both the client have to give each input respectively when their turn come's for continuation of game This 
game's logic is such that after sequential inputs from clients if any three lines horizontal or vertical completly filled up the 
he win the game who can't reach this mark loses.
###....Input.....for.....Running......this......Application......Game....####
Dowanload this folder or clone -> open in Terminal -> Then type Following commands. 

Running Server:
             
             =>javac BingoServerFinal.java (compilation)
             =>java BingoServer
             
             Now you will see somthing Like this...
             
             Bingo Server is Running... (That means your server has started)
             
Running Client:
             (You have to run atleast 2 clients to run this Application)
             You can do that by opening multiple terminal in same folder location or have to know the IP address of Server system.
             for same system you can use localhost: 127.0.0.1
             
             =>javac BingoClientFinal.java
             =>java BingoClientFinal 127.0.0.1
             
             Now you will see somthing Like this...
             
             Welcome Player 1
             MESSAGE: Waiting for opponent to connect (This message is coming from server telling that no opponent is there)
             
             (for that u have to re-run this client code in another console or system with proper IP address)
             
             =>java BingoClientFinal 127.0.0.1 (remmember this IP is for same system for diff system Ip will vary)
             
             Now you will see somthing Like this...
             
             welcome player 2
             You are second player fill the table
             waiting for the move

             Fill in values 1 to 9 in random order
             
             ^
             |
             Above u have to give manually 1-9 in random order somthing like 
             1 3 5 7 9 8 6 4 2 
             for above input the game table will become.
             1 3 5 
             7 9 8 
             6 4 2 
             
             Now for first player it will show...
             
             You are first player start the game by choosing any number 1-9.
             2
             After this it's Player2's turn keep playing until You Win.
             
             BINGO!!!
             
