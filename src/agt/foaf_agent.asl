/* Believe */

/* Initial goals */
!start.

/* Initial plans */
+!start
  :
    true
  <-
    .print("I am Foaf Server agent")
    .

{ include("common/server_agent.asl") }
{ include("$jacamoJar/templates/common-cartago.asl") }