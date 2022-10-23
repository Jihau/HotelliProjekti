package view;

public interface ISimulaattorinUI {
	
	// Kontrolleri tarvitsee syötteitä, jotka se välittää Moottorille
	public double getAika();
	public long getViive();
	
	//Kontrolleri antaa käyttöliittymälle tuloksia, joita Moottori tuottaa 
	public void setLoppuaika(double aika);
	
	// Kontrolleri tarvitsee  
	public IVisualisointi getVisualisointi();
	
	/**
	 * Toteuttavan luokan tulee toteuttaa tämä metodi. 
	 * Palauttaa asiakaskohtaisen todennakoisyyden kahvilaan menoon
	 * @return todennakoisyys asiakkaan kahvilaan menoon
	 */
	public int getKahvilanTodennakoisyys();
	/**
	 * Toteuttavan luokan tulee toteuttaa tämä metodi. 
	 * Palauttaa asiakaskohtaisen todennakoisyyden ennakkovaraukselle
	 * @return todennakoisyys asiakkaan ennakkovaraukselle
	 */
	public int getVarauksenTodennakoisyys();

}
