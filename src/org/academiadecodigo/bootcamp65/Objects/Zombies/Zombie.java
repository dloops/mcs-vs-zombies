package org.academiadecodigo.bootcamp65.Objects.Zombies;

import org.academiadecodigo.bootcamp65.Objects.Characters;
import org.academiadecodigo.bootcamp65.gfx.simplegfx.GridImage;

public class Zombie implements Characters {
    private int health;
    private boolean dead = false;
    private float acc;
    private float moveAcc = 0.3f;
    private int col;
    private int row;
    private int startingCol;
    private int startingRow;

    GridImage zombieImage;

    public Zombie(int col, int row) {
        this.col = col;
        this.row = row;
        startingCol = col;
        startingRow = row;
        createImage(ZombiePictures.BasicZombie[(int) (Math.random() * ZombiePictures.BasicZombie.length)]);
    }

    @Override
    public void move() {
        if(row > 1) {
            if ((int) acc >= 1) {
                row -= 1;
                zombieImage.move(row, col);
                acc = 0.0f;
                //MOVE
            } else accumulator(moveAcc);
        }
    }

    private int accumulator(float acc) {
        this.acc += moveAcc;
        return (int) acc;
    }

    @Override
    public void damage(int dmg) {
        if((health -= dmg) >= 0) {
            health -= dmg;
            return;
        }

        health = 0;
        setDead();
    }

    @Override
    public void createImage(String source) {
        zombieImage = new GridImage(0, row, col, source);
    }

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public boolean isDead() {
        return dead;
    }

    @Override
    public void setHealth(int health) {
        this.health = health;
    }

    @Override
    public void setImage(String source) {

    }


    @Override
    public void setDead() {
        zombieImage.hide();
        dead = true;
    }

    @Override
    public int gridCol() {
        return col;
    }

    @Override
    public int gridRow() {
        return row;
    }

    public boolean atSpawn() {
        return startingRow == row;
    }
}
