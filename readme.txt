# Test the app

curl -u admin:admin -H "Content-Type: application/json" -d '{"name":"John Doe", "email": "john.doe@alfresco.com", "phoneNumber":"123456789"}' https://activitiexample.cfapps.io/startProcess

curl -u admin:admin -H "Content-Type: application/json" -d '{"telephoneInterviewOutcome" : "true"}' https://activitiexample.cfapps.io/completeTelephoneInterview/2501

curl -u admin:admin -H "Content-Type: application/json" -d '{"techOk" : "true"}' https://activitiexample.cfapps.io/completeTechInterview/2501

curl -u admin:admin -H "Content-Type: application/json" -d '{"financialOk" : "false"}' https://activitiexample.cfapps.io/completeFinancialNegotiation/2501

curl -u admin:admin https://activitiexample.cfapps.io/tasks/2501
