package model;

public enum Color {
	RED, BLUE, GREEN, BLACK;
	
	java.awt.Color toAwtColor() {
		if (this.equals(RED))
			return java.awt.Color.RED;
		else if (this.equals(BLACK))
			return java.awt.Color.BLACK;
		else if (this.equals(GREEN))
			return java.awt.Color.GREEN;
		else
			return java.awt.Color.BLUE;
	}
	
}
