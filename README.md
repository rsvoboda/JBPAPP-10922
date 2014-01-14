*JBPAPP-10940*

run test

	#install EAP 5.1.0
	export JBOSS_HOME=YOUR_PATH_TO_JBOSS_INSTANCE #eg. export JBOSS_HOME=../jboss-eap-5.1/jboss-as
	mvn clean verify


run test on server already running in different (possibly remote) jvm
	
	#required to find dependencies
	export JBOSS_HOME=YOUR_PATH_TO_JBOSS_INSTANCE #eg. export JBOSS_HOME=../jboss-eap-5.2/jboss-as
	
	start server #eg 
	mvn -Djbossas-remote-5.1 clean verify
