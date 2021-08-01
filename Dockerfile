FROM maven:3.6.3-ibmjava-8

RUN mkdir /MyTestProject

WORKDIR /MyTestProject

COPY . .

CMD "mvn test"
