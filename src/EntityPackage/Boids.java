package EntityPackage;

public class Boids extends Balls {
    private double[][] accel;

    private double[][] nextAccel;
    private double[][] nextVelocity;
    private double[][] nextPosition;

    private Boids[] otherBoids;

    // Rayon de vision des boids
    private static int radius = 30;
    private static int radiusSq = (int) Math.pow(radius, 2);

    // Angle de vision (en radians)
    private static double fov = 300 *2*Math.PI/360;

    private static double maxLinearVelocity = 30;

    /**
     * Constructeur de Boids
     * TODO : rendre possible plus tard le fait de définir les groupes de boids externes
     * @param num Nombre de boids de cette instance
     * @param w Taille en largeur de l'espace
     * @param h Taille en hauteur de l'espace
     * @param externBoidsGroupsNb Nombre de groupes de boids externe
     */
    public Boids(int num, int w, int h, int externBoidsGroupsNb){
        super(num, w, h);

        accel = new double[num][2];
        nextAccel = new double[num][2];
        nextVelocity = new double[num][2];
        nextPosition = new double[num][2];

        // Récupération des autres groupes de boids (si il y en a)
        this.otherBoids = new Boids[externBoidsGroupsNb];

        for(int i = 0; i < internSize(); i++){
            accel[i][0] = 0;
            accel[i][1] = 0;

            nextAccel[i][0] = 0;
            nextAccel[i][1] = 0;

            nextVelocity[i][0] = 0;
            nextVelocity[i][1] = 0;

            nextPosition[i][0] = 0;
            nextPosition[i][1] = 0;
        }
    }


    /**
     * Ajoute des groupes externe de Boids
     * @param externBoids Instances de Boids à considérer
     */
    public void addExternBoids(Boids ...externBoids){
        for(int i=0; i < otherBoids.length; i++){
            this.otherBoids[i] = externBoids[i];
        }
    }

    /**
     * Retourne la taille de ce groupe de boids
     * Redéfinie pour une question de clareté après l'intégration de groupes de boids étrangers
     * @return Taille de cette instance de boids
     */
    public int internSize(){
        return size();
    }


    /**
     * Retourne la taille totale des groupes de boids
     * @return Taille totale des groupes de boids
     */
    public int wholeSize(){
        int sumSize = 0;
        for(Boids b : otherBoids){
            sumSize += b.size();
        }

        return sumSize + internSize();
    }


    public double[] getAccel(int index){
        return accel[index];
    }


    public int getRadius(){
        return radius;
    }


    /**
     * TODO : Par construction, on est obligé de réecrire setPosition pour toucher
     * nextVelocity puis ballVelocity après updateStatus. A terme, on pourra implémenter
     * directement la bonne version dans balls (avec les nextPos/Spd/Accel) et on
     * aura plus à réimplémenter setPosition
     */
    @Override
    public void setPosition(int index, double x, double y){
        nextPosition[index][0] = x;
        nextPosition[index][1] = y;
    }


    public void reInit(){
        super.reInit();

        for(int i = 0; i < internSize(); i++){
            accel[i][0] = 0;
            accel[i][1] = 0;

            nextAccel[i][0] = 0;
            nextAccel[i][1] = 0;

            nextVelocity[i][0] = 0;
            nextVelocity[i][1] = 0;

            nextPosition[i][0] = 0;
            nextPosition[i][1] = 0;
        }

    }


    /**
     * Met à jour le statut mécanique futur du boid en mécanique classique
     * L'accélération est la seule à ne pas dépendre directement de la vitesse ou position
     * contrairement à la vitesse et position futures du boid
     * @param index
     * @param acl
     */
    public void setFutureStatus(int index, double[] acl){
        nextAccel[index] = acl;

        nextVelocity[index][0] = getVelocity(index)[0] + nextAccel[index][0];
        nextVelocity[index][1] = getVelocity(index)[1] + nextAccel[index][1];

        nextPosition[index][0] = getPosition(index)[0] + nextVelocity[index][0];
        nextPosition[index][1] = getPosition(index)[1] + nextVelocity[index][1];
    }

    /**
     * Permet d'inverser l'orientation d'une entité selon un axe
     * A appeler nécessairement avant l'update status
     * @param index
     * @param axis: 0 pour horizontal, 1 pour vertical, 2 pour H+V
     */
    public void invertOrientation(int index, int axis){
        if(axis == 0){
            nextAccel[index][1] *= -1;
            nextVelocity[index][1] *= -1;
        }

        if(axis == 1){
            nextAccel[index][0] *= -1;
            nextVelocity[index][0] *= -1;
        }
    }

    /**
     * Prépare les paramètres physiques (position, vitesse, accel) du boids
     * pour la prochain instant t
     */
    public void updateStatus(){
        for(int i=0; i < accel.length; i++){
            points[i].setLocation(nextPosition[i][0], nextPosition[i][1]);

            // On limite la vitesse
            ballsVelocity[i][0] = Utils.absMin(nextVelocity[i][0], maxLinearVelocity);
            ballsVelocity[i][1] = Utils.absMin(nextVelocity[i][1], maxLinearVelocity);

            accel[i] = nextAccel[i];
        }
    }

    @Override
    public String toString(){
        String str = "";
        for(int i=0; i < points.length; i++){
            str += String.format("Boid %d : x(%f, %f), v(%f, %f), a(%f, %f)\n", i, points[i].getX(), points[i].getY(), getVelocity(i)[0], getVelocity(i)[1], getAccel(i)[0], getAccel(i)[1]);
        }

        return str;
    }


    /**
     *
     * @param boids : Liste des boids
     * @param refBoidIndex : Index du boid de référence
     * @return (Fx, Fy, nb voisins)
     */
    public double[] computeFlockStress(int refBoidIndex){
        int nbNei = 0; // Nombre de voisins

        double cohesionConst = 0.1;
        //double cohesionConst = 0;
        double[] massCtr = {0, 0};
        double[] cohesionAcc = {0, 0};

        double alignConst = 0.2;
        //double alignConst = 0;
        double[] alignAcc = {0, 0};

        double repulsionConst = 0.05;
        //double repulsionConst = 0;
        double[] repulsionAcc = {0, 0};

        // Déterminer les boids dans le rayon
        for(int i=0; i < wholeSize(); i++){
            if(i != refBoidIndex){
                if(inRange(refBoidIndex, i, radiusSq) && inFOV(refBoidIndex, i, fov)){
                    //System.out.println(String.format("Voisin %d trouvé dans le FOV à %d", i, refBoidIndex));
                    nbNei++;

                    // Centre de masse
                    massCtr[0] += getPosition(i)[0];
                    massCtr[1] += getPosition(i)[1];

                    // Alignement
                    alignAcc[0] += getVelocity(i)[0] - getVelocity(refBoidIndex)[0];
                    alignAcc[1] += getVelocity(i)[1] - getVelocity(refBoidIndex)[1];

                    // Répulsion
                    /*
                    // Version non linéaire
                    repulsionAcc[0] += Utils.absMin(20, 1/(getPosition(i)[0] - getPosition(refBoidIndex)[0]));
                    repulsionAcc[1] += Utils.absMin(20, 1/(getPosition(i)[1] - getPosition(refBoidIndex)[1]));
                    */
                    // Version linéaire
                    repulsionAcc[0] += ((getPosition(i)[0] - getPosition(refBoidIndex)[0]) > 0 ? -1 : 1) * (2*radius - Math.abs(getPosition(i)[0] - getPosition(refBoidIndex)[0]));
                    repulsionAcc[1] += ((getPosition(i)[1] - getPosition(refBoidIndex)[1]) > 0 ? -1 : 1) * (2*radius - Math.abs(getPosition(i)[1] - getPosition(refBoidIndex)[1]));
                }
            }
        }

        // Mise à la moyenne (si il y a eu des voisins)
        if(nbNei > 0){
            massCtr[0] /= nbNei;
            massCtr[1] /= nbNei;

            alignAcc[0] /= nbNei;
            alignAcc[1] /= nbNei;

            repulsionAcc[0] /= nbNei;
            repulsionAcc[1] /= nbNei;

            //System.out.println(String.format("Ctr de masse (%f, %f) du voisin pour %d", massCtr[0], massCtr[1], refBoidIndex));
            //System.out.println(String.format("Répulsion calculée à (%f, %f)", repulsionAcc[0], repulsionAcc[1]));

            // Différence du centre de masse par rapport à la position actuelle
            cohesionAcc[0] = massCtr[0] - getPosition(refBoidIndex)[0];
            cohesionAcc[1] = massCtr[1] - getPosition(refBoidIndex)[1];

            // Application des coefficients
            double[] acc = {cohesionAcc[0]*cohesionConst + alignAcc[0]*alignConst + repulsionAcc[0]*repulsionConst, cohesionAcc[1]*cohesionConst + alignAcc[1]*alignConst + repulsionAcc[1]*repulsionConst};

            return acc;
        }
        else{
            return new double[]{0, 0};
        }
    }


    /**
     * Calcule l'orientation du boid d'index index
     * @param index Index du boid
     * @return Tuple double (cos(theta), sin(theta)) où theta l'orientation
     */
    public double[] getOrientation(int index){
        double hyp = Math.sqrt(Math.pow(getVelocity(index)[0], 2) + Math.pow(getVelocity(index)[1], 2));
        return new double[]{getVelocity(index)[0]/hyp, getVelocity(index)[1]/hyp};
    }


    /**
     * Renvoie un booléen selon la distance entre deux boids
     * @param index1
     * @param index2
     * @param range (double) distance à vérifier
     * @return Booléen d'appartenance à la portée
     */
    public boolean inRange(int index1, int index2, double range){
        return Utils.getDistSq(getPosition(index1), getPosition(index2)) < range;
    }


    /**
     * Vérifie qu'un boid proie est dans le champ de vision d'un boid chasseur
     * @param index_hunter
     * @param index_prey
     * @param fov angle en radian du champ de vision du chasseur
     * @return Booléen d'appartenance au champ de vision
     */
    public boolean inFOV(int index_hunter, int index_prey, double fov){
        double[] ph_or = getOrientation(index_hunter);
        double[] ph_ctr = getPosition(index_hunter);
        double[] pp_ctr = getPosition(index_prey);

        return Math.abs(Math.atan2(pp_ctr[1] - ph_ctr[1], pp_ctr[0] - ph_ctr[0]) - Math.atan2(ph_or[1] - ph_ctr[1], ph_or[0] - ph_ctr[0])) < fov;
    }

    /**
     * Applique l'ensemble des fonctions physique d'évovlution des boids
     */
    public void animateBoids(){
        System.out.println(toString());

        for(int i=0; i < internSize(); i++){
            double[] stress = computeFlockStress(i);

            setFutureStatus(i, stress);

            // Gestion hors bord
            // Version rebond
            /*
            if(getPosition(i)[0] < 0 || getPosition(i)[0] > getSpaceSize()[0]){
                invertOrientation(i, 1);
                setPosition(i, getSpaceSize()[0] * ((getPosition(i)[0] < 0) ? 0 : 1), getPosition(i)[1]);
            }

            if(getPosition(i)[1] < 0 || getPosition(i)[1] > getSpaceSize()[1]){
                invertOrientation(i, 0);
                setPosition(i, getPosition(i)[0], getSpaceSize()[1] * ((getPosition(i)[1] < 0) ? 0 : 1));
            }
            */

            // Version espace infini
            if(getPosition(i)[0] < 0 || getPosition(i)[0] > getSpaceSize()[0]){
                setPosition(i, getSpaceSize()[0] * ((getPosition(i)[0] < 0) ? 1 : 0), getPosition(i)[1]);
            }

            if(getPosition(i)[1] < 0 || getPosition(i)[1] > getSpaceSize()[1]){
                setPosition(i, getPosition(i)[0], getSpaceSize()[1] * ((getPosition(i)[1] < 0) ? 1 : 0));
            }

            // Version repousse par les murs ?
        }
        updateStatus();
    }

     /**
     * Réecriture de la fonction getVelocity définie dans Balls
     * pour prendre en compte les groupes de boids extérieurs.
     * Cela permet d'éviter d'avoir à réecrire la fonction computeFlockStress.
     */
    @Override
    public double[] getVelocity(int index){
        // Si l'on fait référence a un groupe de boids externe
        int relativeIndex = index;
        if(index >= internSize()){
            relativeIndex -= internSize();

            // Détermine l'index relatif au groupe externe de boids
            int externBoidsGroupCpt = 0; // Compteur de groupes
            while(relativeIndex >= otherBoids[externBoidsGroupCpt].size()){
                relativeIndex -= otherBoids[externBoidsGroupCpt].size();
                externBoidsGroupCpt ++;
            }

            return otherBoids[externBoidsGroupCpt].getVelocity(relativeIndex);
        }
        else{
            return super.getVelocity(index);
        }
    }

    /**
     * Réecriture de la fonction getPosition définie dans Balls
     * pour prendre en compte les groupes de boids extérieurs.
     * Cela permet d'éviter d'avoir à réecrire la fonction computeFlockStress.
     */
    @Override
    public double[] getPosition(int index){
        // Si l'on fait référence a un groupe de boids externe
        int relativeIndex = index;
        if(index >= internSize()){
            relativeIndex -= internSize();

            // Détermine l'index relatif au groupe externe de boids
            int externBoidsGroupCpt = 0; // Compteur de groupes
            while(relativeIndex >= otherBoids[externBoidsGroupCpt].size()){
                relativeIndex -= otherBoids[externBoidsGroupCpt].size();
                externBoidsGroupCpt ++;
            }

            return otherBoids[externBoidsGroupCpt].getPosition(relativeIndex);
        }
        else{
            return super.getPosition(index);
        }
    }
}
