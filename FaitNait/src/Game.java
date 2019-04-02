import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import static java.lang.String.valueOf;

public class Game extends Canvas implements Runnable {

    // Game window values
    private static final int WIDTH = 1000;
    private static final int HEIGHT = 700;
    private static final String NAME = "FaitNait";

    // Input handler
    private InputHandler input = new InputHandler(this);

    // In-game images
    private BufferedImage start;
    private BufferedImage charselect;
    private BufferedImage arena;
    private BufferedImage mageTown;
    private BufferedImage magePlayer;
    private BufferedImage rangeTown;
    private BufferedImage rangePlayer;
    private BufferedImage store;
    private BufferedImage training;
    private BufferedImage gui;
    private BufferedImage exit;
    private BufferedImage hitSplat;
    private BufferedImage missSplat;
    private BufferedImage victory;
    private BufferedImage defeat;
    private BufferedImage finalVicotry;
    private BufferedImage enemyPickle;
    private BufferedImage enemyDude;
    private BufferedImage enemyShrek;
    private BufferedImage enemySpeaker;
    private BufferedImage enemyBoss;

    // Initializing enemies
    private Enemy pickleEnemy = new Enemy(80, 20);
    private Enemy dudeEnemy = new Enemy(120, 35);
    private Enemy shrekEnemy = new Enemy(145, 40);
    private Enemy speakerEnemy = new Enemy(250, 40);
    private Enemy bossEnemy = new Enemy(295, 55);
    private List<Enemy> enemies = Arrays.asList(pickleEnemy, dudeEnemy, shrekEnemy, speakerEnemy, bossEnemy);

    // Initializing values for the game
    private int hitSplatTimer = 100;
    private String location = "Start";
    private String exitLocation = "Start";
    private int playerHit;
    private int enemyHit;

    // Initializing the player and setting a current enemy
    private Player player = new Player("Range", 150, 30, 60, 1, 0, 0);
    private Enemy currentEnemy = enemies.get(player.getProgress() - 1);

    // Assigning the images
    {
        try {
            start = ImageIO.read(new File("StartScreen.png"));
            arena = ImageIO.read(new File("Arena.png"));
            mageTown = ImageIO.read(new File("MageMain.png"));
            magePlayer = ImageIO.read(new File("MagePlayer.png"));
            rangeTown = ImageIO.read(new File("RangeMain.png"));
            rangePlayer = ImageIO.read(new File("RangePlayer.png"));
            exit = ImageIO.read(new File("Exit.png"));
            charselect = ImageIO.read(new File("NewGameChoose.png"));
            store = ImageIO.read(new File("Store.png"));
            training = ImageIO.read(new File("training.png"));
            gui = ImageIO.read(new File("ArenaGui.png"));
            enemyPickle = ImageIO.read(new File("EnemyPickle.png"));
            enemyDude = ImageIO.read(new File("EnemyDude.png"));
            enemyShrek = ImageIO.read(new File("EnemyShrek.png"));
            enemySpeaker = ImageIO.read(new File("EnemySpeaker.png"));
            enemyBoss = ImageIO.read(new File("EnemyBoss.png"));
            hitSplat = ImageIO.read(new File("HitSplat.png"));
            missSplat = ImageIO.read(new File("MissSplat.png"));
            victory = ImageIO.read(new File("Victory.png"));
            defeat = ImageIO.read(new File("Defeat.png"));
            finalVicotry = ImageIO.read(new File("FinalVictory.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private Game() {

        // Constructing the game frame

        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setMaximumSize(new Dimension(WIDTH, HEIGHT));
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        JFrame frame = new JFrame(NAME);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(this, BorderLayout.CENTER);
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setVisible(true);
    }

    private synchronized void start() {
        new Thread(this).start();
    }

    public void run() {

        // Game loop

        while (true) {
            update();
            render();
        }
    }

    private void update() {

        // Updates the sprites on screen based in input handler

        currentEnemy = enemies.get(player.getProgress() - 1);
        if (input.loadgame.isPressed()) {
            if (location.equals("Start")) {
                try {
                    loadGame("savegame.txt");
                    exitLocation = "Town";
                    location = "Town";
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else if (input.newgame.isPressed()) {
            if (location.equals("Start")) {
                exitLocation = "CharSelect";
                location = "CharSelect";
            }
        } else if (input.arena.isPressed()) {
            if (location.equals("Town")) {
                try {
                    saveGame("savegame.txt");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                player.setCurrenthp(player.getHp());
                exitLocation = "Arena";
                location = "Arena";
            }
        } else if (input.opt_1.isPressed()) {
            if (location.equals("CharSelect")) {
                player = new Player("Mage", 110, 20, 35, 1, 0, 0);
                location = "Town";
                exitLocation = "Town";
            } else if (location.equals("Training")) {
                if (player.getPowerPoints() >= 2){
                    player.setHp(player.getHp() + 25);
                    player.setPowerPoints(player.getPowerPoints() - 2);
                    location = "Town";
                    exitLocation = "Town";
                }
            } else if (location.equals("Stores")){
                if (player.getCoins() >= 20){
                    player.setHealthpotions(player.getHealthpotions() + 1);
                    player.setCoins(player.getCoins() - 20);
                    location = "Town";
                    exitLocation = "Town";
                }
            }
        } else if (input.opt_2.isPressed()) {
            if (location.equals("CharSelect")) {
                player = new Player("Range", 150, 20, 40, 1, 0, 0);
                location = "Town";
                exitLocation = "Town";
            } else if (location.equals("Training")) {
                if (player.getPowerPoints() >= 3){
                    player.setStr(player.getStr() + 10);
                    player.setPowerPoints(player.getPowerPoints() - 3);
                    location = "Town";
                    exitLocation = "Town";
                }
            } else if (location.equals("Stores")){
                if (player.getCoins() >= 50){
                    player.setSuperstr(player.getSuperstr() + 20);
                    player.setCoins(player.getCoins() - 50);
                    location = "Town";
                    exitLocation = "Town";
                }
            }
        } else if (input.stores.isPressed()) {
            if (location.equals("Town")) {
                exitLocation = "Stores";
                location = "Stores";
            }
        } else if (input.training.isPressed()) {
            if (location.equals("Town")){
                exitLocation = "Training";
                location = "Training";
            }
        } else if (input.exit.isPressed()) {
            if (location.equals("CharSelect")) {
                exitLocation = "CharSelect";
                location = "Exit";
            } else if (location.equals("Start")) {
                exitLocation = "Start";
                location = "Exit";
            } else if (location.equals("Arena")) {
                exitLocation = "Arena";
                location = "Exit";
            } else if (location.equals("Stores")){
                exitLocation = "Town";
                location = "Town";
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else if (location.equals("Training")){
                exitLocation = "Town";
                location = "Town";
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else if (location.equals("Victory")){
                if(player.getProgress() == 5){
                    System.exit(0);
                }
                exitLocation = "Town";
                location = "Town";
                player.setProgress(player.getProgress() + 1);
                player.setCurrenthp(player.getHp());
                currentEnemy.setCurrenthp(currentEnemy.getHp());
                try {
                    Thread.sleep(500);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (location.equals("Defeat")){
                exitLocation = "Town";
                location = "Town";
                player.setCurrenthp(player.getHp());
                currentEnemy.setCurrenthp(currentEnemy.getHp());
                currentEnemy.setTookSuper(false);
                try {
                    Thread.sleep(500);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (location.equals("Town")){
                exitLocation = "Town";
                location = "Exit";
            }
        } else if (input.yes.isPressed()) {
            if (location.equals("Exit")) {
                if (exitLocation.equals("Arena")) {
                    exitLocation = "Town";
                    location = "Town";
                    currentEnemy.setCurrenthp(currentEnemy.getHp());
                    currentEnemy.setTookSuper(false);
                } else if (exitLocation.equals("Start")) {
                    System.exit(0);
                } else if (exitLocation.equals("CharSelect")) {
                    exitLocation = "Start";
                    location = "Start";
                } else if (exitLocation.equals("Town")){
                    try {
                        saveGame("savegame.txt");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    System.exit(0);
                }
            }
        } else if (input.no.isPressed()) {
            if (location.equals("Exit")) {
                location = exitLocation;
            }
        } else if (input.attack.isPressed()) {
            if (location.equals("Arena")) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e){
                    throw new RuntimeException(e);
                }
                int playerHit_chance = (int)(Math.random() * 10);
                if (playerHit_chance < 10) {
                    playerHit = (int)(Math.random() * player.getStr());
                }
                int enemyhit_chance = (int)(Math.random() * 10);
                if (enemyhit_chance < 10) {
                    enemyHit = (int)(Math.random() * currentEnemy.getStr());
                }

                currentEnemy.takeDmg(playerHit);
                if (playerHit > 0){
                    hitSplatTimer = 0;
                }
                checkDefeat();
            }
        } else if (input.superAttack.isPressed()) {
            if (!currentEnemy.isTookSuper()) {
                currentEnemy.setTookSuper(true);
                if (location.equals("Arena")) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e){
                        throw new RuntimeException(e);
                    }
                    int playerHit_chance = (int) (Math.random() * 10);
                    if (playerHit_chance < 8) {
                        playerHit = (int) (Math.random() * player.getSuperstr());

                    }
                    int enemyhit_chance = (int) (Math.random() * 10);
                    if (enemyhit_chance < 5) {
                        enemyHit = (int) (Math.random() * currentEnemy.getStr());
                    }
                    if (player.getPlayerType().equals("Mage")){
                        player.setCurrenthp(player.getCurrenthp() + (int)(player.getSuperstr() * 0.4));
                    }
                    currentEnemy.takeDmg(playerHit);
                    if (playerHit > 0){
                        hitSplatTimer = 0;
                    }
                    checkDefeat();
                }
            }
        } else if (input.potion.isPressed()){
            if (location.equals("Arena")){
                if (player.getHealthpotions() > 0){
                    if ((player.getCurrenthp() + 35) > player.getHp()){
                        player.setCurrenthp(player.getHp());
                    } else {
                        player.setCurrenthp(player.getCurrenthp() + 35);
                    }
                    player.setHealthpotions(player.getHealthpotions() - 1);
                    try {
                        Thread.sleep(500);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void checkDefeat(){

        // Checks player's health

        if (currentEnemy.getCurrenthp() <= 0) {
            currentEnemy.setCurrenthp(0);
            player.setCoins(player.getCoins() + 50);
            player.setPowerPoints(player.getPowerPoints() + 5);
            location = "Victory";
            exitLocation = "Victory";
        } else {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e){
                throw new RuntimeException(e);
            }
            player.takeDmg(enemyHit);
            if (player.getCurrenthp() <= 0) {
                player.setCurrenthp(0);
                location = "Defeat";
                exitLocation = "Defeat";
            }
        }
    }

    public void render() {

        // Renders the game images to the frame

        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }

        Graphics background = bs.getDrawGraphics();
        Graphics playermodel = bs.getDrawGraphics();
        Graphics enemymodel = bs.getDrawGraphics();
        Graphics guiOverlay = bs.getDrawGraphics();
        Graphics hitsplats = bs.getDrawGraphics();
        Graphics victoryScreen = bs.getDrawGraphics();
        Graphics defeatScreen = bs.getDrawGraphics();


        if (location.equals("Start")) {
            background.drawImage(start, 0, 0, getWidth(), getHeight(), null);
        } else if (location.equals("Town")) {
            if (player.getPlayerType().equals("Range")) {
                background.drawImage(rangeTown, 0, 0, getWidth(), getHeight(), null);
                paint_progress(background);
            } else if (player.getPlayerType().equals("Mage")) {
                background.drawImage(mageTown, 0, 0, getWidth(), getHeight(), null);
                paint_progress(background);
            }
        }else if (location.equals("Arena") || location.equals("Victory") || location.equals("Defeat")) {
            background.drawImage(arena, 0, 0, getWidth(), getHeight(), null);

            if (player.getPlayerType().equals("Range")) {
                playermodel.drawImage(rangePlayer, 150, 270, 300, 350, null);
            } else if (player.getPlayerType().equals("Mage")) {
                playermodel.drawImage(magePlayer, 150, 270, 300, 350, null);
            }

            if (player.getProgress() == 1) {
                enemymodel.drawImage(enemyPickle, 700, 30, 250, 300, null);
            } else if (player.getProgress() == 2) {
                enemymodel.drawImage(enemyDude, 700, 30, 250, 300, null);
            } else if (player.getProgress() == 3) {
                enemymodel.drawImage(enemyShrek, 700, 30, 250, 300, null);
            } else if (player.getProgress() == 4) {
                enemymodel.drawImage(enemySpeaker, 700, 50, 250, 300, null);
            } else if (player.getProgress() == 5) {
                enemymodel.drawImage(enemyBoss, 700, 30, 250, 300, null);
            }

            if(location.equals("Victory")){
                if(player.getProgress() >= 5){
                    victoryScreen.drawImage(finalVicotry, 0, 0, getWidth(), getHeight(), null);
                } else {
                    victoryScreen.drawImage(victory, 0, 0, getWidth(), getHeight(), null);
                }
            } else if(location.equals("Defeat")){
                defeatScreen.drawImage(defeat, 0, 0, getWidth(), getHeight(), null);
            }
            guiOverlay.drawImage(gui, 0, 0, getWidth(), getHeight(), null);
            paint_arena(background);
            paint_potions(background);
        } else if (location.equals("Exit")) {
            Graphics exitScreen = bs.getDrawGraphics();
            exitScreen.drawImage(exit, 340, 280, 300, 100, null);
        } else if (location.equals("Stores")) {
            background.drawImage(store, 150, 170, 750, 450, null);
            paint_coins(background);
        } else if (location.equals("Training")) {
            background.drawImage(training, 150, 170, 750, 450, null);
            paint_powerpoints(background);
        } else if (location.equals("CharSelect")) {
            background.drawImage(charselect, 0, 0, getWidth(), getHeight(), null);
        }

        if (hitSplatTimer < 100){

            if (playerHit > 0){
                hitsplats.drawImage(hitSplat,700,30,125,125,null);
            } else if (playerHit == 0){
                hitsplats.drawImage(missSplat,700,30,125,125,null);
            }

            if (enemyHit > 0){
                hitsplats.drawImage(hitSplat,150,270,125,125,null);
            } else if (enemyHit == 0){
                hitsplats.drawImage(missSplat,150,270,125,125,null);
            }

            paint_hits(background);

            hitSplatTimer++;
        }

        background.dispose();
        playermodel.dispose();
        enemymodel.dispose();
        guiOverlay.dispose();
        hitsplats.dispose();
        victoryScreen.dispose();
        defeatScreen.dispose();

        bs.show();
    }

    // Methods for displaying text in different locations

    private void paint_arena(Graphics g) {
        int fontSize = 40;
        g.setFont(new Font("Agency FB", Font.BOLD, fontSize));
        g.setColor(Color.BLACK);
        g.drawString(valueOf(player.getCurrenthp()), 120, 280);
        g.drawString(valueOf(currentEnemy.getCurrenthp()), 580, 67);
    }

    private void paint_hits(Graphics g) {
        int fontSize = 40;
        g.setFont(new Font("Agency FB", Font.BOLD, fontSize));
        g.setColor(Color.WHITE);
        g.drawString(valueOf(playerHit), 750, 100);
        g.drawString(valueOf(enemyHit), 200, 340);
    }

    private void paint_powerpoints(Graphics g) {
        int fontSize = 40;
        g.setFont(new Font("Agency FB", Font.BOLD, fontSize));
        g.setColor(Color.WHITE);
        g.drawString(valueOf(player.getPowerPoints()), 650, 240);
    }

    private void paint_coins(Graphics g) {
        int fontSize = 40;
        g.setFont(new Font("Agency FB", Font.BOLD, fontSize));
        g.setColor(Color.WHITE);
        g.drawString(valueOf(player.getCoins()), 650, 240);
    }

    private void paint_potions(Graphics g) {
        int fontSize = 40;
        g.setFont(new Font("Agency FB", Font.BOLD, fontSize));
        g.setColor(Color.WHITE);
        g.drawString(valueOf(player.getHealthpotions()), 630, 680);
    }

    private void paint_progress(Graphics g) {
        int fontSize = 40;
        g.setFont(new Font("Agency FB", Font.BOLD, fontSize));
        g.setColor(Color.BLACK);
        g.drawString(valueOf(player.getProgress()), 900, 152);
        g.drawString(valueOf(player.getHp()), 430, 250);
        g.drawString(valueOf(player.getStr()), 430, 295);
        if (player.getPlayerType().equals("Mage")) {
            g.drawString(player.getSuperstr() + " DMG + " + (int)(player.getSuperstr() * 0.40) + " HEAL", 430, 380);
        } else if (player.getPlayerType().equals("Range")) {
            g.drawString((player.getSuperstr() / 2) + "-" + (player.getSuperstr() / 2) + " FIRE ARROWS", 430, 375);
        }
    }


    // Loading the game from file
    private void loadGame(String filename) throws Exception {
        Scanner sc = new Scanner(new File(filename));
        String[] dataArray = {};
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            dataArray = line.split("-");
        }
        sc.close();
        player.setHp(Integer.parseInt(dataArray[0]));
        player.setStr(Integer.parseInt(dataArray[1]));
        player.setSuperstr(Integer.parseInt(dataArray[2]));
        player.setProgress(Integer.parseInt(dataArray[3]));
        player.setCoins(Integer.parseInt(dataArray[4]));
        player.setPowerPoints(Integer.parseInt(dataArray[5]));
        player.setHealthpotions(Integer.parseInt(dataArray[6]));
        player.setPlayerType(dataArray[7]);
    }


    // Saves the game to file
    private void saveGame(String filename) throws Exception{
        StringBuilder saveBuilder = new StringBuilder();
        saveBuilder.append(player.getHp());
        saveBuilder.append("-");
        saveBuilder.append(player.getStr());
        saveBuilder.append("-");
        saveBuilder.append(player.getSuperstr());
        saveBuilder.append("-");
        saveBuilder.append(player.getProgress());
        saveBuilder.append("-");
        saveBuilder.append(player.getCoins());
        saveBuilder.append("-");
        saveBuilder.append(player.getPowerPoints());
        saveBuilder.append("-");
        saveBuilder.append(player.getHealthpotions());
        saveBuilder.append("-");
        saveBuilder.append(player.getPlayerType());
        String text = saveBuilder.toString();
        Files.write(Paths.get(filename), text.getBytes());
    }

    public static void main(String[] args) {
        new Game().start();
    }
}