import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import java.util.Scanner;
import java.io.*;
import server.*;
/**
Client class will connect to server using the IP and port number specified
in the machine.txt file, the port number that server listens on is determined to be 9000
 */
public class Client {
	public static void main(String[] args){
		//read in server address from machine.txt file
		try {
			//get the server address
			String serverAddr = getServerAddr(args[0]);
			System.out.println(serverAddr);
			TTransport transport;
			//create socket connection with server
			transport = new TSocket(serverAddr, 9000);
			transport.open();
			TProtocol protocol = new TBinaryProtocol(transport);
			//connect with client stub
			ServerService.Client client = new ServerService.Client(protocol);
			//perform client job
			perform(client);
			//finish client job
			System.out.println("Exit client");
			transport.close();
			//sleep to wait for autograder
			try{
				Thread.sleep(22000);
			} catch (Exception e){
				System.out.println("Exception while trying to sleep");
			}
		}catch (TException x){
			x.printStackTrace();
		}
	}
	/**
	*@param client client stub that helps handle RPC made from client
	*/
	private static void perform(ServerService.Client client) throws TException{
		System.out.println("Sending result and waiting");
		//make RPC to the server
		boolean result = client.imgprocess("input_dir");
		//server finishes handling the request
		System.out.println("result return");
		//if the server fails handling the request
		if (!result){
			System.out.println("Failed to RPC image process");
		}else{
		//if the server finishes handling the request successfully
			System.out.println("Canny edge detect image successfully");
		}
			

	}


  // A helper function to get the server IP address from a config file
  // the argument is set in the build.xml
  private static String getServerAddr(String file_path) {
    // Based on Ngan's implementation
    File file = new File(file_path);
    Scanner scanner;
    try {
      scanner = new Scanner(file);
    } catch (FileNotFoundException e) {
      scanner = null;
      System.out.println("machine file path not found");
      // If no machine file is found, set the IP address to localhost
      return "localhost";
    }
    while (scanner.hasNext()) {
      String machine_config = scanner.nextLine();
      String[] info = machine_config.split(" ");
      String role = info[0];
      String IP = info[1];
      System.out.println("role: " + role);
      // check if it's compute node configuration
      if (role.startsWith("server")) {
        System.out.println("IP: " + IP);
        return IP;
      }
    }
    // The file was there but the server IP was not. So just return localhost
    return "localhost";
  }
	
}
