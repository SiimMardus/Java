public class Player {
    private String playerType;
    private int hp;
    private int currenthp;
    private int str;
    private int superstr;
    private int progress;
    private int coins;
    private int powerPoints;
    private int healthpotions;

    public Player(String playerType, int player_hp, int player_strength, int player_superstrength, int progress, int coins, int powerPoints) {
        this.playerType = playerType;
        this.hp = player_hp;
        this.currenthp = player_hp;
        this.str = player_strength;
        this.superstr = player_superstrength;
        this.progress = progress;
        this.coins = coins;
        this.powerPoints = powerPoints;
    }

    public int getHealthpotions() {
        return healthpotions;
    }

    public void setHealthpotions(int healthpotions) {
        this.healthpotions = healthpotions;
    }

    public int getCurrenthp() {
        return currenthp;
    }

    public void setCurrenthp(int currenthp) {
        this.currenthp = currenthp;
    }

    public void takeDmg(int damage){
        currenthp -= damage;
    }

    public String getPlayerType() {
        return playerType;
    }

    public void setPlayerType(String playerType) {
        this.playerType = playerType;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getStr() {
        return str;
    }

    public void setStr(int str) {
        this.str = str;
    }

    public int getSuperstr() {
        return superstr;
    }

    public void setSuperstr(int superstr) {
        this.superstr = superstr;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public int getPowerPoints() {
        return powerPoints;
    }

    public void setPowerPoints(int powerPoints) {
        this.powerPoints = powerPoints;
    }
}