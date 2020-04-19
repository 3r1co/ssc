FROM python as mkdocs
RUN pip install mkdocs mkdocs-material

WORKDIR /src

ADD . .
RUN mkdocs build

FROM nginx:alpine
COPY --from=mkdocs /src/site/ /usr/share/nginx/html 
