package com.cjf.practice.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.thrift.TException;

import com.cjf.practice.UserProfile;
import com.cjf.practice.UserStorage.Iface;

public class UserStorageServiceImpl implements Iface{

	private static Map<Integer, UserProfile> userMap = new HashMap<Integer, UserProfile>();

	@Override
	public void store(UserProfile user) throws TException {
		userMap.put(user.getUid(), user);
	}

	@Override
	public UserProfile retrieve(int uid) throws TException {
		return userMap.get(uid);
//		return null;
	}

}
