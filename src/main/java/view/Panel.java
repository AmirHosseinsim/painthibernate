package view;

import model.Color;
import model.DBMS;
import model.Shape;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class Panel extends JPanel {

	private static final long serialVersionUID = 1L;

	protected Set<Shape> shapes = new HashSet<>();
	private DBMS dbms;

	public Panel(User user) {
		this.dbms = new DBMS(user);
		initialize();
	}

	private void initialize() {
		shapes = dbms.loadShapes();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (Shape s : shapes)
			s.draw(g);
	}

	public void addShape(Shape shape) {
		shapes.add(shape);
		dbms.saveShape(shape);
		repaint();
	}

	public void clear() {
		shapes.clear();
		dbms.clear();
		repaint();
	}

	public void setColor(Shape shape, Color color) {
		shape.setColor(color);
		dbms.update(shape);
	}

}
