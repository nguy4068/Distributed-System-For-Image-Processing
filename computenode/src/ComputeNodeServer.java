import org.apache.thrift.server.TServer.Args;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import cpnode.*;
import java.util.Scanner;
import java.io.*;
public class ComputeNodeServer{
	public static ComputeNodeServiceHandler computeNodeHandler;
	public static ComputeNodeService.Processor processor;
	public static double inject_prob = 0.2;
	public static int port = 4068;
	public static void main(String[] args){
		try {
			if (args.length == 1){
				int index = Integer.parseInt(args[0]);
				System.out.println(index);
				File config_file = new File("config/computenode_config.txt");
				try{
					Scanner s = new Scanner(config_file);
					String id = "computenode" + index;
					while (s.hasNext()){
						String line = s.nextLine();
						System.out.println(line);
						if (line.startsWith(id)){
							String[] config_details = line.split(" ");
							port = Integer.parseInt(config_details[1]);
							inject_prob = Float.parseFloat(config_details[2]);
							break;
						}
					}
					System.out.println("Compute node " + index + " run on port " + port + " with injecting probability of " + inject_prob);
				
				}catch (FileNotFoundException exception){
					System.out.println("Config file not found");
				} 
				
			}
			computeNodeHandler = new ComputeNodeServiceHandler(inject_prob);
			processor = new ComputeNodeService.Processor(computeNodeHandler);

		Runnable simple = new Runnable() {
			public void run(){
				simple(processor);
			}
		};
		new Thread(simple).start();
	}catch (Exception x){
		x.printStackTrace();
	}
	}
	public static void simple(ComputeNodeService.Processor processor){
		try{
			TServerTransport serverTransport = new TServerSocket(port);
			TThreadPoolServer server = new TThreadPoolServer(new TThreadPoolServer.Args(serverTransport).processor(processor));
			//System.out.println("Starting a multithreaded compute node server...");
			server.serve();
		}catch (Exception e){
			e.printStackTrace();
		}
	}
}

