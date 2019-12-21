package Visualizers;

import Lib.Vector2d;
import Maps.IMap;

import java.util.Collection;

public class MapVisualizer {
  private static final String EMPTY_CELL = "    ";
  private static final String FRAME_SEGMENT = "-";
  private static final String CELL_SEGMENT = "|";
  private IMap map;

  public MapVisualizer(IMap map) {
    this.map = map;
  }

  public String draw(Vector2d lowerLeft, Vector2d upperRight) {
    StringBuilder builder = new StringBuilder();
    for (int i = upperRight.y + 1; i >= lowerLeft.y - 1; i--) {
      if (i == upperRight.y + 1) {
        builder.append(drawHeader(lowerLeft, upperRight));
      }
      builder.append(String.format("%3d: ", i));
      for (int j = lowerLeft.x; j <= upperRight.x + 1; j++) {
        if (i < lowerLeft.y || i > upperRight.y) {
          builder.append(drawFrame(j <= upperRight.x));
        } else {
          builder.append(CELL_SEGMENT);
          if (j <= upperRight.x) {
            builder.append(drawObject(new Vector2d(j, i)));
          }
        }
      }
      builder.append(System.lineSeparator());
    }
    return builder.toString();
  }

  private String drawFrame(boolean innerSegment) {
    if (innerSegment) {
      return FRAME_SEGMENT + FRAME_SEGMENT;
    } else {
      return FRAME_SEGMENT;
    }
  }

  private String drawHeader(Vector2d lowerLeft, Vector2d upperRight) {
    StringBuilder builder = new StringBuilder();
    builder.append(" y\\x ");
    for (int j = lowerLeft.x; j < upperRight.x + 1; j++) {
      builder.append(String.format("%2d", j));
    }
    builder.append(System.lineSeparator());
    return builder.toString();
  }

  private String drawObject(Vector2d currentPosition) {
    String result = null;
    if (this.map.isOccupied(currentPosition)) {
      Object object = this.map.objectAt(currentPosition);
      if (object != null) {
        if(object instanceof Collection) result = "\ud83d\udc2e";
        else result = object.toString();
      } else {
        result = EMPTY_CELL;
      }
    } else {
      result = EMPTY_CELL;
    }
    return result;
  }
}
