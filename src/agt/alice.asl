/* Believe */
acceptedMediaType("text/turtle").


/* Initial goals */
!start.

/* Initial plans */
+!start
  :
    true
  <-
     .print("I am Alice!");
    .

{ include("$jacamo/templates/common-cartago.asl") }
{ include("$jacamo/templates/common-moise.asl") }