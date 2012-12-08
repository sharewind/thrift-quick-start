package com.cjf.practice.client;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.async.TAsyncClientManager;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.transport.TNonblockingSocket;
import org.apache.thrift.transport.TNonblockingTransport;

import com.cjf.practice.UserProfile;
import com.cjf.practice.UserStorage;
import com.cjf.practice.UserStorage.AsyncClient.retrieve_call;
import com.cjf.practice.UserStorage.AsyncClient.store_call;

public class AsyncUserStorageClient {


	static final CountDownLatch countDownLatch = new CountDownLatch(1);

	public static void main(String[] args) throws TException, IOException, InterruptedException {


		TNonblockingTransport transport = new TNonblockingSocket("localhost", 8600);
		TAsyncClientManager clientManager = new TAsyncClientManager();

		TProtocolFactory protocolFactory = new TBinaryProtocol.Factory();
		final UserStorage.AsyncClient client = new UserStorage.AsyncClient.Factory(clientManager, protocolFactory)
										.getAsyncClient(transport);

		final UserProfile user = new UserProfile(1, "user_name", "sth... i don't know");

		System.out.println(user);
		client.store(user, new AsyncMethodCallback<UserStorage.AsyncClient.store_call>() {

			@Override
			public void onError(Exception exception) {
				System.out.println(exception.getMessage());

				countDownLatch.countDown();
			}

			@Override
			public void onComplete(store_call response) {
				// TODO Auto-generated method stub
				try {
					System.out.println("store finished.");
					step2(client, user);
				} catch (TException e) {
					e.printStackTrace();
				}
			}
		});


		countDownLatch.await();

	}

	private static void step2(UserStorage.AsyncClient client, final UserProfile user) throws TException {

		client.retrieve(user.getUid(), new AsyncMethodCallback<UserStorage.AsyncClient.retrieve_call>() {

			@Override
			public void onError(Exception exception) {
				// TODO Auto-generated method stub
				countDownLatch.countDown();
			}

			@Override
			public void onComplete(retrieve_call response) {
				try {
					UserProfile fetchUser = response.getResult();
					System.out.println(fetchUser);
					System.out.println(user.equals(fetchUser));
				} catch (TException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				countDownLatch.countDown();

			}
		});
	}



}
