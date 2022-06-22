import java.util.Hashtable;

public class SistemaLanzador {
	
	public static void main(String[] args) {
		Juego J = new Juego();
		Hashtable<Integer, Thread> hilos_enemigos = new Hashtable<Integer,Thread>();
		Hashtable<Integer, Thread> hilos_aliados = new Hashtable<Integer,Thread>(); 
		
		for (int i=0; i < J.T; i++) {
			hilos_enemigos.put(i, new Thread(new ActividadEnemiga(i, J)));
			hilos_enemigos.get(i).start();
			hilos_aliados.put(i, new Thread(new ActividadAliada(i, J)));
			hilos_aliados.get(i).start();
		}
	}

}	




