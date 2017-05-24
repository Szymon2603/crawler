#!/bin/bash
# Use -gt 1 to consume two arguments per pass in the loop (e.g. each
# argument has a corresponding value to go with it).
# Use -gt 0 to consume one or more arguments per pass in the loop (e.g.
# some arguments don't have a corresponding value to go with it such
# as in the --default example).
# note: if this is set to -gt 0 the /etc/hosts part is not recognized ( may be a bug )

# Based on https://stackoverflow.com/questions/192249/how-do-i-parse-command-line-arguments-in-bash

PARAMETERS_DESC="Parametry wywołania:\n\
                 -s|--skipTests - pominięcie testów podczas budowania aplikacji\n\
                 -b|--build - wywołanie maven-a w celu przebudowania projektu\n\
                 -d|--dir <ścieżka> - ścieżka do katalogu z projektem\n\
                 -n|--image-name <nazwa> - nazwa obrazu docker, jeżeli nie podano to zostanie użyta domyślna nazwa crawler-app-dev-server\n\
                 -t|--tag <nazwa> - nazwa tagu, jeżeli nie podano to zostanie użyta domyślna nazwa latest\n\
                 -f|--force - flaga do wymuszenia utworzenia nowego obrazu poprzez usunięcie istniejącego obrazu\n\
                 -ff| --double-force - flaga do wymuszenia utworzenia nowego obrazu poprzez usunięcie istniejącego obrazu oraz kontenerów"
BUILD=false
FORCE=false
DOUBLE_FORCE=false
SKIP_TESTS=false
IMAGE_NAME="crawler-server-dev"
IMAGE_TAG="latest"

while [[ $# -gt 0 ]]
do
key="$1"

case $key in
    -s|--skipTests)
    SKIP_TESTS=true
    ;;
    -b|--build)
    BUILD=true
    ;;
    -d|--dir)
    DIR="$2"
    shift
    ;;
    -n|--image-name)
    IMAGE_NAME="$2"
    shift
    ;;
    -t|--tag)
    IMAGE_TAG="$2"
    shift
    ;;
    -f|--force)
    FORCE=true
    ;;
    -ff|--double-force)
    DOUBLE_FORCE=true
    ;;
    -h|--help)
    echo "Skrypt do budowania obrazu aplikacji osadzonej w kontenerze Tomcat"
    echo "Wywołanie powinno nastąpić w katalogu głównym projektu."
    echo -e $PARAMETERS_DESC
    exit 0
    ;;
    *)
    echo "Błędny parametr wywołania! $1 $2"
    echo -e $PARAMETERS_DESC
    exit 1
    ;;
esac

shift # past argument or value
done

set -e

if ! [[ -z ${DIR+s} ]]; then
    echo "Zmieniam katalog roboczy na $DIR"
    cd DIR
fi

if $BUILD ; then
    echo "Uruchamiam polecenie mvn clean install -DskipTests=$SKIP_TESTS"
    mvn clean install -DskipTests=$SKIP_TESTS
    echo "Projekt zbudowany."
fi

if [[ "$(sudo docker images -q $IMAGE_NAME:$IMAGE_TAG 2> /dev/null)" != "" ]]; then
    if  $FORCE || $DOUBLE_FORCE ; then
        if [[ "$(sudo docker ps -q -f "ancestor=$IMAGE_NAME:$IMAGE_TAG" 2> /dev/null)" != "" ]]; then
            if $DOUBLE_FORCE ; then
                echo "Stopuję kontenery"
                sudo docker stop $(sudo docker ps -q -f "ancestor=$IMAGE_NAME:$IMAGE_TAG" 2> /dev/null)
                echo "Usuwam kontenery"
                sudo docker rm $(sudo docker ps -aq -f "ancestor=$IMAGE_NAME:$IMAGE_TAG" 2> /dev/null)
            else
                echo "Istnieją kontenery oparte o obraz."
                exit 0
            fi
        fi
        echo "Usuwam obraz."
        sudo docker rmi $(sudo docker images -q $IMAGE_NAME:$IMAGE_TAG 2> /dev/null)
    else
        echo "Obraz istnieje! Użyj flagi -f lub --force aby nadpisać obraz."
        exit 0
    fi
fi

echo "Buduję nowy obraz za pomocą komendy sudo docker build -t $IMAGE_NAME:$IMAGE_TAG ."
sudo docker build -t $IMAGE_NAME:$IMAGE_TAG .
