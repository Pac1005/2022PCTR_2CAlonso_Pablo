

import java.util.Random;
import java.util.concurrent.TimeUnit;


/* Cada hilo se encarga de generar los enemigos de sutipo
 * "autoriza" a crear enemigos del tipo siguiente
 */
public class ActividadEnemiga implements Runnable {
	private int tipoEnemigo;
	private Juego J;
	private static Random generarAleatorios = new Random();
	@Override
	public void run() {
		/* Precondicion: Un tipo de enemigo de un numero mayor no podrá generarse hasta que se haya generado
		al menos un enemigo de un tipo del entero inmediatamente inferior */
		if (tipoEnemigo != 0) {
			while ((J.contadoresEnemigosTipo.get(tipoEnemigo-1) <= 0)) {
				try {
					TimeUnit.MILLISECONDS.sleep(generarAleatorios.nextInt(2000));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} 
		}
		for (int iteracion = 0; iteracion < J.N; iteracion ++) {
			J.generarEnemigo(tipoEnemigo);
		}
	}
	public ActividadEnemiga(int tipoEnemigo, IJuego J) {
			this.tipoEnemigo = tipoEnemigo;
			this.J = (Juego) J;
			
	}

}
