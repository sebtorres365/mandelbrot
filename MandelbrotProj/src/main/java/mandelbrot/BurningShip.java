package mandelbrotproject;

import java.awt.geom.Rectangle2D;
import java.lang.Math;

public class BurningShip extends FractalGenerator {
    public static final int MAX_ITERATIONS = 2000;
    
    @Override
    public void getInitialRange(Rectangle2D.Double range){
        range.x=-2;
        range.y=-2.5;
        range.width=4;
        range.height=4;
    }
    
    /*Doing imaginary number math which the computer doesn't know how to do. Iterating throught
    Z=z^2+C*/
    @Override
    public int numIterations(double cr, double ci){
        double zr=0;
        double zi=0;
        int count=0;
        while(count<MAX_ITERATIONS && ((zr*zr)+(zi*zi)<4)){
            double nextZr;
            double nextZi;
            nextZr=(zr*zr)-(zi*zi)+cr;
            nextZi=Math.abs(2*zr*zi+ci);
            zr=Math.abs(nextZr);
            zi=nextZi;
            count++;
        }
        if(count==MAX_ITERATIONS){
            return -1;
        }
        return count;
    }
    
    @Override
    public String toString(){
       return("Burning Ship"); 
    } 
}