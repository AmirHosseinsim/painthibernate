package model;

import java.awt.*;
import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class DBMS {

	private static final String className = "com.mysql.jdbc.Driver";
	private static final String database = "hw9_paint";
	private static final String url = "jdbc:mysql://localhost:3306/" + database;
	private static final String username = "root";
	private static final String password = "toor";

	private User user;
	private String sql;
	private Connection connection;
	private Statement statement;
	private ResultSet rs;

	{
		try {
			Class.forName(className).getInterfaces();
			connection = DriverManager.getConnection(url, username, password);
			statement = connection.createStatement();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public DBMS(User user) {
		super();
		this.user = user;
	}

	public DBMS() {
		super();
	}

	@Override
	protected void finalize() throws Throwable {
		rs.close();
		statement.close();
		connection.close();
		super.finalize();
	}

	public void clear() {
		sql = "delete from `circle` where user = '" + user.getName() + "'";
		update();
		sql = "delete from `line` where user = '" + user.getName() + "'";
		update();
		sql = "delete from `rectangle` where user = '" + user.getName() + "'";
		update();
	}

	private boolean update() {
		try {
			statement.executeUpdate(sql);
			return true;
		} catch (SQLException e) {
			return false;
		}
	}

	private void query() {
		try {
			rs = statement.executeQuery(sql);
		} catch (SQLException e) {
			rs = null;
		}
	}

	public void saveShape(Shape shape) {
		if (shape instanceof Line) {
			Line l = (Line) shape;
			sql = "INSERT INTO `line` (`x1`, `y1`, `x2`, `y2`, `c`, user) VALUES ('" + l.getBase().x + "', '"
					+ l.getBase().y + "', '" + l.getEnd().x + "', '" + l.getEnd().y + "', '" + l.getColor().toString()
					+ "', '" + shape.getUser().getName() + "');";
		} else if (shape instanceof Circle) {
			Circle c = (Circle) shape;
			sql = "INSERT INTO `circle` (`x`, `y`, `r`, `c`, user) VALUES ('" + c.getBase().x + "', '" + c.getBase().y
					+ "', '" + c.getRadius() + "', '" + c.getColor().toString() + "', '" + shape.getUser().getName()
					+ "');";
		} else if (shape instanceof Rectangle) {
			Rectangle r = (Rectangle) shape;
			sql = "INSERT INTO `rectangle` (`x`, `y`, `w`, `h`, `c`, user) VALUES ('" + r.getBase().x + "', '"
					+ r.getBase().y + "', '" + r.getWidth() + "', '" + r.getHeight() + "', '" + r.getColor().toString()
					+ "', '" + shape.getUser().getName() + "');";
		}
		update();
	}

	public Set<Shape> loadShapes() {
		Set<Shape> shapes = new HashSet<Shape>();
		addLines(shapes);
		addCircles(shapes);
		addRectangles(shapes);
		return shapes;
	}

	private void addRectangles(Set<Shape> shapes) {
		sql = "select * from rectangle";
		query();
		try {
			if (rs != null)
				while (rs.next()) {
					int x = rs.getInt(1);
					int y = rs.getInt(2);
					int w = rs.getInt(3);
					int h = rs.getInt(4);
					Color c = Color.valueOf(rs.getString(5));
					User u = new User(rs.getString(6));
					if (u.equals(user))
						shapes.add(new Rectangle(c, new Point(x, y), u, w, h));
				}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void addCircles(Set<Shape> shapes) {
		sql = "select * from circle";
		query();
		try {
			if (rs != null)
				while (rs.next()) {
					int x = rs.getInt(1);
					int y = rs.getInt(2);
					int r = rs.getInt(3);
					Color c = Color.valueOf(rs.getString(4));
					User u = new User(rs.getString(5));
					if (user.equals(u))
						shapes.add(new Circle(c, new Point(x, y), u, r));
				}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void addLines(Set<Shape> shapes) {
		sql = "select * from line";
		query();
		try {
			if (rs != null)
				while (rs.next()) {
					int x1 = rs.getInt(1);
					int y1 = rs.getInt(2);
					int x2 = rs.getInt(3);
					int y2 = rs.getInt(4);
					Color c = Color.valueOf(rs.getString(5));
					User u = new User(rs.getString(6));
					if (u.equals(user))
						shapes.add(new Line(c, new Point(x1, y1), u, new Point(x2, y2)));
				}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean isPasswordCorrect(User user) {
		sql = "select * from user where name = '" + user.getName() + "' and password = '" + user.getPassword() + "';";
		query();
		try {
			return (rs.next());
		} catch (SQLException e) {
			return false;
		}
	}

	public boolean userExists(User user) {
		sql = "select * from user where name = '" + user.getName() + "';";
		query();
		try {
			return (rs.next());
		} catch (SQLException e) {
			return false;
		}
	}

	public boolean addUser(User user) {
		sql = "insert into user (name, password) values ('" + user.getName() + "', '" + user.getPassword() + "');";
		return update();
	}

	public void update(Shape shape) {
		if (shape instanceof Line) {
			Line line = (Line) shape;
			sql = "update line set c = '" + line.getColor() + "' where x1 = '" + line.getBase().getX() + "' and x2 = '"
					+ line.getEnd().getX() + "' and y1 = '" + line.getBase().getY() + "' and y2 = '"
					+ line.getEnd().getY() + "' and user = '" + user.getName() + "';";

		} else if (shape instanceof Circle) {
			Circle circle = (Circle) shape;
			sql = "update circle set c = '" + circle.getColor() + "' where x = '" + circle.getBase().getX()
					+ "' and y = '" + circle.getBase().getY() + "' and r = '" + circle.getRadius() + "' and user = '"
					+ user.getName() + "'";
		} else if (shape instanceof Rectangle) {
			Rectangle rectangle = (Rectangle) shape;
			sql = "update rectangle set c = '" + rectangle.getColor() + "' where x = '" + rectangle.getBase().getX()
					+ "' and y = '" + rectangle.getBase().getY() + "' and w = '" + rectangle.getWidth() + "' and h = '"
					+ rectangle.getHeight() + "' and user = '" + user.getName() + "'";
		}
		update();
	}

}
