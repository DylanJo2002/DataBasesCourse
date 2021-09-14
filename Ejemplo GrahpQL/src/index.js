const express = require('express');
const {graphqlHTTP} = require('express-graphql');
const schema = require('./graphql/schema');
const mongoose = require('mongoose');
const app = express();

app.use('/graphql',graphqlHTTP({
    schema,
    graphiql: true
}));

mongoose.connect('mongodb://localhost:27017/graphql');
mongoose.connection.on('error', error=>console.log(`Ocurrió el siguiente error ${error}`));
mongoose.connection.once('open',()=>{
    app.listen(3000, console.log(`Server running on port 3000`));

});


