# Test the app

curl -u admin:admin -H "Content-Type: application/json" -d '{"name":"John Doe", "email": "john.doe@alfresco.com", "phoneNumber":"123456789"}' http://activitiexample-dev.cfapps.io/startProcess

curl -u admin:admin -H "Content-Type: application/json" -d '{"telephoneInterviewOutcome" : "true"}' http://activitiexample-dev.cfapps.io/completeTelephoneInterview/2501

curl -u admin:admin -H "Content-Type: application/json" -d '{"techOk" : "true"}' http://activitiexample-dev.cfapps.io/completeTechInterview/2501

curl -u admin:admin -H "Content-Type: application/json" -d '{"financialOk" : "false"}' http://activitiexample-dev.cfapps.io/completeFinancialNegotiation/2501

curl -u admin:admin http://activitiexample-dev.cfapps.io/tasks/2501

/history/historic-activity-instances
/runtime/process-instances
/runtime/tasks

# Deploy via Concourse
cd ci
fly login -t lite http://192.168.100.4:8080 -k
fly sp -t lite -c pipeline.yml -p activiti-example -l credentials.yml -n
fly up -t lite -p activiti-example
