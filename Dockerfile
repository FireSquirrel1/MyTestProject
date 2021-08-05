FROM maven:3.6.3-ibmjava-8

COPY . /usr/src/MyTestProject
WORKDIR /usr/src/MyTestProject

CMD ["mvn", "test"]
