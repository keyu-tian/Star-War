package gamemain;

import java.awt.event.KeyEvent;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import gamefield.FieldConstants;
import gamefield.GameField;
import gameview.GameView;
import player.KeyPlayer;
import star.StarID;

public class GameMain {

//	private static final int DELAY_FEWER_MIRCOSECS = 180;
	private static final int DELAY_MORE_MIRCOSECS = 800;
	private static final int N_ROW = FieldConstants.DEFAULT_N_ROW;
	private static final int N_COL = FieldConstants.DEFAULT_N_COL;

	private static UpdateableField field;
	private static UpdateableView view;

	static {
		GameField gameField = new GameField(N_ROW, N_COL);
		field = gameField;
		view = new GameView(N_ROW, N_COL, gameField);
		view.addKeyPlayer(new KeyPlayer(StarID.P1, gameField, view, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_1));
		view.addKeyPlayer(new KeyPlayer(StarID.P2, gameField, view, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_ENTER));
		view.addKeyPlayer(new KeyPlayer(StarID.P3, gameField, view, KeyEvent.VK_G, KeyEvent.VK_B, KeyEvent.VK_V, KeyEvent.VK_N, KeyEvent.VK_SPACE));
//		view.addKeyPlayer(new KeyPlayer(StarID.P4, gameField, view, KeyEvent.VK_I, KeyEvent.VK_K, KeyEvent.VK_J, KeyEvent.VK_L, KeyEvent.VK_O));
	}

	public static void main(String[] args) {
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
		executor.scheduleWithFixedDelay(new Runnable() {
			@Override
			public void run() {
				field.updateFieldOneStep();
				view.updateView();
			}
		}, 0, DELAY_MORE_MIRCOSECS, TimeUnit.MILLISECONDS);
		
	}
}
