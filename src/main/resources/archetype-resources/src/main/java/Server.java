package ${package};

import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TServer.Args;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;

public class Server {

  public static ServiceHandler service;

  public static Service.Processor<ServiceHandler> processor;

  public static void main(String[] args) {
    try {
      service = new ServiceHandler();
      processor = new Service.Processor<ServiceHandler>(service);

      Runnable server = new Runnable() {
        public void run() {
          startServer(processor);
        }
      };      

      new Thread(server).start();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void startServer(Service.Processor<ServiceHandler> processor) {
    try {
      TServerTransport serverTransport = new TServerSocket(9090);
      TServer server = new TSimpleServer(new Args(serverTransport).processor(processor));

      // Use this for a multithreaded server
      // TServer server = new TThreadPoolServer(new TThreadPoolServer.Args(serverTransport).processor(processor));

      System.out.println("Starting the server...");
      server.serve();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}