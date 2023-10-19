package fr.minesstetienne.ci.cn;

/**
 * @author YoucTagh
 */
public class FoafArtifact extends ServerArtifact {
    private final String location = "foaf-negotiable-resource.ttl";

    void init(){
        super.init(location);

        representations.put("application/rdf+xml","representation 1");
        representations.put("text/html","representation 2");
        representations.put("text/turtle","representation 3");

    }
}
