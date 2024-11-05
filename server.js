const express = require('express');
const bodyParser = require('body-parser');
const fs = require('fs');
const path = require('path');

const app = express();
const PORT = 3000;

app.use(bodyParser.json());

const jsonFilePath = path.join("src\assets\dat.json", 'dat.json');

// Get data
app.get('/api/items', (req, res) => {
  fs.readFile(jsonFilePath, 'utf8', (err, data) => {
    if (err) {
      return res.status(500).send('Error reading file.');
    }
    res.json(JSON.parse(data));
  });
});

// Post data
app.post('/api/items', (req, res) => {
  fs.readFile(jsonFilePath, 'utf8', (err, data) => {
    if (err) {
      return res.status(500).send('Error reading file.');
    }
    const jsonData = JSON.parse(data);
    jsonData.items.push(req.body);
    fs.writeFile(jsonFilePath, JSON.stringify(jsonData, null, 2), (err) => {
      if (err) {
        return res.status(500).send('Error writing file.');
      }
      res.status(201).send(req.body);
    });
  });
});

app.listen(PORT, () => {
  console.log(`Server running on http://localhost:${PORT}`);
});
