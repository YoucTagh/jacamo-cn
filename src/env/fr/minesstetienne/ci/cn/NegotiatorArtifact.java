package fr.minesstetienne.ci.cn;

import cartago.Artifact;
import cartago.OPERATION;
import cartago.OpFeedbackParam;
import fr.minesstetienne.ci.cn.frame.Display;
import fr.minesstetienne.ci.cn.io.NegotiableResourceReader;
import fr.minesstetienne.ci.cn.negotiation.CnCcMean;
import fr.minesstetienne.ci.cn.negotiation.CnCcMeanHeaderBased;
import fr.minesstetienne.ci.cn.negotiation.CnCcMeanURIBased;
import fr.minesstetienne.ci.cn.negotiation.NegotiableResource;
import jason.functions.Count;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author YoucTagh
 */
public class NegotiatorArtifact extends Artifact {

    private int count;
    private final int MAX_ATTEMPT_COUNT = 3;
    private HashMap<String, Set<String>> acceptedValues;

    private Display display;

    void init(String name) {
        count = MAX_ATTEMPT_COUNT;
        acceptedValues = new HashMap<>();
        System.out.println("Initialising Negotiator ...");
        display = new Display(name);

    }

    @OPERATION
    void decAttemptCount() {
        count--;
    }

    @OPERATION
    void resetAttemptCount() {
        count = MAX_ATTEMPT_COUNT;
    }

    @OPERATION
    public void getAttemptCount(OpFeedbackParam<Object> output) {
        output.set(count);
    }

    @OPERATION
    public void negotiateARepresentation(String resourceKG) throws Exception {

        NegotiableResource negotiableResource = NegotiableResourceReader.readFromString(resourceKG);
        String url = negotiableResource.getIRIAsString().get();
        String dimension = negotiableResource.getCnDimension().getIRIAsString().get();
        String protocol = negotiableResource.getCnProtocol().getIRIAsString().get();

        Set<String> acceptedValues = this.acceptedValues
                .entrySet()
                .stream()
                .filter(entry -> dimension.equals(entry.getKey()))
                .map(entry -> entry.getValue())
                .findFirst()
                .get();
        CnCcMean cnCcMean = negotiableResource.getCnCcMean();

        HttpURLConnection con = null;
        if (cnCcMean instanceof CnCcMeanHeaderBased) {
            URL obj = new URL(url);
            con = (HttpURLConnection) obj.openConnection();
            //con.setRequestProperty("User-Agent", "Mozilla/5.0");
            con.setRequestProperty(((CnCcMeanHeaderBased) cnCcMean).getUsedHeader().get(), acceptedValues.stream().reduce("", (a, b) -> a + b + ","));
        } else if (cnCcMean instanceof CnCcMeanURIBased) {
            url += "?" + ((CnCcMeanURIBased) cnCcMean).getUsedQSAParam().get() + "=" + acceptedValues;
            URL obj = new URL(url);
            con = (HttpURLConnection) obj.openConnection();
            //con.setRequestProperty("User-Agent", "Mozilla/5.0");
        } else {
            System.out.println("GET request did not work.");
            return;
        }

        int responseCode = con.getResponseCode();
        System.out.println("GET Response Code :: " + responseCode);
        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine).append("\n");

            }
            in.close();

            display.addText(response.toString());
        } else {
            display.addText("GET request did not work.");
        }

    }


    @OPERATION
    void clearNegotiation() {
        acceptedValues.clear();
    }

    @OPERATION
    void addAcceptedValue(String x, String y) {
        Set<String> acceptedValues = this.acceptedValues.getOrDefault(x, new HashSet<>());
        acceptedValues.add(y);
        this.acceptedValues.put(x, acceptedValues);
    }


}
