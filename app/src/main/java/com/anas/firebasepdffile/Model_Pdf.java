package com.anas.firebasepdffile;

public class Model_Pdf {

    String Filename,Fileurl;
    int LC,VC,DC;

    public Model_Pdf(){


    }

    public Model_Pdf(String filename, String fileurl, int LC, int VC, int DC) {
        Filename = filename;
        Fileurl = fileurl;
        this.LC = LC;
        this.VC = VC;
        this.DC = DC;
    }


    public String getFilename() {
        return Filename;
    }

    public void setFilename(String filename) {
        Filename = filename;
    }

    public String getFileurl() {
        return Fileurl;
    }

    public void setFileurl(String fileurl) {
        Fileurl = fileurl;
    }

    public int getLC() {
        return LC;
    }

    public void setLC(int LC) {
        this.LC = LC;
    }

    public int getVC() {
        return VC;
    }

    public void setVC(int VC) {
        this.VC = VC;
    }

    public int getDC() {
        return DC;
    }

    public void setDC(int DC) {
        this.DC = DC;
    }
}
