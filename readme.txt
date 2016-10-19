# Test the app

curl -u kermit:kermit -H "Content-Type: application/json" -d '{"name":"John Doe", "email": "john.doe@alfresco.com", "phoneNumber":"123456789"}' http://hireprocess.local.pcfdev.io/startProcess

curl -u kermit:kermit http://hireprocess.local.pcfdev.io/runtime/tasks

curl -u kermit:kermit -H "Content-Type: application/json" -d '{"telephoneInterviewOutcome":true}' http://hireprocess.local.pcfdev.io/completeTelephoneInterview/2501

curl -u kermit:kermit -H "Content-Type: application/json" -d '{"techOk" : "true"}' http://hireprocess.local.pcfdev.io/completeTechInterview/2501

curl -u kermit:kermit -H "Content-Type: application/json" -d '{"financialOk" : "false"}' http://hireprocess.local.pcfdev.io/completeFinancialNegotiation/2501

curl -u kermit:kermit http://hireprocess.local.pcfdev.io/tasks/2501

/history/historic-activity-instances
/runtime/process-instances
/runtime/tasks

# Deploy via Concourse
cd ci
fly login -t lite http://192.168.100.4:8080 -k
fly sp -t lite -c pipeline.yml -p activiti-example -l credentials.yml -n
fly up -t lite -p activiti-example
