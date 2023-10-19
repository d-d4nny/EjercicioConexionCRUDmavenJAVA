package servicios;

import java.sql.Connection;
import java.util.ArrayList;
import entidades.LibroDto;

public interface IntzCRUD {

    // Método para seleccionar libros //
    public ArrayList<LibroDto> seleccionaLibros(Connection conexionGenerada);

    // Método para insertar libros
    public ArrayList<LibroDto> insertarLibros(Connection conexionGenerada);

    // Método para modificar libros
    public ArrayList<LibroDto> modificarLibros(Connection conexionGenerada);

    // Método para eliminar libros
    public ArrayList<LibroDto> eliminarLibros(Connection conexionGenerada);
}