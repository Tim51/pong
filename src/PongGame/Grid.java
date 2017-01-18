package PongGame;

import java.util.HashMap;
import java.util.Set;

public class Grid {
    
    private final int width, height;
    private int xPointer, yPointer;
    private HashMap<Integer, String> grid;
    private boolean horizontalWarp, verticalWarp;
    private boolean checkSides;
    
    public Grid(int x, int y, boolean horizontalWarp, boolean verticalWarp, boolean checkSides) {
        this.height = y;
        this.width = x;
        this.yPointer = 1;
        this.xPointer = 1;
        this.horizontalWarp = horizontalWarp;
        this.verticalWarp = verticalWarp;
        this.checkSides = checkSides;
        initializeGrid();
    }
    
    public Grid(int x, int y, boolean horizontalWarp, boolean verticalWarp) {
        this(x,y,horizontalWarp,verticalWarp,true);
    }
    
    public Grid(int x, int y) {
        this(x,y,false,false);
    }
    
    private void initializeGrid() {
        grid = new HashMap<>();
        for (int i = 1; i <= height * width; i++) {
            grid.put(i,"");
        }
    }
    
    public void movePointerRight() {
        int newXPointer = xPointer;
        int newYPointer = yPointer;
        newXPointer = (newXPointer)%width+1;
        while (isEmpty(newXPointer,newYPointer)) {
            if (checkSides) {
                for (int i=1; i<height; i++) {
                    //check down
                    newYPointer = yPointer+i;
                    if (newYPointer <= height) {
                        if (!isEmpty(newXPointer,newYPointer)) {
                            break;
                        }
                    } else if ((newYPointer > height)&&verticalWarp) {
                        newYPointer = newYPointer%height +1;
                        if (!isEmpty(newXPointer,newYPointer)) {
                            break;
                        }
                    }
                    //check up
                    newYPointer = yPointer-i;
                    if (newYPointer >= 1) {
                        if (!isEmpty(newXPointer,newYPointer)) {
                            break;
                        }
                    } else if ((newYPointer < 1)&&verticalWarp) {
                        newYPointer = (newYPointer+height)%height +1;
                        if (!isEmpty(newXPointer,newYPointer)) {
                            break;
                        }
                    }
                }
            } 
            if ((newYPointer>=1)&&(newYPointer<=height)&&!isEmpty(newXPointer,newYPointer)) {
                break;
            }
            newXPointer = (newXPointer)%width+1;
            newYPointer = yPointer;
        }
        if (horizontalWarp || (newXPointer > xPointer)) {
            xPointer = newXPointer;
            yPointer = newYPointer;
        }
    }
    
    public void movePointerLeft() {    
        int newXPointer = xPointer;
        int newYPointer = yPointer;
        newXPointer = (newXPointer-2+width)%width+1;
        while (isEmpty(newXPointer,newYPointer)) {
            if (checkSides) {
                for (int i=1; i<height; i++) {
                    //check down
                    newYPointer = yPointer+i;
                    if (newYPointer <= height) {
                        if (!isEmpty(newXPointer,newYPointer)) {
                            break;
                        }
                    } else if ((newYPointer > height)&&verticalWarp) {
                        newYPointer = newYPointer%height +1;
                        if (!isEmpty(newXPointer,newYPointer)) {
                            break;
                        }
                    }
                    //check up
                    newYPointer = yPointer-i;
                    if (newYPointer >= 1) {
                        if (!isEmpty(newXPointer,newYPointer)) {
                            break;
                        }
                    } else if ((newYPointer < 1)&&verticalWarp) {
                        newYPointer = (newYPointer+height)%height +1;
                        if (!isEmpty(newXPointer,newYPointer)) {
                            break;
                        }
                    }
                }
            } 
            if ((newYPointer>=1)&&(newYPointer<=height)&&!isEmpty(newXPointer,newYPointer)) {
                break;
            }
            newXPointer = (newXPointer-2+width)%width+1;
            newYPointer = yPointer;
        }
        if (horizontalWarp || (newXPointer < xPointer)) {
            xPointer = newXPointer;
            yPointer = newYPointer;
        }
    }
    
    public void movePointerUp() {
        int newXPointer = xPointer;
        int newYPointer = yPointer;
        newYPointer = (newYPointer-2+height)%height+1;
        while (isEmpty(newXPointer,newYPointer)) {
            if (checkSides) {
                for (int i=1; i<width; i++) {
                    //check down
                    newXPointer = xPointer+i;
                    if (newXPointer <= width) {
                        if (!isEmpty(newXPointer,newYPointer)) {
                            break;
                        }
                    } else if ((newXPointer > width)&&horizontalWarp) {
                        newXPointer = newXPointer%width +1;
                        if (!isEmpty(newXPointer,newYPointer)) {
                            break;
                        }
                    }
                    //check up
                    newXPointer = xPointer-i;
                    if (newXPointer >= 1) {
                        if (!isEmpty(newXPointer,newYPointer)) {
                            break;
                        }
                    } else if ((newXPointer < 1)&&horizontalWarp) {
                        newXPointer = (newXPointer+width)%width +1;
                        if (!isEmpty(newXPointer,newYPointer)) {
                            break;
                        }
                    }
                }
            } 
            if ((newXPointer>=1)&&(newXPointer<=width)&&!isEmpty(newXPointer,newYPointer)) {
                break;
            }
            newYPointer = (newYPointer-2+height)%height+1;
            newXPointer = xPointer;
        }
        if (verticalWarp || (newYPointer < yPointer)) {
            xPointer = newXPointer;
            yPointer = newYPointer;
        }
    }
    
    public void movePointerDown() {
        int newXPointer = xPointer;
        int newYPointer = yPointer;
        newYPointer = (newYPointer)%height+1;
        while (isEmpty(newXPointer,newYPointer)) {
            if (checkSides) {
                for (int i=1; i<width; i++) {
                    //check down
                    newXPointer = xPointer+i;
                    if (newXPointer <= width) {
                        if (!isEmpty(newXPointer,newYPointer)) {
                            break;
                        }
                    } else if ((newXPointer > width)&&horizontalWarp) {
                        newXPointer = newXPointer%width +1;
                        if (!isEmpty(newXPointer,newYPointer)) {
                            break;
                        }
                    }
                    //check up
                    newXPointer = xPointer-i;
                    if (newXPointer >= 1) {
                        if (!isEmpty(newXPointer,newYPointer)) {
                            break;
                        }
                    } else if ((newXPointer < 1)&&horizontalWarp) {
                        newXPointer = (newXPointer+width)%width +1;
                        if (!isEmpty(newXPointer,newYPointer)) {
                            break;
                        }
                    }
                }
            } 
            if ((newXPointer>=1)&&(newXPointer<=width)&&!isEmpty(newXPointer,newYPointer)) {
                break;
            }
            newYPointer = (newYPointer)%height+1;
            newXPointer = xPointer;
        }
        if (verticalWarp || (newYPointer > yPointer)) {
            xPointer = newXPointer;
            yPointer = newYPointer;
        }
    }
    
    public boolean isEmpty(int x, int y) {
        return get(x,y).isEmpty();
    }
    
    public void set(int x, int y, String string) {
        grid.put(width*(y-1) + x, string);
    }
    
    public void set(int place, String string) {
        grid.put(place, string);
    }
    
    public String get(int x, int y) {
        return grid.get(width*(y-1) + x);
    }
    
    public void setPointer(int x, int y) {
       xPointer = x;
       yPointer = y;
    }
    
    public String getPointerValue() {
        return get(xPointer,yPointer);
    }
    
    public int getPointerPosition() {
        return width*(yPointer-1) + xPointer;
    }
    
    public int getPointerXPosition() {
        return xPointer;
    }
    
    public int getPointerYPosition() {
        return yPointer;
    }
    
    public String get(int place) {
        return grid.get(place);
    }
    
    public static void main(String[] args) {
        Grid g = new Grid(3, 4,false,true);
        System.out.println(g.getPointerPosition() + " " + g.getPointerValue());
        g.movePointerRight();
        g.movePointerRight();
        g.movePointerRight();
        System.out.println(g.getPointerPosition() + " " + g.getPointerValue());
        g.movePointerDown();
        System.out.println(g.getPointerPosition() + " " + g.getPointerValue());
        
        
    }
    
    public Set<Integer> keySet() {
        return grid.keySet();
    }
    
    public int getWidth() {
        return width;
    }
    
    public int getHeight() {
        return height;
    }
}
