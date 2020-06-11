package com.zegames.entities;

import com.zegames.main.Game;

import java.awt.image.BufferedImage;

public class Enemy extends Entity {
    public int life = rand.nextInt(7 - 3) + 3;

    public Enemy(double x, double y, int width, int height, double speed, BufferedImage sprite) {
        super(x, y, width, height, speed, sprite);
    }

    public void tick() {
        y+=speed;

        if (y >= Game.HEIGHT) {
            Game.entities.remove(this);
            Game.life--;

            if (Game.life <= 0) {
                System.exit(1);
            }

            return;
        }

        for (int i = 0; i < Game.entities.size(); i++) {
            Entity entity = Game.entities.get(i);

            if (entity instanceof Bullet) {
                if (Entity.isColidding(this, entity)) {
                    life--;
                    Game.entities.remove(entity);
                    break;
                }
            }

            if (entity instanceof Player) {
                if (Entity.isColidding(this, entity)) {
                    Game.life--;
                    Explosion explosion = new Explosion(this.getX(), this.getY(),16, 16, 0, null);
                    Game.entities.add(explosion);
                    Game.entities.remove(this);

                    if (Game.life <= 0) {
                        System.exit(1);
                    }

                    break;
                }
            }
        }

        if (life < 0) {
            Explosion explosion = new Explosion(this.getX(), this.getY(),16, 16, 0, null);
            Game.entities.add(explosion);
            Game.entities.remove(this);
            Game.score++;
        }
    }
}
