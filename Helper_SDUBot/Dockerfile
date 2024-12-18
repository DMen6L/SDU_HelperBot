# Step 1: Use a base image with Java installed (e.g., OpenJDK)
FROM openjdk:21

# Step 2: Set the working directory inside the container
WORKDIR /app

# Step 3: Copy the compiled .jar file (or your project files) into the container
COPY target/Helper_SDUBot-0.0.1-SNAPSHOT.jar /app/Helper_SDUBot-0.0.1-SNAPSHOT.jar

# Step 4: Expose the port that your app will run on
EXPOSE 8080

# Step 5: Define the command to run the Java application
CMD ["java", "-jar", "Helper_SDUBot-0.0.1-SNAPSHOT.jar"]