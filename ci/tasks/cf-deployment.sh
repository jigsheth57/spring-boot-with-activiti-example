#!/usr/bin/env bash
set -e

# login to PWS
echo "Login to PWS ....."

cf login -a $API_ENDPOINT -u $USERNAME -p $PASSWORD -o $ORG -s $SPACE --skip-ssl-validation

DEPLOYED_VERSION_CMD=$(CF_COLOR=false cf routes | awk -v ROUTE_NAME="$CF_ROUTE_NAME" '$2 ~ ROUTE_NAME { print $4; }' | cut -d"," -f1 | cut -d"-" -f2)
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
cf p "$CF_APP_NAME-$CURRENT_VERSION" -p demo-release/activiti-example-*.jar -f git-repo/ci/tasks/manifest.yml
cf map-route "$CF_APP_NAME-$CURRENT_VERSION" $CF_APPS_DOMAIN -n $CF_ROUTE_NAME
if [ ! -z "$DEPLOYED_VERSION" ]; then
  # Unmap the route and delete
  cf unmap-route "$CF_APP_NAME-$DEPLOYED_VERSION" $CF_APPS_DOMAIN -n $CF_ROUTE_NAME
  # Scaling down
  cf scale "$CF_APP_NAME-$DEPLOYED_VERSION" -i 1
fi
