/* Believe */

/* Initial goals */
!start.

/* Initial plans */
+!start
  :
    true
  <-
    .print("I am Abies Numidica Server agent")
    .

/* Try negotiating an acceptable representation */
+!negotiateRepresentation
  :
    true
  <-
    ?acceptedMediaType(M);
    .print("MediaType: ",M);
    negotiateRepresentations(M,R);
    .print("Representations Obtained ",R);
    .


{ include("common/server_agent.asl") }
{ include("$jacamoJar/templates/common-cartago.asl") }