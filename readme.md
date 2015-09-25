# RSI Hackathon Android Application Bundle

## Directory contents
ApplicationStub contains the android app source.

RESTServer contains the backend server source.

## Gradle Instructions
Please extract the .gradle zip into your Window's user folder.

This will let gradle use an existing cache to download dependencies so that the provided internet connection isn't overwhelmed.

### Instructions for the Android app
Import the ApplicationStub folder as an android app.

There should be a run configuration already created for the app module.

### Instructions for the backend server
Import the RESTServer as a gradle project, made available by buildship.

Run the bootRun gradle task.
