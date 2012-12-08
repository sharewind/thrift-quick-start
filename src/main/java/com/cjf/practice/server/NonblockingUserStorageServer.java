package com.cjf.practice.server;

import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TBinaryProtocol.Factory;
import org.apache.thrift.server.TThreadedSelectorServer;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TTransportException;

import com.cjf.practice.UserStorage;
import com.cjf.practice.service.UserStorageServiceImpl;

public class NonblockingUserStorageServer {

	public static void main(String[] args) throws TTransportException {
		int port = 8600;

		TNonblockingServerSocket serverTransport = new TNonblockingServerSocket(port);
		Factory portFactory = new TBinaryProtocol.Factory(true, true);
		TProcessor processor = new UserStorage.Processor<UserStorageServiceImpl>(new UserStorageServiceImpl());

		TThreadedSelectorServer.Args serverArgs = new TThreadedSelectorServer.Args(serverTransport);
		serverArgs.processor(processor);
		serverArgs.protocolFactory(portFactory);

		TThreadedSelectorServer server = new TThreadedSelectorServer(serverArgs);
		server.serve();
	}

}
