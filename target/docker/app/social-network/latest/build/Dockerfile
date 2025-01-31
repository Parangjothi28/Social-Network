# Use the official Tomcat image as the base image
# FROM tomcat:11.0
FROM tomcat@sha256:c760f04a82de4dac3e5b9392c2c4e275136149d5b99add0fe52b7c3fd97b50e6

# Define a variable for the WAR file name (with a default value)
ARG WAR_FILE=my-web-app.war

# Set the desired Java version using environment variables
ENV JAVA_HOME=/usr/local/openjdk-20
ENV PATH="$PATH:$JAVA_HOME/bin"

# Replace the following URL with the URL to the JDK binary you want to install
ENV JDK_URL=https://download.java.net/java/GA/jdk20/bdc68b4b9cbc4ebcb30745c85038d91d/36/GPL/openjdk-20_linux-x64_bin.tar.gz

# Download and install the JDK
RUN curl -L -o /tmp/jdk.tar.gz $JDK_URL && \
	tar -xvf /tmp/jdk.tar.gz -C /usr/local && \
	rm /tmp/jdk.tar.gz && \
	mv /usr/local/jdk* $JAVA_HOME

# Copy the WAR file into the Tomcat webapps directory
COPY ${WAR_FILE} /usr/local/tomcat/webapps/

# Optionally, you can provide additional configuration files or resources
# COPY your-config-file.properties /usr/local/tomcat/conf/

# Expose the Tomcat port
EXPOSE 8080

# Start Tomcat with Java 20 when the container starts
CMD ["catalina.sh", "run", "-Djava.home=$JAVA_HOME"]
