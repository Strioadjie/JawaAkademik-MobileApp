package id.kelompok5.jawaakademik;

public class MataPelajaran {
    private String namaMatkul;
    private String detailJadwal;
    private String progressBelajar;
    // Variabel baru untuk menyimpan alamat gambar (bertipe int karena ID di Android adalah angka)
    private int gambarMatkul;

    // Constructor diperbarui untuk menerima gambar
    public MataPelajaran(String namaMatkul, String detailJadwal, String progressBelajar, int gambarMatkul) {
        this.namaMatkul = namaMatkul;
        this.detailJadwal = detailJadwal;
        this.progressBelajar = progressBelajar;
        this.gambarMatkul = gambarMatkul;
    }

    public String getNamaMatkul() { return namaMatkul; }
    public String getDetailJadwal() { return detailJadwal; }
    public String getProgressBelajar() { return progressBelajar; }
    public int getGambarMatkul() { return gambarMatkul; } // Getter baru untuk gambar
}