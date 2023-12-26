# Use the official MySQL 8.0 image from Docker Hub
FROM mysql:8.0
ENV MYSQL_ROOT_PASSWORD=pwd123
EXPOSE 3306
VOLUME mysql-data:/var/lib/mysql
# Set the default command to run when the container starts
CMD ["mysqld"]
