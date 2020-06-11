package com.zegames.entities;

import com.zegames.main.Game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Explosion extends Entity {
    private int frames = 0;
    private int targetFrames = 4;
    private int maxAnimation = 4;
    private int curAnimation = 0;

    public BufferedImage[] sprites = {
            Game.spritesheet.getSprite(0, 16, 16, 16),
            Game.spritesheet.getSprite(16, 16, 16, 16),
            Game.spritesheet.getSprite(32, 16, 16, 16),
            Game.spritesheet.getSprite(48, 16, 16, 16)
    };

    public Explosion(double x, double y, int width, int height, double speed, BufferedImage sprite) {
        super(x, y, width, height, speed, sprite);
    }

    @Override
    public void tick() {
        frames++;

        if (frames == targetFrames) {
            frames = 0;
            curAnimation++;

            if (curAnimation > maxAnimation - 1) {
                curAnimation = 0;
                Game.entities.remove(this);
            }
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(sprites[curAnimation], this.getX(), this.getY(), null);
    }
}
