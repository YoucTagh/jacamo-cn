package fr.minesstetienne.ci.cn;

import cartago.Artifact;
import cartago.OPERATION;
import cartago.OpFeedbackParam;
import fr.minesstetienne.ci.cn.io.NegotiableResourceReader;
import fr.minesstetienne.ci.cn.negotiation.NegotiableResource;
import jason.stdlib.send;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.logging.Logger;

/**
 * @author YoucTagh
 */
public class ServerArtifact extends Artifact {

    protected HashMap<String, String> representations;

    protected NegotiableResource negotiableResource;

    void init(String location) {
        representations = new HashMap<>();

        try {
            String absoluteLocation = getAbsoluteLocation(location);
            this.negotiableResource = NegotiableResourceReader.readFromFile(absoluteLocation);
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }

    }

    @OPERATION
    public void negotiateRepresentations(String mediaType, OpFeedbackParam<Object> returnedRepresentations) {
        System.out.println("MediaType: " + mediaType);
        representations.entrySet()
                .stream()
                .filter(entry -> entry.getValue().equals(mediaType))
                .findFirst()
                .ifPresent(entry -> {
                    System.out.println(entry.getKey());
                    returnedRepresentations.set(entry.getKey());
                });
    }

    @OPERATION
    public void getNR(OpFeedbackParam<Object> output) {
        negotiableResource.getResource().getIRIAsString().ifPresent(output::set);
        System.out.println("getNR ..."+output.get());
    }

    String getAbsoluteLocation(String location) throws URISyntaxException {
        URI negotiableResource = NegotiableResource.class.getClassLoader().getResource(location).toURI();
        return Paths.get(negotiableResource).toFile().getPath();
    }
}
