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
/**
ServerServiceHandler is the core of Server, each RPC request from client
will be handled using instance of this class
 */
public class ServerServiceHandler implements ServerService.Iface{
		/**
		ComputeNodeThread is a customized thread used to perform RPC request
		for a single task (an image path)
		 */
        class ComputeNodeThread implements Runnable{
			//server's stub that connects with compute node's stub
			//we'll make RPC image processing request using this client
			//each task will have a seperate client
        	private ComputeNodeService.Client client;
			//socket to maintain connection between this server stub and 
			//the compute node stub
        	private TTransport transport;
        	private double prob;
        	private String task;
			/**
			Constructor
			@param client server's stub responsible for making RPC to computenode
			@param transport socket maintain connection between this thread with computenode
			@param task image filename (task of this thread)
			 */
            public ComputeNodeThread(ComputeNodeService.Client client, TTransport transport,String task){
                this.client = client;
                this.transport = transport;
                this.prob = prob;
                this.task = task;
            }
			public void run(){
				try{
					//print out image path
					System.out.println(task);
					//make RPC to compute node to handle the image processing task for this image
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
	Scanner scanner;
	List<String> nodes_IP;//list of IP addresses of compute nodes
    List<TTransport> transports;//list of connections
	List<ComputeNodeService.Client> clients;//list of clients responsible to make RPC to compute node
	List<String> image_path;//list of image filenames inside the input_dir folder
        int numNodes;//number of compute nodes that server can connect tos
	double[] probs = new double[]{0.8,0.2,0.5,0.4};//default value of load probabilities for each compute node
	int[] port_numbers = new int[]{4068,4000,4001,4002};//default running of each compute node
	List<List<String>> works;
	int scheduling_algo;//scheduling algorithm that server should use to schedule task for compute node
	int[] assigned_node;//helps identify which task is assigned to which compute node
	public ServerServiceHandler(String machine_file_path, String compute_node_file_path){
		//open machine file path to read in compute node's IP addresses and ports
		File file = new File(machine_file_path);
		//clients and transports for each threads in server
		clients = new ArrayList<>();
		transports = new ArrayList<>();
		try{
			scanner = new Scanner(file);
		}catch(FileNotFoundException e){
			scanner = null;
			System.out.println("machine file path not found");
		}
		nodes_IP = new ArrayList<>();
		//read in and store IP address for each node
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
		try {
			//read in port number and load probability for each node
			file = new File(compute_node_file_path);
			scanner = new Scanner(file);
			for (int i = 0; i < 4; i++){
				String node_config = scanner.nextLine();
				String[] detailed_configs = node_config.split(" ");
				//port number
				int port_num = Integer.parseInt(detailed_configs[1]);
				//load probability
				double prob_node = Double.parseDouble(detailed_configs[2]);
				port_numbers[i] = port_num;
				probs[i] = prob_node;
			}
			String algo = scanner.nextLine();
			String[] details = algo.split(" ");
			System.out.println(details[1]);
			if (details[1].equals("load_balancing")){
				this.scheduling_algo = 0;
			}else{
				this.scheduling_algo = 1;
			}
			
			
		}catch (FileNotFoundException e){
		}
		numNodes = nodes_IP.size();
		image_path = new ArrayList<>();
		this.works = new ArrayList<>();
		
	}
        
	@Override
	public boolean imgprocess(String filepath){
		//traverse input folder to extract file names
		traverseFolder(filepath);
		//delegate tasks for each compute node
		delegateWorks();
		int numTasks = image_path.size();
		List<Thread> threads = new ArrayList<>();
		//started timing time needed for all tasks to be completed
		long startTime = System.currentTimeMillis();
		//each task will now be handled by a single thread
		for (int i = 0; i < numTasks; i++){
			int node = assigned_node[i]; 
			try {
				//create seperate transport and client for the thread to make
				//RPC to the node that the task was assigned to
				TTransport transport = new TSocket(nodes_IP.get(node),port_numbers[node]);
				transport.open();
				TProtocol protocol = new TBinaryProtocol(transport);
				ComputeNodeService.Client client = new ComputeNodeService.Client(protocol);
				clients.add(client);
				transports.add(transport);
				Thread task = new Thread(new 		ComputeNodeThread(client,transport, image_path.get(i)));
				threads.add(task);
				//start performing task
				task.start();
			}catch (TException x){
				System.out.println("Failed to open connection");
				x.printStackTrace();
			}
			
		}
		//wait for all task to be done
		for (int i = 0; i < numTasks; i++){
			try{
				threads.get(i).join();
			}catch (Exception e){
			
				e.printStackTrace();
			}
		}
		//end timming
		long endTime = System.currentTimeMillis();
		long duration = endTime - startTime;
		//check scheduling algorithm to print out message
		if (scheduling_algo == 0){
			System.out.println("jobs using load balancing scheduler were done in " + duration + " milliseconds");
		}else{
			System.out.println("jobs using random scheduler were done in " + duration + " milliseconds");
		}
		//close all connections
		for (int i = 0; i < numTasks; i++){
			transports.get(i).close();
		}
		transports = new ArrayList<>();
		clients = new ArrayList<>();
		return true;
		
	
	
	}
	/**
	@brief Traverse read and save file name of image
	@param filepath the input directory
	 */
	public void traverseFolder(String filepath){
	 	File directory = new File(filepath);
	 	File[] files = directory.listFiles();
	 	for (File f: files){
	 		image_path.add(f.getAbsolutePath());
	 		System.out.println(f.getAbsolutePath());
	 	}
		//initialize array to later keep track on which node will handle which task
	 	this.assigned_node = new int[image_path.size()];

	}
	/**
	* Function used to delegate works for compute node
	 */
	public void delegateWorks(){
		System.out.println("Started delegate works");
		int remained_tasks = image_path.size();
		int first_task = 0;
		//if using the load balancing algorithm
		if (scheduling_algo == 0){
			System.out.println("Using load balancing algorithm");
			//keep running while haven't assigned all the requested tasks
			while (remained_tasks > 0){
				for (int i = 0; i < numNodes && remained_tasks > 0; i++){
					//loop through each node and ask them if they want to
					//handle this task or not
					double random = Math.random();
					//if the node wants to do the task, assign the task to the node
					//by marking that task's corresponding index in assgined_node array
					//the node number
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
			//if using random algorith,
			System.out.println("Using random algorithm");
			while (remained_tasks > 0){
				//randomly choose node to assign task until there're no tasks left
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
	
