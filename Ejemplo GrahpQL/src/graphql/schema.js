const {makeExecutableSchema} = require('graphql-tools');
const resolvers = require('./resolver');

const typeDefs = `
  type Query {
    estudiantes: [EstudianteC]
    estudiante(_id:ID!): EstudianteC
  }

  type Mutation {
    crearEstudiante(estudiante: Estudiante!): ID
    editarEstudiante(_id:ID!, estudiante: Estudiante!): ID
    eliminarEstudiante(_id:ID!): ID
  }

  type EstudianteC {
    _id: ID
    documento: Int
    codigo: Int
    nombre: String
    programa: String
  }

  input Estudiante {
    documento: Int!
    codigo: Int!
    nombre: String!
    programa: String!
  }


`;

module.exports = makeExecutableSchema({
  typeDefs,
  resolvers
});