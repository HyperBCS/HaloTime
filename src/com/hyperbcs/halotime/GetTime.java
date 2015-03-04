package com.hyperbcs.halotime;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class GetTime {
	public String GetTimes(String game, int mode){
		// TODO Auto-generated method stub
		int loopcount = 1;
		String url = null;
		String cleanTime = null;
        boolean retry = false;  
        while(!retry)  {
		try {
		if (mode == 1){
			url = "http://halo.bungie.net/Stats/GameStatsCampaignHalo3.aspx?gameid="+game;
		} else if (mode == 2 | mode == 3){
			url = "http://halo.bungie.net/Stats/GameStatsHalo3.aspx?gameid="+game;
		}
		Document doc = Jsoup.connect(url).get();
		Elements players1 = doc.select(".summary"); // a with href
		String time = players1.toString();
		//System.out.println(players1);
		if (mode == 2 | mode == 3){
		cleanTime = time.replaceAll("\n", "").replaceAll("<ul.*Length: ", "").replaceAll("&.*ul>", "").replaceAll(":", ",");
		} else if ( mode == 1){
			cleanTime = time.replaceAll("\n", "").replaceAll("<ul.*Time: ", "").replaceAll("</.*ul>", "").replaceAll(":", ",");
		}
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
		return cleanTime;
	}

}
