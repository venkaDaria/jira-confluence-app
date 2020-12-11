# Jira API app

## Docker

### Simple start

Run this (or just add `jira.cert` into `config` folder):
    
    openssl s_client -connect ${JIRA_HOST}:${JIRA_PORT} </dev/null \
    | sed -ne '/-BEGIN CERTIFICATE-/,/-END CERTIFICATE-/p' > config/jira.cert

Also, you need to add `properties.env` file into `config` folder.

And then run: `./gradlew build && docker-compose up` .

For Windows: `gradlew build && docker-compose up`

***

To rebuild images you must use `docker-compose build` or 
`docker-compose up --build`.

### Where to see

- UI: `localhost:3000`;
- Server: `localhost:8080`;
- SonarQube: `localhost:9000`.

### Properties

You can see properties for `.env` file [below](#How to start).

Other properties can be changed using `application=[profile].yml`.

### SonarQube

Run with Docker (if it does not run with `docker-compose`):
        
    docker run --publish 9090:9000 sonarqube
    
Then:
    
    ./gradlew sonarqube    
   
Then go to [SonarQube page](http://127.0.0.1:9000).
      
## Without Docker

### How to start

- Start server with following environment properties:

    - mongo_host `(dev-profile 127.0.0.1)`;
    - mongo_port `(dev-profile 27017)`;
    - mongo_database `(dev-profile metrics)`;
    - MONGO_INITDB_DATABASE `(dev-profile admin)`;
    
    **
    
    - MONGO_INITDB_ROOT_USERNAME;
    - MONGO_INITDB_ROOT_PASSWORD;
    - jira_people;
    - jira_host; 
    - jira_port; 
    - jira_board `(6260)`;
    - okta_password;
    - okta_username;
    - jira_project;
    - confluence_url; `(https://<host>:<port>)`
    - confluence_parent_page; `(id from Page Information)`
    - confluence_space; `(e.g., HEL from /rest/api/space/HEL)`
    

- For dev instead of `okta_username` and `okta_password` you can use:

    - jira_password;
    - jira_username;
    - confluence_password;
    - confluence_username;

- Start client with this command:

      npm install && npm start

### JIRA and SSL

Add a certificate to a Java trust store for JIRA (with Unix script):

    sudo ./load_cert.sh <host> <port> <Java trust store> <Java trust store password>
     
Where:
    
    Java trust store = $JAVA_HOME/lib/security/cacerts
    Java trust store password = changeit 
       
Or you can use [KeyStore Explorer](http://keystore-explorer.org/).
Just use the "Examine SSL" feature and then click on "Import".
