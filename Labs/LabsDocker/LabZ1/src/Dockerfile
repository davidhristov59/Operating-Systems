FROM debian:11-slim
WORKDIR /usr/share/nginx
EXPOSE 80
RUN apt-get update && apt-get install -y nginx
CMD ["nginx", "-g", "daemon off;"]