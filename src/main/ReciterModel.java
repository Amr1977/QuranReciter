package main;

import common.Downloads;
import common.Sound;
import static common.Sound.getMediaPlayer;
import java.awt.Image;
import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.media.MediaPlayer;




import org.apache.commons.io.FileUtils;




import test.BaseTest;
//import main.Reciter.Downloader;
//import common.Items;
import logging.Logging;
import logging.TextFiles;
import logging.FreeTTS;

//TODO mode 4 add mode each reciter reads complete sura,others read same sura afterwards
//TODO mode 5 add mode each reciter reads complete sura,others read next suras afterwards


public class ReciterModel {
	public static boolean RESTORED_STATE=false;
	public static int NORMAL_MODE=0;
	public static int RABANI_MODE=1;
	public static int FULL_MODE=2;
        public static boolean raabbaniSura=false;
        public static boolean rabbaniReciter=false;
        public static boolean rabbaniDelay=false;
        public static boolean speech=false;
	public static String baseFolder;

	static {
		baseFolder = TextFiles.getStartLocation();
	}

	public static int[] ayatCount = { 
		0, 
		7,//1
		286,//2
		200,// 3
		176,//4
		120,//5
		165,// 6
		206,//7
		75,//8
		129,// 9
		109,//10
		123,//11
		111,// 12
		43,//13
		52,//14
		99,// 15
		128,//16
		111,//17
		110,// 18
		98,//19
		135,//20
		112,// 21
		78,//22
		118,//23
		64,// 24
		77, //25
		227,//26
		93,//27
		88,//28
		69,//29
		60,//30
		34,//31
		30,//32
		73,//33
		54,//34
		45,//35
		83,//36
		182,//37
		88,//38
		75,//39
		85,//40
		54,//41
		53,
		89,
		59,
		37,
		35,
		38,
		29,
		18,
		45,//50
		60,// OK
		49,
		62,
		55,
		78,
		96,
		29,
		22,
		24,
		13,// 60
		14, 
		11,
		11,
		18,
		12,
		12,// OKKKKKKKKKKKKKKKKKKKKKKK
		30,
		52,
		52,
		44,//70
		28, 
		28, 
		20, 
		56, 
		40, 
		31,
		50,
		40,// OK
		46,
		42, 
		29,//80 
		19, 
		36, 
		25,
		22, 
		17,
		19,
		26,
		30,
		20,//90
		15,
		21,
		11,
		8,
		8,
		19, 
		5,
		8,
		8, 
		11,//100
		11, 
		8,
		3,
		9,
		5,
		4,
		7,
		3,
		6,
		3,//110
		5,//
		4, 
		5,
		6 //114 
	};
	public static String[] mashayekh;

	static {
		mashayekh = arrayListToArray(TextFiles.load(baseFolder + "readers.txt"));
		//handle if file not found
		if (mashayekh==null || mashayekh.length==0){
			ArrayList<String> defaultRecitersArrayList=new ArrayList<String>();
			defaultRecitersArrayList.add("Abdul_Basit_Mujawwad_128kbps");
			mashayekh= arrayListToArray(defaultRecitersArrayList);
		}

	}

	public static String[] arrayListToArray(ArrayList<String> al){
		if (al==null){
			return null;
		}
		String[] result= new String[al.size()];
		for(int i=0; i<al.size();i++){
			result[i]=al.get(i);
		}
		return result;
	}

	public static final String[] Sura_Name={
		"",
		"Al-Fatiha",
		"Al-Baqara",
		"Al Imran",
		"An-Nisa",
		"Al-Ma'ida",
		"Al-An'am",
		"Al-A'raf",
		"Al-Anfal",
		"At-Tawba",
		"Yunus",
		"Hud",
		"Yusuf",
		"Ar-Ra'd",
		"Ibrahim",
		"Al-Hijr",
		"An-Nahl",
		"Al-Isra",
		"Al-Kahf",
		"Maryam",
		"Ta-Ha",
		"Al-Anbiya",
		"Al-Hajj",
		"Al-Mu'minoon",
		"An-Nur",
		"Al-Furqan",
		"Ash-Shu'ara",
		"An-Naml",
		"Al-Qasas",
		"Al-Ankabut",
		"Ar-Rum",
		"Luqman",
		"As-Sajda",
		"Al-Ahzab",
		"Saba",
		"Fatir",
		"Ya Sin",
		"As-Saaffat",
		"Sad",
		"Az-Zumar",
		"Ghafir",
		"Fussilat",
		"Ash-Shura",
		"Az-Zukhruf",
		"Ad-Dukhan",
		"Al-Jathiya",
		"Al-Ahqaf",
		"Muhammad",
		"Al-Fath",
		"Al-Hujurat",
		"Qaf",
		"Adh-Dhariyat",
		"At-Tur",
		"An-Najm",
		"Al-Qamar",
		"Ar-Rahman",
		"Al-Waqi'a",
		"Al-Hadid",
		"Al-Mujadila",
		"Al-Hashr",
		"Al-Mumtahina",
		"As-Saff",
		"Al-Jumua",
		"Al-Munafiqun",
		"At-Taghabun",
		"At-Talaq",
		"At-Tahrim",
		"Al-Mulk",
		"Al-Qalam",
		"Al-Haaqqa",
		"Al-Maarij",
		"Nuh",
		"Al-Jinn",
		"Al-Muzzammil",
		"Al-Muddathir",
		"Al-Qiyama",
		"Al-Insan",
		"Al-Mursalat",
		"An-Naba",
		"An-Naziat",
		"Abasa",
		"At-Takwir",
		"Al-Infitar",
		"Al-Mutaffifin",
		"Al-Inshiqaq",
		"Al-Burooj",
		"At-Tariq",
		"Al-Ala",
		"Al-Ghashiya",
		"Al-Fajr",
		"Al-Balad",
		"Ash-Shams",
		"Al-Lail",
		"Ad-Dhuha",
		"Al-Inshirah",
		"At-Tin",
		"Al-Alaq",
		"Al-Qadr",
		"Al-Bayyina",
		"Az-Zalzala",
		"Al-Adiyat",
		"Al-Qaria",
		"At-Takathur",
		"Al-Asr",
		"Al-Humaza",
		"Al-Fil",
		"Quraysh",
		"Al-Ma'un",
		"Al-Kawthar",
		"Al-Kafirun",
		"An-Nasr",
		"Al-Masadd",
		"Al-Ikhlas",
		"Al-Falaq",
		"Al-Nas",

	};


        public static Integer downloadQueueMaxSize = 3;
	public static boolean isDownloading=false;
	public static boolean SURA_REPEAT_FOREVER=false;
	public TreeMap<String, String> config = new TreeMap<String, String>(String.CASE_INSENSITIVE_ORDER);
	public static boolean DOWNLOAD_ONLY=false;
	public static boolean PAUSE=false;
	public static boolean EXIT=false;
	//public static boolean MODE_CHANGE=false;
	public static boolean suraChanged=false;
	public static boolean reciterChanged=false;
	public static Integer reciter=0;
	public static String reciterName;
	public static boolean showImages=false;

	public static Integer sura=1;
	public static boolean SURA_CHANGE=false;

	public static Integer ayaStart=1;
	public static Integer ayaEnd=1000;
	public static Integer currentAya=1;
	public static boolean AYA_CHANGE=false;
	//Integer ayaRepeat=1;
	public static Integer rangeRepeat=1;

	public static Integer ayaWait=1000;
	public static Integer afterAyaWait=1000;

	public static String selectedReciter;
	public static Integer ayaRepeatCount=1;
	public static Integer groupRepeatCount=1;
	public static Integer interAyaWait=0;
	public static boolean AYA_REPEAT_FOREVER=false;
	public static boolean GOTO_NEXT_AYA=false;
	public static int currentMode=0;// 0 for normal , 1 for rabani

	//Items items;
	public SystemTray tray = SystemTray.getSystemTray();
	final PopupMenu popup = new PopupMenu();

	public TrayIcon trayIcon;
	Menu voices;
	Menu suras;
	Menu ayaRepeats;
	Menu ayaWaitMenu;
	MenuItem aboutItem;
	MenuItem pauseItem ;
	Image trayIconImage;


	//static Downloader  downloader=null;



	public static void saveState() throws IOException{
		ArrayList<String> lines=new ArrayList<>();
		//sheikh, sura, aya
		lines.add("reciter="+reciter.toString());
		lines.add("sura="+sura.toString());
		lines.add("currentAya="+currentAya.toString());
		lines.add("ayaStart="+ayaStart.toString());
		lines.add("ayaEnd="+ayaEnd.toString());
		lines.add("ayaRepeatCount="+ayaRepeatCount.toString());
		lines.add("ayaWait="+ayaWait.toString());
		lines.add("groupRepeatCount="+groupRepeatCount.toString());
		lines.add("afterAyaWait="+afterAyaWait.toString());
		lines.add("mode="+currentMode);
		lines.add("images="+(showImages?"1":"0"));
		lines.add("download_only="+(DOWNLOAD_ONLY?"1":"0"));
		lines.add("aya_repeat_forever="+(AYA_REPEAT_FOREVER?"1":"0"));
		lines.add("sura_repeat_forever="+(SURA_REPEAT_FOREVER?"1":"0"));
                lines.add("random_sura="+(raabbaniSura?"1":"0"));
                lines.add("random_reciter="+(rabbaniReciter?"1":"0"));
                lines.add("random_delay="+(rabbaniDelay?"1":"0"));
                lines.add("speech="+(speech?"1":"0"));
		//DOWNLOAD_ONLY
		//REPEAT_FOREVER

		TextFiles.save(lines, baseFolder+"reciter-state.txt");
		Logging.log("states saved.");
	}

	public static void restoreState(int state)throws Exception{
		if (state==0){//fatiha
			reciterName=mashayekh[0];	reciter=0;
			sura=1;
			ayaStart=1;
			currentAya=1;
			ayaEnd=7;
			ayaRepeatCount=1;
			ayaWait=0;
			groupRepeatCount=1;
			afterAyaWait=0;
			currentMode=0;
		}else{
			if(new File(baseFolder+"reciter-state.txt").exists()){
				ArrayList<String> lines=TextFiles.load(baseFolder+"reciter-state.txt");
				for(String s:lines){
					String[] terms=s.split("=");
					if (terms.length!=2){//if invalid line skip it
						Logging.log("skipping line ["+s+"] in "+baseFolder+"reciter-state.txt");
						continue;
					}
					switch (terms[0]){
                                            case "speech":
                                                speech=("1".equals(terms[1]));
                                                break;
					case "reciter": 
						reciterName=mashayekh[(reciter=Integer.valueOf(terms[1]))];
						break;
					case "sura": 
						sura=Integer.valueOf(terms[1]);
						break;
					case "currentAya": 
						currentAya=Integer.valueOf(terms[1]);
						break;

					case "ayaStart": 
						ayaStart=Integer.valueOf(terms[1]);
						break;
					case "ayaEnd": 
						ayaEnd=Integer.valueOf(terms[1]);
						break;
					case "ayaRepeatCount": 
						ayaRepeatCount=Integer.valueOf(terms[1]);
						break;
					case "ayaWait": 
						ayaWait=Integer.valueOf(terms[1]);
						break;
					case "groupRepeatCount": 
						groupRepeatCount=Integer.valueOf(terms[1]);
						break;
					case "afterAyaWait": 
						afterAyaWait=Integer.valueOf(terms[1]);
						break;
					case "mode":
						try{
							currentMode=Integer.valueOf(terms[1]);
						}catch(NumberFormatException e){
							currentMode=0;
						}
						break;
					case "images":
						showImages=("1".equals(terms[1]));
						break;
					case "aya_repeat_forever":
						AYA_REPEAT_FOREVER=("1".equals(terms[1]));
						break;
					case "download_only":
						DOWNLOAD_ONLY=("1".equals(terms[1]));
						break;
					case "sura_repeat_forever":
						SURA_REPEAT_FOREVER=("1".equals(terms[1]));
                                                break;
                                        case "random_sura":
                                            raabbaniSura=("1".equals(terms[1]));
                                            break;
                                        case "random_reciter":
                                            rabbaniReciter=("1".equals(terms[1]));
                                            break;
                                        case "random_delay":
                                            rabbaniDelay=("1".equals(terms[1]));
                                            break;
					default : Logging.log("can't parse ["+s+"]");
					}
				}

			} else{
				reciterName=mashayekh[0];	reciter=0;
				sura=1;
				ayaStart=1;
				currentAya=1;
				ayaEnd=7;
				ayaRepeatCount=1;
				ayaWait=1000;
				groupRepeatCount=1;
				afterAyaWait=0;
				currentMode=0;
				AYA_REPEAT_FOREVER=false;
				DOWNLOAD_ONLY=false;
                                rabbaniReciter=false;
                                raabbaniSura=false;
                                rabbaniDelay=false;
                                speech=false;


			}
		}
		RESTORED_STATE=true;
	}

	static String leadingZeros(int aNumber,int width){
		String result=Integer.toString(aNumber);
		while(result.length()<width){
			result="0"+result;
		}
		return result;

	}

	public void downloadSura(int sheikhID,int sura){
		downloadAyat(sheikhID, sura,1,ayatCount[sura]);
	}
	public void downloadSura(String sheikhID,int sura){
		int number=0;
		for(int i=0;i<mashayekh.length;i++){
			if (mashayekh[i]==sheikhID){
				number=i;
				break;
			}
		}
		downloadAyat(number, sura,1,ayatCount[sura]);
	}

	public static void downloadAyat(int sheikhID,int sura, int startAya, int endAya){
            Logging.log("downloadAyat: sheik: "+sheikhID+" sura["+sura+"] startAya["+startAya+"] edAya["+endAya+"]");
		if (endAya>ayatCount[sura]){
			endAya=ayatCount[sura];
		}
		if (startAya>ayatCount[sura]){
			startAya=1;
		}
		for (int aya=startAya;aya<=endAya;aya++){
			String foldername=baseFolder+"mp3"+File.separator+mashayekh[sheikhID]+File.separator+leadingZeros(sura,3);
			String filename=baseFolder+"mp3"+File.separator+mashayekh[sheikhID]+File.separator+leadingZeros(sura,3)+File.separator+leadingZeros(sura,3)+leadingZeros(aya,3)+".mp3";
			String fileUrl="http://www.everyayah.com/data/"+mashayekh[sheikhID]+"/"+leadingZeros(sura,3)+leadingZeros(aya,3)+".mp3";
			try{
				new File(foldername).mkdirs();
			}catch(Exception e){
				Logging.log("Can't create folder ["+foldername+"]");
				Logging.log(e);

			}

			try {
				Downloads.add(fileUrl,filename);
                                
			} catch (Exception e) {
				// TODO what if Internet not available

				File f=new File(filename);
				if (f.isFile()){
					f.deleteOnExit();
				}
				Logging.log(e);
			}

		}
	}
	/**
	 * Tries to download a file from the url (if file not already existing) and save it to filePath. 
	 * @param url to download 
	 * @param filePath full destination file name to save.
	 * @throws Exception 
	 */
	public static void downloadFileZ(String url, String filePath) throws Exception{
		File myFile=new File(filePath);
		if (!myFile.exists()){
			try{
				isDownloading=true;
				Logging.log("Download start: "+url);
				FileUtils.copyURLToFile(new URL(url),myFile );
				Logging.log("Download ended: "+url);
				isDownloading=false;
			} catch(Exception e){
				Logging.log("Error downloading/copying file: "+url);
			}
			isDownloading=false;
		}else{
                    Logging.log("downloadFile: File already exists ["+filePath+"].");
                }
	}




	

	public static void playMp3(String fileName) throws Exception{
		
		Sound.playMp3(fileName);
	}

        
        public static int getSura(int currentSura, int CurrentAya, int advance){
            if (advance==0){
                return currentSura;
            }else{
                
            }
            return 0;
        }
        
        public static int getAya(int currentSura, int CurrentAya, int advance){
            
            return 0;
        }
	
	public static  void readAya(int sheik, int sura, int aya){
            //download ahead entries insertion
            //TODO fix this
            //TODO handle immediate shutdown (create a folder with a separate 
            //text file for each file being downloaded and erase file after 
            //download complete, name files randomly)
            //maybe we could use sqlite db
            Logging.log("readAya: sheik:["+sheik+"], sura: ["+sura+"], aya:["+aya+"]");
            for(int i=0;i<=downloadQueueMaxSize;i++){
                if ((aya+i) <= ayatCount[sura]){
                    downloadAyat(sheik,sura,aya+i,aya+i);
                }else{
                    int newAya=(aya+i)-ayatCount[sura];
                    //TODO what if next sura is shorter than that
                    int newSura=(sura+1) <= 114 ? (sura+1) : 1 ;
                    downloadAyat(sheik,newSura,newAya,newAya);
                }
                    
                
            }
		
		if (!DOWNLOAD_ONLY){
			try {
                            String fileName=baseFolder+"mp3"+File.separator+mashayekh[sheik]+File.separator+leadingZeros(sura,3)+File.separator+leadingZeros(sura,3)+leadingZeros(aya,3)+".mp3";
                            Logging.log("Waiting for file to Download: "+fileName);
                            while(Downloads.isInQueue(fileName)){
                                Thread.sleep(10);
                            }
                            Logging.log("Waiting completed: "+fileName);
                            playMp3(fileName);
                            saveState();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				Logging.log(e);
			}
		}
	}

	public static void recordStackTrace(Exception e){
		for(StackTraceElement s: e.getStackTrace()){
			Logging.log(s.toString());
		}
	}

	public static void simpleReader() throws Exception{
		Runtime.getRuntime().addShutdownHook(new Thread(){
			@Override
			public void run(){
				try {
					Logging.log("ShutdownHook: saving state...",1);
					ReciterModel.saveState();
				} catch (IOException e) {
					recordStackTrace( e);
					Logging.log(e);
				}
			}
		});

		new File(baseFolder+"log").mkdirs();
		Logging.setLogFile(baseFolder+"log"+File.separator+Logging.getTimeStamp()+".txt");
		Logging.log("=========================================================================");
		Logging.log("Besmellah !",1);
		Logging.log("MP3s Downloaded from www.EveryAyah.com",1);
		Logging.log(" ");
		Random r=new Random();
		Random rWait=new Random();
		Random rReciter=new Random();
		Random rSura=new Random();
		Random rRepeat=new Random();

		restoreState(1);
		boolean justStarted=true;
		int ayaCount=0;
		while (true){
			if (currentMode==0){
				Logging.log("Current Mode: Normal.",1);
			} else if (currentMode==1){
				Logging.log("Current Mode: Rabani.",1);
			}else if (currentMode==2){
				Logging.log("Current Mode: Full.",1);
			}else if (currentMode==3){
				Logging.log("Current Mode: Alternating readers.",1);
			}
			Logging.log("sura: "+Sura_Name[sura]);
			//	repeat entire sura
			for(int repeatSura=1; (repeatSura<=groupRepeatCount)||(SURA_REPEAT_FOREVER);repeatSura++){

				Logging.log("Sura repeat number "+repeatSura+" of "+groupRepeatCount,1);

				//add basmalla

				if (SURA_CHANGE){
					break;
				}
				if ((sura!=9)&&(sura!=1)){
					//ayaLabel.setIcon(getAyaImage(1, 1));
					readAya(reciter, 1, 1);
				}
				//ayaEnd=ayatCount[sura];
				Logging.log("Sura number "+sura+" "+Sura_Name[sura],1);
				for (int aya=ayaStart; aya<=ayaEnd;aya++){
					if (justStarted){
						aya=currentAya;
						justStarted=false;
					}else{
						if (AYA_CHANGE){
							AYA_CHANGE=false;
							if (currentAya<=ayatCount[sura]){
								aya=currentAya;
							}
						}else{
							currentAya=aya;
						}
					}
					Logging.log("Aya number "+currentAya,1);
					if (SURA_CHANGE){
						ayaStart=1;
						ayaEnd=ayatCount[sura];
						break;
					}
					if (currentMode==1){
						ayaRepeatCount=rRepeat.nextInt(7)+1;
					}else if(currentMode==2){
						ayaRepeatCount=mashayekh.length;
					}
					if (showImages){
						try {
							BaseTest.executer("cmd", "/c start /max "+baseFolder+"images"+File.separator+sura+"_"+currentAya+".png");
						} catch (IOException e) {
							Logging.log(e);
						}
					}
					for(int k=1;((k<=ayaRepeatCount)||(AYA_REPEAT_FOREVER))&&(!AYA_CHANGE);k++){
						while (PAUSE&&(!EXIT)){
							Thread.sleep(1000);
                                                        if (SURA_CHANGE){
                                                            break;
                                                        }
							ReciterWindow.refreshState();
							
						}
                                                if (SURA_CHANGE){
                                                    break;
                                                }
						if (EXIT){
							System.exit(0);
						}
						if(currentMode==1){
							ayaWait=rWait.nextInt(40000)+1;
						}
						//randomAyaWait=((currentMode==0)? ayaWait : (rWait.nextInt(40000)+1));
						if (!DOWNLOAD_ONLY){
							Logging.log("wait: ["+ayaWait+"] ms.");
							Thread.sleep(ayaWait);
						}
						//TODO fix first reader
						if (currentMode==1){
							reciter=rReciter.nextInt(mashayekh.length-1)+1;
						}else if(currentMode==2){
							reciter=((k-1) % mashayekh.length);
						} else if(currentMode==3){
							reciter=ayaCount % mashayekh.length;
						}
						//int randomReader=( ((currentMode==0)) ? reciter : (rReciter.nextInt(mashayekh.length-1)+1));

						Logging.log("reciter: #["+reciter+"] "+mashayekh[reciter ]+", aya repeat : "+k+"/"+ayaRepeatCount);
						ReciterWindow.refreshState();

						if(AYA_CHANGE){
							AYA_CHANGE=false;
							break;
						}

						if (SURA_CHANGE){
							ayaStart=1;
							ayaEnd=ayatCount[sura];
							break;
						}
						ReciterWindow.refreshState();
						try{
							readAya(reciter, sura, aya);
							ayaCount++;
						}catch(Exception e){
							Logging.log("Error reading reciter="+reciter+", sura="+sura+", aya="+aya);
						}
						Logging.log("");
						ReciterWindow.refreshState();
					}
				}
			}
			if (!SURA_CHANGE){
				if (((currentMode==0)||(currentMode==2)||(currentMode==3))&&(!raabbaniSura)){
					sura+=1;
					if (sura>114){
						sura=1;
					}
				}else{
					sura=rSura.nextInt(114)+1;
				}
                                if (rabbaniReciter){
                                    reciter=rReciter.nextInt(mashayekh.length-1)+1;
                                }
				ayaStart=1;
				ayaEnd=ayatCount[sura];
				Logging.log("Going to next sura: "+Sura_Name[sura],1);
                                
                                
			}else{
				SURA_CHANGE=false;
			}
                        
		}
	}

	public static void execute(String command){
		String[] terms=command.split("=");
		if(terms.length==1){
			terms=command.split(" ");
		}

		switch(terms[0].toLowerCase()){
		case "help":
		case "/?":
		case "?":
			Logging.log("Command list:",1);
			Logging.logLines(TextFiles.getStartLocation()+"/help.txt");
			break;

                case "random":
                    try{
                        switch(terms[1]){
                            case "reciter":
                            case "reader":
                            case "r":
                            
                                break;
                            case "sure":
                            case "s":
                                raabbaniSura=!raabbaniSura;
                                Logging.log("randomSura: "+(raabbaniSura?"ON":"OFF"),1);
                                break;
                            case "delay":
                            case "wait":
                            case "d":
                                break;
                        }
                        
                    }catch(Exception e){
                        
                    }
                    
                    break;
		case "reader":
		case "reciter":
		case "r":
			try{
				reciter=Integer.valueOf(terms[1]);
				Logging.log("Reciter set to ["+mashayekh[reciter]+"]",1);
			}catch(Exception e){
				Logging.log(e);
			}
			break;
		case "start":
			try{
				ayaStart=Integer.valueOf(terms[1]);
				Logging.log("Start aya set to ["+ayaStart+"]",1);
			}catch(Exception e){
				Logging.log("Start aya unchanged ["+ayaStart+"]",1);
			}
			break;

		case "end":
			try{
				ayaEnd=Integer.valueOf(terms[1]);
				Logging.log("End aya set to ["+ayaEnd+"]",1);
			}catch(Exception e){
				Logging.log("End aya unchanged ["+ayaEnd+"]",1);
			}
			break;

		case "mode":
		case "m":
			try{
				switch(terms[1]){
				case "0":
				case "normal":
					if (currentMode!=0){
						currentMode=0;
					}
					break;
                                    
				case "1":
				case "rabani":
						currentMode=1;
					
					break;
                                    
				case "2":
				case "full":
						currentMode=2;
                                    break;
                                    
					
					//alternating readers mode !
				case "3":
                    currentMode=3;
                    break;
					

				}
				Logging.log("Mode set to ["+currentMode+"]",1);
			}catch(Exception e){
				Logging.log(e);
			}
			break;

		case "sura":
		case "s":
			try{
				sura=Integer.valueOf(terms[1]);
				SURA_CHANGE=true;
				AYA_CHANGE=true;
				ayaStart=1;
				ayaEnd=ayatCount[sura];
				currentAya=1;
				Logging.log("Sura set to ["+Sura_Name[sura]+"]",1);
			}catch(Exception e){
				Logging.log(e);
			}
			break;

		case "aya":
		case "a":
			try{
				currentAya=Integer.valueOf(terms[1]);
				AYA_CHANGE=true;
				Logging.log("Aya set to ["+currentAya+"]",1);
			}catch(Exception e){
				Logging.log(e);
			}
			break;

		case "repeat":
			try{
				if (terms.length==2){
					ayaRepeatCount=Integer.valueOf(terms[1]);
					Logging.log("Aya repeat set to ["+ayaRepeatCount+"] times.",1);
				}else{
					AYA_REPEAT_FOREVER=!AYA_REPEAT_FOREVER;
					if (AYA_REPEAT_FOREVER){
						Logging.log("REPEAT FOR EVER MODE ENABLED.",1);
					} else{
						Logging.log("REPEAT FOR EVER MODE DISABLED.",1);
					}
				}

			}catch(Exception e){
				Logging.log(e);
			}
			break;

		case "aya+":
			//TODO fix range checking
			currentAya++;
			AYA_CHANGE=true;
			Logging.log("going to next aya ["+currentAya+"]",1);
			break;

		case "aya-":
			//TODO fix range checking
			currentAya--;
			AYA_CHANGE=true;
			Logging.log("going to previous aya ["+currentAya+"]",1);
			break;

		case "sura+":
			if(sura==114){
				sura=1;
			}else{
				sura+=1;
			}
			Logging.log("going to next sura ["+Sura_Name[sura]+"]",1);
			SURA_CHANGE=true;
			break;

		case "sura-":
			if(sura==0){
				sura=114;
			}else{
				sura-=1;
			}
			SURA_CHANGE=true;
			Logging.log("going to previous sura ["+Sura_Name[sura]+"]",1);
			break;

		case "n":
		case "next":
			String next="sura";
			if (terms.length==2){
				next=terms[1];
			}
			switch(next.toLowerCase()){
			case "aya":
				//TODO fix range checking
				if(currentAya<ayatCount[sura]){
					currentAya++;
					AYA_CHANGE=true;
					Logging.log("going to next aya ["+currentAya+"]",1);
				}else{
					if(sura==114){
						sura=1;
					}else{
						sura+=1;
					}
					currentAya=1;
					AYA_CHANGE=true;
					Logging.log("going to next sura ["+Sura_Name[sura]+"], #"+sura,1);
					SURA_CHANGE=true;
				}
				break;
			case "reader":
			case "reciter":
				if (reciter<(mashayekh.length-1)){
					reciter++;
				} else{
					reciter=0;
				}
				Logging.log("Reader set to "+mashayekh[reciter]+", #"+reciter,1);
				break;
			default:
				if(sura==114){
					sura=1;
				}else{
					sura+=1;
				}
				Logging.log("going to next sura ["+Sura_Name[sura]+"], #"+sura,1);
				SURA_CHANGE=true;
				break;
			}
			break;

		case "prev":
			String prev="sura";
			if (terms.length==2){
				prev=terms[1];
			}
			switch(prev.toLowerCase()){
			case "aya":
				if (currentAya>1){
					currentAya--;
				}else{

					if(sura==1){
						sura=114;
					}else{
						sura-=1;
					}
					Logging.log("going to previous sura ["+Sura_Name[sura]+"], #"+sura,1);
					currentAya=ayatCount[sura];
					SURA_CHANGE=true;
				}
				AYA_CHANGE=true;
				Logging.log("going to previous aya #"+currentAya,1);
				break;
			case "reader":
			case "reciter":
				if (reciter>0){
					reciter--;
				} else{
					reciter=(mashayekh.length-1);
				}
				Logging.log("Reader set to "+mashayekh[reciter]+", #"+reciter,1);
				break;
			default:
				if(sura==1){
					sura=114;
				}else{
					sura-=1;
				}
				Logging.log("going to previous sura ["+Sura_Name[sura]+"], #"+sura,1);
				SURA_CHANGE=true;
				break;
			}
			break;

		case "srepeat":
			try{
				if (terms.length==2){
					groupRepeatCount=Integer.valueOf(terms[1]);
					Logging.log("Sura repeat set to ["+groupRepeatCount+"] times.",1);
				}else{
					SURA_REPEAT_FOREVER=!SURA_REPEAT_FOREVER;
					Logging.log("sura repeat forever "+(SURA_REPEAT_FOREVER?"ON":"OFF"),1);
				}

			}catch(Exception e){
				Logging.log(e);
			}

			break;

		case "delay":
		case "d":
			try{

				ayaWait=Integer.valueOf(terms[1]);
				Logging.log("Delay set to ["+ayaWait+"] milliseconds.",1);

			}catch(Exception e){
				Logging.log(e);
			}
			break;
		case "download":
			try{
				if ("on".equals(terms[1])){
					DOWNLOAD_ONLY=true;
					Logging.log("DOWNLOAD_ONLY is turned ON.",1);
				}else if ("off".equals(terms[1])){
					DOWNLOAD_ONLY=false;
					Logging.log("DOWNLOAD_ONLY is turned OFF.",1);
				}
			}catch(Exception e){
				Logging.log("DOWNLOAD_ONLY is "+(DOWNLOAD_ONLY?"ON.":"OFF."),1);
			}
			break;

		case "pause":
		case "stop":
		case "p":
			PAUSE=!PAUSE;
			Logging.log((PAUSE?"pause.":"continue."),1);
			break;

		case "resume":
		case "go":
		case "continue":
			Logging.log("Resuming ... :)",1);
			PAUSE=false;
			break;

		case "exit":
		case "x":
		case "quit":
		case "q":
			Logging.log("Exiting ... ",1);
			EXIT=true;
			break;
		case "save":
			try {
				Logging.log("Saving state ... ",1);
				saveState();
			} catch (IOException e2) {
				Logging.log(e2);
			}
			break;
		case "list":
		case "ls":
			try{
				System.out.println();
				System.out.println("Reciters list: ");
				System.out.println();
				for(int i=0;i<mashayekh.length;i++){
					System.out.println(i+" "+mashayekh[i]);
				}
				System.out.println();
				System.out.println("Sura numbering list: ");
				System.out.println();
				for(int i=1; i<=114;i++){

					Logging.log(
							"["+i+"] "+Sura_Name[i]+"("+ayatCount[i]+")");
				}
				Logging.log("Current state: ");
				Logging.log("reciter="+reciter.toString());
				Logging.log("sura="+sura.toString());
				Logging.log("currentAya="+currentAya.toString());
				Logging.log("ayaStart="+ayaStart.toString());
				Logging.log("ayaEnd="+ayaEnd.toString());
				Logging.log("ayaRepeatCount="+ayaRepeatCount.toString());
				Logging.log("ayaWait="+ayaWait.toString());
				Logging.log("groupRepeatCount="+groupRepeatCount.toString());
				Logging.log("afterAyaWait="+afterAyaWait.toString());
				Logging.log("mode="+currentMode);
				Logging.log("DOWNLOAD_ONLY: "+DOWNLOAD_ONLY);
				Logging.log("showImages:"+showImages);
				Logging.log("AYA_REPEAT_FOREVER:"+AYA_REPEAT_FOREVER);
				Logging.log("SURA_REPEAT_FOREVER:"+SURA_REPEAT_FOREVER);

			}catch(Exception e){
				Logging.log(e);
			}
			break;					

		case "restart":
		case "update":
			try {
				saveState();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				BaseTest.executer("cmd", "/c start /max "+TextFiles.getStartLocation()+"run.cmd  ^& exit ");
			} catch (IOException e) {
				Logging.log(e);
			}
			EXIT=true;
			break;
		case "images":
			showImages=!showImages;
			Logging.log("showImages:"+showImages,1);
			break;
		case "speech":
			try{
				if (terms.length==2){
					if ("on".equals(terms[1])){
						logging.FreeTTS.MUTE=false;
					}else if ("off".equals(terms[1])){
						logging.FreeTTS.MUTE=true;
					} 
				}
			}catch(Exception e){
				FreeTTS.MUTE=!FreeTTS.MUTE;
			}
			Logging.log("Speech is "+(FreeTTS.MUTE? "OFF":"ON"),1);
			break;
		case "arepeat+":
			ayaRepeatCount++;
			Logging.log("aya repeat set to "+ayaRepeatCount,1);
			break;
		case "arepeat-":
			if(ayaRepeatCount-1>0){
				ayaRepeatCount--;
				Logging.log("aya repeat set to "+ayaRepeatCount,1);
			}
			break;
		case "srepeat+":
			groupRepeatCount++;
			Logging.log("sura repeat set to "+groupRepeatCount,1);
			break;

		case "srepeat-":
			if(groupRepeatCount-1>0){
				groupRepeatCount--;
				Logging.log("sura repeat set to "+groupRepeatCount,1);
			}
			break;
		case "volume":
			try{
				if (terms.length==2){
					if ("up".equalsIgnoreCase(terms[1])){
						BaseTest.executer("cmd","/c nircmdc.exe changesysvolume 3000");
						Logging.log("Volume up.",1);
					}else if ("down".equalsIgnoreCase(terms[1])){
						BaseTest.executer("cmd","/c nircmdc.exe changesysvolume -3000");
						Logging.log("Volume down. ",1);
					}
				}
			}catch(Exception e){
				Logging.log(e);
			}
			
			break;
		case "split":
			//get current position and record it in a text file with same name but with another extension
			//TODO
			/**
			 * get current read position 
			 * save to time splitting file
			 * 
			 */
			try {
				TextFiles.save(baseFolder+"mp3"+File.separator+mashayekh[reciter]+File.separator+leadingZeros(sura,3)+File.separator+"split.txt", Long.toString(common.Timer.getTime(mashayekh[reciter]+File.separator+leadingZeros(sura,3))));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			

			break;
		//fourcePause
		case "fpause":
			PAUSE=!PAUSE;
			Logging.log((PAUSE?"Force pause.":"Force continue."),1);	
			if(PAUSE){
				try {
					getMediaPlayer().pause();
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else{
				try {
					getMediaPlayer().play();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			break;
		
			
		default:
			Logging.log("unknown command ["+command+"]",1);

		}
		ReciterWindow.refreshState();
	}
	public static void shell(){
		Logging.log("Build: "+TextFiles.JAR_BUILD, 1);
		Scanner input=new Scanner(System.in);
		String command="";
		Logging.log("OS Name: "+Logging.getOSName());
                Downloads.start();
		while(true){
			System.out.print("<<Reciter>>");
			command=input.nextLine();
			execute(command);
		}
	}

	public static void main(String[] args){

		ReciterController.start();
	}




}
