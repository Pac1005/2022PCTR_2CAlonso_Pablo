

import java.util.Random;
import java.util.concurrent.TimeUnit;



// matan hilos enemigos
public class ActividadAliada implements Runnable {
	private int tipoEnemigo;
	private Juego J;
	private static Random generarAleatorios = new Random();
	public ActividadAliada(int tipoEnemigo, IJuego J) {
		this.tipoEnemigo = tipoEnemigo;
		this.J = (Juego) J;
	}

	@Override
	public void run() {
		for (int i = 0; i < J.N; i ++) {
			// nunca puede haber menos de 0 enemigos.
				while ((J.contadoresEnemigosTipo.get(tipoEnemigo) <= 0)) {
					try {
						TimeUnit.MILLISECONDS.sleep(generarAleatorios.nextInt(5000));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				} 
			J.eliminarEnemigo(tipoEnemigo);
		}
	}
	

}
