package fr.minesstetienne.ci.cn;

/**
 * @author YoucTagh
 */
public class CNTFArtifact extends ServerArtifact {
    private final String location = "cntf-negotiable-resource.ttl";

    void init(){
        super.init(location);

        representations.put("text/turtle","representation 1");
        representations.put("text/html","representation 2");
        representations.put("text/plain","representation 3");

    }
}
