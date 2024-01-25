FROM openjdk:11

# Set the working directory in the container
WORKDIR /usr/src/app

# Install Scala CLI and make
RUN curl -sSLf https://scala-cli.virtuslab.org/get | sh

RUN apt-get update && apt-get install -y make

# Make scala-cli available in the PATH
# ENV PATH="/root/.local/share/coursier/bin:$PATH"

# Copy the current directory contents into the container at /usr/src/app
COPY . /usr/src/app
