package com.spring.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.dto.HackerNew;
import com.spring.dto.HitsWrapper;
import com.spring.dto.JSONHackNew;
import com.spring.repository.HackerNewsImplement;
import com.spring.util.Util;


@Service
public class ReadAPI {

	private static final String url = "http://hn.algolia.com/api/v1/search_by_date?query=nodejs";

	@Autowired
	@Qualifier("HackerNewsImplement")
	private HackerNewsImplement hackerNewsImplement;
	
	@Scheduled(fixedRate = 60 * 60 * 1000)
	public void read() throws IOException {

		// Trae informaicón de API
		HitsWrapper hitsWrapper = readHackerNews();

		// Mapea información en objeto jpa
		List<HackerNew> listaHackerNews = mapsHacherNews(hitsWrapper);

		// Guarda objetos en BD
		guardarHackerNews(listaHackerNews);

	}

	private void guardarHackerNews(List<HackerNew> listaHackerNews) {
		// TODO Auto-generated method stub

		try {
			for( HackerNew hackerNew : listaHackerNews ) {
	
				// Si ya existe el registro no vuelve a rearlo
				if ( !hackerNewsImplement.existsById(hackerNew.getId()) )
					hackerNewsImplement.Create(hackerNew);
	
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}


	}

	private List<HackerNew> mapsHacherNews(HitsWrapper hitsWrapper) {
		// TODO Auto-generated method stub
		List<JSONHackNew> JSONHackNew = hitsWrapper.getJSONHackNew();
		List<HackerNew> listaHackerNews = new ArrayList<HackerNew>();
		Util util = new Util();

		ZonedDateTime zoneDateTime;
		try {
		
			for(JSONHackNew obj : JSONHackNew){
				HackerNew hackerNew = new HackerNew();
				zoneDateTime = ZonedDateTime.parse(obj.getCreated_at());

				hackerNew.setId(util.hashCode(zoneDateTime.toLocalDateTime().toString(), obj.getAuthor()) );
				hackerNew.setAuthor(obj.getAuthor());
				hackerNew.setCreatedAt( zoneDateTime.toLocalDateTime() );
				hackerNew.setStoryTitle(obj.getStory_title());

				if (obj.getStory_title() == null )
					hackerNew.setStoryTitle(obj.getTitle());

				hackerNew.setStoryUrl(obj.getStory_url());
				if (obj.getStory_url() == null)
					hackerNew.setStoryUrl(obj.getUrl());

				listaHackerNews.add(hackerNew);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return listaHackerNews;
	}

	public HitsWrapper readHackerNews() {

		HitsWrapper hitsWrapper = new HitsWrapper();

	    try {

			URL obj = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
			conn.setReadTimeout(5000);
			conn.addRequestProperty("Accept-Language", "en-US,en;q=0.8");
			conn.addRequestProperty("User-Agent", "Mozilla");
			conn.addRequestProperty("Referer", "google.com");
	
			System.out.println("Request URL ... " + url);
	
			boolean redirect = false;
	
			// normally, 3xx is redirect
			int status = conn.getResponseCode();
			if (status != HttpURLConnection.HTTP_OK) {
				if (status == HttpURLConnection.HTTP_MOVED_TEMP
					|| status == HttpURLConnection.HTTP_MOVED_PERM
						|| status == HttpURLConnection.HTTP_SEE_OTHER)
				redirect = true;
			}
	
			System.out.println("Response Code ... " + status);
	
			if (redirect) {
	
				// get redirect url from "location" header field
				String newUrl = conn.getHeaderField("Location");
	
				// get the cookie if need, for login
				String cookies = conn.getHeaderField("Set-Cookie");
	
				// open the new connnection again
				conn = (HttpURLConnection) new URL(newUrl).openConnection();
				conn.setRequestProperty("Cookie", cookies);
				conn.addRequestProperty("Accept-Language", "en-US,en;q=0.8");
				conn.addRequestProperty("User-Agent", "Mozilla");
				conn.addRequestProperty("Referer", "google.com");
										
				System.out.println("Redirect to URL : " + newUrl);
	
			}
	
			BufferedReader in = new BufferedReader( new InputStreamReader(conn.getInputStream()) );
			String inputLine;
			StringBuffer sfHakerNews = new StringBuffer();
	
			while ((inputLine = in.readLine()) != null) {
				sfHakerNews.append(inputLine);
			}
			in.close();
	
			System.out.println("URL Content... \n" + sfHakerNews.toString());
	
			ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	
			hitsWrapper = mapper.readValue(sfHakerNews.toString(), HitsWrapper.class);
			

	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	    
	    return hitsWrapper;

	  }
	

}
























