FROM library/postgres
ENV POSTGRES_USER postgres
ENV POSTGRES_PASSWORD root
ENV POSTGRES_DB delivery
# COPY src/main/resources/db/migration/V1__Delivery.sql /docker-entrypoint-initdb.d/
EXPOSE 5433

# docker build -t delivery .
# docker run --rm --name test -p 5433:5432 delivery