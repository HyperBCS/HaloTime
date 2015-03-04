package com.hyperbcs.halotime;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.InputMismatchException;
import java.util.Scanner;

public class HaloTime {

	public static void main(String[] args) {
		try {
		final GetTime time = new GetTime();
		final GetGames list = new GetGames();
		final PageNum pages = new PageNum();
		String GamerTag = "HyperBCS";
		System.out.print("Enter Your Gamertag:");
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		GamerTag = reader.readLine();
		String filename = GamerTag+".csv";
		Scanner reader2 = new Scanner(System.in);
		int mode = 3;
		while(true){
		System.out.println("1: Campaign");
		System.out.println("2: Matchmaking");
		System.out.println("3: Custom Games");
		System.out.println("4: Combinationsd");
		System.out.print("Enter the game mode to gather data from: ");
		mode=reader2.nextInt();
		if(mode >4 | mode <= 0){
			System.out.println("Please choose a correct number.");
		} else {
			String yesno;
			System.out.print("Do you want to use the default filename? (default \""+filename+"\"): ");
			yesno=reader2.next();
			if (!yesno.equals("yes") && !yesno.equals("ye") && !yesno.equals("y") ){
				System.out.println("Please specify the filename: ");
				filename=reader2.next();
			}
			File f = new File(filename); 
			if (f.exists()){
				System.out.println("The filename specified already exists. Do you wish to override the existing file(y/N):");
				yesno=reader2.next();
				if (yesno.equals("y")){
					break;
				} else {
					System.out.println("Exiting...");
					mode = 0;
					break;
				}
		}
			reader2.close();
			break;
		}
		}
		reader2.close();
		String times = "Hours,Minutes,Seconds,GameID,Mode\n";
		int length = 1;
		if(mode <4 && mode > 0){
			int gt = 0;
			String gameMode = "Campaign";
			int page = pages.GetPages(GamerTag, mode);
			if (page == 0) {
				System.out.println("Gamertag not found");
				gt = 1;
			}
			if (gt == 0){
		for (int i=1; i<=page;i++){
		String[] test2 = list.GetList(GamerTag, i, mode);
		if (mode == 1) gameMode = "Campaign";
		if (mode == 2) gameMode = "Matchmaking";
		if (mode == 3) gameMode = "Custom Games";
		System.out.println(gameMode+" Page: "+i+"/"+page);
		for (String j: test2){
			//System.out.println(time.GetTimes(j));
			String time2 = time.GetTimes(j, mode);
			if (!time2.equals("")){
			times = times+time2+","+j+","+gameMode+"\n";
			length++;
			}
		}
		}
			}
		}
		if(mode == 4){
			mode = 1;
		for (int h=1; h<=3;h++){
			int gt = 0;
			String gameMode = "Campaign";
			int page = pages.GetPages(GamerTag, mode);
			if (page == 0) {
				System.out.println("Gamertag not found");
				gt = 1;
			}
			if (gt == 0){
		for (int i=1; i<=page;i++){
		String[] test2 = list.GetList(GamerTag, i, mode);
		if (mode == 1) gameMode = "Campaign";
		if (mode == 2) gameMode = "Matchmaking";
		if (mode == 3) gameMode = "Custom Games";
		System.out.println(gameMode+" Page: "+i+"/"+page);
		for (String j: test2){
			//System.out.println(time.GetTimes(j));
			String time2 = time.GetTimes(j, mode);
			if (!time2.equals("")){
			times = times+time2+","+j+","+gameMode+"\n";
			length++;
			}
		}
		}
		mode++;
		}
		}
		}
		//System.out.println(times);
		if (mode !=0){
		String sumEq = "Total Time,=SUM(A2:A"+length+")"+"+SUM(B2:B"+length+")/60"+"+SUM(C2:C"+length+")/3600"+",Hours";
    	  FileWriter fstream = null;
    	    fstream = new FileWriter(filename);
    	        BufferedWriter out = new BufferedWriter(fstream);
    	        length = length-1;
    	        System.out.println("Total Games: "+length);
    	    out.write(times);
    	    out.write(sumEq);
    	    //Close the output stream
    	    out.close();
		}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    catch (InputMismatchException e1) {
				// TODO Auto-generated catch block
				System.out.println("Incorrect game mode... Exiting...");
			}
		//String test = time.GetTimes();
		//System.out.println(test);

}
}