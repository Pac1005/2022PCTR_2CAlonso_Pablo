

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Juego implements IJuego{
	//parametros de inicio
	int T = 5; //tipos de enemigos
	int N = 5; // numero de enemgios de cada tipo T
	int M = 7; // máximo de enemigos al mismo tiempo
	
	private int contadorEnemigosTotales; // numero de enemigos totales 
	Hashtable<Integer,Integer> contadoresEnemigosTipo; //<tipo, n_enemigos> enemigos actuales por tipos
	private Hashtable<Integer,Integer> contadoresEliminadosTipo; // enemigos eliminados por tipos
	int MAXENEMIGOS = M;
	int MINENEMIGOS = 0;
	
	private static Random generarAleatorios = new Random();	
	public Juego() {
		contadorEnemigosTotales=0;
		contadoresEnemigosTipo = new Hashtable<Integer,Integer>();
		contadoresEliminadosTipo = new Hashtable<Integer,Integer>();
		MAXENEMIGOS = M;
		for (int i=0;i<N;i++) {
			contadoresEnemigosTipo.put(i, 0);
			contadoresEliminadosTipo.put(i, 0);
		}
	}	

	
	public synchronized void generarEnemigo(int enemigo) {
		
		int Nenemigos = 0; 

		try {
			TimeUnit.MILLISECONDS.sleep(generarAleatorios.nextInt(3000));
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}		
		do {
			Nenemigos = 0;
			Enumeration<Integer> vivos = contadoresEnemigosTipo.elements();
			while (vivos.hasMoreElements()) {
				Nenemigos = Nenemigos + vivos.nextElement();
		}
		if (Nenemigos >= MAXENEMIGOS) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		} while (Nenemigos >= MAXENEMIGOS);
		contadorEnemigosTotales++;
		
		Integer NumEnemigosTipo = contadoresEnemigosTipo.get(enemigo);
		NumEnemigosTipo++;
		contadoresEnemigosTipo.put(enemigo, NumEnemigosTipo);
		System.out.println("\n\n Generado enemigo tipo " + enemigo);
		imprimirInfo(enemigo);
		checkInvariente();
	}

	
	public synchronized void eliminarEnemigo(int enemigo) {
		

		try {
			TimeUnit.MILLISECONDS.sleep(generarAleatorios.nextInt(3000));
		} catch(InterruptedException e) {
			Logger.getGlobal().log(Level.INFO,"Interrupcion del hilo que utiliza el objeto Juego ");
			return;
		}
		

		contadoresEnemigosTipo.put(enemigo, contadoresEnemigosTipo.get(enemigo)-1);
		contadoresEliminadosTipo.put(enemigo, contadoresEliminadosTipo.get(enemigo)+1);
		contadorEnemigosTotales--;
		System.out.println("\n\n Eliminado enemigo tipo " + enemigo);		
		imprimirInfo(enemigo);
		notifyAll();
		
		checkInvariente();
	}
	
	//muestra por pantalla la infomracion indicada en el anexo 
	private void imprimirInfo (int enemigo) {
		
		System.out.println("--> Enemigos totales " + contadorEnemigosTotales); 
		Enumeration<Integer> enemigos = contadoresEnemigosTipo.keys();
		int Nenemigo; 
		while (enemigos.hasMoreElements()) {
			Nenemigo = enemigos.nextElement();
			System.out.println("----> Enemigos tipo " + Nenemigo + ": " + contadoresEnemigosTipo.get(Nenemigo) + "------ [Eliminados :"+ contadoresEliminadosTipo.get(Nenemigo) +"]");
	
		}
	}
	private void checkInvariente() {
		int Nenemigos = 0;
		Enumeration<Integer> vivos = contadoresEnemigosTipo.elements();
		while (vivos.hasMoreElements()) {
			Nenemigos = Nenemigos + vivos.nextElement();
	}
	if (!(Nenemigos == contadorEnemigosTotales)) {
		System.out.println("NO SE CUMPLE EL INVARIANTE");
	}
	}

}
