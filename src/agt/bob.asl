/* Believe */
acceptedMediaType("text/html").

neededResource("http://ci.minesstetienne.fr/ontology/cntf").

/* Initial goals */
!start.

/* Initial plans */
+!start
  :
    true
  <-
     .print("I am Bob!");
     //.send(kg_mediator,tell,canNegotiate("http://somestuff.com"));
     //?neededResource(X);
     getAttemptCount(C);
     !negotiate(C);
     .

+!negotiate(C)
    :
     C == 0
     <-
     .print("No more negotiation attempts");
.

+!negotiate(C)
    :
    C > 0
    <-
    .send(kg_mediator,askAll,canNegotiate(A,B));
    .print("ask mediator to know with whom negotiate");
    .wait(5000);
    decAttemptCount;
    getAttemptCount(NewCount);
    !negotiate(NewCount);
.

+canNegotiate(X,Y)[source(S)]
    :
    neededResource(P) & P \== Y
    <-
    -canNegotiate(X,Y)[source(S)];
.

+canNegotiate(X,Y)[source(S)]
    :
    neededResource(P) & P == Y
    <-
    .print("Let's negotiate with ",X," to get ",P);
.




{ include("$jacamo/templates/common-cartago.asl") }
{ include("$jacamo/templates/common-moise.asl") }