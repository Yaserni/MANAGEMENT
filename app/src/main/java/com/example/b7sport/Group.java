package com.example.b7sport;

public class Group {

    private String groupname,groupid;
    private int playersnumber;
    private int arenaid;
    private String arenaname;
    private String arenatype;
    private String arenastreet;
    private Double arenahousenumber;
    private String arenaneighbor;
    private String arenaactivity;
    private String arenalighing;
    private String arenasport_type;
    private double arenalon;
    private double arenalat;
    private boolean isprivate;

    public String getSecretcode() {
        return secretcode;
    }

    public void setSecretcode(String secretcode) {
        this.secretcode = secretcode;
    }

    public String secretcode;

    public Group(String groupname, String groupid, int playersnumber, int arenaid, String arenaname, String arenatype, String arenastreet, Double arenahousenumber, String arenaneighbor, String arenaactivity, String arenalighing, String arenasport_type, double arenalon, double arenalat, boolean isprivate) {
        this.groupname = groupname;
        this.groupid = groupid;
        this.playersnumber = playersnumber;
        this.arenaid = arenaid;
        this.arenaname = arenaname;
        this.arenatype = arenatype;
        this.arenastreet = arenastreet;
        this.arenahousenumber = arenahousenumber;
        this.arenaneighbor = arenaneighbor;
        this.arenaactivity = arenaactivity;
        this.arenalighing = arenalighing;
        this.arenasport_type = arenasport_type;
        this.arenalon = arenalon;
        this.arenalat = arenalat;
        this.isprivate = isprivate;
    }

    static Group makeGroup(String groupname, String groupid, int playersnumber , boolean isprivate , Arena arena)
    {
        return new Group(groupname,groupid,playersnumber,arena.getId(),arena.getName(),arena.getType(),arena.getStreet(),arena.getHousenumber(),arena.getNeighbor(),arena.getActivity(),arena.getLighing(),arena.getSport_type(),arena.getLon(),arena.getLat(),isprivate);
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }

    public int getPlayersnumber() {
        return playersnumber;
    }

    public void setPlayersnumber(int playersnumber) {
        this.playersnumber = playersnumber;
    }

    public int getArenaid() {
        return arenaid;
    }

    public void setArenaid(int arenaid) {
        this.arenaid = arenaid;
    }

    public String getArenaname() {
        return arenaname;
    }

    public void setArenaname(String arenaname) {
        this.arenaname = arenaname;
    }

    public String getArenatype() {
        return arenatype;
    }

    public void setArenatype(String arenatype) {
        this.arenatype = arenatype;
    }

    public String getArenastreet() {
        return arenastreet;
    }

    public void setArenastreet(String arenastreet) {
        this.arenastreet = arenastreet;
    }

    public Double getArenahousenumber() {
        return arenahousenumber;
    }

    public void setArenahousenumber(Double arenahousenumber) {
        this.arenahousenumber = arenahousenumber;
    }

    public String getArenaneighbor() {
        return arenaneighbor;
    }

    public void setArenaneighbor(String arenaneighbor) {
        this.arenaneighbor = arenaneighbor;
    }

    public String getArenaactivity() {
        return arenaactivity;
    }

    public void setArenaactivity(String arenaactivity) {
        this.arenaactivity = arenaactivity;
    }

    public String getArenalighing() {
        return arenalighing;
    }

    public void setArenalighing(String arenalighing) {
        this.arenalighing = arenalighing;
    }

    public String getArenasport_type() {
        return arenasport_type;
    }

    public void setArenasport_type(String arenasport_type) {
        this.arenasport_type = arenasport_type;
    }

    public double getArenalon() {
        return arenalon;
    }

    public void setArenalon(double arenalon) {
        this.arenalon = arenalon;
    }

    public double getArenalat() {
        return arenalat;
    }

    public void setArenalat(double arenalat) {
        this.arenalat = arenalat;
    }

    public boolean isIsprivate() {
        return isprivate;
    }

    public void setIsprivate(boolean isprivate) {
        this.isprivate = isprivate;
    }
}
