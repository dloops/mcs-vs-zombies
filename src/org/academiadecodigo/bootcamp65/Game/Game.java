package org.academiadecodigo.bootcamp65.Game;

import org.academiadecodigo.bootcamp65.Objects.Plants.*;
import org.academiadecodigo.bootcamp65.Objects.Zombies.Zombie;
import org.academiadecodigo.bootcamp65.gfx.simplegfx.SimpleGfxGrid;

public class Game {
    public final static int gameSize = 200;
    public final static int PADDING = 10;
    private int cols;
    private int rows;
    private int threadSleep;
    private int sleepCount;
    private int buyableLand;

    private SimpleGfxGrid grid;
    private Menu intro;
    private GameOver gameOver;
    private Zombie[] zombies;
    private Plant[] plants;

    private float zombieSpawnChance = 0.015f;


    public Game(int cols, int rows, int threadSleep) throws InterruptedException {
        this.cols = cols;
        this.rows = rows;
        buyableLand = (int) Math.ceil((double) rows / 3);
        plants = new Plant[buyableLand*rows];

        zombies = new Zombie[(cols*rows) - (rows *2)];
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
     *
     * TIP: Each object, for example MC or Zombie
     * will have a GridImage object inside of it
     *
     * @throws InterruptedException
     */

    public void start() throws InterruptedException {

        Plant plant = new Plant(1, 1);
        Plant plant1 = new Plant(2, 1);
        Plant plant2 = new Plant(3, 1);
        Plant plant3 = new Plant(4, 1);
        Plant plant4 = new Plant(5, 1);


        while(true) {

    Thread.sleep(200);

    difficultyIncrease();
    spawnZombies();
    moveZombies();
}

    }

    private void difficultyIncrease() {
        sleepCount += 200;
        if(sleepCount >= 10000) {
            sleepCount = 0;
            zombieSpawnChance += 0.001f;
            System.out.println("zombie spawn chance - " + zombieSpawnChance);
        }
    }

    private void spawnZombies() {
        for (int i = 1; i <= cols; i++) {
            boolean occupied = false;

            for (int j = 0; j < zombies.length; j++) {
                if(zombies[j] != null) {
                if(zombies[j].gridCol() == i && zombies[j].gridRow() == rows) {
                    occupied = true;
                    continue;
                }
                }
            }

            if(!occupied) {
                if (zombieSpawnChance > Math.random()) {
                    for (int k = 0; k < zombies.length; k++) {
                        if(zombies[k] == null || zombies[k].isDead()) {
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
                    // make boolean array with gridCol and gridRow
                }
            }
        }
    }

    private void checkCollision() {
        for (int i = 0; i < buyableLand; i++) {
            for (int j = 0; j < rows; j++) {
                
            }
        }
    }

}
