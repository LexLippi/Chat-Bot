package chat_bot.game;

import chat_bot.*;
import chat_bot.game.levels.*;
import chat_bot.game.return_types.GameReturnedValue;
import chat_bot.game.return_types.GameState;

import java.util.Random;

public class CityGame implements IGame {
	private Api api;
	private GameLevel level;
	private GameState currentState;
	private GameState[][] jumpTable;
	private Data data;

	public static void main(String[] args) {
		var game = new CityGame(new Console(), new Data(new City[]
				{
						new City ("Москва", 1000000),
						new City ("Минск", 50000)
				}));
		game.startGame();
	}

	public CityGame(Api api, Data data) {
		this.api = api;
		this.data = data;
		createJumpTable();
		currentState = GameState.SelectLevel;
	}

	public CityGame(Api api) {
		this.data = new Data();
		this.api = api;
		createJumpTable();
		currentState = GameState.SelectLevel;
	}

	@Override
	public GameReturnedValue process(String answer) {
		return null;
	}

	public void startGame() {
		switch (currentState){
			case SelectLevel:
				try {
					var returnedLevel = getDifficultLevel();
					var jump = 2;
					if (returnedLevel != null) {
						level = returnedLevel;
						jump = 1;
					}
					currentState = jumpTable[currentState.ordinal()][jump];
				}
				catch (IllegalArgumentException e) {
					currentState = jumpTable[currentState.ordinal()][0];
				}
				break;
			case GetDraw:
				try {
					var jump = (getDraw()) ? 2 : 1 ;
					currentState = jumpTable[currentState.ordinal()][jump];
					var answer = (jump == 2) ? "Ты победил, не ожидал от тебя такой прыти. Ходи!" :
							"Ха-ха. Ходить буду я!";
					api.out(answer);
					break;
				}
				catch (IllegalArgumentException e) {
					var jump = 0;
					currentState = jumpTable[currentState.ordinal()][jump];
					var answer = "Кажется, ты выбрал не орла или решку, а что-то посерьезнее... Попробуй повторить!";
					api.out(answer);
				}
				break;
			case FirstBotCourse:
				var gameResult = level.getBotCourse();
				var jump = (gameResult.getType() != null) ? 1 : 0;
				currentState = jumpTable[currentState.ordinal()][jump];
				api.out(gameResult.getMessage());
				break;
			case BotCourse:
				var inputString = api.in().toLowerCase();
				gameResult = level.processingUserCourse(inputString);
				jump =  (gameResult.getType() != null) ? 1 : 0;
				currentState = jumpTable[currentState.ordinal()][jump];
				break;
			case Exit:
				break;
			default:
				throw new IllegalArgumentException();
		}
	}

	private GameLevel getDifficultLevel() {
		api.out("Выбери уровень сложности: легкий, средний, тяжелый");
		var userLevel = api.in().toLowerCase();
		return new LevelFactory(data, api).getLevel(userLevel);
	}

	private boolean getDraw() {
		api.out("Пора выбрать, кто будет ходить первым! Орёл или решка?");
		var side = api.in().toLowerCase();
		if (side.compareTo("орёл") == 0 || side.compareTo("решка") == 0) {
			return getDrawResult(side);
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
		jumpTable = new GameState[4][4];
		jumpTable[0][0] = GameState.SelectLevel;
		jumpTable[0][1] = GameState.GetDraw;
		jumpTable[0][2] = GameState.Exit;
		jumpTable[1][0] = GameState.GetDraw;
		jumpTable[1][1] = GameState.FirstBotCourse;
		jumpTable[1][2] = GameState.BotCourse;
		jumpTable[1][3] = GameState.Exit;
		jumpTable[2][0] = GameState.BotCourse;
		jumpTable[2][1] = GameState.Exit;
		jumpTable[3][0] = GameState.BotCourse;
		jumpTable[3][1] = GameState.Exit;
	}
}
