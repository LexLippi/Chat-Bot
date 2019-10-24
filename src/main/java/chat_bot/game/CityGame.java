package chat_bot.game;

import chat_bot.*;
import chat_bot.game.levels.*;
import chat_bot.game.return_types.GameReturnedValue;
import chat_bot.game.return_types.GameState;

import java.util.Random;

public class CityGame implements IGame {
	private GameLevel level;
	private GameState currentState;
	private GameState[][] jumpTable;
	private Data data;

	public static void main(String[] args) {
		var game = new CityGame(new Data(new City[]
				{
						new City ("Москва", 1000000),
						new City ("Минск", 50000)
				}));
		game.startGame();
	}

	public CityGame(Data data) {
		this.data = data;
		createJumpTable();
		currentState = GameState.SelectLevel;
	}

	public CityGame() {
		this.data = new Data();
		createJumpTable();
		currentState = GameState.SelectLevel;
	}

	@Override
	public GameReturnedValue process(String inputString) {
		inputString = inputString.toLowerCase();
		switch (currentState){
			case SelectLevel:
				try {
					var returnedLevel = new LevelFactory(data).getLevel(inputString);;
					var jump = 2;
					if (returnedLevel != null) {
						level = returnedLevel;
						jump = 1;
					}
					currentState = jumpTable[currentState.ordinal()][jump];
					return new GameReturnedValue(null, "Пора выбрать, кто будет ходить первым! Орёл или решка?");
				}
				catch (IllegalArgumentException e) {
					currentState = jumpTable[currentState.ordinal()][0];
					return new GameReturnedValue(null, "Я не знаю такого уровня, попробуй снова!");
				}
			case GetDraw:
				try {
					var answer = "Ты победил, не ожидал от тебя такой прыти. Ходи!";
					if (!getDraw(inputString)) {
						answer = "Ха-ха. Ходить буду я!";
						var gameResult = level.getBotCourse();
						currentState = jumpTable[currentState.ordinal()][1];
						return new GameReturnedValue(gameResult.getType(), gameResult.getMessages()[0], answer);
					}
					currentState = jumpTable[currentState.ordinal()][1];
					return new GameReturnedValue(null, answer);
				}
				catch (IllegalArgumentException e) {
					var jump = 0;
					currentState = jumpTable[currentState.ordinal()][jump];
					var answer = "Кажется, ты выбрал не орла или решку, а что-то посерьезнее... Попробуй повторить!";
					return new GameReturnedValue(null, answer);
				}
			case BotCourse:
				var gameResult = level.processingUserCourse(inputString);
				var jump =  (gameResult.getType() != null) ? 1 : 0;
				currentState = jumpTable[currentState.ordinal()][jump];
				return gameResult;
			default:
				throw new IllegalArgumentException();
		}
	}

	public GameReturnedValue startGame() {
		return new GameReturnedValue(null, "Выбери уровень сложности: легкий, средний, тяжелый");
	}

	private boolean getDraw(String inputString) {
		if (inputString.compareTo("орёл") == 0 || inputString.compareTo("решка") == 0) {
			return getDrawResult(inputString);
		}
		throw new IllegalArgumentException();
	}

	private boolean getDrawResult(String side) {
		var rnd = new Random();
		var result = rnd.nextInt(2);
		var resultSide = result == 0 ? "орёл" : "решка";
		return side.compareTo(resultSide) == 0;
	}

	private void createJumpTable() {
		jumpTable = new GameState[3][3];
		jumpTable[0][0] = GameState.SelectLevel;
		jumpTable[0][1] = GameState.GetDraw;
		jumpTable[0][2] = GameState.Exit;
		jumpTable[1][0] = GameState.GetDraw;
		jumpTable[1][1] = GameState.BotCourse;
		jumpTable[1][2] = GameState.Exit;
		jumpTable[2][0] = GameState.BotCourse;
		jumpTable[2][1] = GameState.Exit;
	}
}
