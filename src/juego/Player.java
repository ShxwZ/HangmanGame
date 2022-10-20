package juego;

public class Player {
	protected String name;
	protected boolean win;
	
	public Player(String name, boolean win) {
		this.name = name;
		this.win = win;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isWin() {
		return win;
	}

	public void setWin(boolean win) {
		this.win = win;
	}
	
	
	
}
