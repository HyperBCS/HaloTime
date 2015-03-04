package com.hyperbcs.halotime;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class GetGames   {
	public String[] GetList(String gamertag, int page, int mode){
		int loopcount = 1;
		String url = null;
		String[] lines = null;
        boolean retry = false;  
        while(!retry)  {
		try {
		url = "http://halo.bungie.net/stats/playerstatshalo3.aspx?player="+gamertag+"&cus=1&ctl00_mainContent_bnetpgl_recentgamesChangePage="+page;
		if (mode == 1){
			url = "http://halo.bungie.net/stats/playercampaignstatshalo3.aspx?player="+gamertag+"&ctl00_mainContent_bnetcppgl_recentgamesChangePage="+page;
		} else if (mode == 2){
			url = "http://halo.bungie.net/stats/playerstatshalo3.aspx?player="+gamertag+"&ctl00_mainContent_bnetpgl_recentgamesChangePage="+page;
		} else if (mode == 3){
			url = "http://halo.bungie.net/stats/playerstatshalo3.aspx?player="+gamertag+"&cus=1&ctl00_mainContent_bnetpgl_recentgamesChangePage="+page;
		}
		Document doc = Jsoup.connect(url).get();
		Elements players1 = doc.select(".rgrow a"); // a with href
		Elements players2 = doc.select(".rgAltrow a"); // a with href
		String time = players1.toString()+"\n"+players2.toString();
		//System.out.println(time);
		//String cleanTime = time;
		String cleanTime = time.replaceAll("<a.*id=", "").replaceAll("&.*a>", "").replaceAll("\"ct.*a>", "").replaceAll("\n\n", "\n").trim();
		//System.out.println("-------------------------------------------------");
		//System.out.println(cleanTime);
		lines = cleanTime.split("[\\s]+");
		//System.out.println(lines);
		retry = true;
	} catch (IOException e) {
		// TODO Auto-generated catch block
		System.out.println("An error has occured. Retrying..."+loopcount);
		System.out.println(url);
		loopcount++;
		if(loopcount > 10) retry = true;
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		e.printStackTrace();
	}
        }
		return lines;
		
	}
}


