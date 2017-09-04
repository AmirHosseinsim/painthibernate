package model;

import java.awt.*;
import java.awt.geom.Point2D;

public class Circle extends Shape {

	protected int radius;

	@Override
	public void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(color.toAwtColor());
		g2d.drawOval(base.x, base.y, radius * 2, radius * 2);
	}

	public Circle(Color color, Point base, User user, int radius) {
		super(color, base, user);
		this.radius = radius;
	}

	public int getRadius() {
		return radius;
	}

	@Override
	public boolean contains(Point point) {
		Point2D base2D = (Point2D) new Point(base.x + radius, base.y + radius);
		Point2D point2D = (Point2D) point;
		if (base2D.distance(point2D) <= radius)
			return true;
		return false;
	}

}
