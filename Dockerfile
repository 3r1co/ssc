FROM python:alpine as mkdocs
RUN pip install mkdocs mkdocs-material

FROM mkdocs as builder
ADD . .
RUN mkdocs build

FROM nginx:alpine
COPY --from=builder ${APP_VAR}/site/ /var/www/html