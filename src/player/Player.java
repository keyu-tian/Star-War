package player;

import gamefield.Position;
import gamemain.UpdateableView;
import gameview.ViewablePlayer;
import star.StarID;

public abstract class Player implements ViewablePlayer {
	
	protected StarID ID;
	protected Position focusedPos;
	protected Position selectedPos;
	protected ControllableField field;
	protected UpdateableView view;

	public Player(StarID ID, ControllableField field, UpdateableView view) {
		this.ID = ID;
		this.field = field;
		this.view = view;
		this.focusedPos = field.getDefaultPos(ID);
		this.selectedPos = null;
	}

	// ViewablePlayer接口实现
	@Override
	public StarID getID() {
		return this.ID;
	}
	@Override
	public Position getFocusedPos() {
		return this.focusedPos;
	}
	@Override
	public Position getSelectedPos() {
		return this.selectedPos;
	}
}
