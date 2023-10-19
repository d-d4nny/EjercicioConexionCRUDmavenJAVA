package servicios;

public class ImplMenus implements IntzMenus{

	public void mostrarMenuMain() {		
		System.out.println("     1 Crear elementos     ");
		System.out.println("  2 Seleccionar elementos  ");
		System.out.println("   3 Modificar elementos   ");
		System.out.println("    4 Eliminar elementos   ");
		System.out.println("          5 Salir          ");
	}
	
	public void mostrarMenuSelect() {		
		System.out.println("      1 Seleccionar un registro      ");
		System.out.println("  2 Seleccionar todos los registros  ");
		System.out.println("              3 Volver        	     ");
	}
	
	public void mostrarMenuInsert() {		
		System.out.println("     1 Insertar un registro    ");
		System.out.println("  2 Insertar varios registros  ");
		System.out.println("            3 Volver           ");
	}
}
