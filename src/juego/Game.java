package juego;

import filemanager.FileManager;
import inputmanager.InputManager;

public class Game {
	protected String word, letters = "* ";
	protected Player [] players;
	protected int failCount = 0, maxFails = 9;

	public Game() {}

	public Game(String word) {
		this.word = word.toUpperCase();
	}
	
	/*
	 * Print board game, character already written n fails count
	 */
	
	public void printBoard(Player player) {	
		InputManager.limpiarConsola();
				
		System.out.println("[Turno de "+ player.getName() +"]");
		if (failCount > 0)
			FileManager.printFileContent(("src/resources/0Fails.txt").replaceAll("0", failCount+""));
		
		System.out.println(word.replaceAll("[^"+letters+"]", " _ "));
		System.out.println("\nIntento de letras: ");
		for (String letter : (letters.replace("* ", "")).split("")) System.out.print(letter + " "); 
		System.out.println();
		System.out.println(failCount > 0 ? "\nNúmero de fallos: " + failCount + "/" + maxFails : "");
	}
	
	/*
	 * Check characters (Only 1 character, no digits, no repeat characters n no symbols)
	 * The game accepts 'Ñ'
	 */
	
	public void validateLetter(Player player) {
		boolean validate = false;
		String Letter;
		do {
			Letter = InputManager.getStringNotEmpty("una letra").toUpperCase();
			validate = (Letter.matches("[a-zA-Z]{1}") || Letter.equals("Ñ")) && !letters.contains(Letter)? true : false;
			System.out.print(Letter.length() > 1 ? "\n[!] Solo un caracter!\n" : 
							 Letter.matches("[0-9]") ? "\n[!] No números!\n" :
							 letters.contains(Letter) ? "\n[!] No puedes repetir una letra ya dicha!\n" :
							 !Letter.matches("[a-zA-Z0-9.]*") ? "\n[!] No simbolos!\n":"");
		} while (!validate);
		letters += Letter;
		failCount+= !(word).contains(Letter) ? 1 : 0;
		player.setWin(word.replaceAll("[^"+letters+"]", "").equals(word));
		
		
	}
	/*
	 * Check word, if it is not correct it will add 1 to the fail counter
	 */
	public boolean validateWord(Player player) {
		String response = InputManager.getStringNotEmpty("la palabra").toUpperCase();
		failCount+= !(word).equals(response) ? 1 : 0;
		player.setWin((word).equals(response));
		return !(word).equals(response);
	}
	/*
	 * Create players
	 */
	public void createPlayers(int n) {
		players = new Player [n];
		
		for (int i = 0; i < players.length; i++) 
			players[i] = new Player(InputManager.getStringNotEmpty("nombre del jugador " + (i+1)), false);
		
	}
	/*
	 * Player turns until 1 wins
	 */
	public void turns() {
		boolean finish = false;
		do {
			for (int i = 0; i < players.length; i++) {
				printBoard(players[i]);
				menu(players[i]);
				finish = players[i].isWin() || failCount == maxFails;
				if (players[i].isWin()) { 
					winMessage(players[i]);
					break;
				}
				if (failCount == maxFails) {
					loseMessage();
					break;
				}
			}
		} while (!finish);
		
	}
	/*
	 * Win n lose Message
	 */
	public void winMessage(Player player) {
		InputManager.limpiarConsola();
		System.out.println("--------------------[Felicidades " + player.getName() + " ganaste!]--------------------");
		System.out.println(" _    _______     _  _       _     _           _               _ ");
		System.out.println("|_|  (_______)   | |(_)     (_)   | |         | |             | |");
		System.out.println(" _    _____ _____| | _  ____ _  __| |_____  __| |_____  ___   | |");
		System.out.println("| |  |  ___) ___ | || |/ ___) |/ _  (____ |/ _  | ___ |/___)  |_|");
		System.out.println("| |  | |   | ____| || ( (___| ( (_| / ___ ( (_| | ____|___ |   _ ");
		System.out.println("|_|  |_|   |_____)\\_)_|\\____)_|\\____\\_____|\\____|_____|___/   |_|");
		System.out.println("                                                       ");
		System.out.println("--------------------[Felicidades " + player.getName() + " ganaste!]--------------------");
		System.out.println();
		System.out.println("[La palabra era: " + word + "] [Número de fallos: " + failCount + "/"+ maxFails+"]");
		restartGame();
	}
	
	public void loseMessage() {
		InputManager.limpiarConsola();
		System.out.println("  _____             _ _     _              __");
		System.out.println(" |  __ \\           | (_)   | |        _   / /");
		System.out.println(" | |__) |__ _ __ __| |_ ___| |_ ___  (_) | | ");
		System.out.println(" |  ___/ _ \\ '__/ _` | / __| __/ _ \\     | | ");
		System.out.println(" | |  |  __/ | | (_| | \\__ \\ ||  __/  _  | | ");
		System.out.println(" |_|   \\___|_|  \\__,_|_|___/\\__\\___| (_) | | ");
		System.out.println("                                          \\_\\");
		System.out.println("                                             ");
		System.out.println("[La palabra era: " + word + "] [Número de fallos: " + failCount + "/"+ maxFails+"]");
		restartGame();
	}
	/*
	 * Menu n options
	 */
	public void menu(Player player) {
		boolean validate = false;
		int response;
		do {
			System.out.println("=".repeat(30));
			System.out.println("1. Probar letra");
			System.out.println("2. Intentar adivinar palabra");
			System.out.println("=".repeat(30));
			response = InputManager.getNumberBiggerCondition("numero de opcion", 0);
			validate = response <=2;
			System.out.println(!validate ? "[!] Opcion no valida" : "");
		} while (!validate);
		
		if (response == 1) validateLetter(player); 
		if (response == 2) validateWord(player);
		
	}
	/*
	 * Restart game
	 */
	public void restartGame() {
		boolean validate = false;
		int response;
		do {
			System.out.println();
			System.out.println("¿Volver a jugar?\n1. Si\n2. Salir");
			System.out.println();
			response = InputManager.getNumberBiggerCondition("numero de opcion", 0);
			validate = response <=2;
			System.out.println(!validate ? "[!] Opcion no valida" : "");
		} while (!validate);
		InputManager.limpiarConsola();
		if (response == 1) {
			setWord(InputManager.getStringNEOC("la nueva palabra").toUpperCase());
			failCount = 0;
			letters = "* ";
			players = null;
			createPlayers(InputManager.getNumberBiggerCondition("numero de jugadores", 0));
			turns();
		}
		
	}


	/*
	 * Getters n Setters
	 */
	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		
		this.word = word;
	}
	
}
