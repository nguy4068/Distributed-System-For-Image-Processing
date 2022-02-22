import server.*;
import cpnode.*;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;
import java.util.Random;
import java.io.*;
public class ServerServiceHandler implements ServerService.Iface{
        class Count{
        	public volatile int curr_index = 0;
        	public volatile int max_index = 0;
        }
        class ComputeNodeThread implements Runnable{
        	private ComputeNodeService.Client client;
        	private TTransport transport;
        	private double prob;
        	private List<String> file_paths;
                public ComputeNodeThread(ComputeNodeService.Client client, TTransport transport, double prob, List<String> file_paths){
                	this.client = client;
                	this.transport = transport;
                	this.prob = prob;
                	this.file_paths = file_paths;
                }
		public void run(){
			while (count.curr_index < count.max_index){
				try{
					double random = Math.random();
					if (random > prob){
						//should do the work
					        int prev = count.curr_index;
						count.curr_index++;
						boolean result = client.imgprocess(file_paths.get(prev));
					if (result){
						System.out.println("Canny edge detect image successfully");
					}else{
						System.out.println("Failed to canny edge detect image");
					}
						
					}else{
						System.out.println("Compute node rejects job in this round");
					}	
				}
				catch (Exception e){
				}
			}
			System.out.println("Done image processing on all images");
			transport.close();
		}
	}
        
        final Count count = new Count();
	Scanner scanner;
	List<String> nodes_IP;
        List<TTransport> transports;
	List<ComputeNodeService.Client> clients;
	List<String> image_path;
        int numNodes;
	int start = 0;
	int end = 0;
	double[] probs = new double[]{0.8,0.6,0.5,0.2};
	
	public ServerServiceHandler(String machine_file_path){
		File file = new File(machine_file_path);
		clients = new ArrayList<>();
		transports = new ArrayList<>();
		try{
			scanner = new Scanner(file);
		}catch(FileNotFoundException e){
			scanner = null;
			System.out.println("machine file path not found");
		}
		nodes_IP = new ArrayList<>();
		while (scanner.hasNext()){
			String machine_config = scanner.nextLine();
			String[] info = machine_config.split(" ");
			String role = info[0];
			String IP = info[1];
			//check if it's compute node configuration
			if (role.startsWith("node")){
				nodes_IP.add(IP);
			
			}else{
				break;
			}
		}
		//set up connection here
		numNodes = nodes_IP.size();
		image_path = new ArrayList<>();
		for (int i = 0; i < numNodes; i++){
			try {
				TTransport transport;
				transport = new TSocket(nodes_IP.get(i),9090);
				transport.open();
				TProtocol protocol = new TBinaryProtocol(transport);
				ComputeNodeService.Client client = new ComputeNodeService.Client(protocol);
				clients.add(client);
				transports.add(transport);
				
			}catch (TException x){
				x.printStackTrace();
			}
		}
		
		
	}
        
        
	@Override
	public boolean imgprocess(String filepath){
		traverseFolder(filepath);
		for (int i = 0; i < numNodes; i++){
			System.out.println("Start compute node " + i);
			Thread computenode = new Thread(new ComputeNodeThread(clients.get(i),transports.get(i), probs[i],image_path));
			computenode.start();
		}
		return true;
		
	
	
	}
	public void traverseFolder(String filepath){
	 	File directory = new File(filepath);
	 	File[] files = directory.listFiles();
	 	count.max_index = files.length;
	 	for (File f: files){
	 		image_path.add(f.getAbsolutePath());
	 		System.out.println(f.getAbsolutePath());
	 	}
	 	end = image_path.size();

	}
}
	
