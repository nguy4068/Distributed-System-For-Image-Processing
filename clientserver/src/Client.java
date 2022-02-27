import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.io.*;
import server.*;
public class Client {
	public static Scanner scanner = new Scanner(System.in);
	public static void main(String[] args){
	try {
    System.out.println("file path: " + args[0]);
    String serverAddr = getServerAddr(args[0]);
    System.out.println("server addr: " + serverAddr);
		TTransport transport;
		transport = new TSocket(serverAddr, 8282);
		transport.open();
		TProtocol protocol = new TBinaryProtocol(transport);
		ServerService.Client client = new ServerService.Client(protocol);
		perform(client);
		transport.close();
	}catch (TException x){
		x.printStackTrace();
	}
	}
	private static void perform(ServerService.Client client) throws TException{
		boolean run = true;
		System.out.println("Welcome to image processing service!");
		System.out.println("Please enter path to image file that you want to do canny edge on:");

    System.out.println("input_dir");
    boolean result = client.imgprocess("input_dir");
	    if (!result){
	    	System.out.println("Failed to RPC image process");
	    }else{
	        System.out.println("Canny edge detect image successfully");
	    }

		//while (run){
		//	String command = scanner.nextLine();
		//	String[] command_token = command.split(" ");
		//	if (command_token[0].equals("quit")){
		//	    run = false;
		//	}else{
		//	    System.out.println(command_token[0]);
		//	    boolean result = client.imgprocess(command_token[0]);
		//	    if (!result){
		//	    	System.out.println("Failed to RPC image process");
		//	    }else{
		//	        System.out.println("Canny edge detect image successfully");
		//	    }
		//	}
		//} // end while
		System.out.println("End program, good bye!");
	}
  private static String getServerAddr(String file_path) {
    // Based on Ngan's implementation
    File file = new File(file_path);
    Scanner scanner; 
    try{
        scanner = new Scanner(file);
    }catch(FileNotFoundException e){
        scanner = null;
        System.out.println("machine file path not found");
        // if no machine file is found, set the IP address to localhost
        return "localhost";
    }
		while (scanner.hasNext()){
			String machine_config = scanner.nextLine();
			String[] info = machine_config.split(" ");
			String role = info[0];
			String IP = info[1];
      System.out.println("role: " + role);
			//check if it's compute node configuration
			if (role.startsWith("server")){
        System.out.println("IP: " + IP);
        return IP;
			}
    }
    return null;
  }
}
