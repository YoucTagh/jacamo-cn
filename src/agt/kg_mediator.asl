/* Believe */
knowsServerAgent(cntf).
knowsServerAgent(foaf).

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

+canNegotiate(X)[source(S)]
    :
        knowsServerAgent(Y) & S == Y
    <-
        .print("Received ",X," from known server ",S);
        -canNegotiate(X)[source(S)];
        +canNegotiate(S,X);
    .

+canNegotiate(X)[source(S)]
    :
        knowsServerAgent(Y) & S \== Y
    <-
        .print("Received ",X," from unknown server ",S);
        -canNegotiate(X)[source(S)];
    .

{ include("$jacamo/templates/common-cartago.asl") }
{ include("$jacamo/templates/common-moise.asl") }