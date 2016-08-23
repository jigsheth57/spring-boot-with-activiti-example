#!/bin/bash
CF_APP='ActivitiExample'
CF_APPS_DOMAIN='cfapps.io'
ROUTE_NAME=$CF_APP
mvn clean package
if [ "$?" -ne "0" ]; then
  exit $?
fi

DEPLOYED_VERSION_CMD=$(CF_COLOR=false cf routes | awk '$2=="ActivitiExample" { print $4; }' | cut -d"," -f1 | cut -d"-" -f2)
DEPLOYED_VERSION="$DEPLOYED_VERSION_CMD"
echo "Deployed Version: $DEPLOYED_VERSION"
CURRENT_VERSION="blue"
if [ ! -z "$DEPLOYED_VERSION" -a "$DEPLOYED_VERSION" == "blue" ]; then
  echo "Setting current version to green!"
  CURRENT_VERSION="green"
fi
echo "Current Version: $CURRENT_VERSION"
# push a new version and map the route
cf cs p-mysql 100mb activiti-db
cf p "$CF_APP-$CURRENT_VERSION"
cf map-route "$CF_APP-$CURRENT_VERSION" $CF_APPS_DOMAIN -n $ROUTE_NAME
if [ ! -z "$DEPLOYED_VERSION" ]; then
  # Unmap the route and delete
  cf unmap-route "$CF_APP-$DEPLOYED_VERSION" $CF_APPS_DOMAIN -n $ROUTE_NAME
  # Scaling down
  cf scale "$CF_APP-$DEPLOYED_VERSION" -i 1
fi
