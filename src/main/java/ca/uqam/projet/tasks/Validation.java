package ca.uqam.projet.tasks;

import java.util.regex.Pattern;

public class Validation {

    /**
     * Verifier la composition du date.
     * @param date
     * @return boolean
     */
    public boolean checkDate(String date) {
        Pattern pattern = Pattern.compile("^\\d{4}\\-(0?[1-9]|1[012])\\-(0?[1-9]|[12][0-9]|3[01])$");
        if (pattern.matcher(date).matches()) {
            return true;
        }
        return false;
    }

    /**
     * Valider Rayon
     * @param rayon
     * @return boolean
     */
    public boolean checkRayon(double rayon) {
        if (rayon >= 0) {
            return true;
        }
        return false;
    }

    /**
     * Valider Latitude
     * @param lat
     * @return boolean
     */
    public boolean checkLatitude(double lat) {
        return lat >= -85 && lat <= 85;
    }

    /**
     * Valider Longitude
     * @param lng
     * @return boolean
     */
    public boolean checkLongitude(double lng) {
        return lng >= -180 && lng <= 180;
    }

    /**
     * Valider les parametre de findByDistanceLocationAndTimeValidation
     * @param duString
     * @param auString
     * @param lng
     * @param lat
     * @param rayon
     * @return boolean
     */
    public boolean findByDistanceLocationAndTimeValidation(String duString, String auString, double lng, double lat, double rayon) {
        return (checkDate(duString) && checkDate(auString) && checkLongitude(lng) && checkLatitude(lat) && checkRayon(rayon));

    }

    /**
     * Valider les parametre de findByTimeValidation
     * @param duString
     * @param auString
     * @return boolean
     */
    public boolean findByTimeValidation(String duString, String auString) {
        return (checkDate(duString) && checkDate(auString));
    }

    /**
     * Valider les parametre de findByTimeValidation
     * @param lng
     * @param lat
     * @param rayon
     * @return boolean
     */
    public boolean findByTimeValidation(double lng, double lat, double rayon) {
        return (checkLongitude(lng) && checkLatitude(lat) && checkRayon(rayon));
    }

    /**
     * Valider les parametre de EventValidation
     * @param lng
     * @param lat
     * @param dates
     * @return boolean
     */
    public boolean EventValidation(double lng, double lat, String[] dates) {
        if (checkLongitude(lng) && checkLatitude(lat)) {
            for (String date : dates) {
                if (checkDate(date)) {
                    return false;
                }
            }
        } else {
            return false;
        }
        return true;
    }

    /**
     * Valider les parametre de findByDistanceLocationAndNumberBixiValidation
     * @param lat
     * @param lng
     * @param rayon
     * @param numberBixiAvailable
     * @return boolean
     */
    public boolean findByDistanceLocationAndNumberBixiValidation(double lat, double lng, double rayon, int numberBixiAvailable) {
        return (checkLongitude(lng) && checkLatitude(lat) && checkRayon(rayon) && numberBixiAvailable >= 0);
    }

}
