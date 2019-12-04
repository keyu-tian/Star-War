package player;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

import gamefield.Position;
import gamemain.UpdateableView;
import star.StarID;

public class KeyPlayer extends Player implements KeyListener {

	private int selectionKey;
	private HashMap<Integer, Position> deltaPosition = new HashMap<>();

	// 构造器
	public KeyPlayer(StarID ID, ControllableField field, UpdateableView view, int upKey, int downKey, int leftKey, int rightKey, int selectionKey) {
		super(ID, field, view);
		deltaPosition.put(upKey, new Position(-1, 0));
		deltaPosition.put(downKey, new Position(1, 0));
		deltaPosition.put(leftKey, new Position(0, -1));
		deltaPosition.put(rightKey, new Position(0, 1));
		this.selectionKey = selectionKey;
	}
	
	// 工具：两个控制逻辑
	private void select() {
		if (this.selectedPos == null) {
			if (field.isSelectableAt(this.ID, this.focusedPos)) {
				this.selectedPos = this.focusedPos;
			} else {
				System.out.println("Invalid Selection(not controllable)");
			}
		} else if (!this.selectedPos.equals(this.focusedPos)) {
			if (field.operateConnection(this.ID, this.selectedPos, this.focusedPos)) {
				System.out.printf("Valid Operation: from (%d, %d) to (%d %d)\n", selectedPos.row, selectedPos.col, focusedPos.row, focusedPos.col);
				this.selectedPos = null;	// 增删成功
			} else {
				System.out.println("Invalid Operation");
//				this.selectedPos = null;	// 增删失败
			}
		} else {
			System.out.println("Invalid Selection(repeated)");
			this.selectedPos = null;		// 重选
		}
	}
	private void move(Position delta) {
		Position newPos = this.focusedPos.add(delta);
		if (field.isFocusableAt(ID, newPos))
			this.focusedPos = newPos;
	}

	// KeyListener接口实现
	@Override
	public void keyTyped(KeyEvent e) {}
	@Override
	public void keyReleased(KeyEvent e) {}
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == this.selectionKey) {
			this.select();
			view.updateView();
		}
		else if (deltaPosition.containsKey(e.getKeyCode())) {
			this.move(deltaPosition.get(e.getKeyCode()));
			view.updateView();
		}
	}
}
