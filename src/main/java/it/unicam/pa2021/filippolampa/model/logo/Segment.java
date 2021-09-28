package it.unicam.pa2021.filippolampa.model.logo;

public abstract class Segment{

    private final Location startingPoint;

    private final Location destinationPoint;

    public Segment(Location startingPoint, Location destinationPoint){
        this.startingPoint = startingPoint;
        this.destinationPoint = destinationPoint;
    }

    /**
     * Restituisce il punto d'inizio del segmento
     *
     * @return Location di inizio del segmento
     */
    public Location getStartingPoint() {
        return startingPoint;
    }

    /**
     * Restituisce il punto di fine del segmento
     *
     * @return Location di fine del segmento
     */
    public Location getDestinationPoint() {
        return destinationPoint;
    }

    /**
     * Determina se un segmento finisce dove un altro inizia
     * @param s segmento
     * @return true se i segmenti sono concatenati
     *          false altrimenti
     */
    public boolean endsWhereTheOtherStarts(Segment s) {
        return this.destinationPoint.equals(s.getStartingPoint());
    }
}

