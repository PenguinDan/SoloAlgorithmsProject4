/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotels;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Daniel
 */
public class Vertex implements Comparable<Vertex> {
    private boolean isAmenity;
    private final int value;
    private int edgeValue;
    private int vertexID;
    private boolean markedAsYes;
    private ArrayList<Integer> amenityListCode;
    private String amenityCode;
    private ArrayList<Vertex> connectedTo;
    
    public Vertex(int value, boolean isAmenity,  int itemID){
        this.value = value;
        edgeValue = value;
        this.isAmenity = isAmenity;
        vertexID = itemID;
        connectedTo = new ArrayList<Vertex>();
        amenityListCode = new ArrayList<Integer>();
        amenityCode = "";
    }
    
    public void addVertex(Vertex vertex){
        connectedTo.add(vertex);
        if(!isAmenity) {
            amenityListCode.add(vertex.getID());
        }
    }
    
    public void updateEdgeValue(int flowAmount) {
        edgeValue += flowAmount;
        //Debug purposes
        int currAmount = edgeValue;
    }
    
    
    public int getID() {
        return vertexID;
    }
    
    public void sortAmenity() {
        Collections.sort(amenityListCode);
        amenityCode = "";
        for(int i = 0; i < amenityListCode.size(); i++) {
            amenityCode = amenityCode + amenityListCode.get(i);
        }
    }
    
    public int getCapacity() {
        return value;
    }
    
    public int getEdgeValue() {
        return edgeValue;
    }
    
    public String getAmenityCode() {
        return amenityCode;
    }
    
    public int getConnectedToSize() {
        return connectedTo.size();
    }
    
    public Vertex getConnectedToVertex(int index) {
        return connectedTo.get(index);
    }
    
    public boolean isAmenity(){
        return isAmenity;
    }
    
    public void markAsYes() {
        markedAsYes = true;
    }
    
    public boolean isMarkedYes() {
        return markedAsYes;
    }
    
    @Override
    public String toString(){
        if(isAmenity){
            return "AmenityID = " + vertexID;
        } else {
            return "InvestorID = " + vertexID;
        }
    }

    @Override
    public int compareTo(Vertex o) {
        return Integer.compare(this.vertexID, o.vertexID);
    }
    
}
