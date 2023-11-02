package mandelbrotproject;

import javax.swing.JComponent;
import java.awt.image.BufferedImage;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Color;

public class JImageDisplay extends JComponent {
    private BufferedImage image;
    
    JImageDisplay(int width, int height){
        image=new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Dimension dimension= new Dimension(width,height);
        super.setPreferredSize(dimension);
    }
    
    public BufferedImage getImage(){
        return image;
    }
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
    }
    
    /*Clear Image goes through every pixel within the set width and height
    and sets its RGB value to black (0)*/
    public void clearImage(){
        Color myBlack = new Color(0,0,0);
        int rgb = myBlack.getRGB();
        for(int x=0;x<=image.getWidth();x++){
            for(int y=0;y<=image.getHeight();y++){
                image.setRGB(x,y,rgb);
            }
        }
    }
    
    /*Draw Pixel simply sets the pixel at the coordinates given to the rgb 
    value wanted*/
    public void drawPixel(int x, int y, int rgbColor){
      image.setRGB(x,y,rgbColor);
    }
    
    
}
