import org.apache.thrift.server.TServer.Args;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import cpnode.*;
public class ComputeNodeServer{
	public static ComputeNodeServiceHandler computeNodeHandler;
	public static ComputeNodeService.Processor processor;
	public static void main(String[] args){
		try {
			computeNodeHandler = new ComputeNodeServiceHandler();
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
			TServerTransport serverTransport = new TServerSocket(9090);
			TSimpleServer server = new TSimpleServer(new Args(serverTransport).processor(processor));
			System.out.println("Starting a multithreaded compute node server...");
			server.serve();
		}catch (Exception e){
			e.printStackTrace();
		}
	}
}

