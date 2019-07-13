package io.github.krasnoludkolo.game;

import java.util.Random;

final class Always4Random extends Random {

    @Override
    public int nextInt() {
        return 4;
    }

}
