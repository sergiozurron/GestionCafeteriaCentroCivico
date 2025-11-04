package com.grupoms.app.negocio.ingrediente;

public class TIngrediente {

        //ATRIBUTOS
        private Integer ID;
        private String Nombre;
        private Double Precio;

        //GETTERS
        public Integer getID(){return ID;}
        public Double getPrecio(){return Precio;}
        public String getNombre(){return Nombre;}

        //SETTERS
        public void setID(Integer id){
                this.ID=id;
        }

        public void setPrecio(Double precio){
                this.Precio = precio;
        }

        public void setNombre(String Nombre){
                this.Nombre = Nombre;
        }

}
