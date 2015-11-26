
public class Case {
	private boolean courantDair;
	private boolean dangersPuit;
	private boolean odeurInfame;
	private boolean dangersWumpus;
	private boolean puit;
	private boolean wumpus;
	private boolean safe;
	private boolean visite;
	
	public Case(){
		this.courantDair=false;
		this.dangersPuit=true;
		this.odeurInfame=false;
		this.dangersWumpus=true;
		this.puit=false;
		this.wumpus=false;
		this.safe=false;
		this.visite = false;
	}

	public boolean getCourantDair() {
		return courantDair;
	}

	public void setCourantDair(boolean courantDair) {
		this.courantDair = courantDair;
	}

	public boolean getDangersPuit() {
		return dangersPuit;
	}

	public void setDangersPuit(boolean dangersPuit) {
		this.dangersPuit = dangersPuit;
	}

	public boolean getOdeurInfame() {
		return odeurInfame;
	}

	public void setOdeurInfame(boolean odeurInfame) {
		this.odeurInfame = odeurInfame;
	}

	public boolean getDangersWumpus() {
		return dangersWumpus;
	}

	public void setDangersWumpus(boolean dangersWumpus) {
		this.dangersWumpus = dangersWumpus;
	}

	public boolean getPuit() {
		return puit;
	}

	public void setPuit(boolean puit) {
		this.puit = puit;
	}

	public boolean getWumpus() {
		return wumpus;
	}

	public void setWumpus(boolean wumpus) {
		this.wumpus = wumpus;
	}

	public boolean getSafe() {
		return safe;
	}

	public void setSafe(boolean safe) {
		this.safe = safe;
	}
	
	public boolean getVisite() {
		return visite;
	}

	public void setVisite(boolean visite) {
		this.visite = visite;
	}
	
	
}
