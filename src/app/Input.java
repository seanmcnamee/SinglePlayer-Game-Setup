package app;

//import java.awt.event.ActionEvent;
//import java.awt.event.KeyAdapter;
//import java.awt.event.MouseWheelEvent;
//import java.beans.PropertyChangeListener;


import javax.swing.event.MouseInputAdapter;
import java.awt.event.KeyListener;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public class Input extends MouseInputAdapter implements KeyListener{

    // Sends the input to the current screen in use
    GameValues gameValues;

    public Input(GameValues gameValues) {
        this.gameValues = gameValues;
    }

    public void mouseClicked(MouseEvent e) {
        gameValues.currentScreen.mouseClicked(e);
    }

    public void mouseEntered(MouseEvent e) {
        gameValues.currentScreen.mouseEntered(e);
    }

    public void mouseWheelMoved(MouseWheelEvent e) {
        gameValues.currentScreen.mouseWheelMoved(e);
    }

    public void keyPressed(KeyEvent e) {
        gameValues.currentScreen.keyPressed(e);
    }

    public void keyReleased(KeyEvent e) {
        gameValues.currentScreen.keyReleased(e);
    }

    public void keyTyped(KeyEvent e) {
        gameValues.currentScreen.keyTyped(e);
    }


}
