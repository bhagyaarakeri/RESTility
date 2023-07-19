# RESTility README

Once you import, for example - create your own controller by extending the GenericController.java with requestmapping(/employee),

Below are the API end points can be used using RESTility library.

GET APIs:
* Get all employee
  http://host:port/employee

* Get employees in bulk
  http://host:port/employee/{id}

POST APIs:
* Add single employee
  http://host:port/employee
  Request Body:
  ```json
  {
    "empId": 12,
    "firstName": "Sri",
    "lastName": "Ram",
    "email": "sri.ram@gmail.com",
    "phoneNo": "546.453.1565",
    "salary": 120000
  }
  ```

* Add employees in bulk
  Add single employee
  http://host:port/employee
  Request Body:
  ```json
  [
    {
        "empId": 9,
        "firstName": "Jai",
        "lastName": "Ram",
        "email": "jai.ram@gmail.com",
        "phoneNo": "324.234.7654",
        "salary": 23000
    },
    {
        "empId": 10,
        "firstName": "Devi",
        "lastName": "sri",
        "email": "sri.devi@gmail.com",
        "phoneNo": "121.222.3333",
        "salary": 220000
    }
  ]
  ```

PUT APIs:
* Update single employee
  http://host:port/employee
  Request Body:
  ```json
  {
    "empId": 12,
    "firstName": "Krishna"
  }
  ```

* Update employees in bulk
  http://host:port/employee
  Request Body:
  ```json
  [
    {
      "empId": 12,
      "firstName": "Krishna"
    }
    {
      "empId": 13,
      "salary": "560000"
    }
  ]
  ```

DELETE APIS:
* Delete single employee
  http://host:port/employee/{id}

* Delete employees in bulk
  http://host:port/employee
  Request Body:
  ```json
  [
    {
      "empId": 16,
    }
    {
      "empId": 17,
    }
  ]
  ```



  
