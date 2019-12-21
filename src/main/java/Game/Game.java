package Game;

import Visualizers.WorldVisualizer;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Game {
  public static void main(String[] args) {
    JSONParser parser = new JSONParser();
    String parametersPath = System.getProperty("user.dir") + "\\parameters.json";
    try {
      Object parameters = parser.parse(new FileReader(parametersPath));
      JSONObject jsonObject = (JSONObject) parameters;
      int width = ((Long) jsonObject.get("width")).intValue();
      int height = ((Long) jsonObject.get("height")).intValue();
      float jungleRatio = ((Double) jsonObject.get("jungleRatio")).floatValue();
      int animalsCount = ((Long) jsonObject.get("animalsCount")).intValue();
      int initialEnergy = ((Long) jsonObject.get("initialEnergy")).intValue();
      int plantEnergy = ((Long) jsonObject.get("plantEnergy")).intValue();
      int moveEnergy = ((Long) jsonObject.get("moveEnergy")).intValue();

      World world = new World(height, width, jungleRatio, initialEnergy, moveEnergy,plantEnergy, animalsCount);

      WorldVisualizer worldVisualizer = new WorldVisualizer(world);

      worldVisualizer.startVisualization();
    }
    catch (IOException | ParseException | NullPointerException exception ){
      if (exception instanceof FileNotFoundException) {
        System.out.println("File not found");
        System.exit(1);
      }
      if (exception instanceof ParseException) {
        System.out.println("Error occured while parsing");
        System.exit(1);
      }
      if (exception instanceof NullPointerException) {
        System.out.println(exception);
        System.exit(1);
      }
    }
  }
}
