package gameview;

import java.util.ArrayList;

import gamefield.Position;
import star.StarID;

public interface ViewableField {
	
	/**
	 * 查询Field内的 @param pos 位置是否为空
	 */
	public boolean isEmptyAt(Position pos);
	
	/**
	 * 查询Field内的 @param pos 位置所属的ID
	 */
	public StarID getIDAt(Position pos);

	/**
	 * 查询Field内的 @param pos 位置的值
	 */
	public int getValueAt(Position pos);
	
	/**
	 * 返回 @return 一个Position容器，表示从给定位置 @param pos 发出连接的所有终点的Position的集合
	 */
	public void fetchDestinationsFrom(Position src, ArrayList<Position> dests);
}
