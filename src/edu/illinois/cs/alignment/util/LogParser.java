package edu.illinois.cs.alignment.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * Created by winglam on 11/4/15.
 */
public class LogParser {
    public static void main(String[] args) {
        List<String> argsList = new ArrayList<>(Arrays.asList(args));

        File inputFile = null;
        int inputFileIndex = argsList.indexOf("-inputFile");
        if (inputFileIndex != -1) {
            int inputFileNameIndex = inputFileIndex + 1;
            if (inputFileNameIndex >= argsList.size()) {
                System.err.println("Input file argument is specified but a file" +
                                   " path is not. Please use the format: -inputFile aFilePath");
                System.exit(0);
            }
            inputFile = new File(argsList.get(inputFileNameIndex));
            if (!inputFile.isFile()) {
                System.err.println("Input file is specified but the file" +
                                   " path is invalid. Please check the file path.");
                System.exit(0);
            }
        } else {
            System.err.println("No input file argument is specified." + " Please use the format: -inputFile aFileName");
            System.exit(0);
        }

        File outputFile = null;
        int outputFileIndex = argsList.indexOf("-outputFile");
        if (outputFileIndex != -1) {
            int outputFileNameIndex = outputFileIndex + 1;
            if (outputFileNameIndex >= argsList.size()) {
                System.err.println("Output file argument is specified but a file" +
                                   " path is not. Please use the format: -outputFile aFilePath");
                System.exit(0);
            }
            outputFile = new File(argsList.get(outputFileNameIndex));
        } else {
            System.err.println("No output file argument is specified." +
                               " Please use the format: -outputFile aFileName");
            System.exit(0);
        }

        Map<String, HashSet<String>> appToUserIds = new HashMap<>();
        Map<String, ArrayList<List>> userIdToInputs = new HashMap<>();

        /**
         * Example of expected format of each line in outputFile
         * com.facebook.katana	Ti	7fb89827de09b98861e46fe72ee0c675	20150822113537945	1440214527382;input:0,16,205,shift;input:707,227,42,T;input:1406,398,50,i;	{"size":"{\"orientation\":1,\"keyboardMode\":\"normal\",\"size\":\"333*540\"}"}
         */
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(inputFile));
            String line = br.readLine();

            while (line != null) {
                String[] splitSpace = line.split("\t");
                line = br.readLine();
                if (splitSpace.length != 6) {
                    continue;
                }
                String app = splitSpace[0];
                List<String> input = Arrays.asList(splitSpace[1]);
                String userId = splitSpace[2];

                HashSet<String> userIds;
                if (appToUserIds.containsKey(app)) {
                    userIds = appToUserIds.get(app);
                } else {
                    userIds = new HashSet<>();
                }
                userIds.add(userId);
                appToUserIds.put(app, userIds);

                ArrayList<List> inputs;
                if (userIdToInputs.containsKey(userId)) {
                    inputs = userIdToInputs.get(userId);
                } else {
                    inputs = new ArrayList<>();
                }
                inputs.add(input);
                userIdToInputs.put(userId, inputs);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        FileWriter output = null;
        BufferedWriter writer = null;
        try {
            output = new FileWriter(outputFile);
            writer = new BufferedWriter(output);

            for (String app : appToUserIds.keySet()) {
                // omit results from particularly popular apps
                if (appToUserIds.get(app).size() > 1000) {
                    continue;
                }
                writer.write(app + "\n");

                for (String userId : appToUserIds.get(app)) {
                    writer.write(userId + ": " + userIdToInputs.get(userId) + "\n");
                }
                writer.write("\n");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
                if (output != null) {
                    output.close();
                }
            } catch (IOException e) {
                // Ignore issues during closing
            }
        }
    }
}
