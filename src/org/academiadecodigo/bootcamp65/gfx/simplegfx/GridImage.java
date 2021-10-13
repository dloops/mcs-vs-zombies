package org.academiadecodigo.bootcamp65.gfx.simplegfx;

import org.academiadecodigo.bootcamp65.Game.*;
import org.academiadecodigo.bootcamp65.pictures.Picture;
import org.academiadecodigo.bootcamp65.pictures.PictureCalc;

public class GridImage {
    private Picture pic;
    private int gameSize = Game.gameSize;
    private int padding = Game.PADDING;
    private int imageReduction;
    private int reductionSafety = 25;

    /**
     * Makes a new image and places it in a col and row, image is resized to comply with gameSize
     * All images are resized equally, some may get distorted.
     *
     * babyboss can't make comments for shit (╯°□°）╯︵ ┻━┻
     *
     * @param col col where we want the image to appear
     * @param row row where we want the image to appear
     * @param source source of the image file
     */

    public GridImage(int imageReduction, int col, int row, String source, boolean bullet) {
        if(imageReduction != 0)
            this.imageReduction = imageReduction + reductionSafety;

        pic = new Picture(col*gameSize, row*gameSize, source); // Magic 0 as both of those parameters are overwritten with move(); straight away
        pic.grow(-PictureCalc.resizeX(pic.getWidth())-imageReduction, -PictureCalc.resizeY(pic.getHeight())-imageReduction);
        if(bullet) {
            moveBullet(col, row);
            return;
        }
        move(col, row);
    }

    public static void designShop(){
        Picture shopPlant = new Picture(150, 50, "src/org/academiadecodigo/bootcamp65/PictureFiles/shopPlant.png");
        Picture shopPlantWall = new Picture(195, 50, "src/org/academiadecodigo/bootcamp65/PictureFiles/PlantWall.png");
        shopPlantWall.draw();
        shopPlant.draw();
    }


    public void move(int col, int row) {
        pic.translate(-pic.getMaxX()+(col*gameSize)+padding-imageReduction, -pic.getMaxY()+(row*gameSize)+padding);
        pic.draw();
    }

    public void moveBullet(int col, int row) {
        pic.translate(-pic.getMaxX()+(col*gameSize)+padding-imageReduction-(gameSize*0.125), -pic.getMaxY()+(row*gameSize)+padding-(gameSize*0.325));
        pic.draw();
    }

    public void show() {
        pic.draw();
    }

    public void hide() {
        pic.delete();
    }
}
