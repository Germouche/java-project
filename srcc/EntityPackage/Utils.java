package EntityPackage;

public class Utils {
    /**
     *
     * @param a Tuple double de la position du point a
     * @param b Tuple double de la position du point b
     * @return Carré de la distance entre les points a et b
     */
    public static double getDistSq(double[] a, double[] b){
        return Math.pow(a[0] - b[0], 2) + Math.pow(a[1] - b[1], 2);
    }

    /**
     *
     * @param a
     * @param b
     * @return Retourne le minimum signé entre les valeurs absolues a et b
     */
    public static double absMin(double a, double b){
        return (a < 0 ? -1 : 1) * Math.min(Math.abs(a), b);
    }

}
