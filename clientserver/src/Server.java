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
	public static ServerServiceHandler serverHandler;
	public static ServerService.Processor processor;
	public static void main(String[] args){
		try {
			String machineList = args[0];
			serverHandler = new ServerServiceHandler(machineList);
			processor = new ServerService.Processor(serverHandler);
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
	public static void simple(ServerService.Processor processor){
		try{
			TServerTransport serverTransport = new TServerSocket(9000);
			TSimpleServer server = new TSimpleServer(new Args(serverTransport).processor(processor));
			System.out.println("Starting a multithreaded compute node server...");
			server.serve();
		}catch (Exception e){
			e.printStackTrace();
		}
	}
}
