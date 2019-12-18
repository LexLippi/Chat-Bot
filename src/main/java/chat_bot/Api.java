package chat_bot;

import java.util.ArrayList;

public interface Api {
	public void out(String massage);

	public boolean invite(String name);

	public void cancelInvision(boolean broadcast);

    public void outkeyboard(ArrayList buttons, String message);
}
