package io.github.krasnoludkolo;

import java.util.Random;

final class FantasticService {

    private Random random;

    FantasticService(Random random) {
        this.random = random;
    }

    int getRandomNumber(){
        return random.nextInt();
    }


}
