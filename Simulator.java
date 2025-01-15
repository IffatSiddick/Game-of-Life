import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.Color;

/**
 * A Life (Game of Life) simulator, first described by British mathematician
 * John Horton Conway in 1970.
 *
 * @author David J. Barnes, Michael Kölling & Jeffery Raphael
 * @version 2022.01.06 (1)
 * 
 * pushing to github repo
 */

public class Simulator {
    // The default width for the grid.
    private static final int DEFAULT_WIDTH = 100;

    // The default depth of the grid.
    private static final int DEFAULT_DEPTH = 80;

    // The probability that a Mycoplasma is alive
    private static final double ALIVE_PROB = 0.1;
    
    public static final Color VERY_LIGHT_RED = new Color(255, 102, 102);
    public static final Color VERY_LIGHT_BLUE = new Color(51, 204, 255);
    public static final Color VERY_LIGHT_GREEN = new Color(102, 255, 102);
    public static final Color VERY_LIGHT_ORANGE = new Color(255, 204, 51);

    // List of cells in the field.
    private List<Cell> cells;

    // The current state of the field.
    private Field field;

    // The current generation of the simulation.
    private int generation;

    // A graphical view of the simulation.
    private SimulatorView view;
    
    // Indicates if the simulation is paused or not
    private boolean pausedSim;
    

    /**
     * Execute simulation
     */
    public static void main(String[] args) {
        Simulator sim = new Simulator();
        sim.simulate(100);
    }

    /**
     * Construct a simulation field with default size.
     */
    public Simulator() {
        this(DEFAULT_DEPTH, DEFAULT_WIDTH);
    }
    
    /**
     * Create a simulation field with the given size.
     * @param depth Depth of the field. Must be greater than zero.
     * @param width Width of the field. Must be greater than zero.
     */
    public Simulator(int depth, int width) {
        if (width <= 0 || depth <= 0) {
            System.out.println("The dimensions must be greater than zero.");
            System.out.println("Using default values.");
            depth = DEFAULT_DEPTH;
            width = DEFAULT_WIDTH;
            pausedSim = false;
        }
        
        cells = new ArrayList<>();
        field = new Field(depth, width);

        // Create a view of the state of each location in the field.
        view = new SimulatorView(depth, width, this);

        // Setup a valid starting point.
        reset();
    }

    /**
     * Run the simulation from its current state for a reasonably long period,
     * (4000 generations).
     */
    public void runLongSimulation() {
        simulate(1000);
    }

    /**
     * Changes value of paused variable to its opposite
     * True or false
     */
    public void paused()
    {
        pausedSim = !pausedSim;
    }
    
    /**
     * Run the simulation from its current state for the given number of
     * generations.  Stop before the given number of generations if the
     * simulation ceases to be viable.
     * Will pause the simulation on its current generation if the pause variable is true
     * @param numGenerations The number of generations to run for.
     */
    public void simulate(int numGenerations) {
        while (view.isViable(field) && generation < numGenerations) {
            if(pausedSim) {
                view.showStatus(generation, field);
            }
            else {
                simOneGeneration();
                delay(100);
            }
        } 
    }

    /**
     * Run the simulation from its current state for a single generation.
     * Iterate over the whole field updating the state of each life form.
     * Cell can become dead, diseased or alive.
     */
    public void simOneGeneration() 
    {  
        generation++;
             
        for (Iterator<Cell> it = cells.iterator(); it.hasNext(); ) {
            Cell cell = it.next();
            if (cell.getDiseased()) {
                cell.actDiseased();
            }
            else if (!cell.getDiseased() && cell.isAlive()) {
                cell.spreadDisease();
            }
            if(!cell.getDiseased()) {
                cell.act();
            }
        }

        for (Cell cell : cells) {
            cell.setLifespan();
            cell.updateState();
        }
        view.showStatus(generation, field);
    }

    /**
     * Reset the simulation to a starting position.
     */
    public void reset() {
        generation = 0;
        cells.clear();
        populate();

        // Show the starting state in the view.
        view.showStatus(generation, field);
    }

    /**
     * Randomly populate the field with alive/diseased/dead life forms
     */
    private void populate() {
        Random rand = Randomizer.getRandom();
        field.clear();
        for (int row = 0; row < field.getDepth(); row++) {
            for (int col = 0; col < field.getWidth(); col++) {
                Location location = new Location(row, col);
                int temp = rand.nextInt(4);
                if (temp == 0){
                    Cell lifeform = new Mycoplasma(field, location, VERY_LIGHT_ORANGE);
                    populateCell(lifeform);
                }
                else if (temp == 1){
                    Cell lifeform = new Salmonella(field, location, VERY_LIGHT_RED);
                    populateCell(lifeform);
                }
                else if (temp == 2){
                    Cell lifeform = new Listeria(field, location, VERY_LIGHT_BLUE);
                    populateCell(lifeform);
                }
                else{
                    Cell lifeform = new Cholera(field, location, VERY_LIGHT_GREEN);
                    populateCell(lifeform);
                }
            }
        }
    }
    
    /**
     * Populate each cell with either a alive/diseased/dead Cell
     * 
     * @param lifeform. The Cell being created.
     */
    private void populateCell(Cell lifeform)
    {
        cells.add(lifeform);
        Random rand = Randomizer.getRandom();           
        if (rand.nextDouble() <= ALIVE_PROB) {
            lifeform.populateDiseased();
        }
        else {
            lifeform.setDead();
        }
    }
    
    /**
     * Pause for a given time.
     * @param millisec  The time to pause for, in milliseconds
     */
    private void delay(int millisec) {
        try {
            Thread.sleep(millisec);
        }
        catch (InterruptedException ie) {
            // wake up
        }
    }
}
