# Test the app

curl -u admin:admin -H "Content-Type: application/json" -d '{"name":"John Doe", "email": "john.doe@alfresco.com", "phoneNumber":"123456789"}' https://activitiexample.cfapps.io/startProcess

curl -u admin:admin -H "Content-Type: application/json" -d '{"telephoneInterviewOutcome" : "true"}' https://activitiexample.cfapps.io/completeTelephoneInterview/2501

curl -u admin:admin -H "Content-Type: application/json" -d '{"techOk" : "true"}' https://activitiexample.cfapps.io/completeTechInterview/2501

curl -u admin:admin -H "Content-Type: application/json" -d '{"financialOk" : "false"}' https://activitiexample.cfapps.io/completeFinancialNegotiation/2501

curl -u admin:admin https://activitiexample.cfapps.io/tasks/2501


# Deploy via Concourse
cd ci
fly login -t lite http://192.168.100.4:8080 -k
fly sp -t lite -c pipeline.yml -p activiti-example -l credentials.yml -n
fly up -t lite -p activiti-example
