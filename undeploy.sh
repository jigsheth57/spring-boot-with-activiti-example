#!/bin/bash
CF_APP='ActivitiExample'
CF_APPS_DOMAIN='cfapps.io'

echo y|cf d -r $CF_APP-blue
echo y|cf d -r $CF_APP-green

cf ds activiti-db -f