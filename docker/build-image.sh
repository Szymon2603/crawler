#!/bin/bash
# Use -gt 1 to consume two arguments per pass in the loop (e.g. each
# argument has a corresponding value to go with it).
# Use -gt 0 to consume one or more arguments per pass in the loop (e.g.
# some arguments don't have a corresponding value to go with it such
# as in the --default example).
# note: if this is set to -gt 0 the /etc/hosts part is not recognized ( may be a bug )
PARAMETERS_DESC="Parametry wywołania:\n\
                 -s|--skipTests <true|false> - pominięcie testów podczas budowania aplikacji\n\
                 -b|--build - wywołanie maven-a w celu przebudowania projektu\n\
                 -d|--dir <ścieżka> - ścieżka do katalogu z projektem\n\
                 -n|--image-name <nazwa> - nazwa obrazu docker, jeżeli nie podano to zostanie użyta domyślna nazwa crawler-app-dev-server\n\
                 -t|--tag <nazwa> - nazwa tagu, jeżeli nie podano to zostanie użyta domyślna nazwa latest\n\
                 -f|--force - flaga do wymuszenia utworzenia nowego obrazu poprzez usunięcie istniejącego obrazu\n"
BUILD=false
FORCE=false
SKIP_TESTS="false"

while [[ $# -gt 0 ]]
do
key="$1"

case $key in
    -s|--skipTests)
    SKIP_TESTS="$2"
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

if ! [[ -z ${DIR+s} ]]; then
    echo "Change directory first $DIR"
    cd DIR
fi

if $BUILD ; then
    if [[ -z ${SKIP_TESTS+s} ]]; then
        echo "Skip tests by default!"
        SKIP_TESTS=true
    fi

    echo "Run maven install."
    mvn clean install -DskipTests=$SKIP_TESTS
    echo "Build done."
fi

if [[ -z ${IMAGE_NAME+s} ]]; then
    IMAGE_NAME="crawler-app-dev-server"
    echo "Set default image name to $IMAGE_NAME."
fi

if [[ -z ${IMAGE_TAG+s} ]]; then
    IMAGE_TAG="latest"
    echo "Set default image tag to $IMAGE_TAG."
fi

if [[ "$(sudo docker images -q $IMAGE_NAME:$IMAGE_TAG 2> /dev/null)" != "" ]]; then
    if ! $FORCE ; then
        echo "Image exist! Use force flag to override."
        exit 0
    else
        echo "Remove old image."
        sudo docker rmi $(sudo docker images -q $IMAGE_NAME:$IMAGE_TAG 2> /dev/null)
    fi
fi

echo "Building image."
sudo docker build -t $IMAGE_NAME:$IMAGE_TAG .
