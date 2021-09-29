import java.lang.reflect.Array;
public class NBody{
	public static double readRadius(String fileName){
	In in = new In("data/planets.txt");
	int number = in.readInt();
	double radius = in.readDouble();	
	return radius;
	}

	public static Planet[] readPlanets(String fileName){
		In in = new In("data/planets.txt");
		int number = in.readInt();
		double radius = in.readDouble();
		Planet[] planets = new Planet[number];
		for(int i = 0; i < number; i++){
			Planet p = new Planet(0,0,0,0,0,null);
			p.xxPos = in.readDouble();
			p.yyPos = in.readDouble();
			p.xxVel = in.readDouble();
			p.yyVel = in.readDouble();
			p.mass = in.readDouble();
			p.imgFileName = in.readString();
			planets[i] = p;
		}
		return planets;
	}

	public static void main(String[] args){
		double T = Double.parseDouble(args[0]);
		double dt = Double.parseDouble(args[1]);
		String filename = args[2];
		Planet[] planets = readPlanets(filename);
		double radius = readRadius(filename);

		String imageToDraw = "/images/starfield.jpg";
		StdDraw.setScale(-radius, radius);
		StdDraw.clear();
		StdDraw.picture(0,0,imageToDraw);
		StdDraw.show();

		for(Planet p : planets){
			p.draw();
		}

		StdDraw.enableDoubleBuffering();
		double time = 0;
		int number = planets.length;
		double[] xForces = new double[number];
		double[] yForces = new double[number];
		for(double t = 0; t < T; t = t + dt){
			for(int i =0; i < number; i++){
				xForces[i] = planets[i].calcNetForceExertedByX(planets);
				yForces[i] = planets[i].calcNetForceExertedByY(planets);
			}
			for(int j = 0; j < number; j++){
			planets[j].update(dt, xForces[j],yForces[j]);
			}
			StdDraw.setScale(-radius, radius);
			StdDraw.clear();
			StdDraw.picture(0,0,imageToDraw);
			StdDraw.show();

			for(Planet p : planets){
				p.draw();
			}
			StdDraw.show();
			StdDraw.pause(10);
		}
		StdOut.printf("%d\n", planets.length);
		StdOut.printf("%.2e\n", radius);
		for (int i = 0; i < planets.length; i++) {
    	StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                  planets[i].xxPos, planets[i].yyPos, planets[i].xxVel,
                  planets[i].yyVel, planets[i].mass, planets[i].imgFileName);   
		}

	}
}
