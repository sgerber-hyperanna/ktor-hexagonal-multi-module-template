FROM openjdk:8-jre-alpine

ADD ./build/distributions/app-1.0.tar /app
WORKDIR /app
CMD ["/app/app-1.0/bin/app"]
