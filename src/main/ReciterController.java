package main;


//import com.melloware.jintellitype.HotkeyListener;

import common.GlobalHotKeys;
import logging.Logging;

public class ReciterController implements Runnable{
	ReciterController(){
            //start();
		
	}
	
	public void onHotKey(int arg0) {
		switch(arg0){
		case GlobalHotKeys.KEY_UP_ARROW:
		
			ReciterModel.execute("next aya");
			break;
		case GlobalHotKeys.KEY_DOWN_ARROW:
			ReciterModel.execute("prev aya");
			break;
			
		case GlobalHotKeys.KEY_RIGHT_ARROW:
			ReciterModel.execute("next sura");
			break;
			
		case GlobalHotKeys.KEY_LEFT_ARROW:
			ReciterModel.execute("prev sura");
			break;
			
		case GlobalHotKeys.KEY_P:
			ReciterModel.execute("pause");
			break;
			
		case GlobalHotKeys.KEY_X:
			ReciterModel.execute("exit");
			break;
		case GlobalHotKeys.KEY_R:
			ReciterModel.execute("repeat");
			break;
		case GlobalHotKeys.KEY_0:
			ReciterModel.execute("mode 0");
			break;
		case GlobalHotKeys.KEY_1:
			ReciterModel.execute("mode 1");
			break;
		case GlobalHotKeys.KEY_2:
			ReciterModel.execute("mode 2");
			break;
			
		case GlobalHotKeys.KEY_3:
			ReciterModel.execute("mode 3");
			break;			
		case GlobalHotKeys.KEY_A:
			//next reader
			ReciterModel.execute("next reader");
			break;
		case GlobalHotKeys.KEY_D:
			//prev reader
			ReciterModel.execute("prev reader");
			break;
			
		case GlobalHotKeys.KEY_PAGE_UP:
			ReciterModel.execute("delay "+(ReciterModel.ayaWait+=5000));
			break;
			
		case GlobalHotKeys.KEY_PAGE_DOWN:
			if (ReciterModel.ayaWait-5000>=0){
				ReciterModel.execute("delay "+(ReciterModel.ayaWait-=5000));
			}else{
				ReciterModel.execute("delay 0");
			}
			break;
		case GlobalHotKeys.KEY_NUM_0:
			Logging.log("Ok, see you, assalamu alaikom.",1);
			logging.FreeTTS.MUTE=true;
			break;
		case GlobalHotKeys.KEY_NUM_1:
			Logging.log("Assalamu alaikom :)",1);
			logging.FreeTTS.MUTE=false;
			break;
		case GlobalHotKeys.KEY_NUM_PLUS:
			ReciterModel.execute("arepeat+");
			break;
		case GlobalHotKeys.KEY_NUM_MINUS:
			ReciterModel.execute("arepeat-");
			break;
		case GlobalHotKeys.KEY_NUM_8:
			ReciterModel.execute("srepeat+");
			break;
		case GlobalHotKeys.KEY_NUM_2:
			ReciterModel.execute("srepeat-");
			break;
		case GlobalHotKeys.KEY_S:
			ReciterModel.execute("srepeat");
			break;
		case GlobalHotKeys.KEY_L:
			ReciterModel.execute("download");
			break;
		case GlobalHotKeys.KEY_MINUS:
			ReciterModel.execute("volume down");
			break;
		case GlobalHotKeys.KEY_PLUS:
			ReciterModel.execute("volume up");
			break;
		}
		
	}
	@Override
	public void run() {
		ReciterModel.shell();
	}
	public static void start(){
                //rw=new ReciterWindow();
		Thread readerThread=new Thread(new Reader(), "ReciterController Thread");
		readerThread.start();
		Thread controllerThread=new Thread(new ReciterController(), "ReciterController Thread");
		controllerThread.start();
		
	}
	
	public static void main(String[] args){
		start();
	}
}
