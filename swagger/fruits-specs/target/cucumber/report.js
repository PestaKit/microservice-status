$(document).ready(function() {var formatter = new CucumberHTML.DOMFormatter($('.cucumber-report'));formatter.uri("creation.feature");
formatter.feature({
  "line": 1,
  "name": "Creation of fruits",
  "description": "",
  "id": "creation-of-fruits",
  "keyword": "Feature"
});
formatter.background({
  "line": 3,
  "name": "",
  "description": "",
  "type": "background",
  "keyword": "Background"
});
formatter.step({
  "line": 4,
  "name": "there is a Fruits server",
  "keyword": "Given "
});
formatter.match({
  "location": "CreationSteps.there_is_a_Fruits_server()"
});
formatter.result({
  "duration": 102512428,
  "status": "passed"
});
formatter.scenario({
  "line": 6,
  "name": "create a fruit",
  "description": "",
  "id": "creation-of-fruits;create-a-fruit",
  "type": "scenario",
  "keyword": "Scenario"
});
formatter.step({
  "line": 7,
  "name": "I have a fruit payload",
  "keyword": "Given "
});
formatter.step({
  "line": 8,
  "name": "I POST it to the /fruits endpoint",
  "keyword": "When "
});
formatter.step({
  "line": 9,
  "name": "I receive a 201 status code",
  "keyword": "Then "
});
formatter.match({
  "location": "CreationSteps.i_have_a_fruit_payload()"
});
formatter.result({
  "duration": 100658,
  "status": "passed"
});
formatter.match({
  "location": "CreationSteps.i_POST_it_to_the_fruits_endpoint()"
});
formatter.result({
  "duration": 329221498,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "201",
      "offset": 12
    }
  ],
  "location": "CreationSteps.i_receive_a_status_code(int)"
});
formatter.result({
  "duration": 1087350,
  "status": "passed"
});
});