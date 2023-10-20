/* Believe */
acceptedValue("https://purl.org/cn/dimension/mediatype","text/html").
//acceptedValue("https://purl.org/cn/dimension/mediatype","text/plain").
acceptedValue("https://purl.org/cn/dimension/profile","http://path-to-prof.com/1").
acceptedValue("https://purl.org/cn/dimension/profile","http://path-to-prof.com/2").
acceptedValue("https://purl.org/cn/dimension/profile","text/plain").
acceptedValue("https://purl.org/cn/dimension/language","ar").

neededResource("http://localhost/thesis/youctagh/specie/abies_numidica").

/* Initial goals */
!start.

/* Initial plans */
+!start
  :
    true
  <-
     .print("I am Bob!");
     ?neededResource(R);
     getAttemptCount(C);
     !negotiate(C,R);
     .

+!negotiate(C,R)
    :
     C == 0
     <-
     .print("No more negotiation attempts");
.

+!negotiate(C,R)
    :
    C > 0
    <-
    .send(kg_mediator,askAll,canNegotiate(Who,R,Rgraph));
    .print("ask mediator to know with whom negotiate");
    .wait(5000);
    decAttemptCount;
    getAttemptCount(NewCount);
    !negotiate(NewCount,R);
.

+canNegotiate(Who,RIri,Rgraph)[source(S)]
    :
    neededResource(P) & P \== RIri
    <-
    -canNegotiate(Who,RIri,Rgraph)[source(S)];
.

+canNegotiate(Who,RIri,Rgraph)[source(S)]
    :
    neededResource(P) & P == RIri
    <-
    .print("Let's negotiate with ",Who," to get ",P);
    clearNegotiation;
    for(acceptedValue(X,Y)){
        addAcceptedValue(X,Y);
   }
    negotiateARepresentation(Rgraph);
.




{ include("$jacamo/templates/common-cartago.asl") }
{ include("$jacamo/templates/common-moise.asl") }