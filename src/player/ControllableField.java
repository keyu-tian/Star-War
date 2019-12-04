package player;

import gamefield.Position;
import star.StarID;

public interface ControllableField {

	/**
	 * 查询Field内的 @param pos 位置是否能被 @param ID 聚焦
	 */
	public boolean isFocusableAt(StarID ID, Position pos);

	/**
	 * 查询Field内的 @param pos 位置是否能被 @param ID 选中
	 */
	public boolean isSelectableAt(StarID ID, Position pos);

	/**
	 * 返回 @return Field上任意一个个由  @param ID 占领的位置
	 */
	public Position getDefaultPos(StarID ID);
	
	/**
	 * 以 @param ID 身份尝试对 @param src 和 @param dest 进行操作（可能是连接或删除连接）
	 * 返回 @return 以表示是否操作成功
	 */
	public boolean operateConnection(StarID ID, Position src, Position dest);
}
