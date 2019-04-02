public class Enemy {
    private int hp;
    private int currenthp;
    private int str;
    private boolean tookSuper;

    public Enemy(int hp, int str) {
        this.hp = hp;
        this.str = str;
        this.currenthp = hp;
        this.tookSuper = false;
    }

    public void takeDmg(int damage){
        currenthp -= damage;
    }

    public boolean isTookSuper() {
        return tookSuper;
    }

    public void setTookSuper(boolean tookSuper) {
        this.tookSuper = tookSuper;
    }

    public int getCurrenthp() {
        return currenthp;
    }

    public void setCurrenthp(int currenthp) {
        this.currenthp = currenthp;
    }

    public int getHp() {
        return hp;
    }

    public int getStr() {
        return str;
    }
}
