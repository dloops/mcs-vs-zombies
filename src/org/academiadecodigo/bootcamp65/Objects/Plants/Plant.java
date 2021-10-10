package org.academiadecodigo.bootcamp65.Objects.Plants;

import org.academiadecodigo.bootcamp65.Objects.Characters;
import org.academiadecodigo.bootcamp65.Objects.Plants.PlantPictures;
import org.academiadecodigo.bootcamp65.gfx.simplegfx.GridImage;

public class Plant implements Characters {
    private int health;
    private boolean dead;
    private int col;
    private int row;
    private int imageReduction;

    GridImage plantImage;

    public Plant(int col, int row) {
        health = 2;
        imageReduction = 30;
        this.col = col;
        this.row = row;
        createImage(PlantPictures.BasicPlant[(int) (Math.random() * PlantPictures.BasicPlant.length)]);
    }

    @Override
    public void move() {
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
        plantImage = new GridImage(imageReduction, row, col, source);
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
        plantImage.hide();
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
}
