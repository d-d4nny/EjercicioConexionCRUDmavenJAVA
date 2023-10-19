package controladores;

import java.sql.Connection;
import java.util.Scanner;

import servicios.ImplConexion;
import servicios.IntzConexion;
import servicios.ImplCRUD;
import servicios.IntzCRUD;
import servicios.ImplMenus;
import servicios.IntzMenus;

public class main {

    public static void main(String[] args) {
        // Instanciar implementaciones de las interfaces para usar en la aplicación
        IntzMenus menu = new ImplMenus();
        IntzConexion cpi = new ImplConexion();
        IntzCRUD crud = new ImplCRUD();

        Scanner scan = new Scanner(System.in);
        boolean cerrarMenu = false;
        int opcion;

        try {
            // Establecer la conexión con la base de datos
            Connection conexion = cpi.generaConexion();

            do {
                // Mostrar el menú principal
                menu.mostrarMenuMain();

                System.out.println("Introduce una opcion: ");
                opcion = scan.nextInt();

                switch (opcion) {
                    case 1:
                        // Llamar al método para insertar libros
                        crud.insertarLibros(conexion);
                        break;
                    case 2:
                        // Llamar al método para seleccionar libros
                        crud.seleccionaLibros(conexion);
                        break;
                    case 3:
                        // Llamar al método para modificar libros
                        crud.modificarLibros(conexion);
                        break;
                    case 4:
                        // Llamar al método para eliminar libros
                        crud.eliminarLibros(conexion);
                        break;
                    case 5:
                        // Cerrar el menú
                        cerrarMenu = true;
                        break;
                    default:
                        System.err.println("\n**[ERROR] Opción elegida no disponible **");
                        break;
                }

            } while (!cerrarMenu);
        } catch (Exception e) {
            System.out.println("[ERROR-Main] Se ha producido un error al ejecutar la aplicación: " + e);
        }

    }

}