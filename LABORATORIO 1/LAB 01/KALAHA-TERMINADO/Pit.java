
/**
 * A well where to put seeds according to the number that a person foresees.
 * 
 * @authors Anderson Fabian Garcia Nieto y Cristian Alfonso Romero Martinez
 * @version (January 31 2025)
 */
public class Pit
{
    private Rectangle rectangulo;
    private Circle[] semillas;
    private int xPosition;
    private int yPosition;
    private String color;
    private String seedcolor;
    private int seed;
    private boolean isVisible;
    private boolean big;

    /**
     * Create a new well with default values except for its size
     * @param distance the desired distance in pixels
     */
    //Mini Ciclo 1
    public Pit(boolean big)
    {
        if (big != true){
            this.seed = 0;
            this.color = "green";
            this.seedcolor = "yellow";
            this.xPosition = -10;
            this.yPosition = -90;
            this.semillas = new Circle[100];
            this.big = big;
            this.rectangulo = new Rectangle();
            this.rectangulo.changeColor(this.color);
        } else{
            this.seed = 0;
            this.color = "green";
            this.seedcolor = "yellow";
            this.xPosition = -10;
            this.yPosition = -90;
            this.semillas = new Circle[400];
            this.big = big;
            this.rectangulo = new Rectangle();
            this.rectangulo.height = this.rectangulo.height * 2;
            this.rectangulo.width = this.rectangulo.width * 2;
            this.rectangulo.changeColor(this.color);
        }
        
        
    }

    /**
     * Increases the number of seeds for which the user logs in and draws them
     */
    public void putSeeds(int numberSeed)
    {   if (this.big){
        if (numberSeed > 0){
            if ((numberSeed + this.seed) <= 400){
                this.seed += numberSeed;
                drawSeeds();
            }
        }
        
    } else {
        if (numberSeed > 0){
            if ((numberSeed + this.seed) <= 100){
                this.seed += numberSeed;
                drawSeeds();
            }
        }
    }
        
    }
    
    /**
     * Draw the seeds since the number of seeds is greater than 0, also drawing them one after the other.
     */
    private void drawSeeds() {
    if (this.big) {
    for (int i = 0; i < seed; i++) {
        if (semillas[i] == null) { 
            semillas[i] = new Circle();
            semillas[i].changeColor("black");
            int seedx = xPosition + (i % 20) * 10 + 50;
            int seedy = yPosition + (i / 20) * 10 + 50;
            semillas[i].moveHorizontal(seedx);
            semillas[i].moveVertical(seedy);
            if (isVisible) {
                semillas[i].makeVisible();
            }
        }
    }
} else {
    for (int i = 0; i < seed; i++) {
        if (semillas[i] == null) { 
            semillas[i] = new Circle();
            semillas[i].changeColor("black");
            int seedx = xPosition + (i % 10) * 10 + 50;
            int seedy = yPosition + (i / 10) * 10 + 50;
            semillas[i].moveHorizontal(seedx);
            semillas[i].moveVertical(seedy);
            if (isVisible) {
                semillas[i].makeVisible();
            }
        }
    }
}

}
    /**
     * Removes the seeds that the user removes, in turn deleting them.
     */
    public void removeSeeds(int numberSeed)
    {
     if (numberSeed > 0 && this.seed >= numberSeed) {
            this.seed -= numberSeed;
            for (int i = seed; i < seed + numberSeed; i++) {
                if (semillas[i] != null) {
                    semillas[i].makeInvisible();
                    semillas[i] = null;
                }
            }
        } else {
            System.out.println("La cantidad de semillas a remover sobrepasa las existentes");
        }
    }
    
    
    /**
     * Returns the number of seeds in the hole.
     */
    public int seeds()
    {
        return this.seed;
    }
    
    //Mini Ciclo 2
    /**
     * Makes all elements of the canvas invisible
     */
    public void makeVisible() {
        rectangulo.makeVisible();
        isVisible = true;
        for (int i = 0; i < seed; i++) {
            if (semillas[i] != null) {
                semillas[i].makeVisible();
            }
        }
    }
    
    /**
     * Makes all elements of the canvas visible
     */
     public void makeInvisible() {
        rectangulo.makeInvisible();
        isVisible = false;
        for (int i = 0; i < semillas.length; i++) {
            if (semillas[i] != null) {
                semillas[i].makeInvisible();
            }
        }
    }
    
    //Mini Ciclo 3
    /**
     * Changes the colors of the hole and of the seeds
     */
     public void changeColors(String newColorrectangle,String newColorseed) {
        this.rectangulo.changeColor(newColorrectangle);
        for (int i = 0; i < this.seed; i++) {
            this.color = newColorrectangle;
            this.seedcolor = newColorseed;
            semillas[i].changeColor(newColorseed);
        }
    }
    
    /**
     * Changes the location of the hole and the seeds
     */
    public void moveTo(int x ,int y ){
        rectangulo.moveVertical(y);
        rectangulo.moveHorizontal(x);
        this.xPosition = this.xPosition + x;
        this.yPosition = this.yPosition + y;
        makeInvisible();
        for (int i =0; i<this.seed; i++){
            semillas[i].moveVertical(y);
            semillas[i].moveHorizontal(x);
        }
        makeVisible();

    }
    
}