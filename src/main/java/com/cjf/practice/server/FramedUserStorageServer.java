package com.cjf.practice.server;

import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TBinaryProtocol.Factory;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TTransportException;

import com.cjf.practice.UserStorage;
import com.cjf.practice.service.UserStorageServiceImpl;

/**
 * 异步调用客户端: AsyncUserStorageClient
 * 同步调用客户端： SyncUserStorageClientForFramedServer
 * @author Caijanfeng
 *
 */
public class FramedUserStorageServer {

	public static void main(String[] args) throws TTransportException {
		int port = 8600;

		TServerTransport serverTransport = new TServerSocket(port);
		Factory portFactory = new TBinaryProtocol.Factory(true, true);
		TProcessor processor = new UserStorage.Processor<UserStorageServiceImpl>(new UserStorageServiceImpl());

		TThreadPoolServer.Args serverArgs = new TThreadPoolServer.Args(serverTransport);
		serverArgs.processor(processor);
		serverArgs.protocolFactory(portFactory);
		serverArgs.transportFactory(new TFramedTransport.Factory());

		TThreadPoolServer server = new TThreadPoolServer(serverArgs);
		server.serve();
	}

}
