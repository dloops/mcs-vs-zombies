package org.academiadecodigo.bootcamp65.Objects;

import org.academiadecodigo.bootcamp65.gfx.simplegfx.GridImage;
import org.academiadecodigo.simplegraphics.mouse.Mouse;
import org.academiadecodigo.simplegraphics.mouse.MouseEvent;
import org.academiadecodigo.simplegraphics.mouse.MouseEventType;
import org.academiadecodigo.simplegraphics.mouse.MouseHandler;

public class Shop implements MouseHandler {

    private Mouse mouse;
    public boolean createPlant;

    public void init() {
        this.mouse = new Mouse(this);
        this.mouse.addEventListener(MouseEventType.MOUSE_CLICKED);
        this.createPlant = false;
        GridImage.designShop();
    }

    public boolean getCreatePlant() {
        return createPlant;
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getX() > 151 && mouseEvent.getX() < 196 && mouseEvent.getY() > 76 && mouseEvent.getY() < 121) {
            System.out.println("Test");
            this.createPlant = true;
        }
        this.createPlant=false;
    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {

    }
}
