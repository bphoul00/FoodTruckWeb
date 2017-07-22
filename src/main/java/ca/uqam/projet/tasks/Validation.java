package ca.uqam.projet.tasks;

import java.util.regex.Pattern;

public class Validation {

    public boolean checkDate(String date) {
        Pattern pattern = Pattern.compile("^\\d{4}\\-(0?[1-9]|1[012])\\-(0?[1-9]|[12][0-9]|3[01])$");
        if (pattern.matcher(date).matches()) {
            return true;
        }
        return false;
    }

    public boolean checkRayon(double rayon) {
        if (rayon >= 0) {
            return true;
        }
        return false;
    }

    public boolean checkLatitude(double lat) {
        return lat >= -85 && lat <= 85;
    }

    public boolean checkLongitude(double lng) {
        return lng >= -180 && lng <= 180;
    }

    public boolean findByDistanceLocationAndTimeValidation(String duString, String auString, double lng, double lat, double rayon) {
        return (checkDate(duString) && checkDate(auString) && checkLongitude(lng) && checkLatitude(lat) && checkRayon(rayon));

    }

    public boolean findByTimeValidation(String duString, String auString) {
        return (checkDate(duString) && checkDate(auString));
    }

    public boolean findByTimeValidation(double lng, double lat, double rayon) {
        return (checkLongitude(lng) && checkLatitude(lat) && checkRayon(rayon));
    }

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

    public boolean findByDistanceLocationAndNumberBixiValidation(double lat, double lng, double rayon, int numberBixiAvailable) {
        return (checkLongitude(lng) && checkLatitude(lat) && checkRayon(rayon) && numberBixiAvailable >= 0);
    }

}
