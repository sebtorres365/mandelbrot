package mandelbrotproject;

import javax.swing.JFrame;
import java.awt.geom.Rectangle2D;
import java.awt.Button;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.*;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileFilter;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;
import javax.swing.JOptionPane;
        
public class FractalExplorer {
    private int displaySize;
    private JImageDisplay imgDisplay;
    private FractalGenerator fractalGen;
    private JComboBox comboBox;
    private FractalGenerator fractalGenM;
    private FractalGenerator fractalGenT;
    private FractalGenerator fractalGenB;
    private Rectangle2D.Double range; 
    private JFrame frame;
    
    
    FractalExplorer(int displaySize){
            this.displaySize=displaySize;
            range = new Rectangle2D.Double();
            comboBox=new JComboBox();
            fractalGenM= new Mandelbrot();
            fractalGenT= new Tricorn();
            fractalGenB= new BurningShip();  
            fractalGen = new Mandelbrot();
            fractalGen.getInitialRange(range);
    }
    
    
    public void createAndShowGUI(){
        frame = new JFrame("Madelbrot");
        imgDisplay= new JImageDisplay(displaySize, displaySize);
        Button buttonReset = new Button();
        Button buttonSave = new Button();
        buttonReset.setLabel("       Reset      ");
        buttonSave.setLabel("       Save        ");
        actionHandlerR buttonHandlerR = new actionHandlerR();
        buttonReset.addActionListener(buttonHandlerR);
        actionHandlerS buttonHandlerS = new actionHandlerS();
        buttonSave.addActionListener(buttonHandlerS); 
        JLabel Jlabel= new JLabel("Fractal: ");
        comboBox.addItem("Mandelbrot");
        comboBox.addItem("Tricorn");
        comboBox.addItem("Burning Ship");
        JPanel panelN = new JPanel();
        JPanel panelS = new JPanel();
        comboBox.addActionListener(new actionHandlerR());
        panelN.add(Jlabel);
        panelN.add(comboBox);
        panelS.add(buttonSave);
        panelS.add(buttonReset);
        mouseHandler mouse = new mouseHandler();
        imgDisplay.addMouseListener(mouse);
        BorderLayout BL = new BorderLayout();
        BL.addLayoutComponent(panelS, BorderLayout.SOUTH); 
        BL.addLayoutComponent(imgDisplay, BorderLayout.CENTER);
        BL.addLayoutComponent(panelN, BorderLayout.NORTH);
        frame.add(panelN);
        frame.add(panelS);
        frame.add(imgDisplay);
        frame.setLayout(BL);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
    } 
    
    public void drawFractal(){
        int x=0;
        int y=0;
        while(x<displaySize){
            while(y<displaySize){
             double xCoord= FractalGenerator.getCoord(range.x, range.x+range.width,displaySize,x);
             double yCoord= FractalGenerator.getCoord(range.y,range.y+range.height,displaySize,y);
             int numIter=fractalGen.numIterations(xCoord, yCoord);
             if(numIter==-1){
                 imgDisplay.drawPixel(x,y,0);
             }
             else{
                 float hue = 0.7f + (float) numIter / 200f;
                 int rgbColor = Color.HSBtoRGB(hue, 1f, 1f);
                 imgDisplay.drawPixel(x,y,rgbColor);
             }             
             y++;   
            }
            x++;
            y=0;
        }
        imgDisplay.repaint();
    }
    
    public class actionHandlerR implements java.awt.event.ActionListener{
        
        @Override
        public void actionPerformed(ActionEvent e){
            if(e.getSource().equals(comboBox)){
                String fractal = comboBox.getSelectedItem().toString();
                if(fractal.equals("Mandelbrot")){
                    fractalGen = fractalGenM; 
                }
                if(fractal.equals("Tricorn")){
                    fractalGen = fractalGenT; 
                }
                if(fractal.equals("Burning Ship")){
                    fractalGen = fractalGenB;
                }
                fractalGen.getInitialRange(range);
                drawFractal();
            }
            else{
                fractalGen.getInitialRange(range);
                drawFractal();
            }
        }
        
    }
    
    public class actionHandlerS implements java.awt.event.ActionListener{
      
        @Override
        public void actionPerformed(ActionEvent e){
            JFileChooser chooser = new JFileChooser();
            FileFilter filter = new FileNameExtensionFilter("PNG Images", "png");
            chooser.setFileFilter(filter);
            chooser.setAcceptAllFileFilterUsed(false);
            int fcVal=chooser.showSaveDialog(frame);
            if(fcVal==JFileChooser.APPROVE_OPTION){
               File file = chooser.getSelectedFile();
               try{
                ImageIO.write(imgDisplay.getImage(), "png", file);    
               }catch(IOException ex){
                   JOptionPane.showMessageDialog(frame, ex.getMessage(), "Cannot Save Title",JOptionPane.ERROR_MESSAGE);
               }
            }
        }
    }
    
    public class mouseHandler extends MouseAdapter{
        
        @Override
        public void mouseClicked(MouseEvent e){
            int x =e.getX();
            int y=e.getY();
            double xCoord= FractalGenerator.getCoord(range.x, range.x+range.width,displaySize, x);
            double yCoord= FractalGenerator.getCoord(range.y,range.y+range.height,displaySize,y);
            fractalGen.recenterAndZoomRange(range, xCoord, yCoord, .5);
            drawFractal();
        }
    }
    
}