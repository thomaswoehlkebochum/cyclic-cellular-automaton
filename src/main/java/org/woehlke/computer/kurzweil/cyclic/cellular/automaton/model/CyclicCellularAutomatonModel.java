package org.woehlke.computer.kurzweil.cyclic.cellular.automaton.model;

import lombok.Getter;
import org.woehlke.computer.kurzweil.cyclic.cellular.automaton.config.ComputerKurzweilProperties;
import org.woehlke.computer.kurzweil.cyclic.cellular.automaton.model.neighbourhood.LatticeNeighbourhood;
import org.woehlke.computer.kurzweil.cyclic.cellular.automaton.view.CyclicCellularAutomatonFrame;
import org.woehlke.computer.kurzweil.cyclic.cellular.automaton.view.canvas.ColorScheme;

import java.awt.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Random;

import static org.woehlke.computer.kurzweil.cyclic.cellular.automaton.model.neighbourhood.LatticeNeighbourhood.*;

/**
 * Cyclic Cellular Automaton.
 * <p>
 * (C) 2006 - 2026 Thomas Woehlke.
 * @see <a href="https://java.woehlke.org/cyclic-cellular-automaton">Maven Project Page</a>
 * @author Thomas Woehlke
 * <p>
 * Created with IntelliJ IDEA.
 * Date: 28.08.13
 * Time: 12:39
 */
public class CyclicCellularAutomatonModel implements Serializable {

    static final long serialVersionUID = -594681595882016258L;

    private Random random;

    private volatile int[][][] lattice;
    private volatile int source;
    private volatile int target;

    @Getter
    private final ComputerKurzweilProperties config;

    @Getter
    private final Point worldDimensions;

    @Getter
    private volatile ColorScheme colorScheme;

    @Getter
    private volatile LatticeNeighbourhood neighbourhood;

    public CyclicCellularAutomatonModel(CyclicCellularAutomatonFrame tab) {
        this.config = tab.getConfig();
        this.colorScheme = new ColorScheme();
        this.random = new Random(new Date().getTime());
        int scale =  config.getCca().getView().getScale();
        int x = scale * config.getCca().getView().getWidth();
        int y = scale * config.getCca().getView().getHeight();
        this.worldDimensions = new Point(x,y);
        this.startVonNeumann();
    }

    private void initCreateLattice() {
        int x = (int) this.worldDimensions.getX();
        int y = (int) this.worldDimensions.getY();
        lattice = new int[2][x][y];
        source = 0;
        target = 1;
    }

    private void initFillLatticeByRandom() {
        int xx = (int) this.worldDimensions.getX();
        int yy = (int) this.worldDimensions.getY();
        for (int y = 0; y < yy; y++) {
            for (int x = 0; x < xx; x++) {
                lattice[source][x][y] = random.nextInt(this.colorScheme.getMaxState());
            }
        }
    }

    public synchronized void step() {
        //System.out.print(".");
        for (int y = 0; y < config.getCca().getView().getHeight(); y++) {
            for (int x = 0; x < worldDimensions.getX(); x++) {
                lattice[target][x][y] = lattice[source][x][y];
                int nextState = (lattice[source][x][y] + 1) % this.colorScheme.getMaxState();
                int west = (int) ((x - 1 + worldDimensions.getX()) % worldDimensions.getX());
                int north = (int) ((y - 1 + worldDimensions.getY()) % worldDimensions.getY());
                int east = (int) ((x + 1 + worldDimensions.getX()) % worldDimensions.getX());
                int south = (int) ((y + 1 + worldDimensions.getY()) % worldDimensions.getY());
                if (neighbourhood == MOORE_NEIGHBORHOOD || neighbourhood == WOEHLKE_NEIGHBORHOOD) {
                    //North-West
                    if (nextState == lattice[source][west][north]) {
                        lattice[target][x][y] = nextState;
                        continue;
                    }
                    //North-East
                    if (nextState == lattice[source][east][north]) {
                        lattice[target][x][y] = nextState;
                        continue;
                    }
                    if (neighbourhood == MOORE_NEIGHBORHOOD) {
                        //South-East
                        if (nextState == lattice[source][east][south]) {
                            lattice[target][x][y] = nextState;
                            continue;
                        }
                    }
                    //SouthWest
                    if (nextState == lattice[source][west][south]) {
                        lattice[target][x][y] = nextState;
                        continue;
                    }
                }
                //North
                if (nextState == lattice[source][x][north]
                ) {
                    lattice[target][x][y] = nextState;
                    continue;
                }
                //East
                if (nextState == lattice[source][east][y]) {
                    lattice[target][x][y] = nextState;
                    continue;
                }
                if (neighbourhood == MOORE_NEIGHBORHOOD || neighbourhood == VON_NEUMANN_NEIGHBORHOOD) {
                    //South
                    if (nextState == lattice[source][x][south]) {
                        lattice[target][x][y] = nextState;
                        continue;
                    }
                }
                //West
                if (nextState == lattice[source][west][y]) {
                    lattice[target][x][y] = nextState;
                }
            }
        }
        this.source = (this.source + 1) % 2;
        this.target = (this.target + 1) % 2;
    }

    public int getCellStatusFor(int x, int y) {
        return this.lattice[source][x][y];
    }

    public synchronized void startVonNeumann() {
        initCreateLattice();
        initFillLatticeByRandom();
        this.neighbourhood = VON_NEUMANN_NEIGHBORHOOD;
    }

    public synchronized void startMoore() {
        initCreateLattice();
        initFillLatticeByRandom();
        this.neighbourhood = MOORE_NEIGHBORHOOD;
    }

    public synchronized void startWoehlke() {
        initCreateLattice();
        initFillLatticeByRandom();
        this.neighbourhood = WOEHLKE_NEIGHBORHOOD;
    }
}
