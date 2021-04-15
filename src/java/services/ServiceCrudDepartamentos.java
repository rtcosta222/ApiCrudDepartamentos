/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import com.google.gson.Gson;
import java.sql.SQLException;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import models.Departamento;
import repositories.RepositoryDepartamentos;

/**
 *
 * @author lscar
 */
@Path("/cruddept")
public class ServiceCrudDepartamentos {
    RepositoryDepartamentos repo;
    
    public ServiceCrudDepartamentos(){
        this.repo = new RepositoryDepartamentos();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getDepartamentos() throws SQLException{
        List<Departamento> depts = this.repo.getDepartamentos();
        Gson converter = new Gson();
        // Convertimos de objeto a string.
        String json = converter.toJson(depts);
        return json;
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public String getDepartamento(@PathParam("id") String id) throws SQLException{
        int iddept = Integer.parseInt(id);
        Departamento dept = this.repo.getDepartamento(iddept);
        Gson converter = new Gson();
        // Convertimos de objeto a string.
        String json = converter.toJson(dept);
        return json;
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/post")
    public Response insertarDepartamento(String jsondept) throws SQLException{
        Gson converter = new Gson();
        // Convertimos de string a objeto.
        Departamento dept = converter.fromJson(jsondept, Departamento.class);
        this.repo.insertarDepartamento(dept.getNum(), dept.getNom(), dept.getLoc());
        return Response.status(200).build();
    }
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/put")
    public Response modificarDepartamento(String jsondept) throws SQLException{
        Gson converter = new Gson();
        Departamento dept = converter.fromJson(jsondept, Departamento.class);
        this.repo.modificarDepartamento(dept.getNum(), dept.getNom(), dept.getLoc());
        return Response.status(200).build();
    }
    
    @DELETE
    @Path("/delete/{iddept}")
    public Response eliminarDepartamento(@PathParam("iddept") String iddept) throws SQLException{
        int id = Integer.parseInt(iddept);
        this.repo.eliminarDepartamento(id);
        return Response.status(200).build();
    }
}