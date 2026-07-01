package org.woehlke.computer.kurzweil.cyclic.cellular.automaton.view.panels;

import org.woehlke.computer.kurzweil.cyclic.cellular.automaton.view.CyclicCellularAutomatonFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Cyclic Cellular Automaton.
 * <p>
 * (C) 2006 - 2026 Thomas Woehlke.
 * @see <a href="https://java.woehlke.org/cyclic-cellular-automaton">Maven Project Page</a>
 *
 * @author Thomas Woehlke
 */
public class PanelButtons extends JPanel implements ActionListener {

    private static final long serialVersionUID = 242L;

    private CyclicCellularAutomatonFrame tab;
    private final JLabel neighborhoodLabel = new JLabel("Neighbourhood:");
    private final JRadioButton buttonVonNeumann = new JRadioButton("Von Neumann", true);
    private final JRadioButton buttonMoore = new JRadioButton("Moore");
    private final JRadioButton buttonWoehlke = new JRadioButton("Woehlke");
    private final JButton buttonRestart = new JButton("Restart");
    private final ButtonGroup bgroup = new ButtonGroup();

    public PanelButtons(CyclicCellularAutomatonFrame tab) {
        this.tab = tab;

        bgroup.add(buttonVonNeumann);
        bgroup.add(buttonMoore);
        bgroup.add(buttonWoehlke);

        this.add(neighborhoodLabel);
        this.add(buttonVonNeumann);
        this.add(buttonMoore);
        this.add(buttonWoehlke);
        this.add(buttonRestart);

        this.buttonRestart.addActionListener(this);
    }

    /**
     * TODO write doc.
     */
    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == this.buttonRestart) {
            if (buttonVonNeumann.isSelected()) {
                this.tab.getController().pushButtonVonNeumann();
            }
            if (buttonMoore.isSelected()) {
                this.tab.getController().pushButtonMoore();
            }
            if (buttonWoehlke.isSelected()) {
                this.tab.getController().pushButtonWoehlke();
            }
        }
    }
}
