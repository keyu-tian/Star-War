package gameview;

import gamefield.Position;
import star.StarID;

public interface ViewablePlayer {

	/**
	 * 查询该Player所属的ID
	 */
	public StarID getID();
	
	/**
	 * 获取该Player当前聚焦的位置 @return
	 */
	public Position getFocusedPos();
	
	/**
	 * 获取该Player历史选中的位置 @return，不存在则返回null
	 */
	public Position getSelectedPos();
}
