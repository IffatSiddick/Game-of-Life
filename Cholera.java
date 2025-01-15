import java.awt.Color;
import java.util.List;
import java.util.Random;

/**
 * A representation of Cholera
 *
 * @Iffat Siddick, Ekaterina Hunter
 * @version 12
 */
public class Cholera extends Cell
{
    //The original colour of Cholera
    public static final Color VERY_LIGHT_GREEN = new Color(102, 255, 102);
    
    /**
     * Create a new lifeform3.
     *
     * @param field The field currently occupied.
     * @param location The location within the field.
     */  
    public Cholera(Field field, Location location, Color col) {
        super(field, location, col);
    }

    /**
    * This is how the lifeform decides if it's alive or not
    */
    public void act() {
        List<Cell> neighbours = getField().getLivingNeighbours(getLocation());
        
        Random rand = Randomizer.getRandom();
        if (isAlive()){ 
            if((getLifespan()%10 == 0) && rand.nextDouble() < 0.2) {
                setDiseased();
                setColourDiseased();
            }
            else if ((getLifespan()%10 == 0) && rand.nextDouble() < 0.3) {
                setNextState(false);
            }
            else {
                setNextState(true);
            }
        }
        else {
            if ((getLifespan()% 3 == 0) && rand.nextDouble() < 0.4) {
                setNextState(true);
                recovered();
            }
            else if ((getLifespan()%7 == 0) && rand.nextDouble() < 0.6) {
                setDiseased();
                setColourDiseased();
            }
            else {
                setNextState(false);
            }
        }
    }
    
    /**
     * This method sets the cell back to its original colour.
     * When it is cureed of disease.
     */
    public void recovered()
    {
        setColor(VERY_LIGHT_GREEN);
    }
}

