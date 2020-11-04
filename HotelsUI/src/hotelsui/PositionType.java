/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotelsui;

/**
 *
 * @author Ειρηνη
 */
public class PositionType {
    private int x;
    private int y;
    
    PositionType(int x, int y){
        this.x = x;
        this.y = y;
    }
            
    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
    
    public int getX() {
       return x;
   }
   
    public void setX(int x) {
       this.x = x;
   }
    
    @Override
    public boolean equals(Object obj) {
        return (this.x == ((PositionType)obj).x && this.y == ((PositionType)obj).y);
    }

}
