#!/bin/sh
# Script para instalar o projeto $
# $Author: Thiago Soares $
# $Date: 2010-05-10 20:00:00 -0300 $
# $Revision: 2 $

# chkconfig: 2345 100 10
# description: 

set -e


#Declaracao das variaveis
projectName=sispat

case "$1" in
  install)
    echo "Instalando..." 
    mvn -cpu install -DskipTests
    ;;
  deploy)
    echo "Instalando localmente..." 
    mvn -cpu install -DskipTests
    echo "Fazendo deploy remoto..."
    scp $projectName-ear/target/$projectName.ear   jboss@desenv:homologacao/deploy
    ;;
  c-deploy)
    echo "Limpando e instalando"
    mvn -cpu clean install -DskipTests
    scp $projectName-ear/target/$projectName.ear   jboss@desenv:homologacao/deploy
    ;;
  re-deploy)
    scp $projectName-ear/target/$projectName.ear   jboss@desenv:homologacao/deploy
    ;;
  *)
    echo "Usage: {install|deploy|c-deploy|re-deploy}"
    exit 1
    ;;
esac

exit 0
