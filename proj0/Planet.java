import java.lang.Math;
public class Planet{
	public double xxPos;
	public double yyPos;
	public double xxVel;
	public double yyVel;
	public double mass;
	public String imgFileName;
	private static final double constantG = 6.67 * Math.pow(10,(-11));

	public Planet(double xP, double yP, double xV, double yV, double m, String img){
		xxPos = xP;
		yyPos = yP;
		xxVel = xV;
		yyVel = yV;
		mass = m;
		imgFileName = img;
	};

	public Planet(Planet b){
		this.xxPos = b.xxPos;
		this.yyPos = b.yyPos;
		this.xxVel = b.xxVel;
		this.yyVel = b.yyVel;
		this.mass = b.mass; 
		this.imgFileName = b.imgFileName;
	};
	public double calcDistance(Planet p){
		double xLen2 = Math.pow((this.xxPos - p.xxPos),2);
		double yLen2 = Math.pow((this.yyPos - p.yyPos),2);
		double distance = Math.pow((xLen2 + yLen2),0.5);
		return distance;
	}
	public double calcForceExertedBy(Planet p){
		double distance2 = Math.pow(calcDistance(p),2);
		double force = constantG * this.mass * p.mass/distance2;
		return force;
	}
	public double calcForceExertedByX(Planet p){
		double dx = this.xxPos - p.xxPos;
		if(dx < 0){
			dx = - dx;
		}
		double forceX = calcForceExertedBy(p)* dx / calcDistance(p);
		return forceX;
	}
	public double calcForceExertedByY(Planet p){
		double dy = this.yyPos - p.yyPos;
		if(dy < 0){
			dy = - dy;
		}
		double forceY = calcForceExertedBy(p)* dy / calcDistance(p);
		return forceY;
	}
	public double calcNetForceExertedByX(Planet[] ps){
		double xNetForce = 0;
		for(int i = 0; i < ps.length;i++){
			if(ps[i].equals(this)){
				continue;
			}
			if(this.xxPos - ps[i].xxPos >= 0 ){
				xNetForce = xNetForce - this.calcForceExertedByX(ps[i]);
			}else{
				xNetForce = xNetForce + this.calcForceExertedByX(ps[i]);
			}
			
		}
		return xNetForce;
	}
	public double calcNetForceExertedByY(Planet[] ps){
		double yNetForce = 0;
		for(int i = 0; i < ps.length;i++){
			if(ps[i].equals(this)){
				continue;
			}
			if(this.yyPos - ps[i].yyPos >= 0 ){
				yNetForce = yNetForce - this.calcForceExertedByY(ps[i]);
			}else{
				yNetForce = yNetForce + this.calcForceExertedByY(ps[i]);
			}
		}
		return yNetForce;
	}

	public void update(double time, double xForce, double yForce){
		double ax = xForce/this.mass;
		double ay = yForce/this.mass;
		this.xxVel = this.xxVel + ax*time;
		this.yyVel = this.yyVel + ay*time;
		this.xxPos = this.xxPos + this.xxVel*time;
		this.yyPos = this.yyPos + this.yyVel*time;
	}
	public void draw(){
		String imageToDraw = "images/"+this.imgFileName;
		StdDraw.picture(this.xxPos,this.yyPos,imageToDraw);
	}
}
