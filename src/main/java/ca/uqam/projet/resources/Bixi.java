package ca.uqam.projet.resources;

import com.fasterxml.jackson.annotation.*;

public class Bixi {

    private int id;
    private String s;
    private int n;
    private int st;
    private boolean b;
    private boolean su;
    private boolean m;
    private long lu;
    private long lc;
    private boolean bk;
    private boolean bl;
    private double la;
    private double lo;
    private int da;
    private int dx;
    private int ba;
    private int bx;

    public Bixi() {
    }

    public Bixi(int id, String nom, int identifiantTerminale, int etat,
            boolean bloquee, boolean suspendue, boolean horService,
            long lu, long lc, boolean bk, boolean bl, double latitude,
            double longitude, int nombreBornesDisponible,
            int nombreBornesIndisponible, int nombreVelosDisponible,
            int nombreVelosIndisponible) {
        this.id = id;
        this.s = nom;
        this.n = identifiantTerminale;
        this.st = etat;
        this.b = bloquee;
        this.su = suspendue;
        this.m = horService;
        this.lu = lu;
        this.lc = lc;
        this.bk = bk;
        this.bl = bl;
        this.la = latitude;
        this.lo = longitude;
        this.da = nombreBornesDisponible;
        this.dx = nombreBornesIndisponible;
        this.ba = nombreVelosDisponible;
        this.bx = nombreVelosIndisponible;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setS(String s) {
        this.s = s;
    }

    public void setN(int n) {
        this.n = n;
    }

    public void setSt(int st) {
        this.st = st;
    }

    public void setB(boolean b) {
        this.b = b;
    }

    public void setSu(boolean su) {
        this.su = su;
    }

    public void setM(boolean m) {
        this.m = m;
    }

    public void setLu(long lu) {
        this.lu = lu;
    }

    public void setLc(long lc) {
        this.lc = lc;
    }

    public void setBk(boolean bk) {
        this.bk = bk;
    }

    public void setBl(boolean bl) {
        this.bl = bl;
    }

    public void setLa(double la) {
        this.la = la;
    }

    public void setLo(double lo) {
        this.lo = lo;
    }

    public void setDa(int da) {
        this.da = da;
    }

    public void setDx(int dx) {
        this.dx = dx;
    }

    public void setBa(int ba) {
        this.ba = ba;
    }

    public void setBx(int bx) {
        this.bx = bx;
    }

    @JsonProperty
    public int getId() {
        return id;
    }

    @JsonProperty
    public String getS() {
        return s;
    }

    @JsonProperty
    public int getN() {
        return n;
    }

    @JsonProperty
    public int getSt() {
        return st;
    }

    @JsonProperty
    public boolean isB() {
        return b;
    }

    @JsonProperty
    public boolean isSu() {
        return su;
    }

    @JsonProperty
    public boolean isM() {
        return m;
    }

    @JsonProperty
    public long getLu() {
        return lu;
    }

    @JsonProperty
    public long getLc() {
        return lc;
    }

    @JsonProperty
    public boolean isBk() {
        return bk;
    }

    @JsonProperty
    public boolean isBl() {
        return bl;
    }

    @JsonProperty
    public double getLa() {
        return la;
    }

    @JsonProperty
    public double getLo() {
        return lo;
    }

    @JsonProperty
    public int getDa() {
        return da;
    }

    @JsonProperty
    public int getDx() {
        return dx;
    }

    @JsonProperty
    public int getBa() {
        return ba;
    }

    @JsonProperty
    public int getBx() {
        return bx;
    }

    @Override
    public String toString() {
        return String.format("«%s» --%d", s, n);
    }
}
