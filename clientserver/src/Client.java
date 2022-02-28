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
		transport = new TSocket("kh4250-09.cselabs.umn.edu",9000);
		transport.open();
		TProtocol protocol = new TBinaryProtocol(transport);
		ServerService.Client client = new ServerService.Client(protocol);
		perform(client);
		System.out.println("Exit client");
		transport.close();
	}catch (TException x){
		x.printStackTrace();
	}
	}
	private static void perform(ServerService.Client client) throws TException{
		System.out.println("Sending result and waiting");
		boolean result = client.imgprocess("input_dir");
		System.out.println("result return");
		if (!result){
			System.out.println("Failed to RPC image process");
		}else{
			System.out.println("Canny edge detect image successfully");
		}
			

	}
	
}
