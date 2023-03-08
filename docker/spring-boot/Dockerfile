FROM openjdk:17.0.2-slim

RUN addgroup --system spring && adduser --system spring

ARG DB_URL
ENV DB_URL ${DB_URL:-undefined_host_name}
ARG DB_USER
ENV DB_USER ${DB_USER:-undefined_db_user}
ARG DB_PWD
ENV DB_PWD ${DB_PWD:-undefined_db_pwd}
ARG IDM_1_URL
ENV IDM_1_URL ${IDM_1_URL:-undefined_host_name}
ARG IDM_2_URL
ENV IDM_2_URL ${IDM_2_URL:-undefined_db_user}
ARG UPDATER_HOST
ENV UPDATER_HOST ${UPDATER_HOST:-undefined_db_pwd}

COPY scripts/post-boot-scripts.sh /usr/local/bin/post-boot-scripts.sh
RUN chmod +x /usr/local/bin/post-boot-scripts.sh

ARG DEPENDENCY=target/dependency

COPY ${DEPENDENCY} /app

RUN mkdir logs && chown spring:spring logs && chown spring:spring app

USER spring:spring

EXPOSE 8080

CMD /usr/local/bin/post-boot-scripts.sh && java -cp app:app/lib/* ch.duong.jmd.#APP_ABBREVIATION.#APPLICATION_FULL_NAMEBackendApplication
