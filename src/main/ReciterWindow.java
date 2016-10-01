/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main;
import common.Downloads;
import common.Internet;
import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.JFXPanel;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import logging.FreeTTS;
import logging.Logging;
import logging.TextFiles;
import test.BaseTest;
/**
 *
 * @author Amr Lotfy
 */
public class ReciterWindow extends javax.swing.JFrame {
    
    
    /**
     * Creates new form NewJFrame
     */
    public static JFXPanel fxPanel = new JFXPanel();
	public static ReciterWindow reciterWindow=null;
        public static String[] surasWithNumbers=new String[115];
        public static String[] readersWithNumbers(){
            String [] result=new String [ReciterModel.mashayekh.length];
            for (int i=0; i<ReciterModel.mashayekh.length;i++){
                result[i]=i+" "+ReciterModel.mashayekh[i];
                        
            }
            return result;
        }
//TODO fix this bug, unmber is not 11
        
        public static ImageIcon getAyaImage(int sura, int aya){
            Logging.log("getAyaImage: sura["+sura+"], aya["+aya+"]");
            String fileName;
            String startLocation=TextFiles.getStartLocation();
            new File(startLocation+"images"+File.separator).mkdirs();
            fileName=startLocation+"images"+File.separator+sura+"_"+aya+".png";
            File f=new File(fileName);
            if (!f.isFile()){
                try {
                    //ReciterModel.downloadFile("http://www.everyayah.com/data/quranpngs/"+sura+"_"+aya+".png",fileName);
                    Downloads.downloadFile("https://googledrive.com/host/0B20GSmZRgPGCfmwxbnJjckFYVDFnWUgwMXhiTklZSmlDYXZrZ1pkLVhyYmI0UjE4MHJ6RVU/"+sura+"_"+aya+".png",fileName);
                } catch (Exception ex) {
                    Logger.getLogger(ReciterWindow.class.getName()).log(Level.SEVERE, null, ex);
                    try {
                        Downloads.downloadFile("http://www.everyayah.com/data/quranpngs/"+sura+"_"+aya+".png",fileName);
                    } catch (Exception ex1) {
                        Logger.getLogger(ReciterWindow.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                }
            }
            //Logging.log("getAyaimage: "+fileName);
            return getImage(fileName);
        }
        
        public static ImageIcon getImage(String filePath){
            try {
                BufferedImage img=ImageIO.read(new File(filePath));
                ImageIcon icon;
                int imageWidth=img.getWidth();
                int imageHeight=img.getHeight();
                int labelWidth=ayaLabel.getWidth()-20;
                int labelHeight=ayaLabel.getHeight()-20;
                int newWidth=imageWidth;
                int newHeight=imageHeight;
                Image dimg=img ;
                int zoomState=
                        (fitWidth.isSelected()? 2:0)+
                        (fitHeight.isSelected()? 1:0);
                
                switch(zoomState){
                    case 0:
                        return new ImageIcon(img);
                        
                    case 3:
                        //fit height and width
                        if (imageWidth>labelWidth){
                            Logging.log("imageHeight:"+imageHeight+", imageWidth:"+imageWidth+", labelHeight: "+labelHeight+", labelWidth:"+labelWidth);
                            newWidth=labelWidth;
                            newHeight=Math.round((imageHeight*newWidth)/imageWidth);
                            //Logging.log("newWidth: "+newWidth+", newHeight: "+newHeight);
                        }
                        
                        if (newHeight>labelHeight){
                            newHeight=labelHeight;
                            newWidth=Math.round((imageWidth*newHeight)/imageHeight);
                            //Logging.log("newWidth: "+newWidth+", newHeight: "+newHeight);
                        }
                        break;
                        
                        //fit width
                    case 2:
                        if (imageWidth>labelWidth){
                            //Logging.log("imageHeight:"+imageHeight+", imageWidth:"+imageWidth+", labelHeight: "+labelHeight+", labelWidth:"+labelWidth);
                            newWidth=labelWidth;
                            newHeight=Math.round((imageHeight*newWidth)/imageWidth);
                            //Logging.log("newWidth: "+newWidth+", newHeight: "+newHeight);
                        }
                        
                        
                       break;
                        
                    case 1:
                        if (imageHeight>labelHeight){
                           // Logging.log("imageHeight:"+imageHeight+", imageWidth:"+imageWidth+", labelHeight: "+labelHeight+", labelWidth:"+labelWidth);
                            newHeight=labelHeight;
                            newWidth=Math.round((imageWidth*newHeight)/imageHeight);
                           // Logging.log("newWidth: "+newWidth+", newHeight: "+newHeight);
                        
                        }
                       break;
                }
                dimg=img.getScaledInstance(newWidth ,newHeight, Image.SCALE_SMOOTH);
                icon=new ImageIcon(dimg);
                return icon;
            } catch (IOException ex) {
                Logger.getLogger(ReciterWindow.class.getName()).log(Level.SEVERE, null, ex);
                return null;
                
            }
        }
        public static void refreshState(){
            
            if (reciterWindow!=null){
                randomSura.setSelected(ReciterModel.raabbaniSura);
                randomReciter.setSelected(ReciterModel.rabbaniReciter);
                randomDelay.setSelected(ReciterModel.rabbaniDelay);
                PAUSE.setSelected(ReciterModel.PAUSE);
            
            suraRepeatForEver.setSelected(ReciterModel.SURA_REPEAT_FOREVER);
            suraRepeat.setValue(ReciterModel.groupRepeatCount);
            setSura(ReciterModel.sura);
            
            
            ayaRepeatForEver.setSelected(ReciterModel.AYA_REPEAT_FOREVER);
            ayaRepeat.setValue(ReciterModel.ayaRepeatCount);
            setAya(ReciterModel.currentAya-1);
            
            startAyaList.setSelectedIndex(ReciterModel.ayaStart-1);
            endAyaList.setSelectedIndex(ReciterModel.ayaEnd-1);
            
            setReciter(ReciterModel.reciter);
            modeList.setSelectedIndex(ReciterModel.currentMode);
            
            mute.setSelected(FreeTTS.MUTE);
            if (!Delay.getValue().toString().equalsIgnoreCase(ReciterModel.ayaWait.toString())){
                Delay.setValue(ReciterModel.ayaWait);
            }
            downloadMode.setSelected(ReciterModel.DOWNLOAD_ONLY);
            
            if (Internet.isConnected()!=connected ){
                if(!Internet.isConnected()){
                jLabelConnection.setText("Offline");
                jLabelConnection.setBackground(Color.RED);
                connected=false;
            }else{
                connected=true;
                jLabelConnection.setText("Online");
                jLabelConnection.setBackground(Color.GREEN);
            }
            }
            
            
            }
        }
        public static boolean connected=false;
    public ReciterWindow() {
        reciterWindow=this;
        initComponents();
        this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        connectivity();
        ReciterController.start();
        FreeTTS.MUTE=true;
        fitWidth.setSelected(true);
        while(!ReciterModel.RESTORED_STATE){
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(ReciterWindow.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
            for(int i=1;i<=114;i++){
                surasWithNumbers[i]=(i+" "+ReciterModel.Sura_Name[i]);
            }
            suraList.setListData(surasWithNumbers);
            recitersList.setListData(readersWithNumbers());
            refreshState();
    }
    
    public static void connectivity(){
        Thread connectivityLabel = new Thread() {
            @Override
            public void run() {
                while (true) {
                    if (!Internet.isConnected()) {
                        jLabelConnection.setText("Offline");
                        jLabelConnection.setForeground(new Color(0,0,0));
                        jLabelConnection.setBackground(new Color(255,0,0));
                    } else {
                        jLabelConnection.setText("Online");
                        //jLabelConnection.set
                        jLabelConnection.setForeground(new Color(255,255,255));
                        jLabelConnection.setBackground(new Color(0,150,0));
                    }
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(ReciterWindow.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        };
           
           connectivityLabel.start();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane9 = new javax.swing.JScrollPane();
        jScrollPane4 = new javax.swing.JScrollPane();
        ayaLabel = new javax.swing.JLabel();
        jScrollPane10 = new javax.swing.JScrollPane();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        rangeJPanel = new javax.swing.JPanel();
        PAUSE = new javax.swing.JToggleButton();
        exit = new javax.swing.JButton();
        jScrollPane6 = new javax.swing.JScrollPane();
        startAyaList = new javax.swing.JList();
        jScrollPane7 = new javax.swing.JScrollPane();
        endAyaList = new javax.swing.JList();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        suraRepeatForEver = new javax.swing.JToggleButton();
        Delay = new javax.swing.JSpinner();
        jLabel2 = new javax.swing.JLabel();
        ayaRepeatForEver = new javax.swing.JToggleButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        suraList = new javax.swing.JList();
        jScrollPane2 = new javax.swing.JScrollPane();
        ayaList = new javax.swing.JList();
        ayaRepeat = new javax.swing.JSpinner();
        suraRepeat = new javax.swing.JSpinner();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        fitHeight = new javax.swing.JCheckBox();
        fitWidth = new javax.swing.JCheckBox();
        randomSura = new javax.swing.JCheckBox();
        jLabelConnection = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        console = new javax.swing.JTextField();
        recitersjPanel = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        recitersList = new javax.swing.JList();
        jButton1 = new javax.swing.JButton();
        jScrollPane8 = new javax.swing.JScrollPane();
        recitersList1 = new javax.swing.JList();
        randomReciter = new javax.swing.JCheckBox();
        modesjPanel = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        modeList = new javax.swing.JList();
        randomDelay = new javax.swing.JCheckBox();
        rabbaniVerseRepeatCount = new javax.swing.JCheckBox();
        downloadMode = new javax.swing.JToggleButton();
        mute = new javax.swing.JToggleButton();
        jPanelTafseer = new javax.swing.JPanel();
        jPanelLog = new javax.swing.JPanel();
        jPanelConsole = new javax.swing.JPanel();
        jPanelDownloadQueue = new javax.swing.JPanel();
        jPanelUpdate = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jCheckBox1 = new javax.swing.JCheckBox();
        aboutJPanel = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Quran Reciter");
        setBackground(new java.awt.Color(0, 0, 0));
        setForeground(new java.awt.Color(0, 0, 0));

        jScrollPane9.setDoubleBuffered(true);

        ayaLabel.setBackground(new java.awt.Color(255, 255, 255));
        ayaLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ayaLabel.setAutoscrolls(true);
        ayaLabel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jScrollPane4.setViewportView(ayaLabel);

        jScrollPane9.setViewportView(jScrollPane4);

        jScrollPane10.setBackground(new java.awt.Color(102, 102, 102));

        PAUSE.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        PAUSE.setMnemonic('P');
        PAUSE.setText("Pause");
        PAUSE.setToolTipText("<html> Pause reading (after aya complete) <br> key shortcut: Ctrl+Alt+P: will pause after finishing reading current verse.");
        PAUSE.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                PAUSEStateChanged(evt);
            }
        });
        PAUSE.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                PAUSEMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                PAUSEMouseEntered(evt);
            }
        });

        exit.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        exit.setMnemonic('x');
        exit.setText("Exit");
        exit.setToolTipText("Ctrl+Alt+X: will exit after finishing reading current verse.");
        exit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                exitMouseClicked(evt);
            }
        });

        startAyaList.setBackground(new java.awt.Color(204, 204, 204));
        startAyaList.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        startAyaList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        startAyaList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        startAyaList.setToolTipText("<html>\nNext Aya: Ctrl+Alt+UP_ARROW<br>\nPrev Aya: Ctrl+Alt+DN_ARROW");
        startAyaList.setSelectionBackground(new java.awt.Color(0, 153, 0));
        startAyaList.setSelectionForeground(new java.awt.Color(0, 0, 0));
        startAyaList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                startAyaListMouseClicked(evt);
            }
        });
        startAyaList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                startAyaListValueChanged(evt);
            }
        });
        jScrollPane6.setViewportView(startAyaList);

        endAyaList.setBackground(new java.awt.Color(204, 204, 204));
        endAyaList.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        endAyaList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        endAyaList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        endAyaList.setToolTipText("<html>\nNext Aya: Ctrl+Alt+UP_ARROW<br>\nPrev Aya: Ctrl+Alt+DN_ARROW");
        endAyaList.setSelectionBackground(new java.awt.Color(0, 153, 0));
        endAyaList.setSelectionForeground(new java.awt.Color(0, 0, 0));
        endAyaList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                endAyaListMouseClicked(evt);
            }
        });
        endAyaList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                endAyaListValueChanged(evt);
            }
        });
        jScrollPane7.setViewportView(endAyaList);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setText("Start");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel4.setText("End");

        suraRepeatForEver.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        suraRepeatForEver.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main/loop.png_0.06_png.png"))); // NOI18N
        suraRepeatForEver.setMnemonic('S');
        suraRepeatForEver.setText("Repeat Current Sura");
        suraRepeatForEver.setToolTipText("<html>\nEndless Repeat current sura <br>\nتكرار السورة لانهائي <br>\nkey shortcut:  Ctrl+Alt+S");
        suraRepeatForEver.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                suraRepeatForEverStateChanged(evt);
            }
        });
        suraRepeatForEver.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                suraRepeatForEverMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                suraRepeatForEverMouseEntered(evt);
            }
        });
        suraRepeatForEver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                suraRepeatForEverActionPerformed(evt);
            }
        });

        Delay.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        Delay.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(0), Integer.valueOf(0), null, Integer.valueOf(5000)));
        Delay.setToolTipText("<html>\nWait time between ayat in milliseconds<br>\nincrease by 5000 milliseconds: Ctrl+Alt+Page_up <br>\ndecrease: Ctrl+Alt+page_down");
        Delay.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                DelayStateChanged(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setLabelFor(Delay);
        jLabel2.setText("Delay Between Verses ");

        ayaRepeatForEver.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        ayaRepeatForEver.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main/loop.png_0.06_png.png"))); // NOI18N
        ayaRepeatForEver.setMnemonic('A');
        ayaRepeatForEver.setToolTipText("<html>\nEndless Repeat current aya<br>\nتكرار الآية لانهائي <br>\nCtrl+Alt+R");
        ayaRepeatForEver.setActionCommand("Repeat Current Aya ");
        ayaRepeatForEver.setLabel("Repeat Current Aya");
        ayaRepeatForEver.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                ayaRepeatForEverStateChanged(evt);
            }
        });
        ayaRepeatForEver.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ayaRepeatForEverMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                ayaRepeatForEverMouseEntered(evt);
            }
        });

        suraList.setBackground(new java.awt.Color(204, 204, 204));
        suraList.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        suraList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        suraList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        suraList.setToolTipText("<html>\nNext Sura: Ctrl+Alt+Right_Arrow <br>\nPrev Sura: Ctrl+Alt+Left_Arrow");
        suraList.setSelectionBackground(new java.awt.Color(0, 153, 0));
        suraList.setSelectionForeground(new java.awt.Color(0, 0, 0));
        suraList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                suraListMouseClicked(evt);
            }
        });
        suraList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                suraListValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(suraList);

        ayaList.setBackground(new java.awt.Color(204, 204, 204));
        ayaList.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        ayaList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        ayaList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        ayaList.setToolTipText("<html>\nNext Aya: Ctrl+Alt+UP_ARROW<br>\nPrev Aya: Ctrl+Alt+DN_ARROW");
        ayaList.setSelectionBackground(new java.awt.Color(0, 153, 0));
        ayaList.setSelectionForeground(new java.awt.Color(0, 0, 0));
        ayaList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ayaListMouseClicked(evt);
            }
        });
        ayaList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                ayaListValueChanged(evt);
            }
        });
        jScrollPane2.setViewportView(ayaList);

        ayaRepeat.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        ayaRepeat.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(1), Integer.valueOf(1), null, Integer.valueOf(1)));
        ayaRepeat.setToolTipText("<html>\nAya Repeat count.<br>\nincrease: Ctrl+Alt+NUM_NUM_PLUS<br>\ndecrease: Ctrl+Alt+NUM_MINUS");
        ayaRepeat.setValue(1);
        ayaRepeat.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                ayaRepeatStateChanged(evt);
            }
        });
        ayaRepeat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ayaRepeatMouseClicked(evt);
            }
        });

        suraRepeat.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        suraRepeat.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(1), Integer.valueOf(1), null, Integer.valueOf(1)));
        suraRepeat.setToolTipText("<html>\nSura repeat count. <br>\n عدد مرات تكرار السورة <br>\nIncrease count : Ctrl+Alt+NUM_8 <br>\nDecrease Count: Ctrl+Alt+NUM_2");
        suraRepeat.setValue(1);
        suraRepeat.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                suraRepeatStateChanged(evt);
            }
        });
        suraRepeat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                suraRepeatMouseClicked(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel5.setLabelFor(Delay);
        jLabel5.setText("Sura Repeat Count");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel6.setLabelFor(Delay);
        jLabel6.setText("Verse Repeat Count");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel7.setText("Sura List");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel8.setText("Current Verse");

        fitHeight.setText("Fit Height");
        fitHeight.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                fitHeightMouseClicked(evt);
            }
        });
        fitHeight.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fitHeightActionPerformed(evt);
            }
        });

        fitWidth.setText("Fit Width");
        fitWidth.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                fitWidthMouseClicked(evt);
            }
        });

        randomSura.setText("Rabbani Sura Selection");
        randomSura.setToolTipText("اختيار السورة التالية ربانيا\nNext sura will be chosen Rabani. ");
        randomSura.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                randomSuraMouseClicked(evt);
            }
        });

        jLabelConnection.setBackground(javax.swing.UIManager.getDefaults().getColor("Button.darkShadow"));
        jLabelConnection.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabelConnection.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelConnection.setText("Internet");
        jLabelConnection.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jLabelConnection.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabelConnection.setOpaque(true);

        jButton3.setText("Next Aya");
        jButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton3MouseClicked(evt);
            }
        });

        console.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                consoleKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout rangeJPanelLayout = new javax.swing.GroupLayout(rangeJPanel);
        rangeJPanel.setLayout(rangeJPanelLayout);
        rangeJPanelLayout.setHorizontalGroup(
            rangeJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rangeJPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(rangeJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(rangeJPanelLayout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addGap(10, 10, 10)))
                .addGroup(rangeJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(28, 28, 28)
                .addGroup(rangeJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addGroup(rangeJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(rangeJPanelLayout.createSequentialGroup()
                        .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(rangeJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(rangeJPanelLayout.createSequentialGroup()
                                .addGroup(rangeJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(suraRepeatForEver, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(ayaRepeatForEver, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(rangeJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(PAUSE, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(exit, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(rangeJPanelLayout.createSequentialGroup()
                                .addGroup(rangeJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2))
                                .addGap(36, 36, 36)
                                .addGroup(rangeJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(Delay, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
                                    .addComponent(ayaRepeat)
                                    .addComponent(suraRepeat))))
                        .addGroup(rangeJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(rangeJPanelLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(rangeJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(fitHeight)
                                    .addComponent(fitWidth)
                                    .addComponent(randomSura, javax.swing.GroupLayout.PREFERRED_SIZE, 149, Short.MAX_VALUE)
                                    .addComponent(jLabelConnection, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButton3)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, rangeJPanelLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(console, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(456, Short.MAX_VALUE))
        );
        rangeJPanelLayout.setVerticalGroup(
            rangeJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rangeJPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(rangeJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, rangeJPanelLayout.createSequentialGroup()
                        .addGroup(rangeJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, rangeJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel1)
                                .addComponent(jLabel4)))
                        .addGroup(rangeJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(rangeJPanelLayout.createSequentialGroup()
                                .addGap(11, 11, 11)
                                .addComponent(jScrollPane2))
                            .addGroup(rangeJPanelLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane1))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, rangeJPanelLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(rangeJPanelLayout.createSequentialGroup()
                                .addGap(11, 11, 11)
                                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, rangeJPanelLayout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addGroup(rangeJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(rangeJPanelLayout.createSequentialGroup()
                                .addComponent(suraRepeatForEver, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(5, 5, 5)
                                .addGroup(rangeJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(exit, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(ayaRepeatForEver, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(rangeJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(suraRepeat, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(console, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(rangeJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(ayaRepeat, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(rangeJPanelLayout.createSequentialGroup()
                                .addGroup(rangeJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(rangeJPanelLayout.createSequentialGroup()
                                        .addComponent(fitWidth)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(fitHeight))
                                    .addComponent(PAUSE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(randomSura)
                                .addGap(18, 18, 18)
                                .addComponent(jButton3)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(rangeJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Delay)
                            .addComponent(jLabelConnection, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(163, Short.MAX_VALUE))
        );

        rangeJPanelLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {Delay, PAUSE, ayaRepeatForEver, jLabel2, suraRepeatForEver});

        ayaRepeatForEver.getAccessibleContext().setAccessibleName("Repeat Current Aya ");

        jTabbedPane1.addTab("Range", rangeJPanel);

        recitersList.setBackground(new java.awt.Color(204, 204, 204));
        recitersList.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        recitersList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        recitersList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        recitersList.setToolTipText("<html>\nNext reader: Ctrl+Alt+A<br>\nPrev reader: Ctrl+Alt+D");
        recitersList.setSelectionBackground(new java.awt.Color(0, 153, 0));
        recitersList.setSelectionForeground(new java.awt.Color(0, 0, 0));
        recitersList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                recitersListMouseClicked(evt);
            }
        });
        recitersList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                recitersListValueChanged(evt);
            }
        });
        jScrollPane3.setViewportView(recitersList);

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton1.setText("Add to favourites");

        recitersList1.setBackground(new java.awt.Color(204, 204, 204));
        recitersList1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        recitersList1.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        recitersList1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        recitersList1.setToolTipText("<html>\nNext reader: Ctrl+Alt+A<br>\nPrev reader: Ctrl+Alt+D");
        recitersList1.setSelectionBackground(new java.awt.Color(0, 153, 0));
        recitersList1.setSelectionForeground(new java.awt.Color(0, 0, 0));
        recitersList1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                recitersList1MouseClicked(evt);
            }
        });
        recitersList1.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                recitersList1ValueChanged(evt);
            }
        });
        jScrollPane8.setViewportView(recitersList1);

        randomReciter.setText("Rabbani Reader Selection");
        randomReciter.setToolTipText("القارئ للسوة التالية يتم اختياره ربانيا\nNext Sura reciter will be chosen Rabani.\n");
        randomReciter.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                randomReciterMouseClicked(evt);
            }
        });
        randomReciter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                randomReciterActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout recitersjPanelLayout = new javax.swing.GroupLayout(recitersjPanel);
        recitersjPanel.setLayout(recitersjPanelLayout);
        recitersjPanelLayout.setHorizontalGroup(
            recitersjPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(recitersjPanelLayout.createSequentialGroup()
                .addGroup(recitersjPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(recitersjPanelLayout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 675, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(recitersjPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 439, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(recitersjPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(randomReciter)))
                .addContainerGap(492, Short.MAX_VALUE))
        );
        recitersjPanelLayout.setVerticalGroup(
            recitersjPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, recitersjPanelLayout.createSequentialGroup()
                .addGroup(recitersjPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(recitersjPanelLayout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(11, 11, 11)
                        .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(randomReciter)
                .addGap(0, 180, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Reciters", recitersjPanel);

        modeList.setBackground(new java.awt.Color(204, 204, 204));
        modeList.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        modeList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Normal mode: Single reader, normal sura order.", "Rabani Mode: random reader, random delay random sura order.", "Full mode: all readers sequentially, normal sura order", "Alternating readers mode: each reader reads a single verse, normal sura order." };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        modeList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        modeList.setSelectionBackground(new java.awt.Color(0, 153, 0));
        modeList.setSelectionForeground(new java.awt.Color(0, 0, 0));
        modeList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                modeListMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(modeList);

        randomDelay.setText("Rabbani inter-verse pause");
        randomDelay.setEnabled(false);
        randomDelay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                randomDelayActionPerformed(evt);
            }
        });

        rabbaniVerseRepeatCount.setText("Rabbani Verse repeat count");
        rabbaniVerseRepeatCount.setToolTipText("اختيار السورة التالية ربانيا\nNext sura will be chosen Rabani. ");
        rabbaniVerseRepeatCount.setEnabled(false);
        rabbaniVerseRepeatCount.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rabbaniVerseRepeatCountMouseClicked(evt);
            }
        });

        downloadMode.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        downloadMode.setText("DOWNLOAD MODE");
        downloadMode.setToolTipText("<html>\nFast download only, no reading.<br>\nCtrl+Alt+L");
        downloadMode.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                downloadModeMouseClicked(evt);
            }
        });

        mute.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        mute.setText("Speech Off");
        mute.setToolTipText("<html>\nON: Ctrl+Alt+NUM_1<br>\nOFF: Ctrl+Alt+NUM_0");
        mute.setEnabled(false);
        mute.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                muteMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout modesjPanelLayout = new javax.swing.GroupLayout(modesjPanel);
        modesjPanel.setLayout(modesjPanelLayout);
        modesjPanelLayout.setHorizontalGroup(
            modesjPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(modesjPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 499, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(modesjPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(randomDelay)
                    .addComponent(rabbaniVerseRepeatCount)
                    .addComponent(downloadMode, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(mute, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(924, Short.MAX_VALUE))
        );
        modesjPanelLayout.setVerticalGroup(
            modesjPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(modesjPanelLayout.createSequentialGroup()
                .addGap(57, 57, 57)
                .addComponent(randomDelay)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rabbaniVerseRepeatCount)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(mute)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(downloadMode)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(modesjPanelLayout.createSequentialGroup()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 164, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Modes", modesjPanel);

        jPanelTafseer.setEnabled(false);

        javax.swing.GroupLayout jPanelTafseerLayout = new javax.swing.GroupLayout(jPanelTafseer);
        jPanelTafseer.setLayout(jPanelTafseerLayout);
        jPanelTafseerLayout.setHorizontalGroup(
            jPanelTafseerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1624, Short.MAX_VALUE)
        );
        jPanelTafseerLayout.setVerticalGroup(
            jPanelTafseerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 482, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Tafseer", jPanelTafseer);

        jPanelLog.setEnabled(false);

        javax.swing.GroupLayout jPanelLogLayout = new javax.swing.GroupLayout(jPanelLog);
        jPanelLog.setLayout(jPanelLogLayout);
        jPanelLogLayout.setHorizontalGroup(
            jPanelLogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1624, Short.MAX_VALUE)
        );
        jPanelLogLayout.setVerticalGroup(
            jPanelLogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 482, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Log", jPanelLog);

        jPanelConsole.setEnabled(false);

        javax.swing.GroupLayout jPanelConsoleLayout = new javax.swing.GroupLayout(jPanelConsole);
        jPanelConsole.setLayout(jPanelConsoleLayout);
        jPanelConsoleLayout.setHorizontalGroup(
            jPanelConsoleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1624, Short.MAX_VALUE)
        );
        jPanelConsoleLayout.setVerticalGroup(
            jPanelConsoleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 482, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Console", jPanelConsole);

        jPanelDownloadQueue.setEnabled(false);

        javax.swing.GroupLayout jPanelDownloadQueueLayout = new javax.swing.GroupLayout(jPanelDownloadQueue);
        jPanelDownloadQueue.setLayout(jPanelDownloadQueueLayout);
        jPanelDownloadQueueLayout.setHorizontalGroup(
            jPanelDownloadQueueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1624, Short.MAX_VALUE)
        );
        jPanelDownloadQueueLayout.setVerticalGroup(
            jPanelDownloadQueueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 482, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Download Queue", jPanelDownloadQueue);

        jButton2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton2.setText("Restart application to Update");
        jButton2.setEnabled(false);

        jCheckBox1.setText("Download Updates automatically");
        jCheckBox1.setEnabled(false);

        javax.swing.GroupLayout jPanelUpdateLayout = new javax.swing.GroupLayout(jPanelUpdate);
        jPanelUpdate.setLayout(jPanelUpdateLayout);
        jPanelUpdateLayout.setHorizontalGroup(
            jPanelUpdateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelUpdateLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelUpdateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jCheckBox1)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(1341, Short.MAX_VALUE))
        );
        jPanelUpdateLayout.setVerticalGroup(
            jPanelUpdateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelUpdateLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jCheckBox1)
                .addGap(18, 18, 18)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(352, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Update", jPanelUpdate);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main/fb.png"))); // NOI18N
        jLabel3.setText("<html> <a href=https://www.facebook.com/pages/Quran-Reciter/1549759911960508 >Application Page.</a> ");
        jLabel3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout aboutJPanelLayout = new javax.swing.GroupLayout(aboutJPanel);
        aboutJPanel.setLayout(aboutJPanelLayout);
        aboutJPanelLayout.setHorizontalGroup(
            aboutJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(aboutJPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(1446, Short.MAX_VALUE))
        );
        aboutJPanelLayout.setVerticalGroup(
            aboutJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(aboutJPanelLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(437, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("About", aboutJPanel);

        jScrollPane10.setViewportView(jTabbedPane1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, 1251, Short.MAX_VALUE)
            .addComponent(jScrollPane9)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 467, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 416, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void PAUSEMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PAUSEMouseClicked
        // TODO add your handling code here:
        ReciterModel.execute("pause");
        
    }//GEN-LAST:event_PAUSEMouseClicked

    private void ayaRepeatForEverStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_ayaRepeatForEverStateChanged
        // TODO add your handling code here:
        
    }//GEN-LAST:event_ayaRepeatForEverStateChanged

    private void ayaRepeatForEverMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ayaRepeatForEverMouseEntered
        // TODO add your handling code here:
        //FreeTTS.say((ReciterModel.REPEAT_FOREVER? "Repeat OFF":"Repeat ON"));
    }//GEN-LAST:event_ayaRepeatForEverMouseEntered

    private void PAUSEMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PAUSEMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_PAUSEMouseEntered
    private void generateAyatList(int newSuraIndex){
        
        String[] ayat=new String[ReciterModel.ayatCount[newSuraIndex]];
        for(int i=0;i<ayat.length;i++){
            ayat[i]="aya #"+(i+1);
        }
        ayaList.setListData(ayat);
        startAyaList.setListData(ayat);
        startAyaList.setSelectedIndex(ReciterModel.ayaStart-1);
        endAyaList.setListData(ayat);
        endAyaList.setSelectedIndex(ReciterModel.ayaEnd-1);
        ayaList.setSelectedIndex(0);
    }
    private void suraListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_suraListValueChanged
        generateAyatList(suraList.getSelectedIndex());
        
    }//GEN-LAST:event_suraListValueChanged

    private void ayaListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_ayaListValueChanged
        
        ImageIcon img;
        if (ayaList.getSelectedIndex()==-1){
            ayaList.setSelectedIndex(0);
        }
        if (suraList.getSelectedIndex()==-1){
            suraList.setSelectedIndex(0);
        }
        Logging.log("ayaList.getSelectedIndex():"+ayaList.getSelectedIndex());
        if ((img=getAyaImage(suraList.getSelectedIndex(),(ayaList.getSelectedIndex()+1)))!=null){
            ayaLabel.setIcon(img);
        }else{
            Logging.log("Image file not found: ["+suraList.getSelectedIndex()+"_"+(ayaList.getSelectedIndex()+1)+"]");
        }
        
        
    }//GEN-LAST:event_ayaListValueChanged
    public static void setSura(int sura){
        suraList.setSelectedValue((sura+" "+ReciterModel.Sura_Name[sura]), true);
    }
    private void suraListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_suraListMouseClicked
        ReciterModel.execute("sura "+suraList.getSelectedIndex());
    }//GEN-LAST:event_suraListMouseClicked
    public static void setAya(int aya){
       // Logging.log("aya: "+aya);
        ayaList.setSelectedValue(("aya #"+(aya+1)), true);
    }
    private void ayaListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ayaListMouseClicked
        ReciterModel.execute("aya "+(ayaList.getSelectedIndex()+1));
    }//GEN-LAST:event_ayaListMouseClicked

    private void ayaRepeatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ayaRepeatMouseClicked
       
    }//GEN-LAST:event_ayaRepeatMouseClicked

    private void ayaRepeatStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_ayaRepeatStateChanged
         ReciterModel.execute("repeat "+ayaRepeat.getValue());
    }//GEN-LAST:event_ayaRepeatStateChanged

    private void suraRepeatStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_suraRepeatStateChanged
         ReciterModel.execute("srepeat "+suraRepeat.getValue());
    }//GEN-LAST:event_suraRepeatStateChanged

    private void suraRepeatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_suraRepeatMouseClicked
    }//GEN-LAST:event_suraRepeatMouseClicked

    private void recitersListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_recitersListMouseClicked
        ReciterModel.execute("reciter "+(recitersList.getSelectedIndex()));
    }//GEN-LAST:event_recitersListMouseClicked
    public static void setReciter(int reciter){
       // Logging.log("Reciter: "+reciter);
        //Logging.log("Mashayekh: "+ReciterModel.mashayekh.length);
        recitersList.setSelectedValue(readersWithNumbers()[reciter], true);
    }
    private void recitersListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_recitersListValueChanged
        recitersList.setSelectedValue(recitersList.getSelectedValue(), true);
        
    }//GEN-LAST:event_recitersListValueChanged

    private void muteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_muteMouseClicked
        ReciterModel.execute("speech "+(mute.isSelected()?"off":"on"));
    }//GEN-LAST:event_muteMouseClicked

    private void suraRepeatForEverStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_suraRepeatForEverStateChanged
        suraRepeat.setEnabled(!suraRepeatForEver.isSelected());
        
    }//GEN-LAST:event_suraRepeatForEverStateChanged

    private void suraRepeatForEverMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_suraRepeatForEverMouseEntered
    }//GEN-LAST:event_suraRepeatForEverMouseEntered

    private void suraRepeatForEverMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_suraRepeatForEverMouseClicked
        ReciterModel.execute("srepeat");
    }//GEN-LAST:event_suraRepeatForEverMouseClicked

    private void downloadModeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_downloadModeMouseClicked
        ReciterModel.execute("download "+(downloadMode.isSelected()?"on":"off"));
    }//GEN-LAST:event_downloadModeMouseClicked

    private void exitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exitMouseClicked
        ReciterModel.execute("exit");
    }//GEN-LAST:event_exitMouseClicked

    private void DelayStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_DelayStateChanged
        ReciterModel.execute("delay "+Delay.getValue());
    }//GEN-LAST:event_DelayStateChanged

    private void ayaRepeatForEverMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ayaRepeatForEverMouseClicked
        ReciterModel.AYA_REPEAT_FOREVER=ayaRepeatForEver.isSelected();
        ayaRepeat.setEnabled(!ayaRepeatForEver.isSelected());
    }//GEN-LAST:event_ayaRepeatForEverMouseClicked

    private void fitWidthMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fitWidthMouseClicked
        // TODO add your handling code here:
        refreshState();
        ayaListValueChanged(null);
    }//GEN-LAST:event_fitWidthMouseClicked

    private void fitHeightMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fitHeightMouseClicked
        // TODO add your handling code here:
         refreshState();
         ayaListValueChanged(null);
    }//GEN-LAST:event_fitHeightMouseClicked

    private void fitHeightActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fitHeightActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fitHeightActionPerformed

    private void modeListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_modeListMouseClicked
        // TODO add your handling code here:
        ReciterModel.execute("mode "+modeList.getSelectedIndex());
    }//GEN-LAST:event_modeListMouseClicked

    private void startAyaListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_startAyaListMouseClicked
        // TODO add your handling code here:
        ReciterModel.execute("start "+(startAyaList.getSelectedIndex()+1));
    }//GEN-LAST:event_startAyaListMouseClicked

    private void startAyaListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_startAyaListValueChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_startAyaListValueChanged

    private void endAyaListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_endAyaListMouseClicked
        // TODO add your handling code here:
        ReciterModel.execute("end "+(endAyaList.getSelectedIndex()+1));
    }//GEN-LAST:event_endAyaListMouseClicked

    private void endAyaListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_endAyaListValueChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_endAyaListValueChanged

    private void randomReciterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_randomReciterActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_randomReciterActionPerformed

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
        try{
            Logging.log("launching https://www.facebook.com/pages/Quran-Reciter/1549759911960508 ...");
            BaseTest.executer("cmd","/c start https://www.facebook.com/pages/Quran-Reciter/1549759911960508");
        }catch(IOException e){

        }

    }//GEN-LAST:event_jLabel3MouseClicked

    private void randomSuraMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_randomSuraMouseClicked
        //ReciterModel.execute("random sura");
        ReciterModel.raabbaniSura=randomSura.isSelected();
        
    }//GEN-LAST:event_randomSuraMouseClicked

    private void randomReciterMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_randomReciterMouseClicked
        // TODO add your handling code here:
        ReciterModel.rabbaniReciter=randomReciter.isSelected();
    }//GEN-LAST:event_randomReciterMouseClicked

    private void rabbaniVerseRepeatCountMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rabbaniVerseRepeatCountMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_rabbaniVerseRepeatCountMouseClicked

    private void randomDelayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_randomDelayActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_randomDelayActionPerformed

    private void recitersList1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_recitersList1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_recitersList1MouseClicked

    private void recitersList1ValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_recitersList1ValueChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_recitersList1ValueChanged

    private void suraRepeatForEverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_suraRepeatForEverActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_suraRepeatForEverActionPerformed

    private void PAUSEStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_PAUSEStateChanged
    }//GEN-LAST:event_PAUSEStateChanged

    private void jButton3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3MouseClicked
        // TODO add your handling code here:
        ReciterModel.execute("next aya");
    }//GEN-LAST:event_jButton3MouseClicked

    private void consoleKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_consoleKeyPressed
        // TODO add your handling code here:
        
        if (evt.getKeyCode() == 10) {
          //TODO do it !  
            ReciterModel.execute(console.getText());
            console.setText("");
        }
        
    }//GEN-LAST:event_consoleKeyPressed
    
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ReciterWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ReciterWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ReciterWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ReciterWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ReciterWindow().setVisible(true);
                
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JSpinner Delay;
    public static javax.swing.JToggleButton PAUSE;
    private javax.swing.JPanel aboutJPanel;
    public static javax.swing.JLabel ayaLabel;
    public static javax.swing.JList ayaList;
    public static javax.swing.JSpinner ayaRepeat;
    public static javax.swing.JToggleButton ayaRepeatForEver;
    private javax.swing.JTextField console;
    public static javax.swing.JToggleButton downloadMode;
    public static javax.swing.JList endAyaList;
    private javax.swing.JButton exit;
    public static javax.swing.JCheckBox fitHeight;
    public static javax.swing.JCheckBox fitWidth;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    public static javax.swing.JLabel jLabelConnection;
    private javax.swing.JPanel jPanelConsole;
    private javax.swing.JPanel jPanelDownloadQueue;
    private javax.swing.JPanel jPanelLog;
    private javax.swing.JPanel jPanelTafseer;
    private javax.swing.JPanel jPanelUpdate;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTabbedPane jTabbedPane1;
    public static javax.swing.JList modeList;
    private javax.swing.JPanel modesjPanel;
    public static javax.swing.JToggleButton mute;
    public static javax.swing.JCheckBox rabbaniVerseRepeatCount;
    public static javax.swing.JCheckBox randomDelay;
    public static javax.swing.JCheckBox randomReciter;
    public static javax.swing.JCheckBox randomSura;
    private javax.swing.JPanel rangeJPanel;
    public static javax.swing.JList recitersList;
    public static javax.swing.JList recitersList1;
    private javax.swing.JPanel recitersjPanel;
    public static javax.swing.JList startAyaList;
    public static javax.swing.JList suraList;
    public static javax.swing.JSpinner suraRepeat;
    public static javax.swing.JToggleButton suraRepeatForEver;
    // End of variables declaration//GEN-END:variables
}
