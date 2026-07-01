package org.woehlke.computer.kurzweil.cyclic.cellular.automaton.view.canvas;

import org.woehlke.computer.kurzweil.cyclic.cellular.automaton.view.CyclicCellularAutomatonFrame;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;

/**
 * Cyclic Cellular Automaton.
 * <p>
 * (C) 2006 - 2026 Thomas Woehlke.
 * @see <a href="https://java.woehlke.org/cyclic-cellular-automaton">Maven Project Page</a>
 *
 * @author Thomas Woehlke
 * <p>
 * Date: 05.02.2006
 * Time: 00:51:51
 */
public class CyclicCellularAutomatonCanvas extends JComponent implements Serializable {

    static final long serialVersionUID = -3057254130516052936L;

    private CyclicCellularAutomatonFrame tab;

    public CyclicCellularAutomatonCanvas(CyclicCellularAutomatonFrame tab) {
        this.tab = tab;
        Dimension preferredSize = new Dimension(
            (int) this.tab.getModel().getWorldDimensions().getX(),
            (int) this.tab.getModel().getWorldDimensions().getY()
        );
        this.setPreferredSize(preferredSize);
    }

    public void paint(Graphics g) {
        super.paintComponent(g);
        int xx = (int) this.tab.getModel().getWorldDimensions().getX();
        int yy = (int) this.tab.getModel().getWorldDimensions().getY();
        for (int y = 0; y < yy; y++) {
            for (int x = 0; x < xx; x++) {
                int state = this.tab.getModel().getCellStatusFor(x, y);
                Color stateColor = this.tab.getModel().getColorScheme().getColorForState(state);
                g.setColor(stateColor);
                g.fillRect(x, y, 1, 1);
            }
        }
    }

    public void update(Graphics g) {
        paint(g);
    }

}
