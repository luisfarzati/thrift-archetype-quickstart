package ${package};

import static junit.framework.Assert.*;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TServer.Args;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;
import org.junit.Test;

public class ServiceHandlerTest {
	private static final int PORT = 9090;
	private static final String HOST = "localhost";
	private TServer server;
	
	@Test
	public void shouldInvokePing() throws TException, InterruptedException {
		System.out.println("Creating server");
		Thread thread = new Thread(serverTask());
		thread.start();

		Thread.sleep(500);
		System.out.println("Creating client");
		TTransport transport = new TSocket(HOST, PORT);
		transport.open();		
		Service.Client client = new Service.Client(new TBinaryProtocol(transport));
		
		System.out.println("PING");
		client.ping();
		
		System.out.println("Closing client");
		transport.close();
		
		System.out.println("Stopping server");
		server.stop();
		thread.interrupt();
	}

	@Test
	public void shouldInvokeSum() throws TException, InterruptedException {
		System.out.println("Creating server");
		Thread thread = new Thread(serverTask());
		thread.start();

		Thread.sleep(500);
		System.out.println("Creating client");
		TTransport transport = new TSocket(HOST, PORT);
		transport.open();		
		Service.Client client = new Service.Client(new TBinaryProtocol(transport));
		
		System.out.println("SUM");
		int one = 1;
		int expected = one + one;
		int actual = client.sum(one, one);
		
		System.out.println("Closing client");
		transport.close();
		
		System.out.println("Stopping server");
		server.stop();
		thread.interrupt();
		
		assertEquals(expected, actual);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Runnable serverTask() {
		return new Runnable() {
			@Override
			public void run() {
				TServerTransport serverTransport;
				try {
					serverTransport = new TServerSocket(PORT);
					server = new TSimpleServer(new Args(serverTransport).processor(new Service.Processor(new ServiceHandler())));
					System.out.println(
							String.format("Starting the server on port %d...", PORT));
					server.serve();
				} catch (TTransportException e) {
					e.printStackTrace();
				}
			}
		};
	}
}
