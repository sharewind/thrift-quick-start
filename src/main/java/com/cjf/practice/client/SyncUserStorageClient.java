package com.cjf.practice.client;

import java.io.IOException;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

import com.cjf.practice.UserProfile;
import com.cjf.practice.UserStorage;

public class SyncUserStorageClient {

	public static void main(String[] args) throws TException, IOException {

		TTransport transport = new TSocket("localhost", 8600);
		TBinaryProtocol binaryProtocol = new TBinaryProtocol(transport);
		UserStorage.Client client = new UserStorage.Client.Factory().getClient(binaryProtocol);

		UserProfile user = new UserProfile(1, "user_name", "sth... i don't know");

		transport.open();

		System.out.println(user);
		client.store(user);
		System.out.println("store finished.");

		UserProfile fetchUser = client.retrieve(user.getUid());
		System.out.println(fetchUser);
		System.out.println(user.equals(fetchUser));

		transport.close();

	}

}
