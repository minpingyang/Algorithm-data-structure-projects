package swen221.lab3.model;

import java.awt.Color;
import java.awt.Graphics;

import com.sun.jmx.snmp.SnmpStringFixed;

public class Book extends PickupableItem {
	public String title;
	public boolean isRead;
	public Book(String title){
		isRead = false;
		this.title = title;
	}
	@Override
	public String[] getActions() {
		return new String[]{"Pickup","Drop","Read"};
	}

	@Override
	public String getDescription() {
		if(isRead)
			return "A book entitled \""+ title +"\"; it looks like it has been read";
		else {
			return "A book entitled \""+ title +"\"";
		}
		}
	}	
	
	@Override
	public void draw(Graphics g) {
		int width = (int) g.getClipBounds().getWidth();
		int height = (int) g.getClipBounds().getHeight();
		int xStart = width / 4;
		int yStart = height / 4;
		int xEnd = (width * 3) / 4;
		int yEnd = (height * 3) / 4;
		g.setColor(Color.YELLOW);
		g.fillRect(xStart, yStart, xEnd - xStart, yEnd - yStart);
		g.setColor(Color.YELLOW.darker());
		g.fillRect(xStart+5, yStart+5, (xEnd-10) - xStart, (yEnd-10) - yStart);
		g.setColor(Color.BLACK);
		g.drawRect(xStart, yStart, xEnd - xStart, yEnd - yStart);
	}
}
