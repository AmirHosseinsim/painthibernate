package view;

import model.*;
import model.Color;
import model.Rectangle;
import model.Shape;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GUI extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;

	private Shape shape;
	private Point base;
	private Point end;
	private Color color;
	private ShapeType shapeType;

	private State state;

	public static void enter(User user) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI frame = new GUI(user);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public GUI(User user) {
		setTitle("Welcome " + user.getName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 520);
		contentPane = new JPanel();
		contentPane.setBackground(UIManager.getColor("Button.background"));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JComboBox comboBoxColor = new JComboBox();
		comboBoxColor.setModel(new DefaultComboBoxModel(Color.values()));
		comboBoxColor.setBounds(548, 79, 115, 20);
		contentPane.add(comboBoxColor);

		JComboBox comboBoxShape = new JComboBox();
		comboBoxShape.setModel(new DefaultComboBoxModel(ShapeType.values()));
		comboBoxShape.setBounds(548, 48, 115, 20);
		contentPane.add(comboBoxShape);

		Panel panel = new Panel(user);
		panel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (state != null) {
					switch (state) {
					case CREATE:
						base = e.getPoint();
						break;
					case SELECT:
						shape = getClickedShape(panel, e);
						break;
					default:
						break;
					}
				}
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				switch (state) {
				case CREATE:
					calculateParams(e);
					panel.addShape(shape);
					break;
				case SELECT:
					break;
				default:
					break;
				}
			}

			private void calculateParams(MouseEvent e) {
				end = e.getPoint();
				color = Color.valueOf(String.valueOf(comboBoxColor.getSelectedItem()));
				shapeType = ShapeType.valueOf(String.valueOf(comboBoxShape.getSelectedItem()));
				switch (shapeType) {
				case LINE:
					shape = new Line(color, base, user, end);
					break;
				case CIRCLE:
					int dx = (int) Math.pow(end.x - base.x, 2);
					int dy = (int) Math.pow(end.y - base.y, 2);
					int r = (int) Math.sqrt(dx + dy);
					shape = new Circle(color, new Point(base.x - r, base.y - r), user, r);
					break;
				case RECTANGLE:
					int w = Math.abs(base.x - end.x);
					int h = Math.abs(base.y - end.y);
					shape = new Rectangle(color, topLeftOf(base, end), user, w, h);
					break;
				}
			}
		});

		panel.setBackground(java.awt.Color.WHITE);
		panel.setBounds(10, 0, 500, 450);
		contentPane.add(panel);

		JButton btnSave = new JButton("Exit");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					GUI.this.setVisible(false);
					Login login = new Login();
					login.setVisible(true);
				} catch (Throwable e1) {
					e1.printStackTrace();
				}
			}
		});
		btnSave.setBounds(548, 407, 115, 23);
		contentPane.add(btnSave);

		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel.clear();
			}
		});
		btnClear.setBounds(548, 373, 115, 23);
		contentPane.add(btnClear);

		JButton btnChangeColor = new JButton("Change Color");
		btnChangeColor.setBounds(548, 219, 115, 23);
		contentPane.add(btnChangeColor);
		btnChangeColor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				color = Color.valueOf(String.valueOf(comboBoxColor.getSelectedItem()));
				if (shape != null)
					panel.setColor(shape, color);
				repaint();
			}
		});

		JButton btnSelectShape = new JButton("Select Shape");
		btnSelectShape.setBounds(548, 185, 115, 23);
		contentPane.add(btnSelectShape);
		btnSelectShape.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				state = State.SELECT;
			}
		});

		JButton btnCreateShape = new JButton("Create Shape");
		btnCreateShape.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				state = State.CREATE;
			}
		});
		btnCreateShape.setBounds(548, 14, 115, 23);
		contentPane.add(btnCreateShape);

	}

	protected Shape getClickedShape(Panel panel, MouseEvent e) {
		for (Shape shape : panel.shapes)
			if (shape.contains(e.getPoint()))
				return shape;
		return null;
	}

	public static Point topLeftOf(Point base, Point end) {
		return new Point(Math.min(base.x, end.x), Math.min(base.y, end.y));
	}

}
