package com.example.Flightaware.controller;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
public class ApiCalls {
	
	//API Call to get leagues of the different games
	public String fprices(String p1,String p2,String p3,String d) throws IOException, InterruptedException, ParseException
	{
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("https://skyscanner50.p.rapidapi.com/api/v1/searchAirport?query=london"))
				.header("X-RapidAPI-Key", "aee42a3a21msh51665c84e6926e0p1b9244jsnccff7582332e")
				.header("X-RapidAPI-Host", "skyscanner50.p.rapidapi.com")
				.method("GET", HttpRequest.BodyPublishers.noBody())
				.build();
		HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
		System.out.println(response.body());
		return response.body();
		
	}
	public String dprices(String ctry,String curr,String p1,String p2,String p3,String d,String d2) throws IOException, InterruptedException, ParseException
	{
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("https://skyscanner50.p.rapidapi.com/api/v1/searchFlights?origin=LOND&destination=NYCA&date=%3CREQUIRED%3E&adults=1&currency=USD&countryCode=US&market=en-US"))
				.header("X-RapidAPI-Key", "aee42a3a21msh51665c84e6926e0p1b9244jsnccff7582332e")
				.header("X-RapidAPI-Host", "skyscanner50.p.rapidapi.com")
				.method("GET", HttpRequest.BodyPublishers.noBody())
				.build();
		HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
		System.out.println(response.body());
		return response.body();
		
	}
	public String rts(String ctry,String curr,String p1,String p2,String p3,String d) throws IOException, InterruptedException, ParseException
	{
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("https://skyscanner50.p.rapidapi.com/api/v1/searchFlightsMultiStops?legs=%5B%7B%22origin%22%3A%22LOND%22%2C%22destination%22%3A%22NYCA%22%2C%22date%22%3A%222023-02-07%22%7D%2C%7B%22origin%22%3A%22NYCA%22%2C%22destination%22%3A%22LAXA%22%2C%22date%22%3A%222023-02-13%22%7D%2C%7B%22origin%22%3A%22LAXA%22%2C%22destination%22%3A%22LOND%22%2C%22date%22%3A%222023-02-18%22%7D%5D&waitTime=5000&adults=1&currency=USD&countryCode=US&market=en-US"))
				.header("X-RapidAPI-Key", "aee42a3a21msh51665c84e6926e0p1b9244jsnccff7582332e")
				.header("X-RapidAPI-Host", "skyscanner50.p.rapidapi.com")
				.method("GET", HttpRequest.BodyPublishers.noBody())
				.build();
		HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
		System.out.println(response.body());
		return response.body();
		
	}
	public String curs() throws IOException, InterruptedException{
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("https://skyscanner50.p.rapidapi.com/api/v1/getFlightDetails?itineraryId=%3CREQUIRED%3E&legs=%5B%7B%22origin%22%3A%22LOND%22%2C%22destination%22%3A%22NYCA%22%2C%22date%22%3A%222023-02-07%22%7D%2C%7B%22date%22%3A%222023-02-14%22%2C%22destination%22%3A%22LOND%22%2C%22origin%22%3A%22NYCA%22%7D%5D&adults=1&currency=USD&countryCode=US&market=en-US"))
				.header("X-RapidAPI-Key", "aee42a3a21msh51665c84e6926e0p1b9244jsnccff7582332e")
				.header("X-RapidAPI-Host", "skyscanner50.p.rapidapi.com")
				.method("GET", HttpRequest.BodyPublishers.noBody())
				.build();
		HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
		System.out.println(response.body());
		return response.body();
		
	}
	public String cntrys() throws IOException, InterruptedException{
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("https://skyscanner50.p.rapidapi.com/api/v1/searchFlightEverywhere?origin=LOND&anytime=true&oneWay=false&currency=USD&countryCode=US&market=en-US"))
				.header("X-RapidAPI-Key", "aee42a3a21msh51665c84e6926e0p1b9244jsnccff7582332e")
				.header("X-RapidAPI-Host", "skyscanner50.p.rapidapi.com")
				.method("GET", HttpRequest.BodyPublishers.noBody())
				.build();
		HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
		System.out.println(response.body());
		return response.body();
		
	}
	public String placess(String curr,String code,String cntry,String locale) throws IOException, InterruptedException{
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("https://skyscanner50.p.rapidapi.com/api/v1/searchFlightEverywhereDetails?origin=LOND&CountryId=US&anytime=true&oneWay=false&currency=USD&countryCode=US&market=en-US"))
				.header("X-RapidAPI-Key", "aee42a3a21msh51665c84e6926e0p1b9244jsnccff7582332e")
				.header("X-RapidAPI-Host", "skyscanner50.p.rapidapi.com")
				.method("GET", HttpRequest.BodyPublishers.noBody())
				.build();
		HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
		System.out.println(response.body());
		return response.body();
		
	}
}
