package org.academiadecodigo.bootcamp65.gfx.simplegfx;

import org.academiadecodigo.bootcamp65.Game.*;
import org.academiadecodigo.bootcamp65.pictures.Picture;
import org.academiadecodigo.bootcamp65.pictures.PictureCalc;

public class GridImage {
    private Picture pic;
    private int gameSize = Game.gameSize;
    private int padding = Game.PADDING;

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

    public GridImage(int col, int row, String source) {
        pic = new Picture(col*gameSize, row*gameSize, source); // Magic 0 as both of those parameters are overwritten with move(); straight away
        pic.grow(-PictureCalc.resizeX(pic.getWidth()), -PictureCalc.resizeY(pic.getHeight()));
        move(col, row);
    }

    public void move(int col, int row) {
        pic.translate(-pic.getMaxX()+(col*gameSize)+padding, -pic.getMaxY()+(row*gameSize)+padding);
        pic.draw();
    }

    public void show() {
        pic.draw();
    }

    public void hide() {
        pic.delete();
    }
}
