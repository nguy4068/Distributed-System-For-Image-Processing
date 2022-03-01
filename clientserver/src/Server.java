import org.apache.thrift.server.TServer.Args;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import java.util.Scanner;
import cpnode.*;
import server.*;
public class Server{
	//the handler of server
	public static ServerServiceHandler serverHandler;
	//server stub
	public static ServerService.Processor processor;
	public static void main(String[] args){
		try {
			//take in machine file which contains IP address of compute node
			String machineList = args[0];
			String nodeConfig = args[1];
			//initialize server handler and server stub
			serverHandler = new ServerServiceHandler(machineList);
			processor = new ServerService.Processor(serverHandler, nodeConfig);
			//run server main thread
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
	/**
		Server main thread
	 */
	public static void simple(ServerService.Processor processor){
		try{
			//create listening socket for server
			TServerTransport serverTransport = new TServerSocket(9000);
			//started server
			TSimpleServer server = new TSimpleServer(new Args(serverTransport).processor(processor));
			System.out.println("Starting a multithreaded compute node server...");
			server.serve();
		}catch (Exception e){
			e.printStackTrace();
		}
	}
}
