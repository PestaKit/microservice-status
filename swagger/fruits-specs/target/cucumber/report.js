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
  "duration": 272907010,
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
  "duration": 154904,
  "status": "passed"
});
formatter.match({
  "location": "CreationSteps.i_POST_it_to_the_fruits_endpoint()"
});
formatter.result({
  "duration": 300764413,
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
  "duration": 1707296,
  "error_message": "java.lang.AssertionError: expected:\u003c201\u003e but was:\u003c0\u003e\n\tat org.junit.Assert.fail(Assert.java:88)\n\tat org.junit.Assert.failNotEquals(Assert.java:834)\n\tat org.junit.Assert.assertEquals(Assert.java:645)\n\tat org.junit.Assert.assertEquals(Assert.java:631)\n\tat io.avalia.fruits.api.spec.steps.CreationSteps.i_receive_a_status_code(CreationSteps.java:64)\n\tat ✽.Then I receive a 201 status code(creation.feature:9)\n",
  "status": "failed"
});
});