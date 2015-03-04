package com.hyperbcs.halotime;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class PageNum {
	public int GetPages(String gamertag, int mode){
		// TODO Auto-generated method stub
		int loopcount = 1;
		String url = null;
		String cleanTime = null;
		int pages = 0;
        boolean retry = false;  
        while(!retry)  {
		try {
		if (mode == 1){
			url = "http://halo.bungie.net/stats/playercampaignstatshalo3.aspx?player="+gamertag;
		} else if (mode == 2){
			url = "http://halo.bungie.net/stats/playerstatshalo3.aspx?player="+gamertag;
		} else if (mode == 3){
			url = "http://halo.bungie.net/stats/playerstatshalo3.aspx?player="+gamertag+"&cus=1";
		}
		String gameMode = "";
		if (mode == 1) gameMode = "Campaign";
		if (mode == 2) gameMode = "Matchmaking";
		if (mode == 3) gameMode = "Custom Games";
		System.out.println("Starting Game Mode: "+gameMode);
		Document doc = Jsoup.connect(url).get();
		Elements players1 = doc.select(".rgWrap"); // a with href
		String time = players1.toString();
		//System.out.println(players1);
		cleanTime = time.replaceAll("\n", "").replaceAll("<div.*> items in  <strong>", "").replaceAll("</.*v>", "");
		if (cleanTime.equals("")) return 0;
		pages = Integer.parseInt(cleanTime);
		retry = true;
		//System.out.println("-------------------------------------------------");
		//System.out.println(cleanTime);
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
		return pages;
	}

}
