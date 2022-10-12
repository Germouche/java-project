package TestPackage;

import EntityPackage.Balls;

public class TestBalls {
    public static void main(String[] args) throws Exception {
        Balls balles = new Balls(5, 100, 100);
        System.out.println(balles.toString());

        balles.translate(10, 10);

        System.out.println(balles.toString());
    }
}
