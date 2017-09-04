package model;

import java.awt.*;

public class Rectangle extends Shape {

	protected int width;
	protected int height;

	public Rectangle(Color color, Point base, User user, int width, int height) {
		super(color, base, user);
		this.width = width;
		this.height = height;
	}

	@Override
	public void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(color.toAwtColor());
		g2d.drawRect(base.x, base.y, width, height);
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	@Override
	public boolean contains(Point point) {
		if (point.x >= base.getX() && point.x <= base.getX() + width)
			if (point.y >= base.getY() && point.y <= base.getY() + height)
				return true;
		return false;
	}

}
