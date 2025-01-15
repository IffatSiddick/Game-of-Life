import java.awt.Color;
import java.util.List;
import java.util.Random;

/**
* Simplest form of life.
* Fun Fact: Mycoplasma are one of the simplest forms of life.  A type of
* bacteria, they only have 500-1000 genes! For comparison, fruit flies have
* about 14,000 genes.
* 
*
* @author David J. Barnes, Michael Kölling, Jeffery Raphael, Iffat Siddick, Ekaterina Hunter
* @version 12
*/

public class Mycoplasma extends Cell {
    //The original colour of Mycoplasma
    public static final Color VERY_LIGHT_ORANGE = new Color(255, 204, 51);
    
    public Mycoplasma(Field field, Location location, Color col) {
        super(field, location, col);
    }
    
    /**
     * This is how the Mycoplasma decides if it's alive or not
    */
    public void act() {
        List<Cell> neighbours = getField().getLivingNeighbours(getLocation());
        
        if (isAlive()) {
            if (neighbours.size() > 3){
                setNextState(false);
            }
            else if (neighbours.size() > 1){
                setNextState(true);
            }
            else if (neighbours.size() < 2 ){
                setNextState(false);
            }
        }
        else if (!isAlive() && neighbours.size() == 3 ){
            setNextState(true);
            recovered();
        }
        else {
            setNextState(false);
        }
    }
    
    /**
     * This method sets the cell back to its priginal colour.
     * When it is cureed of disease.
     */
    public void recovered()
    {
        setColor(VERY_LIGHT_ORANGE);
    }
}



