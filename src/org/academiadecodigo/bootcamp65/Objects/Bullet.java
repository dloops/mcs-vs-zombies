package org.academiadecodigo.bootcamp65.Objects;

import org.academiadecodigo.bootcamp65.Objects.Zombies.ZombiePictures;
import org.academiadecodigo.bootcamp65.gfx.simplegfx.GridImage;
import org.academiadecodigo.simplegraphics.pictures.Picture;

public class Bullet {
    private String source = "src/org/academiadecodigo/bootcamp65/PictureFiles/pea.png";
    private int dmg;
    private int col;
    private int row;
    private int imageReduction = 100;

    private GridImage bulletImage;

    public Bullet(int col, int row) {
        dmg = 1;

        this.col = col;
        this.row = row;
        createImage(ZombiePictures.BasicZombie[(int) (Math.random() * ZombiePictures.BasicZombie.length)]);
    }

    public void move() {

    }

    public void createImage(String source) {
        bulletImage = new GridImage(imageReduction, row, col, source);
    }

}
