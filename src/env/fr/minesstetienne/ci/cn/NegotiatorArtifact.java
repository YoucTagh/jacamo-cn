package fr.minesstetienne.ci.cn;

import cartago.Artifact;
import cartago.OPERATION;
import cartago.OpFeedbackParam;
import jason.functions.Count;

/**
 * @author YoucTagh
 */
public class NegotiatorArtifact extends Artifact {

    private int count;
    private final int MAX_ATTEMPT_COUNT = 3;

    void init() {
        count = MAX_ATTEMPT_COUNT;
        System.out.println("Initialising Negotiator ...");
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
}
