package id.kelompok5.jawaakademik;

public class MataPelajaran {
    private String namaMatkul;
    private String detailJadwal;
    private String deskripsi;
    private int gambarMatkul;

    public MataPelajaran(String namaMatkul, String detailJadwal, String deskripsi, int gambarMatkul) {
        this.namaMatkul = namaMatkul;
        this.detailJadwal = detailJadwal;
        this.deskripsi = deskripsi;
        this.gambarMatkul = gambarMatkul;
    }

    public String getNamaMatkul() { return namaMatkul; }
    public String getDetailJadwal() { return detailJadwal; }
    public String getDeskripsi() { return deskripsi; }
    public int getGambarMatkul() { return gambarMatkul; }
}
