#!/bin/sh

mvn install:install-file -Dfile=xp-st-sync-util-1.0.5.7-SNAPSHOT.jar -DgroupId=ua.dp.ardas.tools.sync -DartifactId=xp_st_sync -Dversion=1.0.5.7-SNAPSHOT -Dpackaging=jar -DgeneratePom=true
mvn install:install-file -Dfile=ws-st-api-1.0.5.7-SNAPSHOT.jar -DgroupId=ua.dp.ardas.tools.sync -DartifactId=ws-st-api -Dversion=1.0.5.7-SNAPSHOT -Dpackaging=jar -DgeneratePom=true
mvn install:install-file -Dfile=common-sync-util-1.0.5.7-SNAPSHOT.jar -DgroupId=ua.dp.ardas.tools.sync -DartifactId=common-sync-util -Dversion=1.0.5.7-SNAPSHOT -Dpackaging=jar -DgeneratePom=true
