beehive4j
=========

### How to build locally
The following command runs local build and tests. You need an actual [Oracle Beehive Enterprise Collaboration Server](https://www.oracle.com/technetwork/middleware/beehive/overview/index.html).

    mvn clean package -settings mvn-settings.xml \
            -Dbeehive4j.test.host=https://your.beehive.com/ \
            -Dbeehive4j.test.user=user@example.com \
            -Dbeehive4j.test.password="your password"
