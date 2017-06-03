#!/bin/sh

## Generate MySQL Create Table script using CSV file headers

## Usage:
##
## sh generate_table.sh "Article Event Data.csv"

echo "create table IF NOT EXISTS events ("
head -1 $1 | sed -e 's/,/ varchar(64), /g'
echo " varchar(64) );"
