package com.akieus.algos.coursera.lib;

/******************************************************************************
 *  Compilation:  javac com.akieus.algos.coursera.lib.DrawListener.java
 *  Execution:    none
 *  Dependencies: none
 *
 *  Interface that accompanies com.akieus.algos.coursera.lib.Draw.java.
 ******************************************************************************/

public interface DrawListener {
    void mousePressed(double x, double y);
    void mouseDragged(double x, double y);
    void mouseReleased(double x, double y);
    void keyTyped(char c);
}
