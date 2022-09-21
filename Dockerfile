# Dockerfile for the latest Ubuntu container.

FROM ubuntu:latest

# Installing Java in the Ubuntu container.

RUN apt-get update -y && apt-get install default-jre -y \
    apt-get install --no-install-recommends -y

