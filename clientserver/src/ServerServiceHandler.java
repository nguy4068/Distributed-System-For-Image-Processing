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
        	private String task;
                public ComputeNodeThread(ComputeNodeService.Client client, TTransport transport,String task){
                	this.client = client;
                	this.transport = transport;
                	this.prob = prob;
                	this.task = task;
                }
		public void run(){
				try{
					System.out.println(task);
					boolean result = client.imgprocess(task);
					if (result){
						System.out.println("Process successfully image " + task);
					
					}else{
						System.out.println("Failed to process image " + task);
					}
				}catch (Exception e){
					e.printStackTrace();
				}

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
	double[] probs = new double[]{0.8,0.2,0.5,0.4};
	int[] port_numbers = new int[]{4068,4000,4001,4002};
	List<List<String>> works;
	int scheduling_algo;
	int[] assigned_node;
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
		this.works = new ArrayList<>();
		this.scheduling_algo = 0;
		
	}
	/*public void generateSockets(){
		this.transports = new ArrayList<>();
		this.clients = new ArrayList<>();
		for (int i = 0; i < numNodes; i++){
			try {
				TTransport transport;
				transport = new TSocket(nodes_IP.get(i),port_numbers[i]);
				transport.open();
				TProtocol protocol = new TBinaryProtocol(transport);
				ComputeNodeService.Client client = new ComputeNodeService.Client(protocol);
				clients.add(client);
				transports.add(transport);
				
			}catch (TException x){
				System.out.println("Failed to open connection");
				x.printStackTrace();
			}
		}
	}*/
        
        
	@Override
	public boolean imgprocess(String filepath){
		count.curr_index = 0;
		traverseFolder(filepath);
		delegateWorks();
		int numTasks = image_path.size();
		List<Thread> threads = new ArrayList<>();
		for (int i = 0; i < numTasks; i++){
			int node = assigned_node[i]; 
			try {
				TTransport transport = new TSocket(nodes_IP.get(node),port_numbers[node]);
				transport.open();
				TProtocol protocol = new TBinaryProtocol(transport);
				ComputeNodeService.Client client = new ComputeNodeService.Client(protocol);
				clients.add(client);
				transports.add(transport);
				Thread task = new Thread(new 		ComputeNodeThread(client,transport, image_path.get(i)));
				threads.add(task);
				task.start();
				
			}catch (TException x){
				System.out.println("Failed to open connection");
				x.printStackTrace();
			}
			
		}
		for (int i = 0; i < numTasks; i++){
			try{
				threads.get(i).join();
			}catch (Exception e){
			
				e.printStackTrace();
			}
		}
		for (int i = 0; i < numTasks; i++){
			transports.get(i).close();
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
	 	this.assigned_node = new int[image_path.size()];

	}
	public void delegateWorks(){
		System.out.println("Started delegate works");
		int remained_tasks = image_path.size();
		int first_task = 0;
		System.out.println("Started assigning empty array");
		for (int i = 0; i < numNodes; i++){
			List<String> individual_task = new ArrayList<>();
			this.works.add(individual_task);
		}
		System.out.println("Finished assigning empty array for each task");
		if (scheduling_algo == 0){
			while (remained_tasks > 0){
				for (int i = 0; i < numNodes && remained_tasks > 0; i++){
					double random = Math.random();
					if (random > probs[i]){
						//should do the work
						assigned_node[first_task] = i;
						/**works.get(i).add(image_path.get(first_task));**/
						first_task++;
						remained_tasks--;
					}
				
				}			
		
			}
		}else{
			while (remained_tasks > 0){
				int chosen_node =(int)(Math.random()*numNodes);
				assigned_node[first_task] = chosen_node;
				//works.get(chosen_node).add(image_path.get(first_task));
				first_task++;
				remained_tasks--;
				
			}
		}
		System.out.println("End delegate works");
	}
}
	
