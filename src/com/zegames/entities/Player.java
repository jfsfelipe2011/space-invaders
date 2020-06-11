package com.zegames.entities;

import com.zegames.main.Game;
import java.awt.image.BufferedImage;

public class Player extends Entity{
    public boolean right, left;
    public boolean isShooting = false;

    public Player(int x, int y, int width, int height,double speed,BufferedImage sprite) {
        super(x, y, width, height,speed,sprite);
    }

    public void tick() {
        if (right) {
            x+=speed;
        } else if (left) {
            x-=speed;
        }

        if (x >= Game.WIDTH) {
            x = -16;
        } else if (x + 16 < 0) {
            x = Game.WIDTH;
        }

        if (isShooting) {
            isShooting = false;

            int xx = this.getX() + 5;
            int yy = this.getY();

            Bullet bullet = new Bullet(xx, yy, 2, 2, 2, null);
            Game.entities.add(bullet);
        }
    }
}
