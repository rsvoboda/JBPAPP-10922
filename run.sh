#!/bin/bash

TMP_DIR=/tmp
PROFILE=default


init_variables() {
	TMP_DIR=/tmp
	PROFILE=default
	export EAP_VERSION=5.3.0.ER2
	export EAP_VERSION_MAJOR=$(echo $EAP_VERSION | awk -F"." '{ print $1 "." $2 }' )

	export EAP_INSTALLATION_ZIP=$ARTIFACTS_DIR/jboss-eap-noauth-${EAP_VERSION}.zip 
	export EAP_CXF_INSTALLATION_ZIP=$ARTIFACTS_DIR/jboss-ep-ws-cxf-${EAP_VERSION}-installer.zip

	export JBOSS_HOME=$(pwd)/jboss-eap-${EAP_VERSION_MAJOR}/jboss-as
}


usage() {
	echo "Usage:"
	echo "$(basename $0) verify ARTIFACTS_DIR (eg. $(basename $0) /home/development/artifacts/JBEAP-5.1.0.GA/) "
}

verify_arguments() {
	EAP_INSTALLATION_ZIP=$1
	if [ ! -f $EAP_INSTALLATION_ZIP ]; then
		echo "EAP installation zip not found $EAP_INSTALLATION_ZIP"
		exit 1
	fi
}

delete_as() {
	EAP_INSTALL_DIR=$(dirname $JBOSS_HOME)
	rm -rf $EAP_INSTALL_DIR
}

install_as() {
	echo "Install $JBOSS_HOME from $EAP_INSTALLATION_ZIP"
	
	EAP_INSTALL_DIR=$(dirname $JBOSS_HOME)
	unzip -q -d $(dirname $EAP_INSTALL_DIR) $EAP_INSTALLATION_ZIP
	
	cp -r  $JBOSS_HOME/server/all/deploy/juddi-service.sar $JBOSS_HOME/server/default/deploy
}



start_as() {
	$JBOSS_HOME/bin/run.sh &
	sleep 25
}

install_server() {
	delete_as
	install_as
}

verify() {
	ARTIFACTS_DIR=$1
	verify_arguments $@
	init_variables
	# install unpatched server
	install_server $EAP_INSTALLATION_ZIP
	# run test to see success
	(cd test; mvn -Djbossas-managed-5.1 clean verify)
	if [ ! $? == 0 ]; then
		echo "Verification failed - expected test pass"
		exit 1
	fi
	(cd test; mvn clean)
	delete_as
	echo "JBPAPP-10922 was verified"
}


if [[ "$1" == "" ]]; then
	usage
	exit 1
fi

$1 ${*:2}
