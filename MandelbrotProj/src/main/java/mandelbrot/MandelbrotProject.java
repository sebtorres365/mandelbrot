package mandelbrotproject;

public class MandelbrotProject {

    public static void main(String[] args) {
        FractalExplorer explorer = new FractalExplorer(800);
        explorer.createAndShowGUI();
        explorer.drawFractal();
    }
    
}