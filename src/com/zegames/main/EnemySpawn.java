package com.zegames.main;

import com.zegames.entities.Enemy;
import com.zegames.entities.Entity;

public class EnemySpawn {
    public int targetTime = 60 * (Entity.rand.nextInt(5 - 2) + 2);
    public int curTime = 0;

    public void tick() {
        curTime++;

        if (curTime == targetTime) {
            curTime = 0;
            int yy = 0;
            int xx = Entity.rand.nextInt(Game.WIDTH - 16);
            int randomEnemy = Entity.rand.nextInt(4);

            Enemy enemy = new Enemy(xx, yy, 16, 16, Entity.rand.nextInt(2 - 1) + 1, Entity.ENEMIES[randomEnemy]);
            Game.entities.add(enemy);
        }
    }
}
