package com.zegames.graficos;

import com.zegames.main.Game;

import java.awt.*;

public class UI {
    public static int seconds = 0;
    public static int minutes = 0;
    public static int frames = 0;

    public void tick() {
        frames++;

        if (frames == 60) {
            frames = 0;

            seconds++;

            if (seconds == 60) {
                seconds = 0;
                minutes++;
            }
        }
    }

    public void render(Graphics g) {
        String formatTime = "";

        if (minutes < 10) {
            formatTime += "0" + minutes + ":";
        } else {
            formatTime += minutes + ":";
        }

        if (seconds < 10) {
            formatTime += "0" + seconds;
        } else {
            formatTime += seconds;
        }

        g.setColor(Color.WHITE);
        g.setFont(new Font("arial", Font.BOLD, 17));
        g.drawString("Score: " + Game.score, 10, 24);

        g.drawString("Life: " + Game.life + "/5", 280, 24);
    }
}
