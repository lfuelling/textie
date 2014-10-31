package de.micromata.azubi.model;

/**
 * @author Julian Siebert (j.siebert@micromata.de)
 * @author Lukas F&uuml;lling (l.fuelling@micromata.de)
 */
public enum Richtung {
    NORD, SUED, WEST, OST, FALLTUER;

    public static Richtung getByText(String text) {
        if ("süd".equals(text.toLowerCase()) || "sued".equals(text.toLowerCase())) {
            return SUED;
        } else if (text.toLowerCase().equals("falltür") || text.toLowerCase().equals("falltuer")) {
            return FALLTUER;
        } else if (text.toLowerCase().equals("nord")) {
            return NORD;
        } else if (text.toLowerCase().equals("west")) {
            return WEST;
        } else if (text.toLowerCase().equals("ost")) {
            return OST;
        } else {
            return null;
        }
    }

    /**
     *
     * @param richtung Richtung deren Gegenteil man haben will.
     * @return Gegenteilige Richtung
     *
     */
    public static Richtung getOpposite(Richtung richtung){
        Richtung opposite;
        switch(richtung){
            case NORD:
                opposite = SUED;
                break;
            case SUED:
                opposite = NORD;
                break;
            case WEST:
                opposite = OST;
                break;
            case OST:
                opposite = WEST;
                break;
            default:
                opposite = null;
        }
        return opposite;
    }
}

