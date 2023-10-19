+!getNegotiableResource[source(S)]:
     S == kg_mediator
    <-
        .print("Request from KG Mediator to get NR");
        getNR(X);

        .send(S,tell,canNegotiate(X));
    .

+!getNegotiableResource[source(S)]:
     S \== kg_mediator
    <-
        .print("The agent: ",S," is not authorised to get NR.")
    .

{ include("$jacamoJar/templates/common-cartago.asl") }