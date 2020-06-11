package com.zegames.entities;

import com.zegames.main.Game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Bullet extends Entity {
    public Bullet(double x, double y, int width, int height, double speed, BufferedImage sprite) {
        super(x, y, width, height, speed, sprite);
    }

    @Override
    public void tick() {
        y-=speed;

        if (y < 0) {
            Game.entities.remove(this);
        }
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillRect(this.getX(), this.getY(), width, height);
    }
}
