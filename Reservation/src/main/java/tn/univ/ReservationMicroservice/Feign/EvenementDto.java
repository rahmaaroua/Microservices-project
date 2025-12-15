package tn.univ.ReservationMicroservice.Feign;

import java.util.Date;

import java.util.Date;

public class EvenementDto {
    private int id;
    private String description;
    private Date dated;
    private Date datef;
    private float cout;

    // getters et setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Date getDated() { return dated; }
    public void setDated(Date dated) { this.dated = dated; }
    public Date getDatef() { return datef; }
    public void setDatef(Date datef) { this.datef = datef; }
    public float getCout() { return cout; }
    public void setCout(float cout) { this.cout = cout; }

    public void setNom(String serviceEvenementIndisponible) {
    }
}
