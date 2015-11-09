package edu.illinois.cs.alignment.Main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import edu.illinois.cs.alignment.data.Activity;
import edu.illinois.cs.alignment.data.InputField;
import edu.illinois.cs.alignment.data.LogElement;
import edu.illinois.cs.alignment.data.LogElementsWithWeight;
import edu.illinois.cs.alignment.graph.Graph;
import edu.illinois.cs.alignment.graph.Path;
import edu.illinois.cs.alignment.graph.SinglePath;
import edu.illinois.cs.alignment.util.Constants;

/**
 * Created by winglam on 10/29/15.
 */
public class Main {

    private static HashMap inputTypeToVal = new HashMap<Constants.INPUT_TYPE, Double>() {{
        put(Constants.INPUT_TYPE.STRING, 1.0);
        put(Constants.INPUT_TYPE.INTEGER, 2.0);
        put(Constants.INPUT_TYPE.PASSWORD, 3.0);
        put(Constants.INPUT_TYPE.EMAIL, 4.0);
    }};

    public static void main(String[] args) {
        Graph<Activity, LogElementsWithWeight> graph = new Graph<>();

        // TODO can we assume program analysis from before will tell us which screens come before which?
        // GUI flow 1
        InputField strInput1 = new InputField(null, Constants.INPUT_TYPE.STRING, false, Constants.VISIBILITY.VISIBLE);
        InputField intInput1 = new InputField(null, Constants.INPUT_TYPE.INTEGER, false, Constants.VISIBILITY.VISIBLE);
        InputField strInput2 = new InputField(null, Constants.INPUT_TYPE.STRING, false, Constants.VISIBILITY.VISIBLE);
        Activity flow1Screen2 = new Activity(Arrays.asList(strInput1, intInput1, strInput2));

        InputField emailInput = new InputField(null, Constants.INPUT_TYPE.EMAIL, true, Constants.VISIBILITY.VISIBLE);
        InputField passwordInput = new InputField(null, Constants.INPUT_TYPE.PASSWORD, true, Constants.VISIBILITY.VISIBLE);
        Activity flow1Screen1 = new Activity(Arrays.asList(emailInput, passwordInput));

        graph.addNode(flow1Screen1);
        graph.addNode(flow1Screen2);
        graph.addEdge(flow1Screen1, flow1Screen2, new LogElementsWithWeight());

        // GUI flow 2
        InputField strInput3 = new InputField(null, Constants.INPUT_TYPE.STRING, false, Constants.VISIBILITY.VISIBLE);
        Activity flow2Screen2 = new Activity(Arrays.asList(strInput3));

        InputField strInput4 = new InputField(null, Constants.INPUT_TYPE.STRING, false, Constants.VISIBILITY.VISIBLE);
        InputField intInput2 = new InputField(null, Constants.INPUT_TYPE.INTEGER, false, Constants.VISIBILITY.VISIBLE);
        Activity flow2Screen1 = new Activity(Arrays.asList(strInput4, intInput2));

        graph.addNode(flow2Screen1);
        graph.addNode(flow2Screen2);
        graph.addEdge(flow2Screen1, flow2Screen2, new LogElementsWithWeight());

        Activity startingActivity = new Activity(null);
        graph.addNode(startingActivity);
        graph.addEdge(startingActivity, flow1Screen1, new LogElementsWithWeight());
        graph.addEdge(startingActivity, flow2Screen1, new LogElementsWithWeight());

        // Log elements for a user session
        LogElement element1 = new LogElement("abc@email.com", Constants.INPUT_TYPE.EMAIL);
        LogElement element2 = new LogElement("abc password", Constants.INPUT_TYPE.PASSWORD);
        LogElement element3 = new LogElement("string1", Constants.INPUT_TYPE.STRING);
        LogElement element4 = new LogElement("123", Constants.INPUT_TYPE.INTEGER);
        LogElement element5 = new LogElement("456", Constants.INPUT_TYPE.INTEGER);
        LogElement element6 = new LogElement("string2", Constants.INPUT_TYPE.STRING);
        List<LogElement> elements = Arrays.asList(element1, element2, element3, element4, element5, element6);

        alignUserSessionToStartingActivity(elements, startingActivity, graph);
    }

    public static void alignUserSessionToStartingActivity(List<LogElement> elements,
                                                          Activity startingActivity,
                                                          Graph<Activity, LogElementsWithWeight> graph) {
        PriorityQueue<Path<Activity>> activePaths = new PriorityQueue<>();

        setAllEdgeValue(elements, startingActivity, graph);
        Map<Activity, List<LogElementsWithWeight>> outgoingNodeData = graph.getOutgoingNodeDatas(startingActivity);
        for (Activity activity : outgoingNodeData.keySet()) {
            activePaths.add(new Path<>(new SinglePath<>(startingActivity, activity,
                                                        outgoingNodeData.get(activity).get(0))));
        }

        List<Path> completePaths = new ArrayList<>();
        Activity minNode = startingActivity;
        while (!activePaths.isEmpty()) {
            Path<Activity> minPath = activePaths.remove();
            if (minPath.size() != 0) {
                minNode = minPath.get(minPath.size() - 1).getEndNode();
            }

            Map<Activity, List<LogElementsWithWeight>> nextNodes = graph.getOutgoingNodeDatas(minNode);
            if (nextNodes.size() == 0) {
                minPath.setSortWithWeight(true);
                completePaths.add(minPath);
            } else {
                List<LogElement> remainingElements = minPath.get(minPath.size() - 1).getEdgeLabel().getElements();
                for (Activity node : nextNodes.keySet()) {
                    if (!remainingElements.isEmpty()) {
                        setAllEdgeValue(remainingElements, minNode, graph);
                        SinglePath<Activity> singlePath = new SinglePath<>(minNode, node, nextNodes.get(node).get(0));
                        Path<Activity> newPath = new Path<>(minPath);
                        newPath.add(singlePath);
                        activePaths.add(newPath);
                    }
                }
            }
        }

        Collections.sort(completePaths);
        for (Path path : completePaths) {
            System.out.println("Total Weight: " + path.getTotalWeight());
            System.out.println("Path: ");
            for (int i = 0; i < path.size(); i++) {
                System.out.println(path.get(i));
                System.out.println("----");
            }
            System.out.println();
        }
    }

    private static void setAllEdgeValue(List<LogElement> elements, Activity startingActivity, Graph graph) {
        Map<Activity, List<LogElementsWithWeight>> outgoingNodeData = graph.getOutgoingNodeDatas(startingActivity);
        for (Activity activity : outgoingNodeData.keySet()) {
            LogElementsWithWeight elementsWithWeight = outgoingNodeData.get(activity).get(0);
            getEdgeValue(elements, activity.getFields(), elementsWithWeight);
        }
    }

    private static void getEdgeValue(List<LogElement> remainingElements, List<InputField> inputs,
                                       LogElementsWithWeight maxWeight) {
        // TODO add weight for skipping elements
        if (remainingElements.size() > 0) {
            double matching = 0.0;
            for (int i = 0; i < inputs.size() && i < remainingElements.size(); i++) {
                Constants.INPUT_TYPE type = inputs.get(i).getInput_type();
                if (type == remainingElements.get(i).getInput_type()) {
                    if (inputTypeToVal.get(type) == null) {
                        throw new RuntimeException("Missing input type weight in inputTypeToVal.");
                    } else {
                        matching += (Double) inputTypeToVal.get(type);
                    }
                }
            }
            if (matching > maxWeight.getWeight()) {
                maxWeight.setWeight(matching);
                int startIndex;
                if (inputs.size() >= remainingElements.size()) {
                    startIndex = 0;
                } else {
                    startIndex = inputs.size();
                }
                maxWeight.setElements(new ArrayList<>(remainingElements.subList(startIndex, remainingElements.size())));
            }
            getEdgeValue(remainingElements.subList(1, remainingElements.size()), inputs, maxWeight);
        }
    }
}
