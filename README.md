# jui - Project
a chic e-boutique

<img src="assets/jui.jpeg" width="700" height="350">

## Setting up Environment Variables
To set up environment variables, run the following command from the root project directory:

```
. ./setEnv.sh
```

## Local MongoDB
To start a local MongoDB instance using Docker Compose, run:

```
docker compose up mongo
```

## Local Redis
To start a local Redis instance using Docker Compose, run:

```
docker compose up redis
```

## User Management Service

### Building the App
To build the `user_management` service, follow these steps:

1. Ensure that environment variables are set from the root project directory using the `setEnv.sh` script:

   ```
   . ./setEnv.sh
   ```

2. Navigate to the `user_management` directory:

   ```
   cd user_management
   ```

3. Clean and build the project using Gradle:

   ```
   ./gradlew clean build
   ```

### Running the App in Standalone Mode
To run the app in standalone mode, use the following command:

```
./gradlew clean build bootRun
```

### Running with Docker Compose
To build and run the `user_management` service using Docker Compose from the root project directory, execute:

```
docker compose build user_management && docker compose up user_management
```
