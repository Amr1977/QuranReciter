package main;

import logging.Logging;
import logging.TextFiles;
import static main.ReciterModel.speech;

public class Reader implements Runnable{

	@Override
	public void run() {
		try {
			ReciterModel.simpleReader();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static void read(){
		Logging.log("Besmellah !",(speech? 1:0));
		Logging.log("Verses Downloaded from  www.EveryAyah.com",(speech? 1:0));
		Logging.log("Build: "+TextFiles.getBuildDate());
		Thread t=new Thread(new Reader(), "Quran Reader Thread");
		t.start();
	}
	public static void test(String[] args){
		
		read();
		ReciterModel.shell();
	}
	
}
