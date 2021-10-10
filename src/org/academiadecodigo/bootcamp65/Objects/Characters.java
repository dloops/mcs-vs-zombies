package org.academiadecodigo.bootcamp65.Objects;
import org.academiadecodigo.bootcamp65.gfx.simplegfx.*;


public interface Characters {

    public void move();

    public void damage(int dmg);

    public void createImage(String source);

    public int getHealth();

    public boolean isDead();

    public void setDead();

    public void setHealth(int health);

    public void setImage(String source);


    public int gridCol();

    public int gridRow();

}
