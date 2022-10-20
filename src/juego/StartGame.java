package juego;

import inputmanager.InputManager;

public class StartGame {
	static Game g;
	public static void configGame() {
		g = new Game(InputManager.getStringNEOC("una palabra para el juego"));
	}
	public static void createPlayers() {
		g.createPlayers(InputManager.getNumberBiggerCondition("numero de jugadores", 0));
	}
	public static void iniGame() {
		g.turns();
	}
	
	public static void main(String[] args) throws Exception{
		configGame();
		createPlayers();
		iniGame();
	}

}
