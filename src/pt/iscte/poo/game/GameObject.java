package pt.iscte.poo.game;

import objects.Manel;

public abstract class GameObject {

    private int health = 3;
    private int attackPower = 1;

    public int getHealth() {
        return health;
    }
    
    public int getAttackPower() {
        return attackPower;
    }   

    public void setAttackPower(int attackPower) {
        this.attackPower = attackPower;
    }

    public boolean isAlive() {
        return health > 0;
    }

}