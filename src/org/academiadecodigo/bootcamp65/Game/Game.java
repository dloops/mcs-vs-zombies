package org.academiadecodigo.bootcamp65.Game;

import org.academiadecodigo.bootcamp65.gfx.simplegfx.GridImage;
import org.academiadecodigo.bootcamp65.gfx.simplegfx.SimpleGfxGrid;

public class Game {
    public final static int gameSize = 200;
    public final static int PADDING = 10;
    private SimpleGfxGrid grid;
    private int threadSleep;
    private Intro intro;
    private GameOver gameOver;

    public Game(int cols, int rows, int threadSleep) throws InterruptedException {
        this.threadSleep = threadSleep;

        intro = new Intro();
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

        GridImage zombie1 = new GridImage(8, 1, "src/org/academiadecodigo/bootcamp65/PictureFiles/baljeet.png");
        GridImage zombie2 = new GridImage(8, 2, "src/org/academiadecodigo/bootcamp65/PictureFiles/baljeet.png");
        GridImage zombie3 = new GridImage(8, 3, "src/org/academiadecodigo/bootcamp65/PictureFiles/baljeet.png");
        GridImage zombie4 = new GridImage(8, 4, "src/org/academiadecodigo/bootcamp65/PictureFiles/baljeet.png");
        GridImage zombie5 = new GridImage(8, 5, "src/org/academiadecodigo/bootcamp65/PictureFiles/baljeet.png");

        for (int i = 8; i > 0; i--) {
            Thread.sleep(500); // 1 second delay on the baljeet moves
            zombie1.move(i, 1);
            zombie2.move(i, 2);
            zombie3.move(i, 3);
            zombie4.move(i, 4);
            zombie5.move(i, 5);

        }

        Thread.sleep(1000);

        gameOver.over();
    }
}
