package fr.minesstetienne.ci.cn;

import cartago.Artifact;
import cartago.OPERATION;
import cartago.OpFeedbackParam;
import fr.minesstetienne.ci.cn.io.NegotiableResourceReader;
import fr.minesstetienne.ci.cn.io.NegotiableResourceWriter;
import fr.minesstetienne.ci.cn.negotiation.NegotiableResource;
import jason.stdlib.send;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.logging.Logger;

/**
 * @author YoucTagh
 */
public class ServerArtifact extends Artifact {

    protected HashMap<String, String> representations;

    protected NegotiableResource negotiableResource;

    private String absoluteLocation;

    void init(String location) {
        representations = new HashMap<>();

        try {
            this.absoluteLocation = getAbsoluteLocation(location);
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
    public void getNR(OpFeedbackParam<Object> resourceIRI,OpFeedbackParam<Object> graph) {
        try {
            String content = Files.readString(new File(this.absoluteLocation).toPath());
            graph.set(content);
            negotiableResource.getIRIAsString().ifPresent(resourceIRI::set);
            System.out.println("getNR ..." + resourceIRI.get());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    String getAbsoluteLocation(String location) throws URISyntaxException {
        URI negotiableResource = NegotiableResource.class.getClassLoader().getResource(location).toURI();
        return Paths.get(negotiableResource).toFile().getPath();
    }
}
