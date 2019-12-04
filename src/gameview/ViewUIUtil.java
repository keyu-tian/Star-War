package gameview;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.border.LineBorder;

import star.StarID;

public class ViewUIUtil {
	private ViewUIUtil() {}
	
	private static final int BORDER_THICKNESS = 3;
	private static final Color BACKGROUND_COLOR = new Color(41, 41, 41);
	private static final Color FOREGROUND_COLOR = new Color(250, 250, 250, 250);
	private static final Font DEFAULT_FONT = new Font("Century Gothic", Font.PLAIN, 20);
	private static final BasicStroke THICK_STROKE_LINE = new BasicStroke(2.1f); 
	private static final BasicStroke THIN_STROKE_LINE = new BasicStroke(1.4f); 

	private static HashMap<StarID, Color> IDColors = new HashMap<>();
	private static HashMap<StarID, Color> IDFadedColors = new HashMap<>();
	private static HashMap<StarID, LineBorder> IDBorders = new HashMap<>();
	private static HashMap<StarID, LineBorder> IDFadedBorders = new HashMap<>();
	
	// 静态初始化：填充哈希表
	static {
		IDColors.put(StarID.NEUTRAL, new Color(94, 94, 94, 200));
		IDColors.put(StarID.P1, new Color(104, 176, 194, 160));
		IDColors.put(StarID.P2, new Color(248, 92, 92, 160));
		IDColors.put(StarID.P3, new Color(255, 213, 95, 240));
//		IDColors.put(StarID.P4, new Color(152, 208, 122, 210));
		
		IDFadedColors.put(StarID.NEUTRAL, new Color(94, 94, 94, 200));
		IDFadedColors.put(StarID.P1, new Color(104, 176, 194, 150));
		IDFadedColors.put(StarID.P2, new Color(248, 92, 92, 150));
		IDFadedColors.put(StarID.P3, new Color(255, 213, 95, 175));
//		IDFadedColors.put(StarID.P4, new Color(152, 208, 122, 150));
		
		for (StarID ID : StarID.values()) {
			if (ID != StarID.NEUTRAL) {
				IDBorders.put(ID, constructBorder(ID, false));
				IDFadedBorders.put(ID, constructBorder(ID, true));
			}
		}
	}
	
	private static LineBorder constructBorder(StarID ID, boolean faded) {
		if (faded) {
			return new LineBorder(IDFadedColors.get(ID), BORDER_THICKNESS);
		} else {
			return new LineBorder(IDColors.get(ID), BORDER_THICKNESS);
		}
	}
	
	// 接口：查询字体
	public static Font getFont() {
		return DEFAULT_FONT;
	}
	
	// 接口：查询线条
	public static BasicStroke getStrokeLine(boolean isThick) {
		return isThick ? THICK_STROKE_LINE : THIN_STROKE_LINE;
	}
	
	// 接口：查询颜色
	public static Color getBackgroundColor() {
		return BACKGROUND_COLOR;
	}
	public static Color getForegroundColor() {
		return FOREGROUND_COLOR;
	}
	public static Color getIDColor(StarID ID, boolean faded) {
		if (faded) {
			return IDFadedColors.get(ID);
		} else {
			return IDColors.get(ID);
		}
	}
	
	// 接口：查询边框
	public static LineBorder getIDBorder(StarID ID, boolean faded) {
		if (faded) {
			return IDFadedBorders.get(ID);
		} else {
			return IDBorders.get(ID);
		}
	}
	
	// 接口：读取图片
	public static Image readImage(String path) {
		return new ImageIcon(path).getImage();
	}
}
