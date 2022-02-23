import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import java.util.Scanner;
import server.*;
public class Client {
	public static Scanner scanner = new Scanner(System.in);
	public static void main(String[] args){
	try {
		TTransport transport;
		transport = new TSocket("localhost",9000);
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
		while (run){
			String command = scanner.nextLine();
			String[] command_token = command.split(" ");
			if (command_token[0].equals("quit")){
			    run = false;
			}else{
			    System.out.println(command_token[0]);
			    boolean result = client.imgprocess(command_token[0]);
			    if (!result){
			    	System.out.println("Failed to RPC image process");
			    }else{
			        System.out.println("Canny edge detect image successfully");
			    }
			}

		}
		System.out.println("End program, good bye!");
	}
	
}
