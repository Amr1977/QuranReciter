/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

/**
 *
 * @author Amr
 */
public class Verse {
    private int verseNumber;
    private int suraNumber;
    Verse(int aSuraNumber, int aVerseNumber){
        setSuraNumber(aSuraNumber);
        setVerseNumber(aVerseNumber);
    }

    /**
     * @return the verseNumber
     */
    public int getVerseNumber() {
        return verseNumber;
    }

    /**
     * @param verseNumber the verseNumber to set
     */
    public void setVerseNumber(int verseNumber) {
        this.verseNumber = verseNumber;
    }

    /**
     * @return the suraNumber
     */
    public int getSuraNumber() {
        return suraNumber;
    }

    /**
     * @param suraNumber the suraNumber to set
     */
    public void setSuraNumber(int suraNumber) {
        this.suraNumber = suraNumber;
    }
    
}
