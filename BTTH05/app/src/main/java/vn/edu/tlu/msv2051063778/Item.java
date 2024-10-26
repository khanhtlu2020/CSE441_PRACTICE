package vn.edu.tlu.msv2051063778;

public class Item {
    private String maMon;
    private String tenMon;
    private Double donGia;
    private String hinh;
    private String moTa;
    public Item(String maMon, String tenMon, Double donGia, String hinh, String moTa) {
        this.maMon = maMon;
        this.tenMon = tenMon;
        this.donGia = donGia;
        this.hinh = hinh;
        this.moTa = moTa;
    }


    public String getMaMon() {
        return maMon;
    }

    public void setMaMon(String maMon) {
        this.maMon = maMon;
    }

    public String getTenMon() {
        return tenMon;
    }

    public void setTenMon(String tenMon) {
        this.tenMon = tenMon;
    }


    public Double getDonGia() {
        return donGia;
    }

    public void setDonGia(Double donGia) {
        this.donGia = donGia;
    }

    public String getHinh() {
        return hinh;
    }

    public void setHinh(String hinh) {
        this.hinh = hinh;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }
}


