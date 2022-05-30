#!/bin/bash



read_env_local_file () {
    while read p
    do 
        export $p
    done <  ./.env-local
}

read_env_local_file
mvn spring-boot:run
