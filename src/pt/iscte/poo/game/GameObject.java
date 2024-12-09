package pt.iscte.poo.game;

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

    public void takeDamage(int damage) {
        this.health =- damage;
    }

    public void attack(GameObject target) {
        target.takeDamage(this.attackPower);
    }
}
