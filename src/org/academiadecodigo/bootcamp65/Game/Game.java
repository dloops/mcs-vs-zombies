package org.academiadecodigo.bootcamp65.Game;

import org.academiadecodigo.bootcamp65.Objects.Plants.Bullet;
import org.academiadecodigo.bootcamp65.Objects.Plants.*;
import org.academiadecodigo.bootcamp65.Objects.Shop;
import org.academiadecodigo.bootcamp65.Objects.Zombies.Zombie;
import org.academiadecodigo.bootcamp65.gfx.simplegfx.SimpleGfxGrid;
import org.academiadecodigo.simplegraphics.graphics.Color;
import org.academiadecodigo.simplegraphics.graphics.Rectangle;

public class Game {
    public final static int gameSize = 100;
    public final static int PADDING = 120;
    private int cols;
    private int rows;
    private int threadSleep;
    private int sleepCount;
    private int buyableLand;
    private float acc;
    private float damageAccumulator = 0.3f;
    private Shop shop;

    private Rectangle sizeGame;
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
        this.occupiedSlots = new int[cols + 1][rows + 1];
        this.buyableLand = (int) Math.ceil((double) rows / 3);
        this.plants = new Plant[this.buyableLand * rows];
        System.out.println(5 * 8 + " " + this.buyableLand * rows);
        this.zombies = new Zombie[(cols * rows) - (rows * 2)];
        this.threadSleep = threadSleep;
        this.shop = new Shop();

        this.intro = new Menu();
        this.gameOver = new GameOver();
        this.intro.intro();


        Thread.sleep(threadSleep);
        this.sizeGame = new Rectangle(0, 0, 1000, 720);
        this.sizeGame.setColor(Color.WHITE);
        this.grid = new SimpleGfxGrid(cols, rows, this.gameSize);

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
        this.sizeGame.draw();
        this.grid.init();
        this.shop.init();

        plants[0] = new Plant(1, 1);
        plants[1] = new Plant(2, 1);
        plants[2] = new Plant(3, 1);
        plants[3] = new Plant(4, 1);
        plants[4] = new Plant(5, 1);

        while (!this.gameOver.isOver()) {

            Thread.sleep(200);

            difficultyIncrease();

            spawnZombies();

            checkCollision();

            plantsShoot();

            bulletCollision();

            moveBullets();

            moveZombies();

            bulletCollision();

            //createPlant(true);

        }

    }

    private void difficultyIncrease() {
        this.sleepCount += 200;
        if (this.sleepCount >= 10000) {
            this.sleepCount = 0;
            this.zombieSpawnChance += 0.01f;
            System.out.println("zombie spawn chance - " + this.zombieSpawnChance);
        }
    }

    private void spawnZombies() {
        for (int i = 1; i <= this.cols; i++) {
            boolean occupied = false;

            for (int j = 0; j < this.zombies.length; j++) {
                if (this.zombies[j] != null) {
                    if (this.zombies[j].getCol() == i && this.zombies[j].getRow() == this.rows) {
                        occupied = true;
                        continue;
                    }
                }
            }

            if (!occupied) {
                if (this.zombieSpawnChance > Math.random()) {
                    for (int k = 0; k < this.zombies.length; k++) {
                        if (this.zombies[k] == null || this.zombies[k].isDead()) {
                            this.zombies[k] = new Zombie(i, this.rows);
                            return;
                        }
                    }
                }
            }
        }
    }

    private void moveZombies() {
        for (int i = 0; i < this.zombies.length; i++) {
            if (this.zombies[i] != null && !this.zombies[i].isDead()) {
                this.zombies[i].move();
                if (this.zombies[i].getRow() == 0) {
                    this.gameOver.over();
                    this.gameOver.setOver();
                }
                // make boolean array with gridCol and gridRow

            }
        }
    }

    private void checkCollision() {
        for (int i = 0; i < this.plants.length; i++) {
            if (this.plants[i] != null) {
                if (this.plants[i].isDead()) {
                    this.plants[i] = null;
                    continue;
                }

                for (int j = 0; j < this.zombies.length; j++) {
                    if (this.zombies[j] != null) {
                        if (this.zombies[j].isDead()) {
                            this.zombies[j] = null;
                            continue;
                        }
                        if (this.plants[i].getCol() == zombies[j].getCol() && this.plants[i].getRow() == this.zombies[j].getRow() - 1) {
                            if ((int) this.acc >= 1) {
                                this.zombies[j].setAllowedToMove(false);
                                this.acc -= 1;
                                this.plants[i].damage(1);
                                if (this.plants[i].isDead())
                                    this.zombies[j].setAllowedToMove(true);
                            } else {
                                this.zombies[j].setAllowedToMove(false);
                                this.acc += this.damageAccumulator;
                                return;
                            }
                        }
                    }
                }
            }
        }
    }

    private void plantsShoot() {


        for (int i = 0; i < this.plants.length; i++) {
            if (this.plants[i] != null) {
                this.plants[i].shoot();
            }
        }
    }


    public void moveBullets() {
        Bullet[] bullets;
        for (int i = 0; i < this.plants.length; i++) {
            if (this.plants[i] != null) {
                bullets = this.plants[i].getBullets();
                for (int j = 0; j < bullets.length; j++) {
                    if (bullets[j] != null) {
                        if (bullets[j].isDestroyed()) {
                            bullets[j] = null;
                            continue;
                        }
                        bullets[j].move(this.rows);
                    }
                }
            }
        }
    }

    public void bulletCollision() {
        Bullet[] bullets;
        for (int i = 0; i < this.plants.length; i++) {
            if (this.plants[i] != null) {
                bullets = this.plants[i].getBullets();
                for (int j = 0; j < bullets.length; j++) {
                    if (bullets[j] != null) {
                        for (int k = 0; k < this.zombies.length; k++) {
                            if (this.zombies[k] != null) {
                                if (bullets[j].getCol() == this.zombies[k].getCol() && bullets[j].getRow() == zombies[k].getRow()) {
                                    //System.out.println(bullets[j].getCol() + " " + bullets[j].getRow());
                                    //System.out.println(this.zombies[k].getCol() + " " + zombies[k].getCol());
                                    this.zombies[k].damage(bullets[j].getDmg());
                                    bullets[j].destroy();
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    //Get rows and cols in the parameters, than create a plant and associate to the array
    public void createPlant(boolean create){

        if(create == true){

            int count=1;
            for (int i = 0; i < plants.length; i++) {

            }
            plants[5] = new Plant(1, 2);
            plants[6] = new Plant(2, 2);
            plants[7] = new Plant(3, 2);
            plants[8] = new Plant(4, 2);
            plants[9] = new Plant(5, 2);
        }

    }
}

