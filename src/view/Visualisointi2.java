package view;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Visualisointi2 extends Canvas implements IVisualisointi{
	
	private GraphicsContext gc;
	
	 static int asiakasLkm;
	 static int kahvilaAsiakasLkm;
	 static int P1AsiakasLkm;
	 static int P2AsiakasLkm;
	 static int huoneAsiakasLkm;
	 static int ravintolaAsiakasLkm;

	public Visualisointi2(int w, int h) {
		super(w, h);
		gc = this.getGraphicsContext2D();
		tyhjennaNaytto();
	}

	public void tyhjennaNaytto() {
		gc.setFill(Color.BLACK);
		gc.fillRect(0, 0, this.getWidth(), this.getHeight());
	}
	
	@Override
	public void uusiAsiakas() {
		// TODO Auto-generated method stub
		asiakasLkm++;	
		gc.setFill(Color.BLACK);
		gc.fillRect(5, 25, 900, 50);
		gc.setFill(Color.WHITE);
		gc.setFont(new Font(20));
		gc.fillText("Asiakkaita saapunut hotelliin: " + asiakasLkm, 10, 50);
	}

}

