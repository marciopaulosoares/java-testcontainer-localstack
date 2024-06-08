#!/usr/bin/env bash
echo '### START CREATE TABLES ###'
awslocal dynamodb create-table --cli-input-json file:///var/run/config/data/01-table-playerhistory.json
echo '### END CREATE TABLES ###'