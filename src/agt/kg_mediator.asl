/* Believe */
knowsServerAgent(cntf).
knowsServerAgent(foaf).
knowsServerAgent(abies_numidica).


/* Initial goals */
!start.

/* Initial plans */
+!start
  :
    true
  <-
    .print("I am the KG mediator");
    for(knowsServerAgent(X)){
        .print("Requesting NG from ",X);
        .send(X,achieve,getNegotiableResource);
    }
    .

+canNegotiate(RIRI,RKG)[source(S)]
    :
        knowsServerAgent(Y) & S == Y
    <-
        .print("Received ",RIRI," from known server ",S);
        -canNegotiate(RIRI,RKG)[source(S)];
        +canNegotiate(S,RIRI,RKG);
    .

+canNegotiate(RIRI,RKG)[source(S)]
    :
        knowsServerAgent(Y) & S \== Y
    <-
        .print("Received ",RIRI," from unknown server ",S);
        -canNegotiate(RIRI,RKG)[source(S)];
    .

{ include("$jacamo/templates/common-cartago.asl") }
{ include("$jacamo/templates/common-moise.asl") }