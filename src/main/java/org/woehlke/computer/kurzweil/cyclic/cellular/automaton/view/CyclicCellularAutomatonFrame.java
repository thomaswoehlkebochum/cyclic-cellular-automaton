package org.woehlke.computer.kurzweil.cyclic.cellular.automaton.view;

import lombok.Getter;
import org.woehlke.computer.kurzweil.cyclic.cellular.automaton.view.canvas.ColorScheme;
import org.woehlke.computer.kurzweil.cyclic.cellular.automaton.config.ComputerKurzweilProperties;
import org.woehlke.computer.kurzweil.cyclic.cellular.automaton.control.CyclicCellularAutomatonController;
import org.woehlke.computer.kurzweil.cyclic.cellular.automaton.model.CyclicCellularAutomatonModel;
import org.woehlke.computer.kurzweil.cyclic.cellular.automaton.view.canvas.CyclicCellularAutomatonCanvas;
import org.woehlke.computer.kurzweil.cyclic.cellular.automaton.view.panels.PanelButtons;
import org.woehlke.computer.kurzweil.cyclic.cellular.automaton.view.panels.PanelSubtitle;

import javax.accessibility.Accessible;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.ImageObserver;
import java.io.Serializable;

/**
 * Cyclic Cellular Automaton.
 * <p>
 * (C) 2006 - 2026 Thomas Woehlke.
 * @see <a href="https://java.woehlke.org/cyclic-cellular-automaton">Maven Project Page</a>
 *
 * @author Thomas Woehlke
 * <p>
 * Date: 04.02.2006
 * Time: 18:47:46
 */
@Getter
public class CyclicCellularAutomatonFrame extends JFrame implements ImageObserver,
    MenuContainer,
    Serializable,
    Accessible,
    WindowListener {

    private static final long serialVersionUID = 4357793241219932594L;

    private final ComputerKurzweilProperties config;

    private volatile CyclicCellularAutomatonController controller;
    private volatile CyclicCellularAutomatonCanvas canvas;
    private volatile CyclicCellularAutomatonModel model;

    private volatile ColorScheme colorScheme;
    private volatile PanelButtons panelButtons;
    private volatile PanelSubtitle subtitle;

    public CyclicCellularAutomatonFrame(ComputerKurzweilProperties config) {
        super(config.getCca().getView().getTitle());
        this.config = config;

        this.model = new CyclicCellularAutomatonModel(this);
        this.canvas = new CyclicCellularAutomatonCanvas(this);
        this.controller = new CyclicCellularAutomatonController(this);
        this.panelButtons = new PanelButtons(this);
        this.subtitle = new PanelSubtitle(this);
        this.colorScheme = new ColorScheme();

        Container pane = this.getContentPane();
        pane.add(this.subtitle, BorderLayout.PAGE_START);
        pane.add(this.canvas, BorderLayout.CENTER);
        pane.add(this.panelButtons, BorderLayout.PAGE_END);
        addWindowListener(this);
        showMe();
    }

    public void start(){
        this.controller.start();
    }

    public void showMe() {
        pack();
        this.setBounds(this.getFrameBounds());
        setVisible(true);
        toFront();
    }

    public void windowOpened(WindowEvent e) {
        showMe();
    }

    public void windowClosing(WindowEvent e) {
        System.exit(0);
    }

    public void windowClosed(WindowEvent e) {
        System.exit(0);
    }

    public void windowIconified(WindowEvent e) {
    }

    public void windowDeiconified(WindowEvent e) {
        showMe();
    }

    public void windowActivated(WindowEvent e) {
        toFront();
    }

    public void windowDeactivated(WindowEvent e) {
    }

    public Rectangle getFrameBounds() {
        int height =(int) this.model.getWorldDimensions().getY();
        int width = (int) this.model.getWorldDimensions().getX();
        int TITLE_HEIGHT = 20;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double startX = (screenSize.getWidth() - height) / 2d;
        double startY = (screenSize.getHeight() - width) / 2d;
        int myheight = Double.valueOf(height).intValue() + TITLE_HEIGHT;
        int mywidth = Double.valueOf(width).intValue();
        int mystartX = Double.valueOf(startX).intValue();
        int mystartY = Double.valueOf(startY).intValue();
        return new Rectangle(mystartX, mystartY, mywidth, myheight);
    }

}
