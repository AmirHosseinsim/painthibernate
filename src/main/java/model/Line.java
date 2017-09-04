package model;

import view.GUI;

import java.awt.*;

public class Line extends Shape {

	protected Point end;

	public Line(Color color, Point base, User user, Point end) {
		super(color, base, user);
		this.end = end;
	}
	
	public Line(Point base, Point end) {
		super(null, base, null);
		this.end = end;
	}

	@Override
	public void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(color.toAwtColor());
		g2d.drawLine(base.x, base.y, end.x, end.y);
	}

	public Point getEnd() {
		return end;
	}

	public void setEnd(Point end) {
		this.end = end;
	}

	@Override
	public boolean contains(Point point) {
		int width = Math.abs(base.x - end.x);
		int height = Math.abs(base.y - end.y);
		Rectangle r = new Rectangle(null, GUI.topLeftOf(base, end), null, width, height);
		return r.contains(point);
	}
	
}
