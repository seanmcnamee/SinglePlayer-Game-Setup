package app;

import javax.swing.JFrame;
import java.awt.Graphics;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

/**
 * Layout of what a display screen should be for the App
 */
abstract class DisplayScreen {
    JFrame mainGUI;

    public DisplayScreen(JFrame frame) {
        mainGUI = frame;
    }
    
    abstract void render(Graphics g);
    
    public void keyPressed(KeyEvent e){System.out.println(e.getKeyChar() + " Key Pressed");}
    public void keyReleased(KeyEvent e){System.out.println(e.getKeyChar() + " Key Released");}
    public void keyTyped(KeyEvent e){System.out.println(e.getKeyChar() + " Key Typed");}

    public void mouseClicked(MouseEvent e){System.out.println("Mouse Clicked");}
    public void mouseEntered(MouseEvent e){}
    public void mouseWheelMoved(MouseWheelEvent e){System.out.println("Mouse Wheel Moved");}
}