package org.example;

public class Libro {
    private int id;
    private String titulo;
    private String autor;
    private int anioPublicacion;
    private String isbn;

    public Libro(int id, String titulo, String autor, int anioPublicacion, String isbn) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.anioPublicacion = anioPublicacion;
        this.isbn = isbn;
    }

    // Getters
    public int getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getAutor() { return autor; }
    public int getAnioPublicacion() { return anioPublicacion; }
    public String getIsbn() { return isbn; }

    @Override
    public String toString() {
        return String.format("[%d] %s - %s (%d) | ISBN: %s",
                id, titulo, autor, anioPublicacion, isbn);
    }
}