const modeloEstudiante = require('../model/estudiante.model');

const resolvers = {
    Query: {
        estudiantes: async()=>{
            return await modeloEstudiante.find();
        },
        estudiante: async(root,{_id})=>{
            try {
                const estudiante = await modeloEstudiante.findById(_id);
                return estudiante; 
            }catch(error){
                return null;
            }
        }
        
    },
    Mutation: {
        crearEstudiante: async(_,{estudiante})=>{
            const nuevoEstudiante = await new modeloEstudiante(estudiante);
            await nuevoEstudiante.save();
            return nuevoEstudiante._id;
        },
        editarEstudiante: async(_,{_id,estudiante})=>{
            try {
                const estudianteBd = await modeloEstudiante.findById(_id);
                estudianteBd.documento = estudiante.documento;
                estudianteBd.codigo = estudiante.codigo;
                estudianteBd.nombre = estudiante.nombre;
                estudianteBd.programa = estudiante.programa;
                await estudianteBd.save();
                return estudianteBd._id;
            }catch(error){
                return null;
            }
        },
        eliminarEstudiante: async(_,{_id})=>{
            try {
                const estudianteBd = await modeloEstudiante.findById(_id);
                if(!estudianteBd) return null;
                await modeloEstudiante.remove(estudianteBd);
                return _id;
            }catch(error){
                return null;
            }
        }
    }
}

module.exports = resolvers;