package units;
import map.Element;

public abstract class Unit extends Element{
    // stats of each unit
    public int hp, atk, def, spd, range, cost;
    public boolean aerial;
    int id;
    static int numOfUnits;
    

    public boolean isAerial() {
        return aerial;
    }

    public void setAerial(boolean aerial) {
        this.aerial = aerial;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getAtk() {
        return atk;
    }

    public void setAtk(int atk) {
        this.atk = atk;
    }

    public int getDef() {
        return def;
    }

    public void setDef(int def) {
        this.def = def;
    }

    public int getSpd() {
        return spd;
    }

    public void setSpd(int spd) {
        this.spd = spd;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isAlive() {
        return this.hp > 0;
    }

    public int Attacking(Unit cible) {
        // dont forget to add a function that checks if the target is in range before
        // calling this method
        // check if the unit can attack aerial/ground units
        if (cible.isAerial() && !this.isAerial()) {
            return 0; // Ground units cannot attack aerial units
        }
        // no need to check if the target or the attacker is alive because if they are
        // dead they will be deleted from the game

        // i added a random factor to the attacking that makes it so you deal between
        // 80% and 120% of your attack stat
        int damage = (int) (this.atk * (0.8 + Math.random() * 0.4)) - cible.getDef();
        if (damage < 0) {
            damage = 0; // If defense of the target is higher than attack, no damage is dealt
        }
        cible.setHp(cible.getHp() - damage);
        return damage;
    }

}
