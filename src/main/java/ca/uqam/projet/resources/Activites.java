package ca.uqam.projet.resources;

import com.fasterxml.jackson.annotation.*;

public class Activites {
  private int id;
  private String nom;
  private String description;
  private String arrondissement;
  //private String dates;
  //private String lieuNom;
  //private int lag;
  //private int lng;

  public Activites(int id, String nom, String description, String arrondissement/*, String dates, String lieuNom, int lag, int lng*/) {
    this.id = id;
    this.nom = nom;
    this.description = description;
    this.arrondissement = arrondissement;
    //this.dates = dates;
    //this.lieuNom = lieuNom;
    //this.lag = lag;
    //this.lng = lng;
  }

  @JsonProperty public int getId() {
    return id;
   }

  @JsonProperty public String getNom() {
    return nom;
   }

   @JsonProperty public String getDescription() {
     return description;
    }

  @JsonProperty public String getArrondissement() {
    return arrondissement;
   }
/*
  @JsonProperty public String getDates() {
     return dates;
    }

 @JsonProperty public String getLieuNom() {
      return lieuNom;
     }

 @JsonProperty public int getLag() {
          return lag;
         }

 @JsonProperty public int getLng() {
              return lng;
             }
*/
  @Override public String toString() {
    return String.format("«%s» --%s", nom, description);
  }
}
