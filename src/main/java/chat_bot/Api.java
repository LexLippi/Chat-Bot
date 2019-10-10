package chat_bot;

public interface Api {
	public String in() throws NoInputException;
	public void out(String massage);
}
