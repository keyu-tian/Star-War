package gamemain;

import player.KeyPlayer;

public interface UpdateableView {

//	public void addMousePlayer(KeyPlayer player);
	public void addKeyPlayer(KeyPlayer player);
	public void updateView();
}
