package test;

import chat_bot.Api;
import java.util.*;

public class TestingApi implements Api {
	
	private String[] replics;
	public List<String> answers;
	private int replicaIndex = 0;
	
	public TestingApi(String[] replics) {
		this.replics = replics;
		this.answers = new ArrayList<String>();
	}

	@Override
	public String in() {
		if (replicaIndex >= replics.length)
			throw new ArrayIndexOutOfBoundsException("no more replics in array");
		return replics[replicaIndex++];
	}

	@Override
	public void out(String massage) {
		answers.add(massage);
	}

}
