package servicios;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import entidades.LibroDto;
import utils.ADto;

public class ImplCRUD implements IntzCRUD{

	 // Instanciar implementaciones de las interfaces para usar en la aplicación
	IntzMenus intM = new ImplMenus();
	Statement declaracionSQL = null;
	ResultSet resultadoConsulta = null;
	ADto adto = new ADto();
	ArrayList<LibroDto> listaLibros = new ArrayList<>();
	
	@Override
	public ArrayList<LibroDto> seleccionaLibros(Connection conexionGenerada) {
	    // Scanner para entrada de usuario
	    Scanner scan = new Scanner(System.in);

	    // Bandera para controlar el cierre del menú
	    boolean cerrarMenu = false;
	    int opcion;

	    try {
	        do {
	            // Mostrar el menú de selección
	            intM.mostrarMenuSelect();
	            System.out.println("Introduce una opcion: ");
	            opcion = scan.nextInt();

	            switch (opcion) {
	                case 1:
	                    try {
	                        // Crear una declaración para ejecutar una consulta SQL
	                        declaracionSQL = conexionGenerada.createStatement();
	                        ResultSet consultaPredeterminada = declaracionSQL.executeQuery("SELECT * FROM gbp_almacen.gbp_alm_cat_libros");

	                        // Convertir los resultados de la consulta a objetos LibroDto
	                        listaLibros = adto.resultsALibrosDto(consultaPredeterminada);

	                        int i = listaLibros.size();
	                        for (int cont = 0; cont < i; cont++) {
	                            System.out.println(listaLibros.get(cont).queryBase());
	                        }

	                        System.out.print("Selecciona el ID del libro que quieres mostrar: ");
	                        long selectLibro = scan.nextLong();

	                        boolean libroEncontrado = false;
	                        // Mostrar detalles del libro seleccionado
	                        for (LibroDto libro : listaLibros) {
	                            if (libro.getIdLibro() == selectLibro) {
	                                System.out.println("Detalles del libro:");
	                                System.out.println("ID Libro: " + libro.getIdLibro());
	                                System.out.println("Título: " + libro.getTitulo());
	                                System.out.println("Autor: " + libro.getAutor());
	                                System.out.println("ISBN: " + libro.getIsbn());
	                                System.out.println("Edición: " + libro.getEdicion());
	                                libroEncontrado = true;
	                                break;
	                            }
	                        }

	                        if (!libroEncontrado) {
	                            System.out.println("No se encontró un libro con el ID proporcionado.");
	                        }

	                        consultaPredeterminada.close();
	                        //declaracionSQL.close();

	                    } catch (SQLException e) {
	                        System.out.println("[ERROR-ConsultasPostgresqlImplementacion-seleccionaTodosLibros] Error generando o ejecutando la declaracionSQL: " + e);
	                        return listaLibros;
	                    }
	                    declaracionSQL.close();
	                    break;
	                case 2:
	                    try {
	                        // Crear una declaración para ejecutar una consulta SQL
	                        declaracionSQL = conexionGenerada.createStatement();
	                        resultadoConsulta = declaracionSQL.executeQuery("SELECT * FROM gbp_almacen.gbp_alm_cat_libros");

	                        // Convertir los resultados de la consulta a objetos LibroDto
	                        listaLibros = adto.resultsALibrosDto(resultadoConsulta);

	                        int i = listaLibros.size();
	                        for (int cont = 0; cont < i; cont++) {
	                            System.out.println(listaLibros.get(cont).toString());
	                        }

	                        resultadoConsulta.close();
	                        //declaracionSQL.close();

	                    } catch (SQLException e) {
	                        System.out.println("[ERROR-ConsultasPostgresqlImplementacion-seleccionaTodosLibros] Error generando o ejecutando la declaracionSQL: " + e);
	                        return listaLibros;
	                    }
	                    declaracionSQL.close();
	                    break;
	                case 3:
	                    cerrarMenu = true;
	                    break;
	                default:
	                    System.err.println("\n**[ERROR] Opción elegida no disponible **");
	                    break;
	            }

	        } while (!cerrarMenu);

	    } catch (InputMismatchException e) {
	        System.err.println("\n**[ERROR] Entrada inválida: por favor ingrese un número entero **");
	    } catch (NullPointerException npe) {
	        System.err.println("\n**[ERROR] Ocurrió una excepción no esperada: " + npe.getMessage() + " **");
	    } catch (Exception e) {
	        System.err.println("\n**[ERROR] Ocurrió una excepción no esperada: " + e.getMessage() + " **");
	    }
	    return listaLibros;
	}
	
	public ArrayList<LibroDto> insertarLibros(Connection conexionGenerada) {

	    // Scanner para entrada de usuario
	    Scanner scan = new Scanner(System.in);

	    // Bandera para controlar el cierre del menú
	    boolean cerrarMenu = false;
	    int opcion;

	    try {
	        do {
	            // Mostrar el menú de inserción
	            intM.mostrarMenuInsert();

	            System.out.println("Introduce una opcion: ");
	            opcion = scan.nextInt();

	            switch (opcion) {
	                case 1:
	                    // Inserción de un solo libro
	                    Scanner scanner = new Scanner(System.in);

	                    System.out.print("Ingrese el título del nuevo libro: ");
	                    String titulo = scanner.nextLine();

	                    System.out.print("Ingrese el autor del nuevo libro: ");
	                    String autor = scanner.nextLine();

	                    System.out.print("Ingrese el ISBN del nuevo libro: ");
	                    String isbn = scanner.nextLine();

	                    System.out.print("Ingrese la edición del nuevo libro: ");
	                    int edicion = scanner.nextInt();

	                    try {
	                        // Crear una declaración para ejecutar una consulta SQL
	                        declaracionSQL = conexionGenerada.createStatement();

	                        // Construir la consulta de inserción
	                        String query = String.format("INSERT INTO gbp_almacen.gbp_alm_cat_libros (titulo, autor, isbn, edicion) VALUES ('%s', '%s', '%s', %d)",
	                                titulo, autor, isbn, edicion);

	                        // Ejecutar la consulta de inserción
	                        int filasAfectadas = declaracionSQL.executeUpdate(query);

	                        // Mostrar el resultado de la inserción
	                        if (filasAfectadas > 0) {
	                            System.out.println("Nuevo libro agregado exitosamente.");
	                        } else {
	                            System.out.println("No se pudo agregar el nuevo libro.");
	                        }

	                        declaracionSQL.close();

	                    } catch (SQLException e) {
	                        System.out.println("[ERROR] Error al agregar un nuevo libro: " + e);
	                    }

	                    break;
	                case 2:
	                    // Inserción de múltiples libros
	                    System.out.print("¿Cuántas inserciones desea realizar?: ");
	                    int numInserciones = scan.nextInt();
	                    scan.nextLine();

	                    for (int i = 0; i < numInserciones; i++) {
	                        System.out.println("Inserción #" + (i + 1));

	                        System.out.print("Ingrese el título del nuevo libro: ");
	                        String tituloInsert = scan.nextLine();

	                        System.out.print("Ingrese el autor del nuevo libro: ");
	                        String autorInsert = scan.nextLine();

	                        System.out.print("Ingrese el ISBN del nuevo libro: ");
	                        String isbnInsert = scan.nextLine();

	                        System.out.print("Ingrese la edición del nuevo libro: ");
	                        int edicionInsert = scan.nextInt();
	                        scan.nextLine();

	                        listaLibros.add(new LibroDto(0, tituloInsert, autorInsert, isbnInsert, edicionInsert));
	                    }

	                    try {
	                        // Crear una declaración para ejecutar una consulta SQL
	                        declaracionSQL = conexionGenerada.createStatement();

	                        // Preparar las consultas de inserción para cada libro
	                        for (LibroDto libro : listaLibros) {
	                            String query = String.format("INSERT INTO gbp_almacen.gbp_alm_cat_libros (titulo, autor, isbn, edicion) VALUES ('%s', '%s', '%s', %d)",
	                                    libro.getTitulo(), libro.getAutor(), libro.getIsbn(), libro.getEdicion());

	                            declaracionSQL.addBatch(query);
	                        }

	                        // Ejecutar las consultas de inserción
	                        int[] filasAfectadas = declaracionSQL.executeBatch();

	                        System.out.println("Inserciones realizadas exitosamente.");
	                        declaracionSQL.close();

	                    } catch (SQLException e) {
	                        System.out.println("[ERROR] Error al agregar nuevos libros: " + e);
	                    }

	                    break;
	                case 3:
	                    // Cerrar el menú
	                    cerrarMenu = true;
	                    break;
	                default:
	                    System.err.println("\n**[ERROR] Opción elegida no disponible **");
	                    break;
	            }

	        } while (!cerrarMenu);

	    } catch (InputMismatchException e) {
	        System.err.println("\n**[ERROR] Entrada inválida: por favor ingrese un número entero **");
	    } catch (NullPointerException npe) {
	        System.err.println("\n**[ERROR] Ocurrió una excepción no esperada: " + npe + " **");
	    } catch (Exception e) {
	        System.err.println("\n**[ERROR] Ocurrió una excepción no esperada: " + e + " **");
	    }
	    return listaLibros;
	}
	
	public ArrayList<LibroDto> modificarLibros(Connection conexionGenerada) {

	    // Inicialización del scanner para entrada de usuario
	    Scanner scanner = new Scanner(System.in);

	    try {
	        // Crear una declaración para ejecutar una consulta SQL
	        Statement statement = conexionGenerada.createStatement();

	        // Ejecutar la consulta para obtener los libros disponibles
	        ResultSet resultSet = statement.executeQuery("SELECT id_libro, titulo FROM gbp_almacen.gbp_alm_cat_libros");

	        // Mostrar los detalles de los libros
	        while (resultSet.next()) {
	            int idLibro = resultSet.getInt("id_libro");
	            String titulo = resultSet.getString("titulo");
	            System.out.println("ID Libro: " + idLibro + ", Título: " + titulo);
	        }

	        // Solicitar al usuario el ID del libro que desea modificar
	        System.out.print("Seleccione el ID del libro que desea modificar: ");
	        int idLibroSeleccionado = scanner.nextInt();
	        scanner.nextLine();

	        // Mostrar los campos disponibles para modificar
	        System.out.println("Campos disponibles para modificar (seleccione primero los campos y luego pulse 5 para confirmar): ");
	        System.out.println("1. Título");
	        System.out.println("2. Autor");
	        System.out.println("3. ISBN");
	        System.out.println("4. Edición");
	        System.out.println("5. Confirmar Selección");

	        // Lista para almacenar los campos a modificar
	        ArrayList<String> camposAModificar = new ArrayList<>();
	        boolean seguirModificando = true;

	        // Permitir al usuario seleccionar los campos a modificar
	        while (seguirModificando) {
	            System.out.print("Introduzca el campo: ");
	            int opcion = scanner.nextInt();
	            scanner.nextLine();  // Limpiar el buffer

	            switch (opcion) {
	                case 1:
	                    camposAModificar.add("titulo");
	                    break;
	                case 2:
	                    camposAModificar.add("autor");
	                    break;
	                case 3:
	                    camposAModificar.add("isbn");
	                    break;
	                case 4:
	                    camposAModificar.add("edicion");
	                    break;
	                case 5:
	                    seguirModificando = false;
	                    break;
	                default:
	                    System.out.println("Opción no válida.");
	                    break;
	            }
	        }

	        // Modificar los campos seleccionados
	        for (String campoAModificar : camposAModificar) {
	            System.out.print("Ingrese el nuevo valor para el campo " + campoAModificar + ": ");
	            String nuevoValor = scanner.nextLine();

	            if (campoAModificar.equals("edicion")) {
	                try {
	                    int edicion = Integer.parseInt(nuevoValor);

	                    if (edicion < 0 || edicion > 32767) {
	                        System.out.println("El valor para edición debe estar en el rango de un smallint (0-32767).");
	                        continue;
	                    }

	                    // Preparar la consulta de actualización para campo "edicion"
	                    String query = "UPDATE gbp_almacen.gbp_alm_cat_libros SET " + campoAModificar + " = ? WHERE id_libro = ?";
	                    PreparedStatement preparedStatement = conexionGenerada.prepareStatement(query);
	                    preparedStatement.setInt(1, edicion);
	                    preparedStatement.setInt(2, idLibroSeleccionado);

	                    // Ejecutar la consulta de actualización
	                    int filasAfectadas = preparedStatement.executeUpdate();

	                    // Mostrar el resultado de la actualización
	                    if (filasAfectadas > 0) {
	                        System.out.println("Campo " + campoAModificar + " modificado exitosamente.");
	                    } else {
	                        System.out.println("No se pudo modificar el campo " + campoAModificar + ".");
	                    }

	                    preparedStatement.close();
	                } catch (NumberFormatException e) {
	                    System.out.println("El valor para edición debe ser un número entero.");
	                    continue;
	                }
	            } else {             

	                // Preparar la consulta de actualización para otros campos
	                String query = "UPDATE gbp_almacen.gbp_alm_cat_libros SET " + campoAModificar + " = ? WHERE id_libro = ?";
	                PreparedStatement preparedStatement = conexionGenerada.prepareStatement(query);
	                preparedStatement.setString(1, nuevoValor);
	                preparedStatement.setInt(2, idLibroSeleccionado);

	                // Ejecutar la consulta de actualización
	                int filasAfectadas = preparedStatement.executeUpdate();

	                // Mostrar el resultado de la actualización
	                if (filasAfectadas > 0) {
	                    System.out.println("Campo " + campoAModificar + " modificado exitosamente.");
	                } else {
	                    System.out.println("No se pudo modificar el campo " + campoAModificar + ".");
	                }

	                preparedStatement.close();
	            }
	        }

	        // Cerrar el ResultSet y la Statement
	        resultSet.close();
	        statement.close();

	    } catch (SQLException e) {
	        // Capturar y mostrar errores de SQL
	        System.err.println("Error al modificar el libro: " + e);
	    }

	    // Devolver la lista de libros (aunque en este caso no se usa)
	    return listaLibros;
	}
	
	public ArrayList<LibroDto> eliminarLibros(Connection conexionGenerada) {

	    // Inicialización del scanner para entrada de usuario
	    Scanner scanner = new Scanner(System.in);

	    try {
	        // Crear una declaración para ejecutar una consulta SQL
	        Statement statement = conexionGenerada.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

	        // Ejecutar la consulta para obtener los libros disponibles
	        ResultSet resultSet = statement.executeQuery("SELECT id_libro, titulo FROM gbp_almacen.gbp_alm_cat_libros");

	        // Mostrar los detalles de los libros
	        while (resultSet.next()) {
	            int idLibro = resultSet.getInt("id_libro");
	            String titulo = resultSet.getString("titulo");
	            System.out.println("ID Libro: " + idLibro + ", Título: " + titulo);
	        }

	        // Solicitar al usuario el ID del libro que desea eliminar
	        System.out.print("Seleccione el ID del libro que desea eliminar: ");
	        int idLibroEliminar = scanner.nextInt();
	        scanner.nextLine();

	        // Mover el cursor al principio del resultado
	        resultSet.beforeFirst();
	        String tituloLibroEliminar = null;

	        // Buscar el título del libro a eliminar
	        while (resultSet.next()) {
	            int idLibro = resultSet.getInt("id_libro");
	            if (idLibro == idLibroEliminar) {
	                tituloLibroEliminar = resultSet.getString("titulo");
	                break;
	            }
	        }

	        // Verificar si se encontró el libro a eliminar
	        if (tituloLibroEliminar != null) {
	            // Solicitar confirmación al usuario
	            System.out.print("Para confirmar la eliminación, escriba el nombre completo del libro a borrar ("
	                    + tituloLibroEliminar + "): ");
	            String confirmacion = scanner.nextLine();

	            // Verificar la confirmación del usuario
	            if (confirmacion.equalsIgnoreCase(tituloLibroEliminar)) {
	                // Preparar la consulta de eliminación
	                String query = "DELETE FROM gbp_almacen.gbp_alm_cat_libros WHERE id_libro = ?";
	                PreparedStatement preparedStatement = conexionGenerada.prepareStatement(query);
	                preparedStatement.setInt(1, idLibroEliminar);

	                // Ejecutar la consulta de eliminación
	                int filasAfectadas = preparedStatement.executeUpdate();

	                // Mostrar el resultado de la eliminación
	                if (filasAfectadas > 0) {
	                    System.out.println("Libro eliminado exitosamente.");
	                } else {
	                    System.out.println("No se pudo eliminar el libro.");
	                }
	            } else {
	                System.out.println("La confirmación no coincide. No se ha eliminado el libro.");
	            }
	        } else {
	            System.out.println("No se encontró un libro con el ID proporcionado.");
	        }

	        // Cerrar el ResultSet y la Statement
	        resultSet.close();
	        statement.close();
	    } catch (SQLException e) {
	        // Capturar y mostrar errores de SQL
	        System.err.println("Error al eliminar el libro: " + e);
	    }

	    // Devolver la lista de libros (aunque en este caso no se usa)
	    return listaLibros;
	}

}