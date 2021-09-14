const mongoose = require('mongoose');

const estudiante = mongoose.Schema({
    documento: Number,
    codigo: Number,
    nombre: String,
    programa: String
});

module.exports = mongoose.model('estudiante',estudiante); 