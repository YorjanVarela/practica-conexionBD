package org.example;

import java.sql.Connection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        System.out.println("--- PRUEBA DE CONEXIÓN Y PERSISTENCIA DE DATOS ---");

        // 1. Obtener la lista actual de libros desde MariaDB
        List<Libro> libros = listarLibros();
        System.out.println("\n📚 Libros registrados en la base de datos:");
        for (Libro l : libros) {
            System.out.println(l);
        }

        // 2. Insertar un nuevo libro para probar la persistencia
        System.out.println("\n➕ Insertando un nuevo libro...");
        boolean insertado = agregarLibro("Patrones de Diseño", "Erich Gamma", 1994, "978-0201633610");

        if (insertado) {
            System.out.println("✅ Libro guardado exitosamente en la base de datos.");
        }

        // 3. Mostrar la lista actualizada
        System.out.println("\n📚 Lista actualizada de libros:");
        for (Libro l : listarLibros()) {
            System.out.println(l);
        }
    }

    /**
     * Consulta y retorna todos los registros de la tabla 'libros' [00:12:46].
     */
    public static List<Libro> listarLibros() {
        List<Libro> lista = new ArrayList<>();
        String sql = "SELECT id, titulo, autor, anio_publicacion, isbn FROM libros";

        Connection cn = ConexionDB.obtenerConexion();
        if (cn != null) {
            try (PreparedStatement stmt = cn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    Libro libro = new Libro(
                            rs.getInt("id"),
                            rs.getString("titulo"),
                            rs.getString("autor"),
                            rs.getInt("anio_publicacion"),
                            rs.getString("isbn")
                    );
                    lista.add(libro);
                }
            } catch (SQLException e) {
                System.err.println("❌ Error al consultar libros: " + e.getMessage());
            } finally {
                ConexionDB.cerrarConexion(cn);
            }
        }
        return lista;
    }

    /**
     * Inserta un registro en la tabla 'libros'.
     */
    public static boolean agregarLibro(String titulo, String autor, int anio, String isbn) {
        String sql = "INSERT INTO libros (titulo, autor, anio_publicacion, isbn) VALUES (?, ?, ?, ?)";
        Connection cn = ConexionDB.obtenerConexion();

        if (cn != null) {
            try (PreparedStatement stmt = cn.prepareStatement(sql)) {
                stmt.setString(1, titulo);
                stmt.setString(2, autor);
                stmt.setInt(3, anio);
                stmt.setString(4, isbn);

                int filasAfectadas = stmt.executeUpdate();
                return filasAfectadas > 0;
            } catch (SQLException e) {
                System.err.println("❌ Error al insertar libro: " + e.getMessage());
            } finally {
                ConexionDB.cerrarConexion(cn);
            }
        }
        return false;
    }
}
