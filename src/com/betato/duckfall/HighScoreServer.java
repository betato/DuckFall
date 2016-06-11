package com.betato.duckfall;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;

public class HighScoreServer {
	
	final String SUBMIT_URL = "http://plasticbagscores.esy.es/submit_score.php";
	final String GET_URL = "http://plasticbagscores.esy.es/get_scores.php";
	
	public HighScoreServer() {

	}
	
	public void submitScore(String name, int score) {
		try {
			URL requestUrl = new URL(SUBMIT_URL);
			HttpURLConnection conn = (HttpURLConnection) requestUrl.openConnection();
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			//submit parameters
			String queryString = String.format("name=%s&hash=%s&score=%s", URLEncoder.encode(name, "UTF-8"), hashScore(score), String.valueOf(score));
			conn.getOutputStream().write(queryString.getBytes());
			conn.getOutputStream().flush();
			conn.getInputStream().read();
		} catch (IOException e) {
			System.out.println("Couldn't submit score: ");
			e.printStackTrace();
		}
	}
	
	public ArrayList<SimpleEntry<String, Integer>> getScores(int count) {
		ArrayList<SimpleEntry<String, Integer>> scores = new ArrayList<>();
		try {
			URL requestUrl = new URL(GET_URL + "?count=" + String.valueOf(count));
			HttpURLConnection conn = (HttpURLConnection) requestUrl.openConnection();
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line = null;
			while ((line = reader.readLine()) != null) {
				String[] dataArr = line.split(":");
				scores.add(new SimpleEntry<String, Integer>(dataArr[0], Integer.parseInt(dataArr[1])));
			}
		} catch (IOException e) {
			System.out.println("Couldn't get scores: ");
			e.printStackTrace();
		}
		return scores;
	}
	
	private String hashScore(int score) {
		MessageDigest digest = null;
		try {
			digest = MessageDigest.getInstance("SHA1");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		digest.update(String.valueOf(score).getBytes());
		byte[] digestBytes = digest.digest();
		return javax.xml.bind.DatatypeConverter.printHexBinary(digestBytes).toLowerCase();
	}
	

}
