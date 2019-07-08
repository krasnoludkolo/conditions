package io.github.krasnoludkolo.points;

import io.github.krasnoludkolo.infrastructure.Identifiable;

final class Point implements Identifiable<Integer> {

    private final int points;
    private final int userId;

    static Point create(int userId){
        return new Point(0,userId);
    }

    private Point(int points, int userId) {
        this.points = points;
        this.userId = userId;
    }

    Point increase(){
        int newPoints = points + 1;
        return new Point(newPoints,userId);
    }

    Point decrease(){
        int newPoints = points - 1;
        return new Point(newPoints,userId);
    }

    Point setCount(int point){
        return new Point(point,userId);
    }

    @Override
    public Integer getId() {
        return points;
    }
}
