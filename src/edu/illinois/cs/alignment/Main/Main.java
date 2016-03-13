package edu.illinois.cs.alignment.Main;

import java.text.NumberFormat;
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

/*
        // GUI flow 1
        InputField strInput1 = new InputField(null, Constants.INPUT_TYPE.STRING, false, Constants.VISIBILITY.VISIBLE);
        InputField intInput1 = new InputField(null, Constants.INPUT_TYPE.INTEGER, false, Constants.VISIBILITY.VISIBLE);
        InputField strInput2 = new InputField(null, Constants.INPUT_TYPE.STRING, false, Constants.VISIBILITY.VISIBLE);
        Activity flow1Screen2 = new Activity("f1s2",Arrays.asList(strInput1, intInput1, strInput2));

        InputField emailInput = new InputField(null, Constants.INPUT_TYPE.EMAIL, true, Constants.VISIBILITY.VISIBLE);
        InputField passwordInput = new InputField(null, Constants.INPUT_TYPE.PASSWORD, true,
                                                  Constants.VISIBILITY.VISIBLE);
        Activity flow1Screen1 = new Activity("f1s1", Arrays.asList(emailInput, passwordInput));

        graph.addNode(flow1Screen1);
        graph.addNode(flow1Screen2);
        graph.addEdge(flow1Screen1, flow1Screen2, new LogElementsWithWeight());

        // GUI flow 2
        InputField strInput3 = new InputField(null, Constants.INPUT_TYPE.STRING, false, Constants.VISIBILITY.VISIBLE);
        Activity flow2Screen2 = new Activity("f2s2", Arrays.asList(strInput3));

        InputField strInput4 = new InputField(null, Constants.INPUT_TYPE.STRING, false, Constants.VISIBILITY.VISIBLE);
        InputField intInput2 = new InputField(null, Constants.INPUT_TYPE.INTEGER, false, Constants.VISIBILITY.VISIBLE);
        Activity flow2Screen1 = new Activity("f2s1", Arrays.asList(strInput4, intInput2));

        graph.addNode(flow2Screen1);
        graph.addNode(flow2Screen2);
        graph.addEdge(flow2Screen1, flow2Screen2, new LogElementsWithWeight());

        Activity startingActivity = new Activity("starting", null);
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
*/


        //Home activity
        Activity startingAct = new Activity("startingAct", null);
        //ExpenseNewTransaction
        InputField amount1 = new InputField(null, Constants.INPUT_TYPE.INTEGER, true, Constants.VISIBILITY.INVISIBLE);
        InputField payer1 = new InputField(null, Constants.INPUT_TYPE.STRING, false, Constants.VISIBILITY.INVISIBLE);
        InputField ref1 = new InputField(null, Constants.INPUT_TYPE.STRING, false, Constants.VISIBILITY.INVISIBLE);
        InputField des1 = new InputField(null, Constants.INPUT_TYPE.STRING, false, Constants.VISIBILITY.INVISIBLE);
        Activity ExpenseNewTransactionAct = new Activity("ExpenseNewTransactionAct", Arrays.asList(amount1, payer1,
                                                                                                   ref1, des1));
        //RecurringList
        Activity recurringList = new Activity("recurringList", new ArrayList<InputField>());
        //ExpenseRepeatingTransaction
        InputField des2 = new InputField(null, Constants.INPUT_TYPE.STRING, true, Constants.VISIBILITY.INVISIBLE);
        InputField amount2 = new InputField(null, Constants.INPUT_TYPE.INTEGER, true, Constants.VISIBILITY.INVISIBLE);
        InputField tax2 = new InputField(null, Constants.INPUT_TYPE.STRING, false, Constants.VISIBILITY.INVISIBLE);
        InputField ref2 = new InputField(null, Constants.INPUT_TYPE.INTEGER, true, Constants.VISIBILITY.INVISIBLE);
        Activity ExpenseRepeatingTransactionAct = new Activity("ExpenseRepeatingTransactionAct", Arrays.asList(des2,
                                                                                                               amount2,
                                                                                                               tax2,
                                                                                                               ref2));
        //accountList
        Activity accountListAct = new Activity("accountListAct", new ArrayList<InputField>());
        //ExpenseNewAccount
        InputField name3 = new InputField(null, Constants.INPUT_TYPE.STRING, false, Constants.VISIBILITY.INVISIBLE);
        InputField des3 = new InputField(null, Constants.INPUT_TYPE.STRING, false, Constants.VISIBILITY.INVISIBLE);
        InputField bal3 = new InputField(null, Constants.INPUT_TYPE.INTEGER, false, Constants.VISIBILITY.INVISIBLE);
        Activity ExpenseNewAccountAct = new Activity("ExpenseNewAccountAct", Arrays.asList(name3, des3, bal3));


        graph.addNode(startingAct);
        graph.addNode(ExpenseNewTransactionAct);
        graph.addNode(accountListAct);
        graph.addNode(recurringList);
        graph.addNode(ExpenseNewAccountAct);
        graph.addNode(ExpenseRepeatingTransactionAct);
        graph.addEdge(startingAct, ExpenseNewTransactionAct, new LogElementsWithWeight());
        graph.addEdge(startingAct, accountListAct, new LogElementsWithWeight());
        graph.addEdge(startingAct, recurringList, new LogElementsWithWeight());
        graph.addEdge(accountListAct, ExpenseNewAccountAct, new LogElementsWithWeight());
        graph.addEdge(recurringList, ExpenseRepeatingTransactionAct, new LogElementsWithWeight());

        List<LogElement> elements = new ArrayList<>();
        LogElement element1 = new LogElement("100", Constants.INPUT_TYPE.INTEGER);
        LogElement element2 = new LogElement("davis li", Constants.INPUT_TYPE.STRING);
        LogElement element3 = new LogElement("PNC", Constants.INPUT_TYPE.STRING);
        LogElement element4 = new LogElement("primary bank", Constants.INPUT_TYPE.STRING);
        LogElement element5 = new LogElement("1000", Constants.INPUT_TYPE.INTEGER);
        LogElement element6 = new LogElement("apartment rate", Constants.INPUT_TYPE.STRING);
        LogElement element7 = new LogElement("500", Constants.INPUT_TYPE.INTEGER);
        LogElement element8 = new LogElement("10", Constants.INPUT_TYPE.INTEGER);
        elements.add(element1);
        elements.add(element2);
        elements.add(element3);
        elements.add(element4);
        elements.add(element5);
        elements.add(element6);
        elements.add(element7);
        elements.add(element8);

        alignUserSessionToStartingActivity(elements, startingAct, graph);
    }

    public static void alignUserSessionToStartingActivity(List<LogElement> elements, Activity startingActivity,
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
                List<LogElement> remainingElements = minPath.get(minPath.size() - 1).getEdgeLabel()
                                                             .getRemainingElements();
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
        NumberFormat formatter = NumberFormat.getPercentInstance();
        formatter.setMaximumFractionDigits(1);
        double maxWeight = 0.0;
        for (LogElement element : elements) {
            maxWeight += inputTypeToWeight(element);
        }
        System.out.println("Log elements: ");
        System.out.println(elements + "\n");
        for (Path path : completePaths) {
            System.out.println("Total/Maximum weight: " + path.getTotalWeight() + " / " + maxWeight + " (" +
                               formatter.format((path.getTotalWeight()) / maxWeight) + ")");
            System.out.println("Path: ");
            for (int i = 0; i < path.size(); i++) {
                System.out.println(path.get(i));
                System.out.println("----");
            }
            System.out.println();
        }
    }

    private static double inputTypeToWeight(LogElement element) {
        Constants.INPUT_TYPE type = element.getInput_type();
        if (inputTypeToVal.get(type) == null && !(inputTypeToVal.get(type) instanceof Double)) {
            throw new RuntimeException("Missing input type weight in inputTypeToVal.");
        } else {
            return (Double) inputTypeToVal.get(type);
        }
    }

    private static void setAllEdgeValue(List<LogElement> elements, Activity startingActivity, Graph graph) {
        Map<Activity, List<LogElementsWithWeight>> outgoingNodeData = graph.getOutgoingNodeDatas(startingActivity);
        for (Activity activity : outgoingNodeData.keySet()) {
            LogElementsWithWeight elementsWithWeight = outgoingNodeData.get(activity).get(0);
            getEdgeValue(elements, activity.getFields(), elementsWithWeight);

            List<LogElement> parsedElements;
            if (elementsWithWeight.getAcceptedElements().isEmpty()) {
                parsedElements = elements;
            } else {
                List<LogElement> lastAcceptedElements = elementsWithWeight.getAcceptedElements();
                LogElement lastAcceptedElement = lastAcceptedElements.get(lastAcceptedElements.size() - 1);
                parsedElements = elements.subList(0, elements.indexOf(lastAcceptedElement) + 1);
            }
            double maxWeightForEdge = 0.0;
            for (LogElement element : parsedElements) {
                maxWeightForEdge += inputTypeToWeight(element);
            }
            elementsWithWeight.setMaxWeight(maxWeightForEdge);
        }
    }

    private static void getEdgeValue(List<LogElement> remainingElements, List<InputField> inputs,
                                     LogElementsWithWeight elementsWithWeight) {
        // TODO add weight for skipping elements
        if (remainingElements.size() > 0) {
            double matching = 0.0;
            List<LogElement> acceptedElements = new ArrayList<>();
            for (int i = 0; i < inputs.size() && i < remainingElements.size(); i++) {
                Constants.INPUT_TYPE type = inputs.get(i).getInput_type();
                if (type == remainingElements.get(i).getInput_type()) {
                    if (inputTypeToVal.get(type) == null && !(inputTypeToVal.get(type) instanceof Double)) {
                        throw new RuntimeException("Missing input type weight in inputTypeToVal.");
                    } else {
                        acceptedElements.add(remainingElements.get(i));
                        matching += (Double) inputTypeToVal.get(type);
                    }
                }
            }

            double weight = elementsWithWeight.getWeight();
            if (matching >= weight) {
                elementsWithWeight.setWeight(matching);
                int startIndex;
                if (inputs.size() >= remainingElements.size()) {
                    startIndex = 0;
                } else {
                    startIndex = inputs.size();
                }
                elementsWithWeight.setAcceptedElements(acceptedElements);
                elementsWithWeight.setRemainingElements(new ArrayList<>(remainingElements.subList(startIndex,
                                                                                                  remainingElements
                                                                                                          .size())));
                if (matching > weight) {
                    getEdgeValue(remainingElements.subList(1, remainingElements.size()), inputs, elementsWithWeight);
                }
            }
        }
    }
}
