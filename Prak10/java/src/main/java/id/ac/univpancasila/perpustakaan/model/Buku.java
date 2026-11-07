package id.ac.univpancasila.perpustakaan.model;

public class Buku {
    private String isbn;
    private String judul;
    private int eksemplar;

    public Buku(String isbn, String judul, int eksemplar) {
        this.isbn = isbn;
        this.judul = judul;
        this.eksemplar = eksemplar;
    }

    public String getIsbn() { return isbn; }
    public String getJudul() { return judul; }
    public int getEksemplar() { return eksemplar; }
    public void setEksemplar(int eksemplar) { this.eksemplar = eksemplar; }
}