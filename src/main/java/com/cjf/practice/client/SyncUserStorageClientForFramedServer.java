package com.cjf.practice.client;

import java.io.IOException;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

import com.cjf.practice.UserProfile;
import com.cjf.practice.UserStorage;

/**
 * 适配服务端 FramedUserStorageServer 或 NonblockingUserStorageServer
 * @author Caijanfeng
 *
 */
public class SyncUserStorageClientForFramedServer {

	public static void main(String[] args) throws TException, IOException {

		TTransport transport = new TFramedTransport(new TSocket("localhost", 8600));
		TBinaryProtocol binaryProtocol = new TBinaryProtocol(transport);
		UserStorage.Client client = new UserStorage.Client.Factory().getClient(binaryProtocol);

		transport.open();

		UserProfile user = new UserProfile(1, "user_name", "sth... i don't know");
		System.out.println(user);
		client.store(user);
		System.out.println("store finished.");

		UserProfile fetchUser = client.retrieve(user.getUid());
		System.out.println(fetchUser);
		System.out.println(user.equals(fetchUser));

		transport.close();
	}

}
