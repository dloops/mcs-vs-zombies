package org.academiadecodigo.bootcamp65.Game;

import org.academiadecodigo.bootcamp65.Objects.Bullet;
import org.academiadecodigo.bootcamp65.Objects.ObjectTypes;
import org.academiadecodigo.bootcamp65.Objects.Plants.*;
import org.academiadecodigo.bootcamp65.Objects.Zombies.Zombie;
import org.academiadecodigo.bootcamp65.gfx.simplegfx.SimpleGfxGrid;

public class Game {
    public final static int gameSize = 150;
    public final static int PADDING = 10;
    private int cols;
    private int rows;
    private int threadSleep;
    private int sleepCount;
    private int buyableLand;
    private float acc;
    private float damageAccumulator = 0.3f;

    private SimpleGfxGrid grid;
    private Menu intro;
    private GameOver gameOver;
    private Zombie[] zombies;
    private Plant[] plants;
    private int[][] occupiedSlots;

    private float zombieSpawnChance = 0.05f;


    public Game(int cols, int rows, int threadSleep) throws InterruptedException {
        this.cols = cols;
        this.rows = rows;
        occupiedSlots = new int[cols + 1][rows + 1];
        buyableLand = (int) Math.ceil((double) rows / 3);
        plants = new Plant[buyableLand * rows];

        zombies = new Zombie[(cols * rows) - (rows * 2)];
        this.threadSleep = threadSleep;

        intro = new Menu();
        gameOver = new GameOver();
        intro.intro();

        Thread.sleep(threadSleep);

        grid = new SimpleGfxGrid(cols, rows, gameSize);
        grid.init();

        start();
    }

    /**
     * All this does so far is create 5 images of baljeets
     * Make all baljeets go from col 5 to 1
     * babyboss
     * <p>
     * TIP: Each object, for example MC or Zombie
     * will have a GridImage object inside of it
     *
     * @throws InterruptedException
     */

    public void start() throws InterruptedException {

        plants[0] = new Plant(1, 1);
        plants[1] = new Plant(2, 1);
        plants[2] = new Plant(3, 1);
        plants[3] = new Plant(4, 1);
        plants[4] = new Plant(5, 1);

        while (!gameOver.isOver()) {

            Thread.sleep(200);

            difficultyIncrease();
            spawnZombies();
            checkCollision();

            plantsShoot();
            bulletCollision();
            moveBullets();
            moveZombies();
            bulletCollision();

        }

    }

    private void difficultyIncrease() {
        sleepCount += 200;
        if (sleepCount >= 10000) {
            sleepCount = 0;
            zombieSpawnChance += 0.01f;
            System.out.println("zombie spawn chance - " + zombieSpawnChance);
        }
    }

    private void spawnZombies() {
        for (int i = 1; i <= cols; i++) {
            boolean occupied = false;

            for (int j = 0; j < zombies.length; j++) {
                if (zombies[j] != null) {
                    if (zombies[j].getCol() == i && zombies[j].getRow() == rows) {
                        occupied = true;
                        continue;
                    }
                }
            }

            if (!occupied) {
                if (zombieSpawnChance > Math.random()) {
                    for (int k = 0; k < zombies.length; k++) {
                        if (zombies[k] == null || zombies[k].isDead()) {
                            zombies[k] = new Zombie(i, rows);
                            return;
                        }
                    }
                }
            }
        }
    }

    private void moveZombies() {
        for (int i = 0; i < zombies.length; i++) {
            if (zombies[i] != null) {
                if (!zombies[i].isDead()) {
                    zombies[i].move();
                    if (zombies[i].getRow() == 0) {
                        gameOver.over();
                        gameOver.setOver();
                    }
                    // make boolean array with gridCol and gridRow
                }
            }
        }
    }

    private void checkCollision() {
        for (int i = 0; i < plants.length; i++) {
            if (plants[i] != null) {
                if (plants[i].isDead()) {
                    plants[i] = null;
                    continue;
                }

                for (int j = 0; j < zombies.length; j++) {
                    if (zombies[j] != null) {
                        if (zombies[j].isDead()) {
                            zombies[j] = null;
                            continue;
                        }
                        if (plants[i].getCol() == zombies[j].getCol() && plants[i].getRow() == zombies[j].getRow() - 1) {
                            if ((int) acc >= 1) {
                                zombies[j].setAllowedToMove(false);
                                acc -= 1;
                                plants[i].damage(1);
                                if (plants[i].isDead())
                                    zombies[j].setAllowedToMove(true);
                            } else {
                                zombies[j].setAllowedToMove(false);
                                acc += damageAccumulator;
                                return;
                            }
                        }
                    }
                }
            }
        }
    }

    private void plantsShoot() {
        for (int i = 0; i < plants.length; i++) {
            if (plants[i] != null) {
                plants[i].shoot();
            }
        }
    }


    public void moveBullets() {
        Bullet[] bullets;
        for (int i = 0; i < plants.length; i++) {
            if (plants[i] != null) {
                bullets = plants[i].getBullets();
                for (int j = 0; j < bullets.length; j++) {
                    if (bullets[j] != null) {
                        if (bullets[j].isDestroyed()) {
                            bullets[j] = null;
                            continue;
                        }
                        bullets[j].move(rows);
                    }
                }
            }
        }
    }

    public void bulletCollision() {
        Bullet[] bullets;
        for (int i = 0; i < plants.length; i++) {
            if (plants[i] != null) {
                bullets = plants[i].getBullets();
                for (int j = 0; j < bullets.length; j++) {
                    if (bullets[j] != null) {
                        for (int k = 0; k < zombies.length; k++) {
                            if (zombies[k] != null) {
                                if (bullets[j].getCol() == zombies[k].getCol() && bullets[j].getRow() == zombies[k].getRow()) {
                                    System.out.println(bullets[j].getCol() + " " + bullets[j].getRow());
                                    System.out.println(zombies[k].getCol() + " " + zombies[k].getCol());
                                    zombies[k].damage(bullets[j].getDmg());
                                    bullets[j].destroy();
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
