import java.awt.Color;
import java.util.List;
import java.util.Random;

/**
 * A class representing the shared characteristics of all forms of life
 *
 * @author David J. Barnes, Michael Kölling & Jeffery Raphael
 * @version 2022.01.06 (1)
 */

public abstract class Cell {
    // Whether the cell is alive or not.
    private boolean alive;

    // Whether the cell will be alive in the next generation.
    private boolean nextAlive;
    
    //Whethere a cell is diseased or  not
    private boolean isDiseased;

    // The cell's field.
    private Field field;

    // The cell's position in the field.
    private Location location;

    // The cell's color
    private Color color = Color.white;
    
    // The cell's lifespan
    private int lifespan;
    
    //The probability of becoming diseased
    private static final double CELL_DISEASED_PROB = 0.1;

    /**
     * Create a new cell at location in field.
     *
     * @param field The field currently occupied.
     * @param location The location within the field.
     * @param col The colour of the cell
     */
    public Cell(Field field, Location location, Color col) {
        alive = true;
        nextAlive = false;
        this.field = field;
        lifespan = 0;
        setLocation(location);
        setColor(col);
        isDiseased = false;
    }

    /**
     * Make this cell act - that is: the cell decides it's status in the
     * next generation.
     */
    abstract protected void act();
    
    /**
     * How the cell behaves when diseased - checks if it has been cured or dies.
     * Otherwise remains diseased.
     * 
     */
    protected void actDiseased()
    {
        setNextState(true);
        cured();
        dies();
    }
    
    /**
     * Randomise disease generation 
     */
    protected void populateDiseased()
    {
        Random rand = Randomizer.getRandom();
        if (rand.nextDouble() <= CELL_DISEASED_PROB) {
            setDiseased();
            setColourDiseased();
        }
    }
    /**
     * Increments the value of the cells lifespan
     * To show how many consecutive generations its been alive for
     * If the cell is dead, lifespan is set to 0.
     */
    protected void setLifespan(){
        if (isAlive()){
            lifespan = lifespan + 1;
        }
        else {
            lifespan = 0;
        }
    }
    
    /**
     * Returns the value of lifespan
     * How many generations the cell has been alive for
     * 
     * @return int
     */
    protected int getLifespan()
    {
        return lifespan;
    }
    
    /**
     * Check whether the cell is alive or not.
     * @return true If the cell is still alive.
     */
    protected boolean isAlive() {
        return alive;
    }

    /**
     * Indicate that the cell is no longer alive.
     */
    protected void setDead() {
        alive = false;
    }

    /**
     * Indicate that the cell will be alive or dead in the next generation.
     * @param boolean - true if cell alive, false if cell is dead
     */
    protected void setNextState(boolean value) {
        nextAlive = value;
    }

    /**
     * Changes the state of the cell
     */
    protected void updateState() {
      alive = nextAlive;
    }

    /**
     * Changes the color of the cell
     * @param Colour
     */
    protected void setColor(Color col) {
      color = col;
    }

    /**
     * Returns the cell's color
     * @return Colour
     */
    protected Color getColor() {
      return color;
    }

    /**
     * Return the cell's location.
     * @return The cell's location.
     */
    protected Location getLocation() {
        return location;
    }

    /**
     * Place the cell at the new location in the given field.
     * @param location The cell's location.
     */
    protected void setLocation(Location location) {
        this.location = location;
        field.place(this, location);
    }

    /**
     * Return the cell's field.
     * @return The cell's field.
     */
    protected Field getField() {
        return field;
    }
    
    /**
     * Changes the value of isDiseased to true/false
     * Indicates is cell is diseased or not
     */
    protected void setDiseased()
    {
        isDiseased = !isDiseased;
    }
    
    /**
     * Returns the boolean value of isDiseased
     * @return boolean
     */
    protected boolean getDiseased()
    {
        return isDiseased;
    }
    
    /**
     * The colour of a diseased cell
     */
    protected void setColourDiseased()
    {
        setColor(Color.BLACK); 
    }
    
    /**
     * The rule for if a diseased cell dies
     */
    protected void dies()
    {
        if (isDiseased && lifespan >= 20) {
            setNextState(false);
            setDiseased();
            recovered();
        }
    }
    
    /**
     * Abstract method for setting the cured cell back to its original colour.
     */
    abstract protected void recovered();
    
    /**
     * The rule for if a dieseased cell is cured.
     */
    protected void cured()
    {   
        List<Cell> notDiseasedNeighbours = getField().getNotDiseasedNeighbours(getLocation());
        //if neighbours are diseased they don't count towards healing
        if(isDiseased && notDiseasedNeighbours.size() >= 4) {
            setDiseased();
            recovered();
            //change color back to orig color
        }
    }
    
    /**
     * The rule for if an alive, not diseased cell will become diseased.
     */
    protected void spreadDisease()
    {
        List<Cell> diseasedNeighbours = getField().getDiseasedNeighbours(getLocation());
        if (!isDiseased && isAlive() && diseasedNeighbours.size() >= 3){
            setDiseased();
            setColourDiseased();
        }
    }
}