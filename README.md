# Microservice-status
## Introduction
This API has been made during the course of *Multi-Tier Application* of the HEIG-VD. This API allows to keep a track of the condition of services. The user can manually change the state of a service or can provide the address for this service to have it checked periodically.
## Starting the application
First of all, clone this repository on your computer. Before using docker to start anything, there must be a *jar* file of the springboot server in */docker/microservice-status*. If there is none or if you want to build a new jar, run the */docker/microservice-status/build-jar.sh* script. Note that **maven** must be installed on the machine in order to run the script. You must be able to run *maven* command in a terminal.<br>
Once you have the wanted jar, go to */docker* folder and run command `docker-compose up --build`. Four containers will be created : 
- **MySQL** : localhost:3306
- **phpMyAdmin** : [http://localhost:6060](http://localhost:6060)
- **angularJS front-end** : [http://localhost:4444](http://localhost:4444)
- **Springboot Server** : [http://localhost:8080](http://localhost:8080)
### Add your first service
Open your browser and access to [http://localhost:8080/api](http://localhost:8080/api).
Click on *services* to open the endpoints of */services*, then on the **Post** method. <br/>
![Post section](images/post.png)<br/>
Click on the JSON structure, under *Example Value*. This will copy the structure of the *service* as a JSON object for you to edit. <br/>
A *service* is represented by the following values :
```json
{
  "contact": "string",
  "description": "string",
  "name": "string",
  "state": "up",
  "statusAddress": "string",
  "statusPort": 0
}
```
---
#### Structure of a service
**Contact** : Name of the person to contact in case of problem <br/>
**Description** : Description of the service. <br/>
**Name\*** : Name of the service. <br/>
**State\*** : State in which the service is. Only 3 defined values are allowed : *"up"*, *"down"*, and *"maintenance"*.  <br/>
**Status address** : Address on which the service is accessible. This property is used along with the *port* to test the availability of the service automatically. If one of these properties is not set, the application will not attempt to test the service.<br/>
**Status port** : Port of the service. If you do not want to set a port, write *null* or *0* as the value of the port. <br/>
**\*** -> required<br/>

---
Change the value of the object to match your service. When you are ready to add you first service, scroll until the **Try it out!** button.<br/>
![Add your first service](images/posttryitout.png)<br/>
If the service has correctly been inserted, the server should return a *201* response code. You will also be provided the *UID* of the service to be able to manage it later.<br>

Now, browse to [http://localhost:4444](http://localhost:4444), to access the management interface.<br/>
![Management interface](images/managementinterface.png)<br/>

## API's endpoints
There are two different endpoints : */services/{serviceUID}* and */services*. The first is to access to a single service with its *UID*, while the latter is for general purposes with all services.

### /services/{serviceUID}
#### DELETE
Delete a single service identified by its *UID*.
#### GET
Get a service from its *UID*.  An object with the structure of a service seen above while be returned. The object include the **_self_** value which is a direct link in the API to access the object.
#### PATCH
Update the status of the service. It require a parameter **_state_** with a value of **_"up"_**, **_"down"_** or **_"maintenance"_** (don't forget the quotation marks !).
#### PUT
Update a service. Allow to modify an existing service with its *UID*. The structure of the JSON object is the same as seen above.
### /services
#### DELETE
Delete all services in the application. It require a passphrase set in the code to be executed. *It must be used sparingly, and for tests purpose only*.
#### GET
Get a list of all the services, with the possibility to filter services by their *state*.
#### POST
Add a new service, respecting the structure of a service.

## Further implementation details

* A service marked as automatically checked (*statusAddress & statusPort set*) will be tested every 20 seconds. The test consist of a simple TCP connection opening to the provided address and port. The check can only set two values `up` or `down`.

* If you want to mark as "in maintenance" your automatically checked service, you need to precise it manualy to the API via a patch on the */services/{serviceUID}* endpoint. Throughout the duration of the maintenance, the service won't be automatically checked anymore. To announce the end of the maintenance, you have to pass againg by the Rest API.

* The docker-compose procedure used for deploying this architecture contain a script which is managing the launch order of the containers. This script is named `/docker/microservice-status/wait_for_it.sh`and it's an open tool available on GitHub : [https://github.com/vishnubob/wait-for-it](https://github.com/vishnubob/wait-for-it), if you want more details about its utilization you can read the official [docker documentation](https://docs.docker.com/compose/startup-order/).

* The angular front-end is based on another project developped during the course *Web technonlogies* at HEIG-VD. You can find the concerned project and more details on its implementation in this repository : [https://github.com/Naewy/TWEB-project_03-angular_interface](https://github.com/Naewy/TWEB-project_03-angular_interface)


## Creators of this API

* Alain Hardy : [https://github.com/AlainHardy](https://github.com/AlainHardy)
* Matthieu Chatelan : [https://github.com/Newtt](https://github.com/Newtt)
* Lara Chauffoureaux : [https://github.com/Naewy](https://github.com/Naewy)


## Sources

* [https://docs.docker.com/compose/startup-order/](https://docs.docker.com/compose/startup-order/)
* [https://docs.docker.com/compose/](https://docs.docker.com/compose/)
* [https://projects.spring.io/spring-boot/](https://projects.spring.io/spring-boot/)
* [https://swagger.io/](https://swagger.io/)
* [https://editor.swagger.io//#/](https://editor.swagger.io//#/)
* [https://cucumber.io/](https://cucumber.io/)
* [https://en.wikipedia.org/wiki/List_of_HTTP_status_codes](https://en.wikipedia.org/wiki/List_of_HTTP_status_codes)
* [http://www.baeldung.com/exception-handling-for-rest-with-spring](http://www.baeldung.com/exception-handling-for-rest-with-spring)
* [https://github.com/vishnubob/wait-for-it](https://github.com/vishnubob/wait-for-it)



