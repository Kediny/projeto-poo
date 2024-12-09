package pt.iscte.poo.game;

import objects.Manel;

public abstract class GameObject {

    private int health = 3;
    private int attackPower = 1;

    public int getHealth() {
        return health;
    }
    
    public void heal() {
        this.health=3;
    }
    
    public int getAttackPower() {
        return attackPower;
    }   

    public void setAttackPower() {
        this.attackPower = 2;
    }

    public boolean isAlive() {
        return health > 0;
    }

}