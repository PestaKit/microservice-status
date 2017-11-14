FROM mongo:latest
COPY status.json /status.json
CMD mongoimport --host mongodb --db status --collection services --type json --file /status.json