package io.github.krasnoludkolo.points;

final class Point {

    final int points;
    final int userId;

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

    int getCount(){
        return points;
    }
}
