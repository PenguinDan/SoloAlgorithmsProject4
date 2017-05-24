/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotels;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Daniel
 */
public class Hotels {

    static final int INFINITY = Integer.MAX_VALUE;
    static Vertex FINAL_VERTEX = null;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        //Initialize Everything
        ArrayList<Vertex> sink = new ArrayList<Vertex>();
        ArrayList<Vertex> source = new ArrayList<Vertex>();
        HashMap<String, Integer> residualGraph = new HashMap<String, Integer>();
        try {
            Scanner input = new Scanner(new File("information2.txt"));
            String listOfAmenities = input.nextLine();
            String[] splitAmenities = listOfAmenities.split(" ");
            int currIndex = 0;
            for (int i = 0; i < splitAmenities.length; i++) {
                Vertex vertex = new Vertex(Integer.parseInt(splitAmenities[i]), true, currIndex++);
                sink.add(vertex);
            }

            String listOfInvestors = input.nextLine();
            String[] splitInvestors = listOfInvestors.split(" ");
            for (int i = 0; i < splitInvestors.length; i++) {
                Vertex vertex = new Vertex(Integer.parseInt(splitInvestors[i]), false, currIndex++);
                source.add(vertex);
            }

            Vertex currInvestor = null;
            int investorIndex = 0;
            while (input.hasNextLine()) {
                currInvestor = source.get(investorIndex++);
                String listOfRequirements = input.nextLine();
                String[] splitRequirements = listOfRequirements.split(" ");
                for (int i = 0; i < splitRequirements.length; i++) {
                    String requirement = splitRequirements[i];
                    Vertex sinkVertex = sink.get(Integer.parseInt(requirement));
                    currInvestor.addVertex(sinkVertex);
                    sinkVertex.addVertex(currInvestor);
                    residualGraph.put(currInvestor.getID() + "," + sinkVertex.getID(), INFINITY);
                    residualGraph.put(sinkVertex.getID() + "," + currInvestor.getID(), 0);
                }
            }
        }

        catch (FileNotFoundException ex) {
            Logger.getLogger(Hotels.class.getName()).log(Level.SEVERE, null, ex);
        }

        //---------------------------------------------------------------------------------------------------
        Vertex currVertex = null;
        LinkedList<Vertex> queue = new LinkedList<Vertex>();
        String connectedToString = "";
        int sourceID = 0;
        int sinkID = 0;
        Vertex[] path = null;
        Vertex prevVertex = null;
        Vertex sinkVertex = null;
        int sourceValue = 0, sinkValue = 0, flowValue = 0, minFlowValue = 0;

        for (int i = 0; i < source.size(); i++) {
            currVertex = source.get(i);
            sourceID = currVertex.getID();

            while (true) {
                path = findAvailableVertex(currVertex, residualGraph, source.size() + sink.size());
                if (path != null) {
                    sinkID = FINAL_VERTEX.getID();
                    sinkVertex = FINAL_VERTEX;
                    sourceValue = currVertex.getEdgeValue();
                    sinkValue = FINAL_VERTEX.getEdgeValue();
                    minFlowValue = (sourceValue < sinkValue) ? sourceValue : sinkValue;

                    for (int j = sinkID; j != sourceID; j = prevVertex.getID()) {
                        prevVertex = path[j];
                        connectedToString = prevVertex.getID() + "," + j;
                        flowValue = residualGraph.get(connectedToString);
                        if (flowValue < minFlowValue) {
                            minFlowValue = flowValue;
                        }
                    }

                    currVertex.updateEdgeValue(-minFlowValue);
                    sinkVertex.updateEdgeValue(-minFlowValue);

                    for (int j = sinkID; j != sourceID; j = prevVertex.getID()) {
                        prevVertex = path[j];
                        connectedToString = j + "," + prevVertex.getID();
                        flowValue = residualGraph.get(connectedToString);
                        if (flowValue != INFINITY) {
                            residualGraph.replace(connectedToString, flowValue + minFlowValue);
                        }
                        else {
                            connectedToString = prevVertex.getID() + "," + j;
                            flowValue = residualGraph.get(connectedToString);
                            residualGraph.replace(connectedToString, flowValue - minFlowValue);
                        }
                    }
                }
                else {
                    break;
                }
                if (currVertex.getEdgeValue() == 0) {
                    break;
                }
            }
        }

        try {
            //-----------------------------------------------------------------------------------------------------------
            PrintWriter output = new PrintWriter(new File("investors.txt"));
            boolean[] visitedList = new boolean[source.size() + sink.size()];
            ArrayList<Vertex> goodSource = new ArrayList<Vertex>();

            int number = source.get(0).getID();
            for (int i = 0; i < source.size(); i++) {
                currVertex = source.get(i);
                if (currVertex.getEdgeValue() != 0) {
                    goodSource.add(currVertex);
                }
            }

            for (int i = 0; i < goodSource.size(); i++) {
                currVertex = goodSource.get(i);
                sourceID = currVertex.getID();

                if (!visitedList[sourceID]) {
                    DFS(residualGraph, visitedList, currVertex);
                }
            }
            for(int i = 0; i < source.size(); i++) {
                if(visitedList[source.get(i).getID()]) {
                    output.println(source.get(i).getID() - number);
                }
            }

            output.close();
        }
        catch (FileNotFoundException ex) {
            Logger.getLogger(Hotels.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static Vertex[] findAvailableVertex(Vertex sourceVertex, HashMap<String, Integer> residualGraph, int arraySize) {
        LinkedList<Vertex> linkedList = new LinkedList<Vertex>();
        linkedList.add(sourceVertex);
        int vertexID = sourceVertex.getID();
        int connectedID = 0;
        Vertex currVertex = null;
        Vertex connectedToVertex = null;
        boolean[] visited = new boolean[arraySize];
        Vertex[] path = new Vertex[arraySize];
        int edgeFlowValue = 0;

        while (!linkedList.isEmpty()) {
            currVertex = linkedList.poll();
            vertexID = currVertex.getID();
            visited[vertexID] = true;

            for (int i = 0; i < currVertex.getConnectedToSize(); i++) {
                connectedToVertex = currVertex.getConnectedToVertex(i);
                connectedID = connectedToVertex.getID();
                edgeFlowValue = residualGraph.get(vertexID + "," + connectedID);
                if (visited[connectedID] || edgeFlowValue == 0) {
                    continue;
                }

                path[connectedID] = currVertex;

                if (connectedToVertex.isAmenity() && connectedToVertex.getEdgeValue() > 0) {
                    FINAL_VERTEX = connectedToVertex;
                    return path;
                }
                else {
                    linkedList.add(connectedToVertex);
                }
            }
        }
        return null;
    }

    public static void DFS(HashMap<String, Integer> residualGraph, boolean[] visited, Vertex reachedVertex) {
        Vertex connectedVertex = null;
        int reachedID = reachedVertex.getID();
        visited[reachedID] = true;
        String connectedToString = "";
        int flowValue = 0;

        for (int i = 0; i < reachedVertex.getConnectedToSize(); i++) {
            connectedVertex = reachedVertex.getConnectedToVertex(i);
            connectedToString = reachedVertex.getID() + "," + connectedVertex.getID();
            flowValue = residualGraph.get(connectedToString);
            if (flowValue > 0) {
                reachedID = connectedVertex.getID();
                if (!visited[reachedID]) {
                    DFS(residualGraph, visited, connectedVertex);
                }
            }
        }
    }

}
