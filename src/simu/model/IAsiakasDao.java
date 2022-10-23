package simu.model;

public interface IAsiakasDao {

	public abstract boolean createAsiakas(Asiakas asiakas);
	//public abstract Asiakas readValuutta(String tunnus);
	public abstract Asiakas readAsiakas(int asiakasnro);
	public abstract Asiakas [] readAsiakkaat();
	public abstract boolean updateAsiakas(Asiakas asiakas);
	public abstract boolean deleteAsiakas(int asiakasnro);
}
