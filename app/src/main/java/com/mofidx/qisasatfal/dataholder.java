package com.mofidx.qisasatfal;

public class dataholder {

    String name;
    String imglink;
    String pdflink;
//
    dataholder()
    {

    }
//    public dataholder(String name, String imglink, String pdflink) {
//        this.name = name;
//        this.imglink = imglink;
//        this.pdflink = pdflink;
//    }
    public dataholder(String name, String imglink,String pdflink) {
        this.name = name;
        this.imglink = imglink;
        this.pdflink = pdflink;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImglink() {
        return imglink;
    }

    public void setImglink(String imglink) {
        this.imglink = imglink;
    }

    public String getPdflink() {
        return pdflink;
    }

    public void setPdflink(String pdflink) {
        this.pdflink = pdflink;
    }
}
