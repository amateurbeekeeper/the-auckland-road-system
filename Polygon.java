import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class Polygon {
	
	List<Float> xcoords = new ArrayList<Float>();
	List<Float> ycoords = new ArrayList<Float>();
	
	List<Location> locations = new ArrayList<Location>();
	
	Color color;
	
	 public Polygon(List<Float> c) {
		 
		 color = Color.BLACK;
		
		//coords = c;
		
		//for(int i = 0; i < coords.size(); i+=2 ) {
		//	locations.add(Location.newFromLatLon(xcoords.get(i), ycoords.get(i+1)));
		//}
		 
	 }
	
	
	
	
	
	public void draw(Graphics g, Location origin, double scale) {
		g.setColor(color);
		
		//g.drawPolygon(xPoints, yPoints, nPoints);
		
		
	}
	

}
