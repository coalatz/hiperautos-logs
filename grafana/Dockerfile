FROM grafana/grafana:11.4.0

COPY provisioning/ /etc/grafana/provisioning/
COPY dashboards/ /var/lib/grafana/dashboards/

ENV GF_SECURITY_ADMIN_PASSWORD=${GF_SECURITY_ADMIN_PASSWORD:-admin}
ENV GF_USERS_ALLOW_SIGN_UP=false
ENV GF_DASHBOARDS_DEFAULT_HOME_DASHBOARD_PATH=/var/lib/grafana/dashboards/hiperautos-logs.json

RUN grafana-cli plugins install marcusolsson-dynamictext-panel
