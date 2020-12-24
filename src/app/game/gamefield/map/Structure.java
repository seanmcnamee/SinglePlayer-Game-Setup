package app.game.gamefield.map;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;

import app.game.gamefield.touchable.Touchable;
import app.supportclasses.GameValues;

public class Structure {
    public enum Type {
        VERTICAL_SMALL_WALL, VERTICAL_LARGE_WALL, HORIZONTAL_SMALL_WALL, HORIZONTAL_LARGE_WALL,
        SMALL_1DOOR_HOUSE, SMALL_2DOOR_HOUSE, SMALL_3DOOR_HOUSE, SMALL_4DOOR_HOUSE,
        LARGE_1DOOR_HOUSE, LARGE_2DOOR_HOUSE, LARGE_3DOOR_HOUSE, LARGE_4DOOR_HOUSE,
    }
    private static final int SMALL_SIZE = 6;
    private static final int LARGE_SIZE = 10;
    private static final int DOOR_SIZE = 2;

    private GameValues gameValues;
    private Map map;
    private BufferedImage wallImage;

    public Structure(GameValues gameValues, Map map, BufferedImage wallImage) {
        this.gameValues = gameValues;
        this.map = map;
        this.wallImage = wallImage;
    }

    public void generate(Type structure, Point topLeftStart) {
        switch (structure) {
            case VERTICAL_SMALL_WALL:
                addVerticalWall(topLeftStart, SMALL_SIZE, false);
                break;
            case VERTICAL_LARGE_WALL:
                addVerticalWall(topLeftStart, LARGE_SIZE, false);
                break;
            case HORIZONTAL_SMALL_WALL:
                addHorizontalWall(topLeftStart, SMALL_SIZE, false);
                break;
            case HORIZONTAL_LARGE_WALL:
                addHorizontalWall(topLeftStart, LARGE_SIZE, false);
                break;
            case SMALL_1DOOR_HOUSE:
                addXDoorHouse(topLeftStart, SMALL_SIZE, 1);
                break;
            case SMALL_2DOOR_HOUSE:
                addXDoorHouse(topLeftStart, SMALL_SIZE, 2);
                break;
            case SMALL_3DOOR_HOUSE:
                addXDoorHouse(topLeftStart, SMALL_SIZE, 3);
                break;
            case SMALL_4DOOR_HOUSE:
                addXDoorHouse(topLeftStart, SMALL_SIZE, 4);
                break;
            case LARGE_1DOOR_HOUSE:
                addXDoorHouse(topLeftStart, LARGE_SIZE, 1);
                break;
            case LARGE_2DOOR_HOUSE:
                addXDoorHouse(topLeftStart, LARGE_SIZE, 2);
                break;
            case LARGE_3DOOR_HOUSE:
                addXDoorHouse(topLeftStart, LARGE_SIZE, 3);
                break;
            case LARGE_4DOOR_HOUSE:
                addXDoorHouse(topLeftStart, LARGE_SIZE, 4);
                break;
            default:

        }
    }

    /**
     * Available as public so that non-random houses can be made
     */
    public void generateHouse(Point topLeftStart, int size, boolean leftDoor, boolean rightDoor, boolean topDoor, boolean bottomDoor) {
        addVerticalWall(topLeftStart, size, leftDoor); //Left Wall
        addVerticalWall(new Point(topLeftStart.x + size - 1, topLeftStart.y), size, rightDoor); //Right wall
        addHorizontalWall(new Point(topLeftStart.x, topLeftStart.y), size, topDoor); //Top wall
        addHorizontalWall(new Point(topLeftStart.x, topLeftStart.y + size - 1 ), size, bottomDoor); //Bottom wall
    }

    private void addVerticalWall(Point topLeftStart, int size, boolean withDoor) {
        int rndDoorPos = withDoor ? generateRndDoorPos(size): size+1;
        for (int i = 0; i < size; i++) {
            if (!withDoor || i < rndDoorPos || i >= rndDoorPos+DOOR_SIZE) {
                map.addTouchable(new Touchable(gameValues, topLeftStart.x, topLeftStart.y+i, wallImage, gameValues.INGAME_TILE_SIZE));
            }
        }
    }

    private void addHorizontalWall(Point topLeftStart, int size, boolean withDoor) {
        int rndDoorPos = withDoor ? generateRndDoorPos(size): size+1;
        for (int i = 0; i < size; i++) {
            if (!withDoor || i < rndDoorPos || i >= rndDoorPos+DOOR_SIZE) {
                map.addTouchable(new Touchable(gameValues, topLeftStart.x+i, topLeftStart.y, wallImage, gameValues.INGAME_TILE_SIZE));
            }
        }
    }

    private void addXDoorHouse(Point topLeftStart, int size, int doorCount) {
        boolean[] doors = generateXRandomDoors(doorCount);
        generateHouse(topLeftStart, size, doors[0], doors[1], doors[2], doors[3]);
    }

    private int generateRndDoorPos(int size) {
        return (int) (Math.random() * (size-DOOR_SIZE-2) + 1);
    }

    private boolean[] generateXRandomDoors(int doors) {
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i=0; i<4; i++) {
            list.add(i);
        }
        Collections.shuffle(list);
        boolean[] boolDoors = new boolean[4];
        for (int i=0; i<4; i++) {
            if (i < doors) {
                boolDoors[i] = true;
            } else {
                boolDoors[i] = false;
            }
        }
        return boolDoors;
    }
}
