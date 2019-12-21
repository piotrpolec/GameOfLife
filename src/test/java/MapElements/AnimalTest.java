package MapElements;

import Lib.Vector2d;
import Maps.WorldMap;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

class AnimalTest {
  Animal animal1;
  Animal animal2;
  WorldMap map;
  int initialEnergy = 4;
  int moveEnergy = 1;
  int plantEnergy = 1;
  @Before
  public void initialize(){
    map = new WorldMap(5,5,0.1);
    animal1 = new Animal(map, new Vector2d(1,1), initialEnergy, moveEnergy );
    animal2 = new Animal(map, new Vector2d(1,1), initialEnergy, moveEnergy );
  }

  @Test
  public void eatTest(){
    map.growGrass(new Vector2d(1,1));
    assertTrue(animal1.eat(plantEnergy));
    assertEquals(initialEnergy + plantEnergy, animal1.getEnergy());
    assertNull(map.getGrassHashMap().get(animal1.getPosition()));

    assertFalse(animal1.eat(plantEnergy));
    assertEquals(initialEnergy + 2 * plantEnergy, animal1.getEnergy());
  }

  @Test
  public void moveTest(){
    Vector2d oldPosition = animal1.getPosition();
    int energy = animal1.getEnergy();
    animal1.move();
    assertNotEquals(oldPosition, animal1.getPosition());

    assertEquals(initialEnergy - moveEnergy, animal1.getEnergy());
    assertNotEquals(initialEnergy, animal1.getEnergy());
  }
  @Test
  public void reproduceTest(){
    assertTrue(animal1.reproduce(animal2, initialEnergy));
    assertNotEquals(initialEnergy, animal1.getEnergy());
    assertNotEquals(initialEnergy, animal2.getEnergy());
    assertEquals(initialEnergy - initialEnergy/4, animal1.getEnergy());
    assertEquals(initialEnergy - initialEnergy/4, animal2.getEnergy());

    assertFalse(animal1.reproduce(animal2,2 * initialEnergy));
  }
}