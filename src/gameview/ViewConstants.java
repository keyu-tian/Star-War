package gameview;

public class ViewConstants {
	private ViewConstants() {}
	
	public static final String WINDOW_TITLE = "Star War";
	public static final int INITIAL_LOCATION_X = 200;
	public static final int INITIAL_LOCATION_Y = 100;
	public static final int INITIAL_SCALE_RATIO = 105;
	public static final int GRID_LAYOUT_GAP = 5;
	public static final int DEFAULT_FOCUSED_CIRCLE_DIAMETER = 30;
	public static final float DEFAULT_DIAMETER_SHRINK_RATE = 0.8f;
//	public static final int DEFAULT_X_OFFSET = 7;
//	public static final int DEFAULT_Y_OFFSET = 30;
	public static final int DEFAULT_ARROW_ANGLE = 12;
	public static final float DEFAULT_ARROW_ANGLE_SIN = (float)Math.sin(DEFAULT_ARROW_ANGLE);
	public static final float DEFAULT_ARROW_ANGLE_COS = (float)Math.cos(DEFAULT_ARROW_ANGLE);
	public static final int DEFAULT_ARROW_LENTH = 12;
}
