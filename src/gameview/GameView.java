package gameview;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import gamefield.Position;
import gamemain.UpdateableView;
import player.KeyPlayer;

public class GameView extends JFrame implements UpdateableView {
	
	private static final long serialVersionUID = 3540379899967367459L;
	private static Image BG_IMAGE = ViewUIUtil.readImage("pic/starwar_bg.jpg");

	private final int N_ROW;
	private final int N_COL;
	private ViewableField field;
	private ArrayList<ViewablePlayer> players;

	private GamePanel gamePanel;
	private StarLabel[][] starLabels;
	private ArrayList<Position> destsBuffer;
	
	
	// 构造器
	public GameView(int N_ROW, int N_COL, ViewableField field) {
		super();
		this.N_ROW = N_ROW;
		this.N_COL = N_COL;
		this.field = field;
		this.players = new ArrayList<>();
		this.gamePanel = new GamePanel();
		this.starLabels = new StarLabel[N_ROW][N_COL];
		this.destsBuffer = new ArrayList<>();
		
		this.initFrame();
		this.initLabels();
	}

	
	// 工具：初始化视图
	private void initFrame() {
//		this.getContentPane().setBackground(ViewUIUtil.getBackgroundColor());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle(ViewConstants.WINDOW_TITLE);
		this.setLocation(ViewConstants.INITIAL_LOCATION_X, ViewConstants.INITIAL_LOCATION_Y);
		this.setSize(N_COL * ViewConstants.INITIAL_SCALE_RATIO, N_ROW * ViewConstants.INITIAL_SCALE_RATIO);
		this.setVisible(true);
		this.getContentPane().add(gamePanel);
	}
	private void initLabels() {
		this.gamePanel.setLayout(new GridLayout(N_ROW, N_COL, ViewConstants.GRID_LAYOUT_GAP, ViewConstants.GRID_LAYOUT_GAP));
		for (int r=0; r<N_ROW; ++r) {
			for (int c=0; c<N_COL; ++c) {
				starLabels[r][c] = new StarLabel(new Position(r, c), "", SwingConstants.CENTER);
				starLabels[r][c].setForeground(ViewUIUtil.getForegroundColor());
				starLabels[r][c].setFont(ViewUIUtil.getFont());
				starLabels[r][c].setOpaque(false);
				this.gamePanel.add(starLabels[r][c]);
			}
		}
	}
	
	
	// 工具：更新视图
	private void updateLabels() {
		Position pos = new Position(0, 0);
		for (int r=0; r<N_ROW; ++r) {
			for (int c=0; c<N_COL; ++c) {
				pos.row = r;
				pos.col = c;
				if (!field.isEmptyAt(pos))
					starLabels[r][c].setText(String.valueOf(field.getValueAt(pos)));
			}
		}
	}
	

	// UpdateableView接口实现
	@Override
	public void addKeyPlayer(KeyPlayer player) {
		this.players.add(player);
		this.addKeyListener(player);
	}
	@Override
	public void updateView() {
		this.updateLabels();
		this.repaint();
		this.requestFocus();
	}
	
	
	// 内部类：绘图背景面板
	private class GamePanel extends JPanel {
		private static final long serialVersionUID = -225487878103772658L;
		private Polygon poly = new Polygon();
		private Point2D.Float p1 = new Point2D.Float(0, 0);
		private Point2D.Float p2 = new Point2D.Float(0, 0);
		private Point2D.Float p3 = new Point2D.Float(0, 0);
		private Point2D.Float p4 = new Point2D.Float(0, 0);
		private Point2D.Float pmid = new Point2D.Float(0, 0);
		private Point2D.Float unit = new Point2D.Float(0, 0);

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(BG_IMAGE, 0, 0, this.getWidth(), this.getHeight(), null);
		}
		
		@Override
		public void paint(Graphics g) {
			super.paint(g);
			int diameter;
			StarLabel tpLabel;
			Graphics2D g2d = (Graphics2D)g;
			// 抗锯齿
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			
			for (ViewablePlayer player : players) {
				if (player.getSelectedPos() != null) {
					g2d.setStroke(ViewUIUtil.getStrokeLine(true));
					g2d.setColor(ViewUIUtil.getIDColor(player.getID(), false));
					tpLabel = starLabels[player.getSelectedPos().row][player.getSelectedPos().col];
					diameter = (int)(Math.min(tpLabel.getWidth(), tpLabel.getHeight()));
					g2d.drawOval(tpLabel.getX() + (tpLabel.getWidth() - diameter) / 2 - 1,
							tpLabel.getY() + (tpLabel.getHeight() - diameter) / 2 - 1,
							diameter, diameter);
				}
				g2d.setStroke(ViewUIUtil.getStrokeLine(false));
				g2d.setColor(ViewUIUtil.getIDColor(player.getID(), true));
				tpLabel = starLabels[player.getFocusedPos().row][player.getFocusedPos().col];
				diameter = field.isEmptyAt(player.getFocusedPos()) ? ViewConstants.DEFAULT_FOCUSED_CIRCLE_DIAMETER : (int)(Math.min(tpLabel.getWidth(), tpLabel.getHeight()));
				g2d.drawOval(tpLabel.getX() + (tpLabel.getWidth() - diameter) / 2 - 1,
						tpLabel.getY() + (tpLabel.getHeight() - diameter) / 2 - 1,
						diameter, diameter);
			}
			
			StarLabel srcLabel, destLabel;
			Position src = new Position(0, 0);
			
			for (int r=0; r<N_ROW; ++r) {
				for (int c=0; c<N_COL; ++c) {
					src.row = r;
					src.col = c;
					if (!field.isEmptyAt(src)) {
						destsBuffer.clear();
						field.fetchDestinationsFrom(src, destsBuffer);
						g2d.setColor(ViewUIUtil.getIDColor(field.getIDAt(src), false));
						for (Position dest : destsBuffer) {
							g2d.setStroke(ViewUIUtil.getStrokeLine(true));
							srcLabel = starLabels[src.row][src.col];
							destLabel = starLabels[dest.row][dest.col];
							
							p1.x = srcLabel.getX() + srcLabel.getWidth() / 2;
							p1.y = srcLabel.getY() + srcLabel.getHeight() / 2;
							p2.x = destLabel.getX() + destLabel.getWidth() / 2;
							p2.y = destLabel.getY() + destLabel.getHeight() / 2;
							unit.x = p2.x - p1.x;
							unit.y = p2.y - p1.y;
							
							float dist12 = (float)Math.sqrt(unit.x * unit.x + unit.y * unit.y);
							unit.x /= dist12;
							unit.y /= dist12;
							diameter = (int)(ViewConstants.DEFAULT_DIAMETER_SHRINK_RATE * Math.min(srcLabel.getWidth(), srcLabel.getHeight()));
							
							p1.x += diameter * unit.x / 2;
							p1.y += diameter * unit.y / 2;
							p2.x -= diameter * unit.x / 2;
							p2.y -= diameter * unit.y / 2;
							
							/*            p1
							 *      p3  /
							 *      | /
							 *      / __ p4
							 *    p2
							 *    
							 *    pmid（未画出）是P1P2线段上、距P2 ViewConstants.DEFAULT_ARROW_LENTH 这么远的点
							 */
							pmid.x = p2.x - unit.x * ViewConstants.DEFAULT_ARROW_LENTH;
							pmid.y = p2.y - unit.y * ViewConstants.DEFAULT_ARROW_LENTH;
							
							dist12 -= diameter;	// P1P2线段缩短两个半径后的P1P2间距
							
							
							
//									point rotate(this=pmid, Point p2,double angle)//p3是pmid绕点p2逆时针旋转angle角度
//								    {
							unit.x = pmid.x - p2.x;
							unit.y = pmid.y - p2.y;
						    
							// p3是pmid绕p2逆时针旋转ViewConstants.DEFAULT_ARROW_ANGLE而来，p4则是顺时针
						    p3.x = (float)(p2.x + unit.x * ViewConstants.DEFAULT_ARROW_ANGLE_COS
						    - unit.y * ViewConstants.DEFAULT_ARROW_ANGLE_SIN);
						    p3.y = (float)(p2.y + unit.x * ViewConstants.DEFAULT_ARROW_ANGLE_SIN
						    + unit.y * ViewConstants.DEFAULT_ARROW_ANGLE_COS);

						    p4.x = (float)(p2.x + unit.x * ViewConstants.DEFAULT_ARROW_ANGLE_COS
						    + unit.y * ViewConstants.DEFAULT_ARROW_ANGLE_SIN);
						    p4.y = (float)(p2.y - unit.x * ViewConstants.DEFAULT_ARROW_ANGLE_SIN
						    + unit.y * ViewConstants.DEFAULT_ARROW_ANGLE_COS);
						    
						    poly.npoints = 3;
						    poly.xpoints[0] = (int)p2.x;
						    poly.xpoints[1] = (int)p3.x;
						    poly.xpoints[2] = (int)p4.x;
						    poly.ypoints[0] = (int)p2.y;
						    poly.ypoints[1] = (int)p3.y;
						    poly.ypoints[2] = (int)p4.y;
						    
						    g2d.drawLine((int)p1.x, (int)p1.y, (int)p2.x, (int)p2.y);
						    g2d.fillPolygon(poly);
//								    g2d.drawLine((int)p2.x, (int)p2.y, (int)p3.x, (int)p3.y);
//								    g2d.drawLine((int)p2.x, (int)p2.y, (int)p4.x, (int)p4.y);
						}
					}
				}
			}
		}
	}
	
	// 内部类：星球画板
	private class StarLabel extends JLabel {
		private static final long serialVersionUID = 8872427131494840936L;
		private Position pos;
		private int diameter;
		
		public StarLabel(Position pos, String name, int horizontalAlignment) {
			super(name, horizontalAlignment);
			this.pos = pos;
		}
		
		@Override
		public void paint(Graphics g) {
			if (!field.isEmptyAt(pos)) {
				Graphics2D g2d = (Graphics2D)g;

				// 抗锯齿
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				diameter = (int)(ViewConstants.DEFAULT_DIAMETER_SHRINK_RATE * Math.min(this.getWidth(), this.getHeight()));
				g2d.setColor(ViewUIUtil.getIDColor(field.getIDAt(this.pos), false));
				g2d.fillOval((this.getWidth() - diameter) / 2, (this.getHeight() - diameter) / 2,
						diameter, diameter);
			}
			super.paint(g);
		}
	}
		
}
