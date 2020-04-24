const http = require('http');
const os = require('os');
const fs = require('fs');

const hostname = '0.0.0.0';
const port = 3000;

const greeting = process.env.GREETING || "Hello World"
const path = process.env.FILE_PATH || '/var/secret/secret.txt'

let fileContent = ""
fs.access(path, fs.F_OK, (err) => {
  if (err) {
    return
  }
  fs.readFile(path, 'utf8', function(err, contents) {
    fileContent = contents;
  });
})

const server = http.createServer((req, res) => {
  res.statusCode = 200;
  res.setHeader('Content-Type', 'text/plain');
  res.end(`${greeting} from ${os.hostname()}\n${fileContent}\n`);
});

server.listen(port, hostname, () => {
  console.log(`Server running at http://${hostname}:${port}/`);
});